package com.cfm.socios.controller;

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
import com.cfm.socios.jpa.entity.SocioEntity;
import com.cfm.socios.model.Socio;
import com.cfm.socios.service.ISociosService;
import com.cfm.socios.util.GUIDGenerator;
import com.cfm.socios.util.LogHandler;
import com.cfm.socios.util.Parseador;

/**
 * @author Jose Daniel Rojas Morales
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/socios")
public class SociosController {

	@Autowired ISociosService serviceSocios;
	
	/**
	 * Endpoint para consultar la lista de todos los socios
	 * @return List<SocioEntity>
	 */
	@GetMapping("/all")
	public ResponseEntity<List<SocioEntity>> getAllListSocios(){
		return new ResponseEntity<>(serviceSocios.getAll(), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar los socios activos
	 * @return List<SocioEntity>
	 */
	@GetMapping("/activos")
	public ResponseEntity<List<SocioEntity>> getListaSocios(){
		List<SocioEntity> listaSocios = serviceSocios.getListaSociosByStatus('A');
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getListaSocios"+Parseador.objectToJson(uid, listaSocios));
		return new ResponseEntity<>(listaSocios, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para consultar los socios inactivos
	 * @return List<SocioEntity>
	 */
	@GetMapping("/inactivos")
	public ResponseEntity<List<SocioEntity>> getListaSociosInactivos(){
		List<SocioEntity> listaSocios = serviceSocios.getListaSociosByStatus('I');
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "getListaSocios"+Parseador.objectToJson(uid, listaSocios));
		return new ResponseEntity<>(listaSocios, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para guardar el registro de un socio
	 * @param Cliente
	 * @return Cliente
	 */
	@PostMapping("/nuevo")
	public ResponseEntity<SocioEntity> agregarSocio(@Valid @RequestBody Socio socio) throws BusinessException{
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "agregarSocio:"+Parseador.objectToJson(uid, socio));
		return new ResponseEntity<>(serviceSocios.guardar(socio, "guardar"), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para modificar el registro de un socio
	 * @param Cliente
	 * @return Cliente
	 */
	@PutMapping("/modificar")
	public ResponseEntity<SocioEntity> modificarSocio(@Valid @RequestBody Socio socio) throws BusinessException{
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "modificarCliente:"+Parseador.objectToJson(uid, socio));
		return new ResponseEntity<>(serviceSocios.guardar(socio, "modificar"), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para dar de baja a un socio en el sistema
	 * @param Socio RFC
	 * @return SocioEntity
	 * @throws BusinessException
	 */
	@PutMapping("/baja/{rfc}")
	public ResponseEntity<SocioEntity> bajaSocio(@PathVariable String rfc) throws BusinessException{
		return new ResponseEntity<>(serviceSocios.modificarStatus(rfc, 'I'), HttpStatus.OK);
	}
	
	/**
	 * Endpoint para reactivar a un socio en el sistema
	 * @param Socio RFC
	 * @return SocioEntity
	 * @throws BusinessException
	 */
	@PutMapping("/reactivar/{rfc}")
	public ResponseEntity<SocioEntity> reactivarSocio(@PathVariable String rfc) throws BusinessException{
		return new ResponseEntity<>(serviceSocios.modificarStatus(rfc, 'A'), HttpStatus.OK);
	}
}
