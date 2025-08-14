package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;

import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.ResumeMapper;
import com.example.jobsearch.mapper.SocialLinksMapper;
import com.example.jobsearch.model.*;
import com.example.jobsearch.repository.*;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final WorkExperienceInfoRepository workExperienceInfoRepository;
    private final EducationInfoRepository educationInfoRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SocialLinksRepository socialLinksRepository;
    @Override
    public List<ResumeResponseDto> getAllResumes() {
        List<Resume> resumes = resumeRepository.findAll();
        return resumes.stream()
                .map(e -> ResumeResponseDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .salary(e.getSalary())
                        .isActive(e.isActive())
                        .createdDate(e.getCreatedDate())
                        .updateDate(e.getUpdateDate())
                        .applicantName(userRepository.findNameById(e.getApplicant().getId()))
                        .categoryName(categoryRepository.findNameById(e.getCategory().getId()))
                        .build())
                .toList();
    }


    @Override
    public Resume getById(long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Вот это " + id + " резюме не найдено"));
    }

    @Override
    public List<Resume> getAllByApplicantId(int applicantId) {
        return resumeRepository.findAllByApplicantId(applicantId);
    }

    @Override
    public Resume create(ResumeRequestDto dto) {
        Optional<User> appllicant = userRepository.findById(dto.getApplicantId());
        if (appllicant.isEmpty()) {
            throw new NotFoundException("Пользователь с данным " + dto.getApplicantId() + " Id не найден");
        }
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()-> new NotFoundException( "Категория с id " + dto.getCategoryId() + " не найдена"));
        if (category == null) {
            throw new NotFoundException("Категория с id " + dto.getCategoryId() + " не найден");
        }

        Resume resume = ResumeMapper.fromDto(dto);
        resume.setApplicant(appllicant.get());
        resume.setCategory(category);


        if (resume.getEducationInfoList() != null) {
            for (EducationInfo educationInfo : resume.getEducationInfoList()) {
                educationInfo.setResume(resume);
            }
        }
        if (resume.getWorkExperienceInfoList() != null) {
            for (WorkExperienceInfo workExperienceInfo : resume.getWorkExperienceInfoList()) {
                workExperienceInfo.setResume(resume);
            }


        }
        if (dto.getSocialLinkRequestDto() != null) {
            SocialLinks socialLinks = SocialLinksMapper.fromDto(dto.getSocialLinkRequestDto());
            socialLinks.setResume(resume);
            resume.setSocialLinks(socialLinks);
        }


        return resumeRepository.save(resume);

    }


    @Override
    public void update(Resume resume, long id, Authentication auth) {
        String email = auth.getName();


            User user = userRepository.findByEmail(email)
                    .orElseThrow(()->new NotFoundException("Пользователь не найден"));



        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Резюме не найдено"));
        if (existingResume.getApplicant().getId() != user.getId()) {
            throw new NotFoundException("У вас нет прав для изменения этого резюме");
        }
        existingResume.setName(resume.getName());
        existingResume.setCategory(resume.getCategory());
        existingResume.setSalary(resume.getSalary());
        existingResume.setActive(resume.isActive());
        existingResume.setUpdateDate(resume.getUpdateDate());
        resumeRepository.save(existingResume);

    }


    @Override
    public void delete(long id, Authentication auth) {
        String email = auth.getName();


//        User user = userRepository.findByEmail(email);
//        if(user == null) {
//                throw  new NotFoundException("Пользователь не найден");
//        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Резюме не найдено"));
        if (existingResume.getApplicant().getId() != user.getId()) {
            throw new NotFoundException("У вас нет прав для удаления этого резюме");
        }

        resumeRepository.deleteById(id);
    }


}
