package com.fonyou.api;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fonyou.entities.InvalidEntityException;

/**
 * Global API exception handler. The exceptions that are currently being 
 * handled are found.
 * @author Johan Ballesteros.
 * @version 1.0.0.
 */
@RestControllerAdvice
public class APIExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<APIException> handleInvalidEntityFields(SQLIntegrityConstraintViolationException exception) {
		return ResponseEntity.ok(new APIException(exception.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<APIException> handleInvalidRequest(HttpMessageNotReadableException exception) {
		return ResponseEntity.badRequest().body(new APIException("The request doesn't meet the expected standard."));
	}
	
	@ExceptionHandler
	public ResponseEntity<APIException> handleInvalidEntity(InvalidEntityException exception) {
		return ResponseEntity.ok(new APIException(exception.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<APIException> handleInvalidEntity(EmptyResultDataAccessException exception) {
		return ResponseEntity.ok(new APIException("There is no entity with the given parameters."));
	}
	
}
