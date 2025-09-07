package com.base.demo.controller;

import com.base.demo.model.AcaoFavorita;
import com.base.demo.service.AcaoFavoritaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AcaoFavoritaController.class)   
class AcaoFavoritaControllerTest {

    @Autowired
    private MockMvc mockMvc;  

    @MockBean
    private AcaoFavoritaService service;  

    @Test
    void deveListarAcoes() throws Exception {
        AcaoFavorita acao = new AcaoFavorita();
        acao.setId(1L);
        acao.setTicker("PETR4");

        when(service.listarTodas()).thenReturn(Collections.singletonList(acao));

        mockMvc.perform(get("/api/acoes-favoritas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ticker").value("PETR4"));
    }

    @Test
    void deveCriarAcao() throws Exception {
        AcaoFavorita acao = new AcaoFavorita();
        acao.setId(1L);
        acao.setTicker("VALE3");

        when(service.salvar(any(AcaoFavorita.class))).thenReturn(acao);

        String jsonRequest = "{ \"ticker\": \"VALE3\" }";

        mockMvc.perform(post("/api/acoes-favoritas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value("VALE3"));
    }

    @Test
    void deveBuscarAcaoPorId() throws Exception {
        AcaoFavorita acao = new AcaoFavorita();
        acao.setId(1L);
        acao.setTicker("ITUB4");

        when(service.buscarPorId(1L)).thenReturn(Optional.of(acao));

        mockMvc.perform(get("/api/acoes-favoritas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticker").value("ITUB4"));
    }

    @Test
    void deveRetornar404SeAcaoNaoExistir() throws Exception {
        when(service.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/acoes-favoritas/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
