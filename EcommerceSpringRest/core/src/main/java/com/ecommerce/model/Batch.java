package com.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Batch.class)
public class Batch {

	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(
			joinColumns = @JoinColumn(name="batch_id"),
			inverseJoinColumns = @JoinColumn(name="product_id")
	)
	@Builder.Default
	private Set<Product> products = new HashSet<>();
	
	public void addProduct(Product p) {
		this.products.add(p);
		p.getBatches().add(this);
	}
	
	public void deleteProducto(Product p) {
		this.products.remove(p);
		p.getBatches().remove(this);
	}
}
