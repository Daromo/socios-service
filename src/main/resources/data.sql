/*
 * DML SACC - SOCIOS
 * Author: Rojas Morales Jose Daniel
 * 
 * */

INSERT INTO cfm.tbl_socios (rfc,nombre_socio,ap_paterno_socio,ap_materno_socio,correo,status,fecha_alta,last_update) VALUES
('XXXX1','JOSE','ROJAS','MORALES','joserojasmorales@gmail.com','A','2021-11-21','2021-11-21'),
('XXXX2','MANUEL','ROJAS','ANDRADE','manuelrojas@gmail.com','A','2021-11-21','2021-11-21'),
('XXXX3','EL TOMATE S.A. DE C.V.',' ',' ','el_tomate@gmail.com','A','2021-11-21','2021-11-21'),
('XXXX4','ARMANDO','MORALES','MORALES','morales_armando@gmail.com','A','2021-11-21','2021-11-21');

INSERT INTO cfm.cat_porcentajes_accionistas
(clave, rfc_socio, porcentaje, fecha_alta, last_update, status) values
('PS-1', 'XXXX1', 50, '2021-11-21', '2021-11-21', 'A'),
('PS-1', 'XXXX2', 50, '2021-11-21', '2021-11-21', 'A'),
('PS-2', 'XXXX1', 30, '2021-11-21', '2021-11-21', 'A'),
('PS-2', 'XXXX2', 30, '2021-11-21', '2021-11-21', 'A'),
('PS-2', 'XXXX4', 40, '2021-11-21', '2021-11-21', 'A');

--CONSULTA PARA OBTENER EL NOMBRE DE LOS SOCIOS DE CADA PORCENTAJE
select 
	t1.clave, 
	t1.porcentaje ,
	concat(t2.nombre_socio, ' ', t2.ap_paterno_socio,' ',t2.ap_materno_socio) as "full_name"
from 
	cfm.cat_porcentajes_accionistas t1
inner join 
	cfm.tbl_socios t2 on  t2.rfc = t1.rfc_socio;