package com.poduct.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poduct.api.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

}
