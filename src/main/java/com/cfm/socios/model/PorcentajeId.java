package com.cfm.socios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class PorcentajeId implements Serializable {
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "rfc_socio")
	private String socioRFC;
	
	public PorcentajeId() {}
}
