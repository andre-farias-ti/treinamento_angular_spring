package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.model.dto.Historico_Andre_Farias_DTO;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.Historico_Andre_Farias;
import com.indracompany.treinamento.model.repository.Historico_Andre_Farias_Repository;
import com.indracompany.treinamento.util.ConverterData;
import com.indracompany.treinamento.util.TipoOperacaoEnum;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
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
                ConverterData.convertStringDateInicial(dataIni),
                ConverterData.convertStringDateFinal(dataFim));

            listaDTO = lista.stream().map(h ->
                                     Historico_Andre_Farias_DTO.builder()
                                    .contaOrigem(h.getContaBancaria().getAgencia() + " / " + h.getContaBancaria().getNumero())
                                    .ContaDestino((h.getContaBancariaOperacao() == null ? "":h.getContaBancariaOperacao().getAgencia())
                                            + " / " + (h.getContaBancariaOperacao() == null ? "": h.getContaBancariaOperacao().getNumero()))
                                    .data(ConverterData.converDadaString(h.getData()))
                                    .tipo(h.getTipo())
                                    .valor(h.getValor())
                                    .cpfOrigem(conta.getCliente().getCpf())
                                    .build()).collect(Collectors.toList());

        return listaDTO;

    }

    @Transactional(rollbackFor = Exception.class)
    public File extratoPDF(String agencia, String numeroConta,
                           String dataIni, String dataFim) throws Exception{
        List<Historico_Andre_Farias_DTO> listaDTO = this.extratoPorData(agencia, numeroConta, dataIni, dataFim);

        Document documentoPDF = new Document();

        try{

            PdfWriter.getInstance(documentoPDF,new FileOutputStream("teste.pdf"));
            documentoPDF.open();
            Paragraph paragraphCabecalo = new Paragraph();
            paragraphCabecalo.setAlignment(Element.ALIGN_CENTER);
            paragraphCabecalo.add(new Chunk("Extrato Bancario", new Font(Font.HELVETICA, 24)));
            documentoPDF.add(paragraphCabecalo);

            documentoPDF.add(new Paragraph(" "));

            Paragraph dataExtrato = new Paragraph();
            dataExtrato.setAlignment(Element.ALIGN_CENTER);
            dataExtrato.add(new Chunk("Data do Extrato: " + ConverterData.converDadaString(new Date()),
                    new Font(Font.HELVETICA, 16)));
            documentoPDF.add(dataExtrato);

            documentoPDF.add(new Paragraph(" "));
            documentoPDF.add(new Paragraph(" "));

            for(Historico_Andre_Farias_DTO dto : listaDTO ){

                Paragraph contaOrigem = new Paragraph();
                dataExtrato.setAlignment(Element.ALIGN_LEFT);
                dataExtrato.add(new Chunk("Conta de Origem: " + dto.getContaOrigem(),
                        new Font(Font.HELVETICA, 14)));
                documentoPDF.add(contaOrigem);

                Paragraph cpfOrigem = new Paragraph();
                cpfOrigem.setAlignment(Element.ALIGN_LEFT);
                cpfOrigem.add(new Chunk("CPF: " + dto.getCpfOrigem(),
                        new Font(Font.HELVETICA, 14)));
                documentoPDF.add(cpfOrigem);

                Paragraph ContaDestino = new Paragraph();
                ContaDestino.setAlignment(Element.ALIGN_LEFT);
                ContaDestino.add(new Chunk("Conta de Destino: " + dto.getContaDestino(),
                        new Font(Font.HELVETICA, 14)));
                documentoPDF.add(ContaDestino);

                Paragraph data = new Paragraph();
                data.setAlignment(Element.ALIGN_LEFT);
                data.add(new Chunk("Data da Operação: " + dto.getData(),
                        new Font(Font.HELVETICA, 14)));
                documentoPDF.add(data);

                Paragraph tipo = new Paragraph();
                tipo.setAlignment(Element.ALIGN_LEFT);
                tipo.add(new Chunk("TIPO da Operação: " + dto.getTipo(),
                        new Font(Font.HELVETICA, 14)));
                documentoPDF.add(tipo);

                Paragraph valor = new Paragraph();
                valor.setAlignment(Element.ALIGN_LEFT);
                valor.add(new Chunk("TIPO da Operação: " + dto.getValor(),
                        new Font(Font.HELVETICA, 14)));
                documentoPDF.add(valor);

                documentoPDF.add(new Paragraph(" "));
                documentoPDF.add(new Paragraph(" "));

            }

            documentoPDF.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return new File("/Users/andrefarias/Documents/ambiente/treinamento_202201/teste.pdf");
    }


}
