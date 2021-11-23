package com.cfm.socios.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfm.socios.jpa.entity.PorcentajeEntity;
import com.cfm.socios.model.PorcentajeId;

public interface PorcentajesRepository extends JpaRepository<PorcentajeEntity, PorcentajeId> {
	List<PorcentajeEntity> findByIdClave(String clave);
	List<PorcentajeEntity> findByStatus(Character status);
}
