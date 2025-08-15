    package com.example.jobsearch.mapper;

    import com.example.jobsearch.dto.WorkExperienceInfoRequestDto;
    import com.example.jobsearch.dto.WorkExperienceInfoResponseDto;
    import com.example.jobsearch.model.Resume;
    import com.example.jobsearch.model.WorkExperienceInfo;

    public class WorkExperienceInfoMapper {
        public static WorkExperienceInfoResponseDto toDto(WorkExperienceInfo workExperienceInfo) {
            if (workExperienceInfo == null) return null;
            WorkExperienceInfoResponseDto dto = new WorkExperienceInfoResponseDto();
            dto.setId(workExperienceInfo.getId());
            dto.setResumeId(workExperienceInfo.getResume().getId());
            dto.setYears(workExperienceInfo.getYears());
            dto.setCompanyName(workExperienceInfo.getCompanyName());
            dto.setPosition(workExperienceInfo.getPosition());
            dto.setResponsibilities(workExperienceInfo.getResponsibilities());
            return dto;
        }
        public static WorkExperienceInfo fronDto(WorkExperienceInfoRequestDto requestDto) {
            if (requestDto == null) return null;
            Resume resume = new Resume();
            WorkExperienceInfo workExperienceInfo = new WorkExperienceInfo();
            resume.setId(requestDto.getResumeId());
            workExperienceInfo.setResume(resume);
            workExperienceInfo.setYears(requestDto.getYears());
            workExperienceInfo.setCompanyName(requestDto.getCompanyName());
            workExperienceInfo.setPosition(requestDto.getPosition());
            workExperienceInfo.setResponsibilities(requestDto.getResponsibilities());
            return workExperienceInfo;
        }
    }
