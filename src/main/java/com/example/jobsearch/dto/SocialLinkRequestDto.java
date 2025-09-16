package com.example.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SocialLinkRequestDto {


    @NotBlank(message = "{social.telegram.notblank}")
    private String telegram;

    @NotBlank(message = "{social.facebook.notblank}")
    private String facebook;

    @NotBlank(message = "{social.linkedin.notblank}")
    private String linkedin;
}
