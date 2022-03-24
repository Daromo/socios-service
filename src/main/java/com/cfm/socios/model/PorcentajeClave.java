package com.cfm.socios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jose Daniel Rojas Morales
 * @version 1.0.0
 */

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PorcentajeClave implements Serializable {
	@Column(name = "clave")
	private String clave;
	
	@Column(name = "rfc_socio")
	private String socioRFC;
}
