package com.ecommerce.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "line_order")
public class LineOrder {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private BigDecimal price;

	private int quantity;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public BigDecimal getSubtotal() {
		return price.multiply(BigDecimal.valueOf(quantity));
	}
}
