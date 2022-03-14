/**
 * 
 */
package com.intech.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Akash Budhwani
 *
 */
@ControllerAdvice
public class Helper {
	
	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<String> handleRunTimeException(RuntimeException ex) {
		return error(HttpStatus.NOT_FOUND, ex);
	}
	
	private ResponseEntity<String> error(HttpStatus status,Exception e){
		return ResponseEntity.status(status).body(e.getMessage());
	}
}
