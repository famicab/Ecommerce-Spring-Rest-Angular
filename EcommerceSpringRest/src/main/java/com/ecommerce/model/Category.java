package com.ecommerce.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {

	@Id
	@Column(name="ID_CATEGORY")
	private long id;
	@Column
	private String name;
	@Column
	private String description;
	
	@OneToMany(mappedBy = "category")
	private Set<Product> products;
	
}
