package com.indracompany.treinamento.controller.rest;

import com.indracompany.treinamento.model.dto.ConsultaContaBancariaDTO;
import com.indracompany.treinamento.model.dto.Historico_Andre_Farias_DTO;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.Historico_Andre_Farias;
import com.indracompany.treinamento.model.service.ContaBancariaService;
import com.indracompany.treinamento.model.service.Historico_Andre_Farias_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("rest/extrato")
public class Historico_Andre_Farias_Rest extends GenericCrudRest<Historico_Andre_Farias, Long, Historico_Andre_Farias_Service> {

    @Autowired
    Historico_Andre_Farias_Service historico;

    @GetMapping(value = "/consultar-extrato-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Historico_Andre_Farias_DTO>> consultarExtratoData(String agencia, String numeroConta,
                                                                                               String dataIni, String dataFim) throws Exception {
        List<Historico_Andre_Farias_DTO> h = historico.extratoPorData(agencia, numeroConta, dataIni, dataFim);
        if (h == null || h.isEmpty()) {
            return new ResponseEntity<List<Historico_Andre_Farias_DTO>>(h, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Historico_Andre_Farias_DTO>>(h, HttpStatus.OK);
    }

	@GetMapping(value = "/pdf")
	public ResponseEntity<Object> extratoPDF(String agencia, String numeroConta,
                                             String dataIni, String dataFim) throws Exception {
		File file = historico.extratoPDF(agencia, numeroConta, dataIni, dataFim);

        InputStreamResource resorce = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + file.getName() );
        headers.add("Cache-Control","no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("aplication/pdf")).body(resorce);

		return responseEntity;
	}
}
