package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.model.dto.Historico_Andre_Farias_DTO;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.Historico_Andre_Farias;
import com.indracompany.treinamento.model.repository.Historico_Andre_Farias_Repository;
import com.indracompany.treinamento.util.ConverterData;
import com.indracompany.treinamento.util.TipoOperacaoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Historico_Andre_Farias_Service extends GenericCrudService<Historico_Andre_Farias, Long, Historico_Andre_Farias_Repository>{

    @Autowired
    ContaBancariaService contaService;

    @Transient
    public Historico_Andre_Farias registrarHistoricoSaque(ContaBancaria conta, TipoOperacaoEnum tipo, double valor){

        Historico_Andre_Farias historico = new Historico_Andre_Farias();
        historico.setContaBancaria(conta);
        historico.setData(new Date());
        historico.setTipo(tipo.getDescricao());
        historico.setValor(valor * -1);

         super.salvar(historico);

        return historico;
    }

    @Transient
    public Historico_Andre_Farias registrarHistoricoDeposito(ContaBancaria conta, TipoOperacaoEnum tipo, double valor){

        Historico_Andre_Farias historico = new Historico_Andre_Farias();
        historico.setContaBancaria(conta);
        historico.setData(new Date());
        historico.setTipo(tipo.getDescricao());
        historico.setValor(valor);

        super.salvar(historico);

        return historico;
    }

    @Transient
    public void registrarHistoricoTransferencia(ContaBancaria contaOrigem, ContaBancaria contaOperacao, TipoOperacaoEnum tipo, double valor){

        Historico_Andre_Farias historicoOrigem = new Historico_Andre_Farias();
        historicoOrigem.setContaBancaria(contaOrigem);
        historicoOrigem.setContaBancariaOperacao(contaOperacao);
        historicoOrigem.setData(new Date());
        historicoOrigem.setTipo(tipo.getDescricao());
        historicoOrigem.setValor(valor * -1);

        super.salvar(historicoOrigem);

        Historico_Andre_Farias historicoDestino = new Historico_Andre_Farias();
        historicoDestino.setContaBancaria(contaOperacao);
        historicoDestino.setContaBancariaOperacao(contaOrigem);
        historicoDestino.setData(new Date());
        historicoDestino.setTipo(tipo.getDescricao());
        historicoDestino.setValor(valor);

        super.salvar(historicoDestino);
    }

    public List<Historico_Andre_Farias_DTO> extratoPorData(String agencia, String numeroConta,
                                                           String dataIni, String dataFim) throws Exception{
        ContaBancaria conta = contaService.consultarConta(agencia, numeroConta);

        List<Historico_Andre_Farias> lista = new ArrayList<>();
        List<Historico_Andre_Farias_DTO> listaDTO = new ArrayList<>();

        lista = repository.buscarPordata(conta,
                ConverterData.convertStringDate(dataIni),
                ConverterData.convertStringDate(dataFim));

            listaDTO = lista.stream().map(h -> {
                        try {
                            return Historico_Andre_Farias_DTO.builder()
                                    .contaOrigem(h.getContaBancaria().getAgencia() + ": " + h.getContaBancaria().getNumero())
                                    .ContaDestino(h.getContaBancariaOperacao().getAgencia() + ": " + h.getContaBancariaOperacao().getNumero())
                                    .data(ConverterData.converDadaString(h.getData()))
                                    .tipo(h.getTipo())
                                    .valor(h.getValor())
                                    .cpfOrigem(conta.getCliente().getCpf())
                                    .build();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .collect(Collectors.toList());

        return listaDTO;

    }


}
