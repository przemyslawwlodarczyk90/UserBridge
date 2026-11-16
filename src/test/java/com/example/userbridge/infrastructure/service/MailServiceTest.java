package com.example.userbridge.infrastructure.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private MailService mailService;

    @Captor
    private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendConfirmationEmailSuccess() throws MessagingException, IOException {
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        String expectedHtmlContent = "<html><body>Test</body></html>";
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn(expectedHtmlContent);

        mailService.sendConfirmationEmail("test@example.com", "Subject", "Jan", "Kowalski", "http://example.com/confirm");

        verify(mailSender).send(mimeMessageCaptor.capture());
        MimeMessage capturedMimeMessage = mimeMessageCaptor.getValue();


        assertEquals("test@example.com", capturedMimeMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
        assertEquals("Subject", capturedMimeMessage.getSubject());
        assertEquals(expectedHtmlContent, capturedMimeMessage.getContent().toString().trim());
    }



    @Test
    void testSendPasswordResetEmail() throws Exception {
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(templateEngine.process(eq("password-reset-email"), any(Context.class))).thenReturn("<html>reset</html>");

        mailService.sendPasswordResetEmail("test@example.com", "Jan", "http://localhost:5173/reset-password?token=x", 30);

        verify(mailSender).send(mimeMessageCaptor.capture());
        assertEquals("test@example.com", mimeMessageCaptor.getValue().getRecipients(MimeMessage.RecipientType.TO)[0].toString());
        assertEquals("Resetowanie hasła", mimeMessageCaptor.getValue().getSubject());
    }
}
