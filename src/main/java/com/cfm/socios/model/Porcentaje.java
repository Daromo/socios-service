package com.cfm.socios.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Porcentaje {
	@NotBlank(message = "El argumento clave no puede ser nulo")
	private String clave;
	@NotBlank(message = "El argumento socioRFC no puede ser nulo")
	private String socioRFC;
	@NotNull(message = "El argumento cantidaPorcentaje no puede ser nulo")
	private Integer cantidadPorcentaje;
	private Date fechaAlta;
	private Date lastUpdate;
}
