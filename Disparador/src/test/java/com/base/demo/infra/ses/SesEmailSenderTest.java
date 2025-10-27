package com.base.demo.infra.ses;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.base.demo.email.exceptions.EmailServiceException;
import com.base.demo.email.infra.ses.SesEmailSender;

@ExtendWith(MockitoExtension.class)
public class SesEmailSenderTest {

    @Mock
    private AmazonSimpleEmailService sesClient;

    @InjectMocks
    private SesEmailSender sesEmailSender;

    @Test
    void shouldBuildCorrectEmailRequest() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Act
        sesEmailSender.sendEmail(toEmail, subject, body);

        // Assert - Verifica que o cliente SES foi chamado com os parâmetros corretos
        verify(sesClient, times(1)).sendEmail(any(SendEmailRequest.class));
    }

    @Test
    void shouldUseCorrectSourceEmail() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Act
        sesEmailSender.sendEmail(toEmail, subject, body);

        // Assert - Verifica que o email de origem é o correto
        verify(sesClient).sendEmail(argThat(request -> 
            "kaikinattansg@gmail.com".equals(request.getSource()) &&
            request.getDestination().getToAddresses().contains(toEmail)
        ));
    }

    @Test
    void shouldHandleAmazonServiceException() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Simula exceção da AWS
        doThrow(new com.amazonaws.AmazonServiceException("AWS error"))
            .when(sesClient).sendEmail(any(SendEmailRequest.class));

        // Act & Assert
        assertThrows(EmailServiceException.class, () -> {
            sesEmailSender.sendEmail(toEmail, subject, body);
        });
    }

    @Test
    void shouldIncludeOriginalExceptionInEmailServiceException() {
        // Arrange
        String toEmail = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        AmazonServiceException originalException = 
            new AmazonServiceException("AWS error");
        
        doThrow(originalException)
            .when(sesClient).sendEmail(any(SendEmailRequest.class));

        // Act
        Exception exception = assertThrows(EmailServiceException.class, () -> {
            sesEmailSender.sendEmail(toEmail, subject, body);
        });

        // Assert - Verifica que a exceção original foi preservada
        assertEquals("Email sending failed", exception.getMessage());
        assertEquals(originalException, exception.getCause());
    }
}