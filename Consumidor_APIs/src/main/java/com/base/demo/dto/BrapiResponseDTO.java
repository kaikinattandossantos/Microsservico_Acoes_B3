// src/main/java/com/base/demo/dto/BrapiResponseDTO.java
package com.base.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrapiResponseDTO {
    
    private List<AcaoDTO> results;

    public List<AcaoDTO> getResults() {
        return results;
    }

    public void setResults(List<AcaoDTO> results) {
        this.results = results;
    }
}