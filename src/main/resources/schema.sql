/*
 * DDL SACC - SOCIOS
 * Author: Rojas Morales Jose Daniel
 * 
 * */

CREATE TABLE if not exists cfm.tbl_socios (
	rfc varchar(13) NOT NULL,
	nombre_socio varchar(50) NOT NULL,
	ap_paterno_socio varchar(50) NOT NULL,
	ap_materno_socio varchar(50) NOT NULL,
	correo varchar(100),
	status char,
	fecha_alta date,
	last_update date,
	CONSTRAINT tbl_socios_pkey PRIMARY KEY (rfc)
);

CREATE TABLE if not exists cfm.cat_porcentajes_accionistas(
    clave varchar(500) NOT NULL,
    rfc_socio varchar(13) NOT NULL,
    porcentaje integer NOT NULL,
    fecha_alta date,
    last_update date,
    status char,
    CONSTRAINT cat_porcentajes_accionistas_pkey PRIMARY KEY (clave, rfc_socio),
    constraint fk_socios foreign key(rfc_socio) references cfm.tbl_socios(rfc)
);
