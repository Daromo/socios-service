package com.cfm.socios.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cfm.socios.model.PorcentajeSocio;

public class ListObjectToListPorcentaje {
	
	private ListObjectToListPorcentaje() {}
	
	public static List<PorcentajeSocio> convertList(List<Object[]> listObjects) {
		List<PorcentajeSocio> lista = new ArrayList<>();
		try {
			lista = listObjects
					.stream()
					.map(result -> new PorcentajeSocio(
							(String) result[0],
							(String) result[1],
							(Integer) result[2],
							(Character) result[3]))
					.collect(Collectors.toList());
		}catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, ListObjectToListPorcentaje.class, "getNombreSocioPorcentajes", e);			
		}
		return lista;
	}
}
