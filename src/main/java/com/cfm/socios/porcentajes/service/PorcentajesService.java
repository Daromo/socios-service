package com.cfm.socios.porcentajes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfm.socios.exception.BusinessException;
import com.cfm.socios.jpa.entity.PorcentajeEntity;
import com.cfm.socios.jpa.repository.PorcentajesRepository;
import com.cfm.socios.model.Porcentaje;
import com.cfm.socios.model.PorcentajeSocio;
import com.cfm.socios.util.GUIDGenerator;
import com.cfm.socios.util.LogHandler;
import com.cfm.socios.util.Parseador;

@Service
public class PorcentajesService implements IPorcentajesService {

	@Autowired 
	PorcentajesRepository repoPorcentajes;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<PorcentajeEntity> guardar(List<Porcentaje> porcentajes) throws BusinessException {
		List<PorcentajeEntity> listaPorcentajes = new ArrayList<>();
		for (Porcentaje porcenteje : porcentajes) {
			listaPorcentajes.add(modelToEntityPorcentaje(porcenteje));
		}
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "guardar"+Parseador.objectToJson(uid, listaPorcentajes));
		if(existsPorcentajeInDB(listaPorcentajes))
			throw new BusinessException("Alguno de los porcentajes ya se encuentra en la base de datos");
		return repoPorcentajes.saveAll(listaPorcentajes);
	}
	
	/*
	 * MAPPEAR LA PROPIEDADES DE LA LLAVE COMPUESTA A UN OBJETO DE TIPO PORCENTAJE
	 */
	public PorcentajeEntity modelToEntityPorcentaje(Porcentaje porcentaje) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Porcentaje, PorcentajeEntity>() {
			@Override
			protected void configure() {
				map().getConstraint().setClave(source.getClave());
				map().getConstraint().setSocioRFC(source.getSocioRFC());
			}
		});
		return modelMapper.map(porcentaje, PorcentajeEntity.class);
	} 
	
	/*
	 * REVISAR QUE LOS PORCENTAJES DE LA LISTA NO SE ENCUENTREB EN BD
	 */
	public boolean existsPorcentajeInDB(List<PorcentajeEntity> listaPorcentajes) {
		boolean flag = false;
		for (PorcentajeEntity porcentaje : listaPorcentajes) {
			if(repoPorcentajes.existsById(porcentaje.getConstraint())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/*
	 * CAMBIAR EL STATUS DE LOS PORCENTAJES
	 */
	@Override
	public List<PorcentajeEntity> modificarStatus(String clavePorcentaje, char newStatus) throws BusinessException{
		List<PorcentajeEntity> listaPorcentajes = repoPorcentajes.findByConstraintClave(clavePorcentaje);
		if(listaPorcentajes.isEmpty())
			throw new BusinessException("No existe la clave del porcentaje - service");
			
		listaPorcentajes.forEach(porcentaje -> porcentaje.setStatus(newStatus));
		return repoPorcentajes.saveAll(listaPorcentajes);
	}

	@Override
	public List<PorcentajeEntity> getAllPorcentajes() {
		return repoPorcentajes.findAll();
	}

	@Override
	public List<PorcentajeEntity> getPorcentajesActivos() {
		return repoPorcentajes.findByStatus('A');
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PorcentajeSocio> getNombreSociosPorcentajes() {
		Query query = entityManager.createQuery("SELECT pa.constraint.clave, CONCAT(s.nombreSocio, ' ', s.apPaternoSocio, ' ',s.apMaternoSocio) as nombre, pa.cantidadPorcentaje, pa.status FROM cat_porcentajes_accionistas pa INNER JOIN tbl_socios s ON s.rfc = pa.constraint.socioRFC");
		List<Object[]> results = query.getResultList();
		List<PorcentajeSocio> lista;
		try {
			lista = results
					.stream()
					.map(result -> new PorcentajeSocio(
							(String) result[0],
							(String) result[1],
							(Integer) result[2],
							(Character) result[3]))
					.collect(Collectors.toList());
		}catch (Exception e) {
			String uid = GUIDGenerator.generateGUID();
			LogHandler.error(uid, getClass(), "getNombreSocioPorcentajes", e);
			lista = new ArrayList<>();
		}
		return lista;
	}
}
