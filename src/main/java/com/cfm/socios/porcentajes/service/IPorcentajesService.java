package com.cfm.socios.porcentajes.service;

import java.util.List;

import com.cfm.socios.exception.BusinessException;
import com.cfm.socios.jpa.entity.PorcentajeEntity;
import com.cfm.socios.model.Porcentaje;
import com.cfm.socios.model.PorcentajeSocio;

public interface IPorcentajesService {
	List<PorcentajeEntity> guardar(List<Porcentaje> porcentajes) throws BusinessException;
	List<PorcentajeEntity> modificarStatus(String clavePorcentaje, char newStatus) throws BusinessException;
	List<PorcentajeEntity> getAllPorcentajes();
	List<PorcentajeEntity> getPorcentajesActivos();
	List<PorcentajeSocio> getNombreSociosPorcentajes();
}
