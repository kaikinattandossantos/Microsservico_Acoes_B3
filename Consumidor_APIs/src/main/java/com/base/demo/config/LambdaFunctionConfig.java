package com.base.demo.config;

import com.base.demo.service.StockAlertService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Function;

@Configuration
public class LambdaFunctionConfig {

    /**
     * O Spring Cloud Function irá expor o Bean para a AWS.
     * Usei Function<Object, String> porque a Lambda será acionada pelo CloudWatch (sem input útil)
     * e retornará uma String de status.
     */
    @Bean
    public Function<Object, String> verificarPrecos(StockAlertService stockAlertService) {
        return (input) -> {
            System.out.println("Função Lambda 'verificarPrecos' iniciada.");
            stockAlertService.verificarPrecosParaAlerta();
            return "Verificação de preços executada com sucesso via Lambda.";
        };
    }
}