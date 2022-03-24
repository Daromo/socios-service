package com.cfm.socios.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cfm.socios.model.PorcentajeClave;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "cat_porcentajes_accionistas")
public class PorcentajeEntity {	
	@EmbeddedId
	private PorcentajeClave constraint;
	
	@Column(name = "porcentaje")
	private Integer cantidadPorcentaje;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update")
	private Date lastUpdate;
	
	@Column(name = "status")
	private Character status;

	@PrePersist
	public void onCreate() {
		fechaAlta = new Date();
		lastUpdate = new Date();
		status = 'A';
	}
	
	@PreUpdate
	private void onUpdate() {
		lastUpdate = new Date();
	}
}
