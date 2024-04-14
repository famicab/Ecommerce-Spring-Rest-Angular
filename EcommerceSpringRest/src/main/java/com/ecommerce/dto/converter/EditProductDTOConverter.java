package com.ecommerce.dto.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ecommerce.dto.EditProductDTO;
import com.ecommerce.model.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EditProductDTOConverter {

	private final ModelMapper modelMapper;

	public EditProductDTO convertToDTO(Product product) {
		return modelMapper.map(product, EditProductDTO.class);

	}
}
