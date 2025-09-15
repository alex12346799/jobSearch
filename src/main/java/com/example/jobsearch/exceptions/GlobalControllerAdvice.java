package com.example.jobsearch.exceptions;

import com.example.jobsearch.service.ErrorService;
import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
//    private final ErrorService errorService;
//    @ExceptionHandler(NotFoundException.class)
//    public ErrorResponse handleNotFoundException(NotFoundException ex) {
//        return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage()).build();
//    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponseBody>  validationHandler(MethodArgumentNotValidException ex) {
//   return new ResponseEntity<>(errorService.makeResponse(ex.getBindingResult()), HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(Exception.class)
//    public String handleNotFoundException(HttpServletRequest request, Model model){
//    model.addAttribute("message", HttpStatus.NOT_FOUND.value());
//    model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
//    model.addAttribute("details", request);
//    model.addAttribute("status", HttpStatus.NOT_FOUND.value());
//    return "errors/error";
//    }
}
