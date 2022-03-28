package com.indracompany.treinamento.util;

public enum TipoOperacaoEnum {

    SAQUE(1l,"Saque"),
    DEPOSITO(2l,"Deposito"),
    TRANSFERENCIA(3l,"Transferencia");

    private Long id;
    private String descricao;

    TipoOperacaoEnum(Long id, String descricao) {
        this.descricao = descricao;
        this.id = id;
    }

    public TipoOperacaoEnum getTipoOperacao(Long id){

        TipoOperacaoEnum tipo = null;
        
        for(TipoOperacaoEnum t : TipoOperacaoEnum.values()){

            if(t.id.equals(id)){
                tipo =  t;
            }
        }
        return tipo;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
