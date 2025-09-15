package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationInfoRequestDto {
//    @NotNull(message = "Обязательно укажите resumeId")
//    private Long resumeId;



    @NotBlank(message = "{education.institution.notblank}")
    private String institution;

    @NotBlank(message = "{education.program.notblank}")
    private String program;

    @NotBlank(message = "{education.degree.notblank}")
    private String degree;

    @NotNull(message = "{education.startDate.notnull}")
    @PastOrPresent(message = "{education.startDate.pastOrPresent}")
    private LocalDate startDate;

    @NotNull(message = "{education.endDate.notnull}")
    @PastOrPresent(message = "{education.endDate.pastOrPresent}")
    private LocalDate endDate;

}
