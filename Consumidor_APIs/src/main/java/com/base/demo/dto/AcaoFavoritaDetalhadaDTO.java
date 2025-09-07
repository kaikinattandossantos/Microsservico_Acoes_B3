package com.base.demo.dto;

import lombok.Data;
import com.base.demo.model.AcaoFavorita;

@Data // Lombok para getters/setters
public class AcaoFavoritaDetalhadaDTO {

    private Long id;
    private String ticker;
    private String anotacaoPessoal;
    private Double precoAtual; 

    public AcaoFavoritaDetalhadaDTO(AcaoFavorita acaoFavorita, Double precoAtual) {
        this.id = acaoFavorita.getId();
        this.ticker = acaoFavorita.getTicker();
        this.anotacaoPessoal = acaoFavorita.getAnotacaoPessoal();
        this.precoAtual = precoAtual;
    }
}