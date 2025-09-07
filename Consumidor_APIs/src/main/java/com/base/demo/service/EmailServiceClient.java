package com.base.demo.service; // <--- CORRETO
import com.base.demo.dto.EmailRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// @FeignClient define que esta é uma interface de cliente Feign
@FeignClient(name = "email-service", url = "http://localhost:8090/api/email")
public interface EmailServiceClient {

    // Este método mapeia para o endpoint POST /enviar no serviço de e-mail
    @PostMapping("/send")
    void enviarEmail(@RequestBody EmailRequestDTO emailRequest);
}