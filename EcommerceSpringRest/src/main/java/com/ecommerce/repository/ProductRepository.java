package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findById(long id);
	
	List<Product> findByCategory(Category c);
}
