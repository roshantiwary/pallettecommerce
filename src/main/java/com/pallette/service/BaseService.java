package com.pallette.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.pallette.exception.ValidationException;

public abstract class BaseService {
	
	protected Validator validator;

	public BaseService(Validator validator) {
		this.validator = validator;
	}

	protected void validate(Object request) {
		Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
		if (constraintViolations.size() > 0) {
			throw new ValidationException(constraintViolations);
		}
	}

}
