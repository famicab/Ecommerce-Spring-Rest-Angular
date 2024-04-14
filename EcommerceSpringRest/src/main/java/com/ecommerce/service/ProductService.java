package com.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.ecommerce.controller.FilesController;
import com.ecommerce.dto.CreateProductDTO;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.upload.StorageService;

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
}
