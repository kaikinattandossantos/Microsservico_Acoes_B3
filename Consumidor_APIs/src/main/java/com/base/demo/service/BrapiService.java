// src/main/java/com/base/demo/service/BrapiService.java
package com.base.demo.service;

import com.base.demo.dto.AcaoDTO;
import com.base.demo.dto.BrapiResponseDTO;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BrapiService {


    @Value("${brapi.api.token}")
    private String apiToken;

    private static final String API_URL_BASE = "https://brapi.dev/api/quote/";
    
    private final RestTemplate restTemplate;

    @Autowired
    public BrapiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Async
    @Cacheable(value = "stocks", key = "#ticker")
    public CompletableFuture<AcaoDTO> consultarAcaoAsync(String ticker) {
        AcaoDTO acao = consultarAcao(ticker); // chama seu método atual
        return CompletableFuture.completedFuture(acao);
    }

    public AcaoDTO consultarAcao(String ticker) {
        if (ticker == null || ticker.trim().isEmpty()) {
            return null;
        }


        String urlCompleta = API_URL_BASE + ticker + "?token=" + apiToken;
        
        System.out.println("[BrapiService] URL da API sendo chamada: " + urlCompleta);
        
        try {
            BrapiResponseDTO response = restTemplate.getForObject(urlCompleta, BrapiResponseDTO.class);
            
            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                return response.getResults().get(0);
            }
        } catch (Exception e) {
            System.err.println("[BrapiService] Ocorreu um erro ao chamar a API da Brapi:");
            e.printStackTrace();
            return null;
        }
        
        return null;
    }
}