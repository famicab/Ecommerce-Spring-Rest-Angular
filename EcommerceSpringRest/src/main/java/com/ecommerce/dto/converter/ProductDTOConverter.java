package com.ecommerce.dto.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Product;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductDTOConverter {

	private final ModelMapper modelMapper;
	
	@PostConstruct
	public void init() {
		modelMapper.addMappings(new PropertyMap<Product, ProductDTO>() {

			@Override
			protected void configure() {
				map().setCategory(source.getCategory().getName());
			}
		});
	}
	
	public ProductDTO convertToDTO(Product product) {
		return modelMapper.map(product, ProductDTO.class);
		
	}
	
	public ProductDTO convertProductToProductDTO(Product product) {
		return ProductDTO.builder()
				.name(product.getName())
				.image(product.getImage())
				.category(product.getCategory().getName())
				.id(product.getId())
				.build();
	}
}
