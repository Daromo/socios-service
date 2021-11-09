package com.cfm.socios.model;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Socio {
	
	@NotNull(message = "El argumento RFC no puede ser nulo")
	private String rfc;
	
	@NotNull(message = "El argumento nombreSocio no puede ser nulo")
	@NotEmpty(message = "El argumento nombreSocio no puede ser vacio")
	private String nombreSocio;
	
	@NotNull(message = "El argumento apPaternoSocio no puede ser nulo")
	@NotEmpty(message = "El argumento apPaternoSocio no puede ser vacio")
	private String apPaternoSocio;
	
	@NotNull(message = "El argumento apMaternoSocio no puede ser nulo")
	@NotEmpty(message = "El argumento apMaternoSocio no puede ser vacio")
	private String apMaternoSocio;
	
	private String correo;
	
	private Character status;
	
	private Date fechaAlta;
	
	private Date lastUpdate;
}
