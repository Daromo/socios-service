package com.cfm.socios.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfm.socios.exception.BusinessException;
import com.cfm.socios.jpa.entity.SocioEntity;
import com.cfm.socios.jpa.repository.SociosRepository;
import com.cfm.socios.model.Socio;

/**
 * @author Jose Daniel Rojas Morales
 * @version 1.0.0
 */
@Service
public class SociosService implements ISociosService {

	private static final String OPTION_UPDATE = "modificar";
	private static final String OPTION_SAVE = "guardar";
	
	@Autowired SociosRepository repoSocios;
	@Autowired ModelMapper modelMapper;
	
	/**
	 * Metodo para obtener la lista de todos los socios
	 * @return List<SocioEntity>
	 */
	@Override
	public List<SocioEntity> getAll() {
		return repoSocios.findAll();
	}
	
	/**
	 * Metodo para obtener la lista de socios de acuerdo al status
	 * @param status
	 * @return List<SocioEntity>
	 */
	@Override
	public List<SocioEntity> getListaSociosByStatus(char status) {
		return repoSocios.findByStatus(status);
	}
	
	/**
	 * Metodo para obtener el registro de un socio a traves de su RFC
	 * @param Socio RFC
	 * @return SocioEntity
	 */	
	@Override
	public SocioEntity buscarSocioByRFC(String rfc) throws BusinessException{
		Optional<SocioEntity> socio = repoSocios.findById(rfc);
		if (!socio.isPresent())
			throw new BusinessException("El RFC del socio no existe - service");
		return socio.get();
	}
	
	/**
	 * Metodo para Agregar o Actualizar los datos de un cliente
	 * @param Socio
	 * @param Tipo de operacion
	 */
	@Override
	public SocioEntity guardar(Socio socio, String operacion) throws BusinessException{
		if (repoSocios.existsById(socio.getRfc()) && operacion.equals(OPTION_SAVE))
			throw new BusinessException("El cliente ya existe - service");
		if (!repoSocios.existsById(socio.getRfc()) && operacion.equals(OPTION_UPDATE))
			throw new BusinessException("El socio no existe - service");
		SocioEntity socioMapper = modelMapper.map(socio, SocioEntity.class);
		return repoSocios.save(socioMapper);
	}
	
	/**
	 * Metodo para dar de BAJA o REACTIVAR un socio en funcion del status que se
	 * recibe como argumento.
	 * @param Socio RFC
	 * @param Nuevo status
	 * @return SocioEntity
	 */
	@Override
	public SocioEntity modificarStatus(String rfc, char newStatus) throws BusinessException{
		Optional<SocioEntity> socioEntity = repoSocios.findById(rfc);
		if(!socioEntity.isPresent())
			throw new BusinessException("El rfc del socio no existe - service");
		socioEntity.get().setStatus(newStatus);
		return repoSocios.save(socioEntity.get());
	}
}
