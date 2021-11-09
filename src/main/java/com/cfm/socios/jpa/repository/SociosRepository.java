package com.cfm.socios.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cfm.socios.jpa.entity.SocioEntity;

public interface SociosRepository extends JpaRepository<SocioEntity, String> {
	List<SocioEntity> findByStatus(Character status);
}
