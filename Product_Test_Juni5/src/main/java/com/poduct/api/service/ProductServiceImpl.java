package com.poduct.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poduct.api.entity.Product;
import com.poduct.api.excepotion.ProductNotFoundExceptoin;
import com.poduct.api.repo.ProductRepo;

@Service

public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepo productRepo;

	public Product saveProduct(Product product) {
		return productRepo.save(product);
	}

	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	@Override
	public Product updateProduct(Product product) {
		Optional<Product> oldProduct = productRepo.findById(product.getId());
		if (oldProduct.isPresent()) {
			return productRepo.save(product);
		}

		return null;
	}

	@Override
	public void deleteProduct(long id) {

		Optional<Product> oldProduct = productRepo.findById(id);
		if (oldProduct.isPresent()) {
			productRepo.deleteById(id);// illigal arg exception for null id
		} else
			throw new ProductNotFoundExceptoin("Product not found with id "+id);
	}

	@Override
	public Optional<Product> findById(long id) {
		return this.productRepo.findById(id);
	}

	@Override
	public void deleteProduct(Product product) {
		this.productRepo.delete(product);
	}
}
