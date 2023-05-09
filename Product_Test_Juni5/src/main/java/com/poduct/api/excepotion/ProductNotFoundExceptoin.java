package com.poduct.api.excepotion;

public class ProductNotFoundExceptoin extends RuntimeException {

	public ProductNotFoundExceptoin(String msg) {
		super(msg);
		
	}

	private static final long serialVersionUID = 1L;

}
