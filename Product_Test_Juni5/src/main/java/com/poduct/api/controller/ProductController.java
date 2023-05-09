package com.poduct.api.controller; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poduct.api.entity.Product;
import com.poduct.api.service.ProductService;

@RestController
@RequestMapping("/Products")
public class ProductController {

	
	@Autowired 
	ProductService service;
	
	@PostMapping
	public Product saveProduct(@RequestBody Product product)
	{
		return service.saveProduct(product);
	}
	@GetMapping("/{id}")
	public Product getproductById(@PathVariable long id )
	{
		return service.findById(id).orElseGet(null);
	}
	
	@GetMapping
	public List<Product> getAllProducts()
	{
		return service.getAllProducts();
	}
	@PutMapping
	public Product updateProduct(@RequestBody Product product)
	{
		return service.updateProduct(product);
	}
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable long id )
	{
		service .deleteProduct(id);
	}
}
