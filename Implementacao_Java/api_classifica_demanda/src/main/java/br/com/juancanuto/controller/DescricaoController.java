package br.com.juancanuto.controller;

import br.com.juancanuto.dto.TipoDescricao;
import br.com.juancanuto.services.ClassificaDescricao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/descricao")
@CrossOrigin
public class DescricaoController {

	Logger logger = LoggerFactory.getLogger(DescricaoController.class);
	
	@Autowired
	ClassificaDescricao descricaoClassService;

	@RequestMapping(value = { "/classifica" } , method = RequestMethod.GET)
	public ResponseEntity<TipoDescricao> classifyDescricao(@RequestParam("descricao") String sProducDesc){
		
		TipoDescricao result = new TipoDescricao();
		if(sProducDesc == null)return new ResponseEntity<TipoDescricao>(result, HttpStatus.BAD_REQUEST);
		try {						
			result = descricaoClassService.findByDesc(sProducDesc);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
            return new ResponseEntity<TipoDescricao>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}				
		return new ResponseEntity<TipoDescricao>(result, HttpStatus.OK);
	}
}
