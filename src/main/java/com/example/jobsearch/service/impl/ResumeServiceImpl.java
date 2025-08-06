package com.example.jobsearch.service.impl;

import com.example.jobsearch.dao.EducationInfoDao;
import com.example.jobsearch.dao.ResumeDao;
import com.example.jobsearch.dao.UserDao;
import com.example.jobsearch.dao.WorkExperienceInfoDao;
import com.example.jobsearch.dto.ResumeRequestDto;
import com.example.jobsearch.exceptions.NotFoundException;
import com.example.jobsearch.mapper.ResumeMapper;
import com.example.jobsearch.model.Resume;
import com.example.jobsearch.model.User;
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
    private final ResumeDao resumeDao;
    private final WorkExperienceInfoDao workExperienceInfoDao;
    private final EducationInfoDao educationInfoDao;
    private final UserDao userDao;

    @Override
    public List<Resume> getAllResumes() {
        return resumeDao.findAll();
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
    public void create(ResumeRequestDto dto) {
        Optional<User> appllicant = userDao.findById(dto.getApplicantId());
        if (appllicant.isEmpty()) {
            throw new NotFoundException("Пользователь с данным " + dto.getApplicantId() + " Id не найден");
        }
        Optional<User> category = userDao.findById(dto.getCategoryId());
        if (category.isEmpty()) {
            throw new NotFoundException("Пользователь с данным " + dto.getCategoryId() + " Id не найден");
        }
        Resume resume = ResumeMapper.fromDto(dto);
        resumeDao.save(resume);
    }

    @Override
    public void createWithDetails(Resume resume) {
        Resume savedResume = resumeDao.save(resume);
        long resumeId = savedResume.getId();

        if (resume.getEducationInfoList() != null || !resume.getEducationInfoList().isEmpty()) {
            educationInfoDao.saveAll(resume.getEducationInfoList(), resumeId);
        }

        if (resume.getWorkExperienceInfoList() != null || !resume.getWorkExperienceInfoList().isEmpty()) {
            workExperienceInfoDao.saveAll(resume.getWorkExperienceInfoList(), resumeId);
        }
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
