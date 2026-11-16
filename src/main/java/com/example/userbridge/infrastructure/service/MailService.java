package com.example.userbridge.infrastructure.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;


@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendConfirmationEmail(String to, String subject, String firstName, String lastName, String confirmationLink) {
        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("lastName", lastName);
        context.setVariable("confirmationLink", confirmationLink);
        send(to, subject, "registration-email", context);
    }

    public void sendPasswordResetEmail(String to, String firstName, String resetLink, long expirationMinutes) {
        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("resetLink", resetLink);
        context.setVariable("expirationMinutes", expirationMinutes);
        send(to, "Resetowanie hasła", "password-reset-email", context);
    }

    private void send(String to, String subject, String template, Context context) {
        String htmlContent = templateEngine.process(template, context);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email", e);
        }
    }
}
