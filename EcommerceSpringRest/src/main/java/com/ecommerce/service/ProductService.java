package com.ecommerce.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.ecommerce.controller.FilesController;
import com.ecommerce.dto.CreateProductDTO;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.upload.StorageService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService<Product, Long, ProductRepository>{

	private final CategoryService categoryService;
	private final StorageService storageService;
	
	public Product newProduct(CreateProductDTO newProductDTO, MultipartFile file) {
		String imageUrl = null;
		
		if(!file.isEmpty()) {
			String image = storageService.store(file);
			imageUrl = MvcUriComponentsBuilder.fromMethodName(FilesController.class, "serveFile", image, null)
					.build().toUriString();
		}
		
		Product newProduct = Product.builder()
				.name(newProductDTO.getName())
				.price(newProductDTO.getPrice())
				.image(imageUrl)
				.category(categoryService.findById(newProductDTO.getIdCategory()).orElse(null))
				.build();
		
		return this.save(newProduct);
	}
	
	public Page<Product> findByName(String txt, Pageable pageable){
		return this.repository.findByNameContainsIgnoreCase(txt, pageable);
	}
	
	public Page<Product> findByArgs(final Optional<String> name, final Optional<BigDecimal> price, Pageable pageable){
		Specification<Product> specProductName = new Specification<Product>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(name.isPresent()) {
					return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+ name.get() + "%");
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
			
		};
		
		Specification<Product> specPriceLowerThan = new Specification<Product>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				if(price.isPresent()) {
					return criteriaBuilder.lessThanOrEqualTo(root.get("price"), price.get());
				} else {
					return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
				}
			}
			
		};
		
		Specification<Product> both = specProductName.and(specPriceLowerThan);
		
		return this.repository.findAll(both, pageable);
	}
	
	public Optional<Product> findByIdWithBatches(Long id){
		return repository.findByIdJoinFetch(id);
	}
}
