package com.example.jobsearch.auth.application;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class PasswordResetMailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:jobsearch@localhost}")
    private String emailFrom;

    public void send(String to, String resetUrl) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(emailFrom, "Job Search support");
        helper.setTo(to);
        helper.setSubject("Password reset instructions");
        helper.setText("<p>A password reset was requested for your account.</p>"
                + "<p><a href=\"" + resetUrl + "\">Reset password</a></p>"
                + "<p>Ignore this email if you did not make this request.</p>", true);
        mailSender.send(message);
    }
}
