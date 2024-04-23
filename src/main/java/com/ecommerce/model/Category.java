package com.ecommerce.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	@JsonManagedReference
	private Set<Product> products;
	
}
