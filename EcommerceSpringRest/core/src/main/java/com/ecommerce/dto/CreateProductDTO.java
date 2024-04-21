package com.ecommerce.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductDTO {

	private String name;
	private BigDecimal price;
	private long idCategory;
}
