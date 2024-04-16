package com.ecommerce.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecommerce.dto.CreateProductDTO;
import com.ecommerce.dto.EditProductDTO;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.converter.ProductDTOConverter;
import com.ecommerce.error.exceptions.ProductNotFoundException;
import com.ecommerce.error.exceptions.SearchProductNoResultException;
import com.ecommerce.model.Product;
import com.ecommerce.model.user.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.pagination.PaginationLinksUtils;
import com.ecommerce.views.ProductViews;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	private final ProductDTOConverter productDTOConverter;
	private final PaginationLinksUtils paginationLinksUtils;
	
	@JsonView(ProductViews.DtoWithPrice.class)
	@GetMapping(value="/product")
	public ResponseEntity<?> searchWithParam(@RequestParam(value = "name") Optional<String> text, 
			@RequestParam(value="price") Optional<BigDecimal> price, Pageable pageable, HttpServletRequest request){

		Page<Product> result = productService.findByArgs(text, price, pageable);

		if(result.isEmpty()) {
			throw new SearchProductNoResultException();
		} else {
			Page<ProductDTO> dtoList = result.map(productDTOConverter::convertToDTO);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			return ResponseEntity.ok().header("Link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
					.body(dtoList);
		}
		
	}
	
	@GetMapping(path="/product/{id}")
	public ResponseEntity<?> searchProductById(@PathVariable Long id){
		Optional<Product> result = productService.findById(id);
		
		if(result.isEmpty()) {
			throw new ProductNotFoundException(id);
		} else {
			ProductDTO dtoProduct = result.map(productDTOConverter::convertToDTO).get();
			
			return ResponseEntity.ok().body(dtoProduct);
		}
	}
	
	@PostMapping(value="/product")
	public ResponseEntity<?> newProduct(@RequestPart("new") CreateProductDTO newProduct, @RequestPart("file") MultipartFile file){
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.newProduct(newProduct, file));
	}
	
	@PutMapping("/product/{id}")
	public Product editProduct(@RequestBody EditProductDTO edit, @PathVariable Long id, @AuthenticationPrincipal User user){
		return productService.findById(id).map(p -> {
			p.setName(edit.getName());
			p.setPrice(edit.getPrice());
			return productService.edit(p);
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
}
