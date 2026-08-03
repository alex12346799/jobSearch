package com.example.jobsearch.storage.web;

import com.example.jobsearch.storage.domain.StoredFilePurpose;
import com.example.jobsearch.storage.infrastructure.*;
import com.example.jobsearch.user.domain.*;
import com.example.jobsearch.user.persistence.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "storage.minio.avatar-max-size=1024")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AvatarIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtEncoder jwtEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private StoredFileRepository storedFileRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @MockitoBean private ObjectStorage objectStorage;

    private final Map<String, ObjectStorage.StoredObject> objects = new ConcurrentHashMap<>();
    private User user;

    @BeforeEach
    void setUp() {
        objects.clear();
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        Role role = roleRepository.findByName(RoleName.APPLICANT).orElseThrow();
        user = new User(); user.setName("Avatar"); user.setSurname("Owner");
        user.setEmail("avatar." + suffix + "@example.com"); user.setPassword(passwordEncoder.encode("password"));
        user.setRole(role); user.setEnabled(true); user = userRepository.saveAndFlush(user);
        doAnswer(invocation -> {
            objects.put(invocation.getArgument(0), new ObjectStorage.StoredObject(
                    invocation.getArgument(1), invocation.getArgument(2)));
            return null;
        }).when(objectStorage).put(anyString(), any(byte[].class), anyString());
        when(objectStorage.get(anyString())).thenAnswer(invocation -> {
            var value = objects.get(invocation.getArgument(0));
            if (value == null) throw new StorageObjectNotFoundException(new IllegalStateException());
            return value;
        });
        doAnswer(invocation -> { objects.remove(invocation.getArgument(0)); return null; })
                .when(objectStorage).delete(anyString());
    }

    @Test
    void uploadsJpegAndPngWithServerKeysAndDoesNotExposeKey() throws Exception {
        String jpegResponse = mockMvc.perform(multipart("/api/v1/users/me/avatar")
                        .file(image("profile.exe.jpg", "image/jpeg", "jpg")).header("Authorization", bearer()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.contentType").value("image/jpeg"))
                .andExpect(jsonPath("$.objectKey").doesNotExist()).andReturn().getResponse().getContentAsString();
        assertThat(jpegResponse).doesNotContain("avatars/");
        String firstKey = storedFileRepository.findByOwnerIdAndPurpose(user.getId(), StoredFilePurpose.AVATAR)
                .orElseThrow().getObjectKey();
        assertThat(firstKey).matches("avatars/" + user.getId() + "/[0-9a-f-]{36}");

        mockMvc.perform(multipart("/api/v1/users/me/avatar")
                        .file(image("anything.png", "image/png", "png")).header("Authorization", bearer()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.contentType").value("image/png"));
        String secondKey = storedFileRepository.findByOwnerIdAndPurpose(user.getId(), StoredFilePurpose.AVATAR)
                .orElseThrow().getObjectKey();
        assertThat(secondKey).isNotEqualTo(firstKey);
        assertThat(objects).doesNotContainKey(firstKey).containsKey(secondKey);
    }

    @Test
    void getsDeletesAndThenReturnsNotFound() throws Exception {
        byte[] png = imageBytes("png");
        mockMvc.perform(multipart("/api/v1/users/me/avatar")
                        .file(new MockMultipartFile("file", "avatar.png", "image/png", png))
                        .header("Authorization", bearer())).andExpect(status().isCreated());
        mockMvc.perform(get("/api/v1/users/me/avatar").header("Authorization", bearer()))
                .andExpect(status().isOk()).andExpect(header().string("Content-Type", "image/png"))
                .andExpect(content().bytes(png));
        mockMvc.perform(delete("/api/v1/users/me/avatar").header("Authorization", bearer()))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/v1/users/me/avatar").header("Authorization", bearer()))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/users/me/avatar").header("Authorization", bearer()))
                .andExpect(status().isNotFound());
    }

    @Test
    void requiresJwtAndIgnoresForeignUserSelection() throws Exception {
        mockMvc.perform(multipart("/api/v1/users/me/avatar").file(image("avatar.png", "image/png", "png")))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(multipart("/api/v1/users/me/avatar").file(image("avatar.png", "image/png", "png"))
                        .param("userId", "1").header("Authorization", bearer()))
                .andExpect(status().isCreated());
        assertThat(storedFileRepository.existsByOwnerIdAndPurpose(user.getId(), StoredFilePurpose.AVATAR)).isTrue();
    }

    @Test
    void rejectsEmptyOversizeWrongTypeFakeJpegAndSvg() throws Exception {
        assertBad(new MockMultipartFile("file", "empty.jpg", "image/jpeg", new byte[0]), 400);
        assertBad(new MockMultipartFile("file", "large.jpg", "image/jpeg", new byte[1025]), 413);
        assertBad(new MockMultipartFile("file", "image.png", "text/plain", imageBytes("png")), 400);
        assertBad(new MockMultipartFile("file", "fake.jpg", "image/jpeg", "not jpeg".getBytes()), 400);
        assertBad(new MockMultipartFile("file", "image.svg", "image/svg+xml",
                "<svg xmlns='http://www.w3.org/2000/svg'/>".getBytes()), 400);
    }

    @Test
    void profileReportsAvailabilityWithoutInternalKey() throws Exception {
        mockMvc.perform(get("/api/v1/users/me").header("Authorization", bearer()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.avatarAvailable").value(false))
                .andExpect(jsonPath("$.avatar").doesNotExist());
        mockMvc.perform(multipart("/api/v1/users/me/avatar").file(image("avatar.png", "image/png", "png"))
                        .header("Authorization", bearer())).andExpect(status().isCreated());
        mockMvc.perform(get("/api/v1/users/me").header("Authorization", bearer()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.avatarAvailable").value(true));
    }

    private void assertBad(MockMultipartFile file, int status) throws Exception {
        mockMvc.perform(multipart("/api/v1/users/me/avatar").file(file).header("Authorization", bearer()))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isEqualTo(status));
    }

    private MockMultipartFile image(String name, String contentType, String format) throws Exception {
        return new MockMultipartFile("file", name, contentType, imageBytes(format));
    }

    private byte[] imageBytes(String format) throws Exception {
        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, format, output);
        return output.toByteArray();
    }

    private String bearer() {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder().subject(user.getId().toString()).claim("email", user.getEmail())
                .claim("role", "APPLICANT").issuedAt(now).expiresAt(now.plusSeconds(300))
                .id(UUID.randomUUID().toString()).build();
        return "Bearer " + jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }
}
