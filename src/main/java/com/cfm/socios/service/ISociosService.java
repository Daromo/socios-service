package com.cfm.socios.service;

import java.util.List;

import com.cfm.socios.exception.BusinessException;
import com.cfm.socios.jpa.entity.SocioEntity;
import com.cfm.socios.model.Socio;

public interface ISociosService {
	List<SocioEntity> getListaSociosByStatus(char status);
	SocioEntity buscarSocioByRFC(String rfc) throws BusinessException;
	SocioEntity guardar(Socio socio, String operacion) throws BusinessException;
	SocioEntity modificarStatus(String rfc, char newStatus) throws BusinessException;
}
