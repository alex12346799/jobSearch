package com.example.jobsearch.service.impl;

import com.example.jobsearch.dto.EducationInfoRequestDto;
import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;

import com.example.jobsearch.dto.WorkExperienceInfoRequestDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.EducationInfoMapper;
import com.example.jobsearch.mapper.ResumeMapper;
import com.example.jobsearch.mapper.SocialLinksMapper;
import com.example.jobsearch.mapper.WorkExperienceInfoMapper;
import com.example.jobsearch.model.*;
import com.example.jobsearch.repository.*;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;

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
//    @Override
//    public List<ResumeResponseDto> getAllSortedResume(String sortedValue) {
//        List<Resume> resumes = resumeRepository.findAll(getSortMethod(sortedValue));
//        return resumes.stream()
//                .map(e -> ResumeResponseDto.builder()
//                        .id(e.getId())
//                        .name(e.getName())
//                        .salary(e.getSalary())
//                        .isActive(e.isActive())
//                        .createdDate(e.getCreatedDate())
//                        .updateDate(e.getUpdateDate())
//                        .applicantName(userRepository.findNameById(e.getApplicant().getId()))
//                        .categoryName(categoryRepository.findNameById(e.getCategory().getId()))
//                        .build())
//                .toList();
//    }
@Override
public List<ResumeResponseDto> getAllSortedAndPagedResume(Pageable pageable) {
        Page<Resume> resumes = resumeRepository.findAll(pageable);
        if (pageable.getPageNumber()>=resumes.getTotalPages()&& resumes.getTotalPages()>0) {
            pageable = PageRequest.of(resumes.getTotalPages()-1, pageable.getPageSize(), pageable.getSort());
            resumes = resumeRepository.findAll(pageable);
        }
       return resumes.getContent().stream()
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
    public List<Resume> findByApplicantId(Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Пользователь не найден"));

        return resumeRepository.findByApplicant(user);
    }

//    @Override
//    public Resume create(ResumeRequestDto dto, Authentication auth) {
//     String email = auth.getName();
//
//        User applicant = userRepository.findByEmail(email)
//                .orElseThrow(() -> new NotFoundException("Пользователь с данным " + email + "  не найден"));
//
//        Category category = categoryRepository.findById(dto.getCategoryId())
//                .orElseThrow(() -> new NotFoundException("Категория с id " + dto.getCategoryId() + " не найдена"));
//
//
//        Resume resume = ResumeMapper.fromDto(dto);
//        resume.setApplicant(applicant);
//        resume.setCategory(category);
//
//        Resume resumeFromDb = resumeRepository.saveAndFlush(resume);
//
//
//        if (dto.getEducationInfoList() != null) {
//            for (EducationInfoRequestDto educationInfoDto : dto.getEducationInfoList()) {
//                EducationInfo educationInfo = EducationInfoMapper.fromDto(educationInfoDto);
//                educationInfo.setResume(resumeFromDb);
//                educationInfoRepository.save(educationInfo);
//            }
//        }
//
//        if (dto.getWorkExperienceInfoList() != null) {
//            for (WorkExperienceInfoRequestDto workExperienceDto : dto.getWorkExperienceInfoList()) {
//                WorkExperienceInfo workExperienceInfo = WorkExperienceInfoMapper.fronDto(workExperienceDto);
//                workExperienceInfo.setResume(resumeFromDb);
//                workExperienceInfoRepository.save(workExperienceInfo);
//            }
//        }
//
//        if (dto.getSocialLinkRequestDto() != null) {
//            SocialLinks socialLinks = SocialLinksMapper.fromDto(dto.getSocialLinkRequestDto());
//            socialLinks.setResume(resumeFromDb);
//            socialLinksRepository.save(socialLinks);
//        }
//
//          return resumeFromDb;
//
//        }













    @Override
    public Resume create(ResumeRequestDto dto, Authentication auth) {
        String email = auth.getName();

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь с email " + email + " не найден"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Категория с id " + dto.getCategoryId() + " не найдена"));


        Resume resume = ResumeMapper.fromDto(dto);
        resume.setApplicant(applicant);
        resume.setCategory(category);


        Resume resumeFromDb = resumeRepository.saveAndFlush(resume);


        if (dto.getEducationInfoList() != null) {
            for (EducationInfoRequestDto eduDto : dto.getEducationInfoList()) {
                EducationInfo educationInfo = EducationInfoMapper.fromDto(eduDto);
                educationInfo.setResume(resumeFromDb);
                educationInfoRepository.save(educationInfo);
            }
        }


        if (dto.getWorkExperienceInfoList() != null) {
            for (WorkExperienceInfoRequestDto workDto : dto.getWorkExperienceInfoList()) {
                WorkExperienceInfo workExperienceInfo = WorkExperienceInfoMapper.fromDto(workDto);
                workExperienceInfo.setResume(resumeFromDb);
                workExperienceInfoRepository.save(workExperienceInfo);
            }
        }


        if (dto.getSocialLinkRequestDto() != null) {
            SocialLinks socialLinks = ResumeMapper.fromDto(dto.getSocialLinkRequestDto());
            socialLinks.setResume(resumeFromDb);
            socialLinksRepository.save(socialLinks);
        }

        return resumeFromDb;
    }




    @Override
    public void update(Resume resume, long id, Authentication auth) {
        String email = auth.getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));


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
