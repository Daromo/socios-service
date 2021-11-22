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

@Service
public class SociosService implements ISociosService {

	private static final String OPTION_UPDATE = "modificar";
	private static final String OPTION_SAVE = "guardar";
	
	@Autowired SociosRepository repoSocios;
	@Autowired ModelMapper modelMapper;
	
	@Override
	public List<SocioEntity> getAll() {
		return repoSocios.findAll();
	}
	
	@Override
	public List<SocioEntity> getListaSociosByStatus(char status) {
		return repoSocios.findByStatus(status);
	}
	
	@Override
	public SocioEntity buscarSocioByRFC(String rfc) throws BusinessException{
		Optional<SocioEntity> socio = repoSocios.findById(rfc);
		if (!socio.isPresent())
			throw new BusinessException("El RFC del socio no existe - service");
		return socio.get();
	}
	
	@Override
	public SocioEntity guardar(Socio socio, String operacion) throws BusinessException{
		
		if (repoSocios.existsById(socio.getRfc()) && operacion.equals(OPTION_SAVE))
			throw new BusinessException("El cliente ya existe - service");
		
		if (!repoSocios.existsById(socio.getRfc()) && operacion.equals(OPTION_UPDATE))
			throw new BusinessException("El socio no existe - service");
		
		SocioEntity socioMapper = modelMapper.map(socio, SocioEntity.class);
		return repoSocios.save(socioMapper);
	}

	@Override
	public SocioEntity modificarStatus(String rfc, char newStatus) throws BusinessException{
		Optional<SocioEntity> socioEntity = repoSocios.findById(rfc);
		if(!socioEntity.isPresent())
			throw new BusinessException("El rfc del socio no existe - service");
		
		socioEntity.get().setStatus(newStatus);
		return repoSocios.save(socioEntity.get());
	}
}
