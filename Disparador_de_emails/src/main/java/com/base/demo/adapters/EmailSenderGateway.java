package com.base.demo.adapters;


public interface EmailSenderGateway {
    void sendEmail(String toEmail, String subject, String body);
}