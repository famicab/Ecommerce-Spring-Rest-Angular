package com.ecommerce.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecommerce.dto.CreateProductDTO;
import com.ecommerce.dto.EditProductDTO;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.converter.ProductDTOConverter;
import com.ecommerce.error.exceptions.NotEnoughPrivilegesException;
import com.ecommerce.error.exceptions.ProductNotFoundException;
import com.ecommerce.error.exceptions.SearchProductNoResultException;
import com.ecommerce.model.Product;
import com.ecommerce.model.user.User;
import com.ecommerce.model.user.UserRole;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.pagination.PaginationLinksUtils;
import com.ecommerce.views.ProductViews;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "3. Product", description = "Product Endpoint")
public class ProductController {

	private final ProductService productService;
	private final ProductDTOConverter productDTOConverter;
	private final PaginationLinksUtils paginationLinksUtils;
	
	@Operation(summary = "Search a product.",
			   description = "Retrieve a list of products. You can retrieve all or filter by name (contains) and/or price (lower than).")
	@JsonView(ProductViews.DtoWithPrice.class)
	@GetMapping(value="/product")
	public ResponseEntity<?> searchWithParam(@RequestParam(value = "name") @Parameter(name="name", example="smart") Optional<String> text, 
			@RequestParam(value="price") @Parameter(name="price", example="700") Optional<BigDecimal> price, @ParameterObject Pageable pageable, HttpServletRequest request){

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
	
	@Operation(summary = "Search product by id. Already inserted values range from 1 to 30")
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
	
	@SecurityRequirement(name = "App Bearer Token")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "Insert a new product",
				description = "Requires authentication. Login as admin:Admin1")
	@PostMapping(value="/product")
	public ResponseEntity<?> newProduct(@RequestBody CreateProductDTO newProduct){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = (User) authentication.getPrincipal();
        
        if (!u.getRoles().contains(UserRole.ADMIN)) {
        	throw new NotEnoughPrivilegesException();
        }
        //TODO add functionality to upload a product with image
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.newProduct(newProduct, null));
	}
	
	@SecurityRequirement(name = "App Bearer Token")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "Edit a product",
				description = "Requires authentication. Login as admin:Admin1")
	@PutMapping("/product/{id}")
	public Product editProduct(@RequestBody EditProductDTO edit, @PathVariable Long id, @AuthenticationPrincipal User user){
		
		return productService.findById(id).map(p -> {
			p.setName(edit.getName());
			p.setPrice(edit.getPrice());
			if(user != null && user.getRoles().contains(UserRole.ADMIN)) {
				return productService.edit(p);
			} else {
				throw new NotEnoughPrivilegesException();
			}
		}).orElseThrow(() -> new ProductNotFoundException(id));
	}
}
