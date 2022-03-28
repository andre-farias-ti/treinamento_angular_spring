package com.indracompany.treinamento.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "hintorico_andre_farias")
public class Historico_Andre_Farias extends GenericEntity<Long>{

    private static final long serialVersionUID = -5824703733929187165L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_conta_id")
    private ContaBancaria contaBancaria;

    @ManyToOne
    @JoinColumn(name = "fk_conta_operacao")
    private ContaBancaria contaBancariaOperacao;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Date data;
}
