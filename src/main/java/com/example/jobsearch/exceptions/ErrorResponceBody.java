package com.example.jobsearch.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorResponceBody {
    private String title;
    private Map<String, List<String>> responce;
}
