package com.base.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.demo.dto.AcaoDTO;
import com.base.demo.service.BrapiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/acoes")
public class ConsultaAcaoController {

    @Autowired
    private BrapiService brapiService;

    @Operation(summary = "Consultar ação por ticker", description = "Busca informações detalhadas de uma ação via Brapi API")
    @ApiResponse(responseCode = "200", description = "Ação encontrada")
    @ApiResponse(responseCode = "404", description = "Ação não encontrada")
    @GetMapping("/consultar/{ticker}")
    public ResponseEntity<AcaoDTO> consultarAcao(@PathVariable String ticker) {
        AcaoDTO acao = brapiService.consultarAcao(ticker);
        if (acao != null) {
            return ResponseEntity.ok(acao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
