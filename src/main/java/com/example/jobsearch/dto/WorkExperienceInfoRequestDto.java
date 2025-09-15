package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkExperienceInfoRequestDto {

//    @NotNull(message = "{work.resumeId.notnull}")
//    private Long resumeId;

    @NotNull(message = "{work.years.notnull}")
    private Long years;

    @NotBlank(message = "{work.companyName.notblank}")
    private String companyName;

    @NotBlank(message = "{work.position.notblank}")
    private String position;

//    @NotBlank(message = "{work.responsibilities.notblank}")
//    private String responsibilities;
}
