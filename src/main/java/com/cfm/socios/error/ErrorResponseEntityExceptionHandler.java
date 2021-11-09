package com.cfm.socios.error;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.cfm.socios.exception.BusinessException;
import com.cfm.socios.model.ErrorDescripcion;
import com.cfm.socios.model.ErrorDetalle;
import com.cfm.socios.util.LogHandler;

@ControllerAdvice
@RestController
public class ErrorResponseEntityExceptionHandler {
	
	private static final String NIVEL_ERROR="ERROR";

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<ErrorDetalle> handleNotReadable(HttpMessageNotReadableException ex,
			WebRequest request) {
		LogHandler.error(null, getClass(), "Error del cliente, envió mal los datos ", ex);
		ErrorDetalle details = this.crearErrorDetalle("SH00001", "Cliente envi\u00f3 request desconocido",
				"No se puede procesar entrada de datos", "Corrija su request.", NIVEL_ERROR);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
	}
	
	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<ErrorDetalle> handleBusinessException(BusinessException ex, WebRequest request) {
		LogHandler.error(null, getClass(), "Ocurrio un error de negocio ", ex);
		ErrorDetalle details = this.crearErrorDetalle("SH00002", "Intente mas tarde",
				ex.getMessage(), "Error. Capa de servicio.", NIVEL_ERROR);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ErrorDetalle> handleValidacionException(MethodArgumentNotValidException ex, WebRequest request) {
		LogHandler.error(null, getClass(), "Ocurrio un error de negocio ", ex);
		ErrorDetalle details = this.crearErrorDetalle("SH00003", "Intente mas tarde",
				ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), "Error. ValidacionDatos.", NIVEL_ERROR);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
	}
	
	//DATOS NULOS
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorDetalle> handleValidacionData(ConstraintViolationException ex, WebRequest request) {
		LogHandler.error(null, getClass(), "Ocurrio un error de negocio", ex);
		ErrorDetalle details = this.crearErrorDetalle("SH00004", "Intente mas tarde",
				ex.getMessage(), "Error. ValidacionDatos.", NIVEL_ERROR);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
	}
	
	private ErrorDetalle crearErrorDetalle(String codigo, String descripcion, String detalle, String mensaje,
			String nivel) {
		ErrorDetalle errorDetails = new ErrorDetalle();
		ErrorDescripcion detail = new ErrorDescripcion();
		detail.setCodigo(codigo);
		detail.setDescripcion(descripcion);
		detail.setDetalle(detalle);
		detail.setNivel(nivel);
		detail.setMensaje(mensaje);
		List<ErrorDescripcion> errors = new ArrayList<>();
		errors.add(detail);
		errorDetails.setErrors(errors);
		return errorDetails;
	}

}
