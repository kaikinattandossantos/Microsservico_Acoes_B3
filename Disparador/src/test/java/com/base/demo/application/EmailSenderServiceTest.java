package com.base.demo.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.base.demo.email.adapters.EmailSenderGateway;
import com.base.demo.email.application.EmailSenderService;

@ExtendWith(MockitoExtension.class)
public class EmailSenderServiceTest {

    @Mock
    private EmailSenderGateway emailSenderGateway;

    @InjectMocks
    private EmailSenderService emailSenderService;

    @Test
    void shouldCallGatewayWithCorrectParameters() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Act
        emailSenderService.SendEmail(toEmail, subject, body);

        // Assert
        verify(emailSenderGateway, times(1))
            .sendEmail(toEmail, subject, body);
    }

    @Test
    void shouldHandleNullParameters() {
        // Act & Assert
        emailSenderService.SendEmail(null, "subject", "body");
        // Verifica que o gateway foi chamado mesmo com parâmetros nulos
        verify(emailSenderGateway, times(1))
            .sendEmail(null, "subject", "body");
    }
        @Test
    void shouldHandleNullParametersInService() {
        // Act
        emailSenderService.SendEmail(null, null, null);

        // Assert
        verify(emailSenderGateway, times(1))
            .sendEmail(null, null, null);
    }

    @Test
    void shouldHandleEmptyParametersInService() {
        // Act
        emailSenderService.SendEmail("", "", "");

        // Assert
        verify(emailSenderGateway, times(1))
            .sendEmail("", "", "");
    }

    @Test
    void shouldHandleVeryLongContentInService() {
        // Arrange
        String longString = "A".repeat(5000);

        // Act
        emailSenderService.SendEmail("test@example.com", longString, longString);

        // Assert
        verify(emailSenderGateway, times(1))
            .sendEmail("test@example.com", longString, longString);
    }

    @Test
    void shouldHandleSpecialCharactersInService() {
        // Arrange
        String email = "test+special@example.com";
        String subject = "Assunto com ç e á";
        String body = "Corpo com caracteres especiais: ã, é, õ, ü";

        // Act
        emailSenderService.SendEmail(email, subject, body);

        // Assert
        verify(emailSenderGateway, times(1))
            .sendEmail(email, subject, body);
    }

    @Test
    void shouldHandleMultipleCalls() {
        // Act - Chamadas múltiplas
        emailSenderService.SendEmail("test1@example.com", "Subject 1", "Body 1");
        emailSenderService.SendEmail("test2@example.com", "Subject 2", "Body 2");
        emailSenderService.SendEmail("test3@example.com", "Subject 3", "Body 3");

        // Assert - Verifica que todas as chamadas foram feitas
        verify(emailSenderGateway, times(3)).sendEmail(any(), any(), any());
        verify(emailSenderGateway).sendEmail("test1@example.com", "Subject 1", "Body 1");
        verify(emailSenderGateway).sendEmail("test2@example.com", "Subject 2", "Body 2");
        verify(emailSenderGateway).sendEmail("test3@example.com", "Subject 3", "Body 3");
    }
}