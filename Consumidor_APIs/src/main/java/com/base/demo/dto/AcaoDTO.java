// src/main/java/com/base/demo/dto/AcaoDTO.java
package com.base.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AcaoDTO {
    
    @JsonProperty("symbol")
    private String ticker;

    @JsonProperty("longName")
    private String nome;

    @JsonProperty("regularMarketPrice")
    private double preco;

    @JsonProperty("currency")
    private String moeda;
}