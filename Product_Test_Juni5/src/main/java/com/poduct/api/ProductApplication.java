package com.poduct.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.poduct.api.entity.Product;
import com.poduct.api.repo.ProductRepo;

@SpringBootApplication
public class ProductApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Autowired
	ProductRepo productRepo;
	
	@Override
	public void run(String... args) throws Exception {
		Product p =new Product();
		p.setTitle("NewProduct");
		p.setPrice(4.44);
		productRepo.save(p);
	}

}
