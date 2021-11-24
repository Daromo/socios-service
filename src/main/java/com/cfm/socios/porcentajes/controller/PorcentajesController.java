package com.cfm.socios.porcentajes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cfm.socios.exception.BusinessException;
import com.cfm.socios.jpa.entity.PorcentajeEntity;
import com.cfm.socios.model.Porcentaje;
import com.cfm.socios.porcentajes.service.IPorcentajesService;
import com.cfm.socios.util.GUIDGenerator;
import com.cfm.socios.util.LogHandler;
import com.cfm.socios.util.Parseador;

@RestController
@RequestMapping(value = "/socios/porcentajes")
public class PorcentajesController {

	@Autowired IPorcentajesService servicePorcentajes;
	
	@PostMapping("/nuevo")
	public ResponseEntity<List<PorcentajeEntity>> agregarPorcentaje(@Valid @RequestBody List<Porcentaje> listPorcentajes)
			throws BusinessException{
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "agregarPorcentaje: "+Parseador.objectToJson(uid, listPorcentajes));
		return new ResponseEntity<>(servicePorcentajes.guardar(listPorcentajes), HttpStatus.OK);
	}
	
	@PutMapping("/baja/{clave}")
	public ResponseEntity<List<PorcentajeEntity>> bajaPorcentaje(@PathVariable("clave") String clavePorcentaje) throws BusinessException{
		return new ResponseEntity<>(servicePorcentajes.modificarStatus(clavePorcentaje, 'I'), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PorcentajeEntity>> getListPorcentajes(){
		return new ResponseEntity<>(servicePorcentajes.getNombreSociosPorcentajes(), HttpStatus.OK);
	}
	
	@GetMapping("/activos")
	public ResponseEntity<List<PorcentajeEntity>> getListPorcentajesActivos(){
		return new ResponseEntity<>(servicePorcentajes.getPorcentajesActivos(), HttpStatus.OK);
	}
	
}
