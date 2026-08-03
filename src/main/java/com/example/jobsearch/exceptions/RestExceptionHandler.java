package com.example.jobsearch.exceptions;

import com.example.jobsearch.category.application.exception.CategoryConflictException;
import com.example.jobsearch.category.application.exception.CategoryValidationException;
import com.example.jobsearch.auth.application.AuthForbiddenException;
import com.example.jobsearch.auth.application.AuthUnauthorizedException;
import com.example.jobsearch.auth.application.PasswordResetTokenException;
import com.example.jobsearch.auth.application.PasswordResetValidationException;
import com.example.jobsearch.vacancy.application.exception.VacancyAccessDeniedException;
import com.example.jobsearch.vacancy.application.exception.VacancyInUseException;
import com.example.jobsearch.vacancy.application.exception.VacancyValidationException;
import com.example.jobsearch.user.application.UserProfileException;
import com.example.jobsearch.resume.application.*;
import com.example.jobsearch.jobapplication.application.*;
import com.example.jobsearch.storage.application.*;
import com.example.jobsearch.admin.application.AdminConflictException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorResponse> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return response(HttpStatus.BAD_REQUEST, "Request validation failed", request, validationErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorResponse> handleUnreadableRequest(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.BAD_REQUEST, "Malformed request or unknown field", request, Map.of());
    }

    @ExceptionHandler(AuthUnauthorizedException.class)
    public ResponseEntity<RestErrorResponse> handleUnauthorized(
            AuthUnauthorizedException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.UNAUTHORIZED, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(AuthForbiddenException.class)
    public ResponseEntity<RestErrorResponse> handleAuthForbidden(
            AuthForbiddenException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.FORBIDDEN, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler({PasswordResetTokenException.class, PasswordResetValidationException.class})
    public ResponseEntity<RestErrorResponse> handlePasswordReset(Exception exception, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(UserProfileException.class)
    public ResponseEntity<RestErrorResponse> handleUserProfile(
            UserProfileException exception, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ResumeNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleResumeNotFound(
            ResumeNotFoundException exception, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ResumeAccessDeniedException.class)
    public ResponseEntity<RestErrorResponse> handleResumeAccessDenied(
            ResumeAccessDeniedException exception, HttpServletRequest request) {
        return response(HttpStatus.FORBIDDEN, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ResumeInUseException.class)
    public ResponseEntity<RestErrorResponse> handleResumeInUse(
            ResumeInUseException exception, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ResumeValidationException.class)
    public ResponseEntity<RestErrorResponse> handleResumeValidation(
            ResumeValidationException exception, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(JobApplicationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleJobApplicationNotFound(
            JobApplicationNotFoundException exception, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(JobApplicationAccessDeniedException.class)
    public ResponseEntity<RestErrorResponse> handleJobApplicationAccessDenied(
            JobApplicationAccessDeniedException exception, HttpServletRequest request) {
        return response(HttpStatus.FORBIDDEN, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(JobApplicationConflictException.class)
    public ResponseEntity<RestErrorResponse> handleJobApplicationConflict(
            JobApplicationConflictException exception, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(AvatarValidationException.class)
    public ResponseEntity<RestErrorResponse> handleInvalidAvatar(
            AvatarValidationException exception, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler({AvatarTooLargeException.class, MaxUploadSizeExceededException.class})
    public ResponseEntity<RestErrorResponse> handleAvatarTooLarge(
            Exception exception, HttpServletRequest request) {
        return response(HttpStatus.PAYLOAD_TOO_LARGE, "Avatar file is too large", request, Map.of());
    }

    @ExceptionHandler(AvatarNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleAvatarNotFound(
            AvatarNotFoundException exception, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(StorageUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handleStorageUnavailable(
            StorageUnavailableException exception, HttpServletRequest request) {
        return response(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler({DataIntegrityViolationException.class, AlreadyExistsException.class})
    public ResponseEntity<RestErrorResponse> handleConflict(Exception exception, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, "The request conflicts with existing data", request, Map.of());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<RestErrorResponse> handleOptimisticLock(
            OptimisticLockingFailureException exception, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT,
                "The resource was changed by another request; reload it and retry", request, Map.of());
    }

    @ExceptionHandler(AdminConflictException.class)
    public ResponseEntity<RestErrorResponse> handleAdminConflict(
            AdminConflictException exception, HttpServletRequest request) {
        return response(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(SystemRoleMissingException.class)
    public ResponseEntity<RestErrorResponse> handleSystemRoleMissing(
            SystemRoleMissingException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(CategoryConflictException.class)
    public ResponseEntity<RestErrorResponse> handleCategoryConflict(
            CategoryConflictException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(CategoryValidationException.class)
    public ResponseEntity<RestErrorResponse> handleCategoryValidation(
            CategoryValidationException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(VacancyAccessDeniedException.class)
    public ResponseEntity<RestErrorResponse> handleVacancyAccessDenied(
            VacancyAccessDeniedException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.FORBIDDEN, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(VacancyInUseException.class)
    public ResponseEntity<RestErrorResponse> handleVacancyInUse(
            VacancyInUseException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(VacancyValidationException.class)
    public ResponseEntity<RestErrorResponse> handleVacancyValidation(
            VacancyValidationException exception,
            HttpServletRequest request
    ) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorResponse> handleUnexpected(Exception exception, HttpServletRequest request) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request, Map.of());
    }

    private ResponseEntity<RestErrorResponse> response(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            Map<String, String> validationErrors
    ) {
        RestErrorResponse body = new RestErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                validationErrors
        );
        return ResponseEntity.status(status).body(body);
    }
}
