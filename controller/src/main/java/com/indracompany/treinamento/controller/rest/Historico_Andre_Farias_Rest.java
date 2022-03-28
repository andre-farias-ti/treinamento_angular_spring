package com.indracompany.treinamento.controller.rest;

import com.indracompany.treinamento.model.dto.ConsultaContaBancariaDTO;
import com.indracompany.treinamento.model.dto.Historico_Andre_Farias_DTO;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.Historico_Andre_Farias;
import com.indracompany.treinamento.model.service.ContaBancariaService;
import com.indracompany.treinamento.model.service.Historico_Andre_Farias_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//	@GetMapping(value = "/extrato", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> extrato() throws Exception {
//		try {
//			var file = contaBancariaService.extrato();
//			var path = Paths.get(file.getAbsolutePath());
//			var resource = new ByteArrayResource(Files.readAllBytes(path));
//			return ResponseEntity
//					.ok()
//					.contentType(MediaType.APPLICATION_OCTET_STREAM)
//					.contentLength(file.length())
//					.body(resource);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return ResponseEntity.notFound().build();
//		}
//	}
}
