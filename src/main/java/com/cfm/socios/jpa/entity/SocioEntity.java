package com.cfm.socios.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tbl_socios")
public class SocioEntity {
	@Id
	private String rfc;
	
	@Column(name = "nombre_socio")
	private String nombreSocio;
	
	@Column(name = "ap_paterno_socio")
	private String apPaternoSocio;
	
	@Column(name = "ap_materno_socio")
	private String apMaternoSocio;
	
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "status")
	private Character status;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update")
	private Date lastUpdate;
	
	@PrePersist
	private void onCreate() {
		status = 'A';
		fechaAlta = new Date();
		lastUpdate= new Date();
	}
	
	@PreUpdate
	private void onUpdate() {
		lastUpdate = new Date();
	}
}
