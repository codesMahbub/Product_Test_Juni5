package com.poduct.api.service;

import java.util.List;
import java.util.Optional;

import com.poduct.api.entity.Product;

public interface ProductService {

	public Product saveProduct(Product product);
	public List<Product> getAllProducts();
	public Product updateProduct(Product product);
	public Optional<Product> findById(long id );
	public void deleteProduct(Product product);
	public void deleteProduct(long id );

}
