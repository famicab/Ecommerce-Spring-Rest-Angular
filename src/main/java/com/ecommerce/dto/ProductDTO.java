package com.ecommerce.dto;

import java.math.BigDecimal;

import com.ecommerce.views.ProductViews;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductDTO {

	@JsonView(ProductViews.Dto.class)
	private long id;
	@JsonView(ProductViews.Dto.class)
	private String name;
	@JsonView(ProductViews.Dto.class)
	private String image;
	@JsonView(ProductViews.DtoWithPrice.class)
	private BigDecimal price;
	@JsonView(ProductViews.Dto.class)
	private String category;
}
