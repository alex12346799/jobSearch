package com.example.jobsearch.storage.web;

import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.storage.application.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/me/avatar")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AvatarResponse upload(@RequestPart("file") MultipartFile file, @AuthenticationPrincipal Jwt jwt) {
        return service.upload(userId(jwt), file);
    }

    @GetMapping
    public ResponseEntity<byte[]> get(@AuthenticationPrincipal Jwt jwt) {
        var avatar = service.get(userId(jwt));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(avatar.contentType()))
                .cacheControl(CacheControl.noStore()).body(avatar.bytes());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Jwt jwt) {
        service.delete(userId(jwt));
    }

    private long userId(Jwt jwt) {
        try { return Long.parseLong(jwt.getSubject()); }
        catch (RuntimeException exception) { throw new AuthUnauthorizedException(); }
    }
}
