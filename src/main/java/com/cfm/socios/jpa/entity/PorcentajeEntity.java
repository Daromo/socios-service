package com.cfm.socios.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.cfm.socios.model.PorcentajeClave;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "cat_porcentajes_accionistas")
public class PorcentajeEntity {	
	
	@NotNull(message = "El argumento PorcentajeClave no puede ser nulo")
	@EmbeddedId
	private PorcentajeClave constraint;
	
	@NotNull(message = "El argumento porcentaje no puede ser nulo")
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
