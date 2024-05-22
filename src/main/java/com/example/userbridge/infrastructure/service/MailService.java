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
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Context context = new Context();
        context.setVariable("firstName", firstName);
        context.setVariable("lastName", lastName);
        context.setVariable("confirmationLink", confirmationLink);

        String htmlContent = templateEngine.process("registration-email", context);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email", e);
        }
    }
}
