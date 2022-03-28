package com.indracompany.treinamento.model.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.indracompany.treinamento.model.entity.Historico_Andre_Farias;
import com.indracompany.treinamento.model.repository.Historico_Andre_Farias_Repository;
import com.indracompany.treinamento.util.TipoOperacaoEnum;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ConsultaContaBancariaDTO;
import com.indracompany.treinamento.model.dto.TransferenciaBancariaDTO;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.repository.ContaBancariaRepository;


@Service
public class ContaBancariaService extends GenericCrudService<ContaBancaria, Long, ContaBancariaRepository>{
	
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private Historico_Andre_Farias_Service historico;

	public double consultarSaldo(String agencia, String numero) {
		ContaBancaria c = consultarConta(agencia, numero);
		return c.getSaldo();
	}

	public void depositar (String agencia, String numeroConta, double valor, boolean transferencia) {
		ContaBancaria conta = consultarConta(agencia, numeroConta);
		conta.setSaldo(conta.getSaldo() + valor);
		super.salvar(conta);
		if(!transferencia) {
			historico.registrarHistoricoDeposito(conta, TipoOperacaoEnum.DEPOSITO, valor);
		}

	}
	
	public void sacar (String agencia, String numeroConta, double valor, boolean transferencia) {
		ContaBancaria conta = consultarConta(agencia, numeroConta);
		
		if (conta.getSaldo() < valor) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_SALDO_INEXISTENTE);
		}
		
		conta.setSaldo(conta.getSaldo() - valor);
		super.salvar(conta);
		if(!transferencia){
			historico.registrarHistoricoSaque(conta, TipoOperacaoEnum.SAQUE, valor);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void transferir(TransferenciaBancariaDTO dto) {
		this.sacar(dto.getAgenciaOrigem(), dto.getNumeroContaOrigem(), dto.getValor(), true);
		this.depositar(dto.getAgenciaDestino(), dto.getNumeroContaDestino(), dto.getValor(), true);
		historico.registrarHistoricoTransferencia(consultarConta(dto.getAgenciaOrigem(),dto.getNumeroContaOrigem()),
				consultarConta(dto.getAgenciaDestino(),dto.getNumeroContaDestino()),
				TipoOperacaoEnum.DEPOSITO, dto.getValor());
	}

	@Transactional(rollbackFor = Exception.class)
	public File extrato() {

		Document documentoPDF = new Document();

		try{
			PdfWriter.getInstance(documentoPDF,new FileOutputStream("teste.pdf"));
			documentoPDF.open();
			Paragraph paragraph = new Paragraph("Olá mundo");
			documentoPDF.add(paragraph);
			documentoPDF.close();

		}catch (Exception e){
			e.printStackTrace();
		}

		return new File("/Users/andrefarias/Documents/ambiente/treinamento_202201/teste.pdf");
	}
	
	public ContaBancaria consultarConta (String agencia, String numeroConta) {
		ContaBancaria c = repository.findByAgenciaAndNumero(agencia, numeroConta);
		
		if (c == null) {
			throw new AplicacaoException(ExceptionValidacoes.ERRO_CONTA_INVALIDA);
		}
		
		return c;
	}
	
	public List<ConsultaContaBancariaDTO> obterContasPorCpf(String cpf){

		List<ConsultaContaBancariaDTO> listaContasRetorno = new ArrayList<>();
		Cliente cli = clienteService.buscarCliente(cpf);

		List<ContaBancaria> listaContasCliente = repository.findByCliente(cli);
		for (ContaBancaria conta : listaContasCliente) {
			ConsultaContaBancariaDTO dtoConta = new ConsultaContaBancariaDTO();
			BeanUtils.copyProperties(conta, dtoConta);
			dtoConta.setCpf(conta.getCliente().getCpf());
			dtoConta.setNomeTitular(conta.getCliente().getNome());
			listaContasRetorno.add(dtoConta);
		}


		return listaContasRetorno;
	}
}
