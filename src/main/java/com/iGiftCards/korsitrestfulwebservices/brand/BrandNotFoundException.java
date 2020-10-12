package com.iGiftCards.korsitrestfulwebservices.brand;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BrandNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BrandNotFoundException(String message) {
		super(message);
	}
}

