package org.quickbundle.third.spring.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RmResponseEntityFactory {
	
	public static ResponseEntity build(Set<? extends ConstraintViolation> constraintViolations, HttpStatus statusCode) {
		Map<String, String> errorMessages = new HashMap<String, String>();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return new ResponseEntity(errorMessages, statusCode);
	}
}
