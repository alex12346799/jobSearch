package com.example.jobsearch.service.impl;

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
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    public void sendEmail(String to, String url) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(EMAIL_FROM, "Job Search support ");
        helper.setTo(to);

        String subject = "Here is the link to reset password";
        String body = "<p>Hello!</p>" +
                "<p>You have requested to reset your password.</p>"+
                "<p>Click on the link bellow to reset your password</p>"+
                "<p><a  href=\"" + url + "\">Change my password</a></p>"+
                "<br>"+
                "<p>Ignore this email if you do remember ypu password." +
                "Or you have not made the request.</p>";
        helper.setSubject(subject);
        helper.setText(body, true);
        javaMailSender.send(mimeMessage);

    }
}
