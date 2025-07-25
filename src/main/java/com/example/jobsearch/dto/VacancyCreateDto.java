package com.example.jobsearch.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VacancyCreateDto {

    @NotBlank(message = "Название вакансии не должно быть пустым")
    @Size(min = 3, max = 255, message = "Название должно содержать от 3 до 255 символов")
    private String title;

    @NotBlank(message = "Описание не должно быть пустым")
    @Size(min = 5, message = "Описание должно содержать не менее 5 символов")
    private String description;

    @NotNull(message = "Поле должно быть заполненым")
    private int categoryId;

    @Positive(message = "Зарплата не может быть отрицательной")
    private double salary;

    @Positive(message = "Опыт не может быть отрицательным ")
    private int expFrom;

    @Positive(message = "Опыт не может быть отрицательным ")
    private int expTo;

    private boolean asActive;

    @Positive(message = "ID работодателя не должен быть  отрицательным ")
    private int employerId;

    @PastOrPresent(message = "Дата создания не может быть в будущем")
    private LocalDateTime createdDate;

    @PastOrPresent(message = "Дата обновления не может быть в будущем")
    private LocalDateTime updatedDate;
}
