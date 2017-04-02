package com.jawahar.springboot.controllers;

import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest httpRequest) {
		logger.error("> handleException");
		logger.error("- exception " + exception);
		
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(
				exception, httpRequest, HttpStatus.INTERNAL_SERVER_ERROR);
		
		logger.error("< handleException");
		
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<Map<String, Object>> handleRequestException (NoResultException noResultException, HttpServletRequest request) {
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(noResultException, request, HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
	}
	
}
