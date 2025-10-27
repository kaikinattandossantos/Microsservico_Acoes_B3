package com.base.demo.email.core;

public record EmailRequest(String to, String subject, String body) {
}
