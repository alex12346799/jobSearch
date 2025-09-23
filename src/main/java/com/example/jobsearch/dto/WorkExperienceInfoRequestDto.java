package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
@Setter
public class WorkExperienceInfoRequestDto {

//    @NotNull(message = "{work.resumeId.notnull}")
//    private Long resumeId;

    @NotNull(message = "{education.startDate.notnull}")
    @PastOrPresent(message = "{education.startDate.pastOrPresent}")
    private LocalDate startDate;

    @NotNull(message = "{education.endDate.notnull}")
    @PastOrPresent(message = "{education.endDate.pastOrPresent}")
    private LocalDate endDate;

    @NotBlank(message = "{work.companyName.notblank}")
    private String companyName;

    @NotBlank(message = "{work.position.notblank}")
    private String position;

//    @NotBlank(message = "{work.responsibilities.notblank}")
    private String responsibilities;
}
