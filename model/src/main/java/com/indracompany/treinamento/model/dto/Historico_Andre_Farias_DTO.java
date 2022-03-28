package com.indracompany.treinamento.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Historico_Andre_Farias_DTO {
    private String contaOrigem;
    private String cpfOrigem;
    private String ContaDestino;
    private double valor;
    private String data;
    private String tipo;
}
