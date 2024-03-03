package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductRepository productRepository;
	
	@GetMapping(path="/category/{idCategory}")
	public ResponseEntity<?> getProductsByCategory(@PathVariable long idCategory){
		Category c = new Category();
		c.setId(idCategory);
		
		List<Product> products = productRepository.findByCategory(c);
		
		return ResponseEntity.ok(products);
	}
}
