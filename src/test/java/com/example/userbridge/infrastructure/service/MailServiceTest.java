package com.example.userbridge.infrastructure.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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


}
