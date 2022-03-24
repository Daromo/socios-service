package com.cfm.socios.porcentajes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cfm.socios.model.PorcentajeSocio;
import com.cfm.socios.porcentajes.service.IPorcentajesService;
import com.cfm.socios.util.GUIDGenerator;
import com.cfm.socios.util.LogHandler;
import com.cfm.socios.util.Parseador;

/**
 * @author Jose Daniel Rojas Morales
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/socios/porcentajes")
public class PorcentajesController {

	@Autowired IPorcentajesService servicePorcentajes;
	
	/**
	 * Endpoint para guardar el registro de un nuevo porcetaje
	 * @param Lista de porcentajes 
	 * @return List<PorcentajeEntity>
	 */
	@PostMapping("/nuevo")
	public ResponseEntity<List<PorcentajeEntity>> agregarPorcentaje(@Valid @RequestBody List<Porcentaje> listPorcentajes)
			throws BusinessException{
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "agregarPorcentaje: "+Parseador.objectToJson(uid, listPorcentajes));
		return new ResponseEntity<>(servicePorcentajes.guardar(listPorcentajes), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para dar de baja a un porcentaje en el sistema
	 * @param Clave del porcentaje
	 * @return List<PorcentajeEntity>
	 */
	@PutMapping("/baja/{clave}")
	public ResponseEntity<List<PorcentajeEntity>> bajaPorcentaje(@PathVariable("clave") String clavePorcentaje) throws BusinessException{
		return new ResponseEntity<>(servicePorcentajes.modificarStatus(clavePorcentaje, 'I'), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar la lista de todos los porcentajes
	 * @return List<PorcentajeSocio>
	 */
	@GetMapping("/")
	public ResponseEntity<List<PorcentajeSocio>> getListPorcentajes(){
		return new ResponseEntity<>(servicePorcentajes.getNombreSociosPorcentajes(), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar la lista de porcentajes activos
	 * @return List<PorcentajeEntity>
	 */
	@GetMapping("/activos")
	public ResponseEntity<List<PorcentajeEntity>> getListPorcentajesActivos(){
		return new ResponseEntity<>(servicePorcentajes.getPorcentajesActivos(), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para recuperar la clave del ultimo porcentaje almacenado
	 * @return Map<clave, valor_ultima_clave_porcentaje>
	 */
	@GetMapping("/last")
	public ResponseEntity<Map<String, String>> getLastRowClave(){
		Map<String, String> map= new HashMap<>();
		map.put("clave", servicePorcentajes.getLastClavePorcentaje());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
}
