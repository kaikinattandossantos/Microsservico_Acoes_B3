package com.base.demo.service;

import com.base.demo.dto.AcaoDTO;
import com.base.demo.dto.BrapiResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrapiServiceTest {

    @Mock
    private RestTemplate restTemplate; 

    @InjectMocks
    private BrapiService brapiService;

    private AcaoDTO acaoMock;

    @BeforeEach
    void setUp() {
        acaoMock = new AcaoDTO();
        acaoMock.setTicker("PETR4");
        acaoMock.setPreco(30.5);
        acaoMock.setNome("Petrobras");
        acaoMock.setMoeda("BRL");
    }

    @Test
    void deveConsultarAcaoComSucesso() {
        BrapiResponseDTO responseMock = new BrapiResponseDTO();
        responseMock.setResults(Collections.singletonList(acaoMock));

        String url = "https://brapi.dev/api/quote/PETR4?token=fakeToken"; 
        when(restTemplate.getForObject(url, BrapiResponseDTO.class)).thenReturn(responseMock);

        AcaoDTO resultado = brapiService.consultarAcao("PETR4");

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTicker()).isEqualTo("PETR4");
        assertThat(resultado.getPreco()).isEqualTo(30.5);
    }

    @Test
    void deveRetornarNullQuandoNaoEncontrarAcao() {
        BrapiResponseDTO responseMock = new BrapiResponseDTO();
        responseMock.setResults(Collections.emptyList());

        String url = "https://brapi.dev/api/quote/VALE3?token=fakeToken"; 
        when(restTemplate.getForObject(url, BrapiResponseDTO.class)).thenReturn(responseMock);

        AcaoDTO resultado = brapiService.consultarAcao("VALE3");

        assertThat(resultado).isNull();
    }
}
