package com.topdf.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topdf.project.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}