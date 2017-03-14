/**
 * 
 */
package com.pallette.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pallette.response.ExceptionResponse;

/**
 * <p>
 * A unified exception handler.
 * </p>
 * 
 * @author amall3
 *
 */

@ControllerAdvice
public class ErrorHandlingController extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingController.class);

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) throws Exception {
		
		ExceptionResponse response = new ExceptionResponse();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(e.getMessage());
		logger.error(e.getMessage());

		return new ResponseEntity<ExceptionResponse>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { NoRecordsFoundException.class })
	public ResponseEntity<ExceptionResponse> handleNoRecordsFoundException(NoRecordsFoundException e) throws Exception {
		
		ExceptionResponse response = new ExceptionResponse();
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		response.setMessage(e.getMessage());
		logger.error(e.getMessage());

		return new ResponseEntity<ExceptionResponse>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
