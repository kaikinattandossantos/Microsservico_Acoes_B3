package com.base.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.base.demo.adapters.EmailSenderGateway;
import com.base.demo.core.cases.EmailSender;

@Service
public class EmailSenderService implements EmailSender {

    private final EmailSenderGateway emailSenderGateway;

    @Autowired
    public EmailSenderService(EmailSenderGateway emailSenderGateway) {
        this.emailSenderGateway = emailSenderGateway;
    }

    @Override
    public void SendEmail(String toEmail, String subject, String body) {
        if (toEmail == null || !toEmail.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        if (subject == null || subject.isBlank()) {
            subject = "(Sem assunto)";
        }

        if (body == null || body.isBlank()) {
            body = "(Mensagem vazia)";
        }

        emailSenderGateway.sendEmail(toEmail, subject, body);
    }
}
