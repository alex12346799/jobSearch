package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.EducationInfoDao;
import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dao.WorkExperienceInfoDao;
import com.example.jobsearch.dao.impl.CategoryDao;
import com.example.jobsearch.dao.impl.RespondentApplicantDao;
import com.example.jobsearch.dao.impl.SocialLinksDao;
import com.example.jobsearch.dto.RespondentApplicantResponseDto;
import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.dto.ResumeResponseDto;
import com.example.jobsearch.exceptions.ApplicantNotFoundException;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.ResumeMapper;
import com.example.jobsearch.model.Category;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.SocialLinks;
import com.example.jobsearch.model.User;
import com.example.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final WorkExperienceInfoDao workExperienceInfoDao;
    private final EducationInfoDao educationInfoDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final SocialLinksDao socialLinksDao;

    @Override
    public List<ResumeResponseDto> getAllResumes() {
        List<Resume> resumes = resumeDao.findAll();
        return resumes.stream()
                .map(e -> ResumeResponseDto.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .salary(e.getSalary())
                        .isActive(e.isActive())
                        .createdDate(e.getCreatedDate())
                        .updateDate(e.getUpdateDate())
                        .applicantName(userDao.findNameById(e.getApplicantId()))
                        .categoryName(categoryDao.findNameById(e.getCategoryId()))
                        .build())
                .toList();
    }


    @Override
    public Resume getById(long id) {
        return resumeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Вот это " + id + " резюме не найдено"));
    }

    @Override
    public List<Resume> getAllByApplicantId(int applicantId) {
        return resumeDao.findAllByApplicantId(applicantId);
    }

    @Override
    public Resume create(ResumeRequestDto dto) {
        Optional<User> appllicant = userDao.findById(dto.getApplicantId());
        if (appllicant.isEmpty()) {
            throw new NotFoundException("Пользователь с данным " + dto.getApplicantId() + " Id не найден");
        }
        Category category = categoryDao.findById(dto.getCategoryId());
        if (category == null) {
            throw new NotFoundException("Категория с id " + dto.getCategoryId() + " не найден");
        }

        Resume resume = ResumeMapper.fromDto(dto);
        resume.setApplicantId(appllicant.get().getId());
        resume.setCategoryId(category.getId());
        resumeDao.save(resume);
        Long resumeId = resume.getId();
        if (resume.getEducationInfoList() != null && !resume.getEducationInfoList().isEmpty()) {
            educationInfoDao.saveAll(resume.getEducationInfoList(), resumeId);
        }
        if (resume.getWorkExperienceInfoList() != null && !resume.getWorkExperienceInfoList().isEmpty()) {
            workExperienceInfoDao.saveAll(resume.getWorkExperienceInfoList(), resumeId);
        }
        if (dto.getSocialLinksDto() != null) {
            SocialLinks socialLinks = ResumeMapper.fromDto(dto.getSocialLinksDto(), resumeId);
            socialLinksDao.save(socialLinks);
            resume.setSocialLinks(socialLinks);
        }


        return resume;

    }


    @Override
    public void update(Resume resume, long id, Authentication auth) {
        String username = auth.getName();


        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Resume existingResume = resumeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Резюме не найдено"));
        if (existingResume.getApplicantId() != user.getId()) {
            throw new NotFoundException("У вас нет прав для изменения этого резюме");
        }

        resumeDao.update(resume);
    }


    @Override
    public void delete(long id, Authentication auth) {
        String username = auth.getName();


        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Resume existingResume = resumeDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Резюме не найдено"));
        if (existingResume.getApplicantId() != user.getId()) {
            throw new NotFoundException("У вас нет прав для удаления этого резюме");
        }

        resumeDao.deleteById(id);
    }


}
