package com.base.demo.dto;

public class TelegramRequestdto {
    private String message;

    public TelegramRequestdto() {}

    public TelegramRequestdto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
