package com.ecommerce.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class EditProductDTO {

	private String name;
	private BigDecimal price;
}
