package com.example.jobsearch.storage.web;

import java.time.Instant;

public record AvatarResponse(boolean avatarAvailable, String contentType, long size, Instant uploadedAt) {
}
