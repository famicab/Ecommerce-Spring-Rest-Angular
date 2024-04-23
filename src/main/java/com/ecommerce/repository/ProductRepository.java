package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{

	Optional<Product> findById(long id);
	
	Page<Product> findByNameContainsIgnoreCase(String text, Pageable pageable);
	
	@Query("select p from Product p LEFT JOIN FETCH p.batches WHERE p.id = :id")
	Optional<Product> findByIdJoinFetch(Long id);
	
	List<Product> findByCategory(Category c);

	//Page<Product> findByArgs(Optional<String> text, Optional<BigDecimal> price, Pageable pageable);	
}
