package com.indracompany.treinamento.model.repository;

import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.Historico_Andre_Farias;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface Historico_Andre_Farias_Repository extends GenericCrudRepository<Historico_Andre_Farias, Long> {

//    @Query("select h from Historico_Andre_Farias h where h.contaBancaria = :conta" +
//            " And h.data BETWEEN :dataIni AND :dataFim")
    List<Historico_Andre_Farias> findByContaBancariaAndDataBetween(@Param("conta") ContaBancaria conta,
                                               @Param("dataIni")Date dataIni,
                                               @Param("dataFim")Date dataFim);

}
