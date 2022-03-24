package com.cfm.socios.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfm.socios.jpa.entity.PorcentajeEntity;
import com.cfm.socios.model.PorcentajeClave;

/**
 * @author Jose Daniel Rojas Morales
 */
public interface PorcentajesRepository extends JpaRepository<PorcentajeEntity, PorcentajeClave> {
	List<PorcentajeEntity> findByConstraintClave(String clave);
	List<PorcentajeEntity> findByStatus(Character status);
}
