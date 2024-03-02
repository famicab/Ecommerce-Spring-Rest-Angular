package com.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	private long id;
	private String name;
	private String description;
	private Category category;
}
