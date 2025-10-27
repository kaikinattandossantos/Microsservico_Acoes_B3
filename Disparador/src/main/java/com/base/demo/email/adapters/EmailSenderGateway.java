package com.base.demo.email.adapters;


public interface EmailSenderGateway {
    void sendEmail(String toEmail, String subject, String body);
}