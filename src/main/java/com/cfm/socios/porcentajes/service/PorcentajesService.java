package com.cfm.socios.porcentajes.service;

import java.util.ArrayList;
import java.util.List;

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
import com.cfm.socios.util.ListObjectToListPorcentaje;
import com.cfm.socios.util.LogHandler;
import com.cfm.socios.util.Parseador;

/**
 * @author Jose Daniel Rojas Morales
 * @version 1.0.0
 */
@Service
public class PorcentajesService implements IPorcentajesService {

	@Autowired 
	PorcentajesRepository repoPorcentajes;
	
	//Inyectamos una instancia de EntityManager para manejar la interaccion con la base de datos
	@Autowired
	private EntityManager entityManager;
	
	/**
	 * Metodo para guardar una lista de porcentajes
	 * @param List<Porcentaje> 
	 * @return List<PorcentajeEntity>
	 */
	@Override
	public List<PorcentajeEntity> guardar(List<Porcentaje> porcentajes) throws BusinessException {
		List<PorcentajeEntity> listaPorcentajes = new ArrayList<>();
		//Recorrer la lista porcentajes para convertir el modelo a una entidad
		for (Porcentaje porcenteje : porcentajes) {
			listaPorcentajes.add(modelToEntityPorcentaje(porcenteje));
		}
		String uid = GUIDGenerator.generateGUID();
		LogHandler.info(uid, getClass(), "guardar"+Parseador.objectToJson(uid, listaPorcentajes));
		//Validar la lista de porcentajes en DB
		if(existsPorcentajeInDB(listaPorcentajes))
			throw new BusinessException("Alguno de los porcentajes ya se encuentra en la base de datos");
		return repoPorcentajes.saveAll(listaPorcentajes);
	}
	
	/**
	 * Metodo para convertir el objeto modelo a un entidad
	 * @param Porcentaje
	 * @return PorcentajeEntity
	 */
	public static PorcentajeEntity modelToEntityPorcentaje(Porcentaje porcentaje) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Porcentaje, PorcentajeEntity>() {
			//Mappear las propiedaes clave y socioRFC 
			@Override
			protected void configure() {
				map().getConstraint().setClave(source.getClave());
				map().getConstraint().setSocioRFC(source.getSocioRFC());
			}
		});
		return modelMapper.map(porcentaje, PorcentajeEntity.class);
	} 
	
	/**
	 * Metodo para validar que la lista de porcentajes no contenga una clave que ya exista en la tabla
	 * @param List<PorcentajeEntity>
	 * @return valor booleano
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
	
	/**
	 * Metodo para cambiar el status de un porcentaje
	 * @param Clave del porcentaje
	 * @param Nuevo status
	 * @return List<PorcentajeEntity>
	 */
	@Override
	public List<PorcentajeEntity> modificarStatus(String clavePorcentaje, char newStatus) throws BusinessException{
		List<PorcentajeEntity> listaPorcentajes = repoPorcentajes.findByConstraintClave(clavePorcentaje);
		if(listaPorcentajes.isEmpty())
			throw new BusinessException("No existe la clave del porcentaje - service");
			
		listaPorcentajes.forEach(porcentaje -> porcentaje.setStatus(newStatus));
		return repoPorcentajes.saveAll(listaPorcentajes);
	}
	
	/**
	 * Metodo para obtener la lista de todos los porcentajes
	 * @return List<PorcentajeEntity>
	 */
	@Override
	public List<PorcentajeEntity> getAllPorcentajes() {
		return repoPorcentajes.findAll();
	}
	
	/**
	 * Metodo para obtener la lista de porcentajes activos
	 * @return List<PorcentajeEntity>
	 */
	@Override
	public List<PorcentajeEntity> getPorcentajesActivos() {
		return repoPorcentajes.findByStatus('A');
	}
	
	/**
	 * Metodo para obtener la lista de los socios y la clave de los porcentajes que tiene asignado
	 * @return List<PorcentajeSocio>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PorcentajeSocio> getNombreSociosPorcentajes() {
		Query query = entityManager.createQuery("SELECT pa.constraint.clave, CONCAT(s.nombreSocio, ' ', s.apPaternoSocio, ' ',s.apMaternoSocio) "
				+ "as nombre, pa.cantidadPorcentaje, pa.status FROM cat_porcentajes_accionistas pa "
				+ "INNER JOIN tbl_socios s ON s.rfc = pa.constraint.socioRFC "
				+ "ORDER BY CAST(split_part(pa.constraint.clave,'-',2) as integer) asc");
		List<Object[]> results = query.getResultList();		
		return ListObjectToListPorcentaje.convertList(results);
	}
	
	/**
	 * Metodo para obtener la ultima clave de los porcentajes
	 * @return clave
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getLastClavePorcentaje() {
		String lastClave;
		try {
			Query query = entityManager.createQuery("SELECT pa.constraint.clave FROM cat_porcentajes_accionistas pa "
					+ "ORDER BY CAST(split_part(pa.constraint.clave,'-',2) as integer) asc");
			List<String> results = query.getResultList();
			return results.get(results.size()-1);
		}catch (Exception e) {
			lastClave = "";
		}
		return lastClave;
	}
}
