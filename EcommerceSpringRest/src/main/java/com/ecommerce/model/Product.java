package com.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PRODUCT")
public class Product {

	@EqualsAndHashCode.Include
	@Id
	@Column(name="ID_PRODUCT")
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@JoinColumn(name = "ID_CATEGORY")
	@ManyToOne(fetch=FetchType.LAZY)
	private Category category;
}
