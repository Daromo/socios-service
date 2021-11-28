package com.cfm.socios.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PorcentajeSocio {
	private String clave;
	private String fullName;
	private Integer porcentaje;
	private Character status;
}
