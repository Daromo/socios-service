package com.cfm.socios.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Porcentaje {
	private String clave;
	private String socioRFC;
	@NotNull(message = "El argumento cantidaPorcentaje no puede ser nulo")
	private Integer cantidadPorcentaje;
	private Date fechaAlta;
	private Date lastUpdate;
}
