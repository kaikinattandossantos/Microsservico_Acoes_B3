package com.base.demo.service;

import com.base.demo.dto.TelegramRequestdto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "telegram-service", url = "http://localhost:8082/api/telegram")
public interface TelegramServiceClient {

    @PostMapping("/api/telegram/send")
    void sendNotification(@RequestBody TelegramRequestdto request);
}
