package com.example.jobsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("Инициализация ResourceHandler: /data/images/** → file:data/images/image/");
        registry.addResourceHandler("/data/images/**")
                .addResourceLocations("file:data/images/image/");
    }
}
