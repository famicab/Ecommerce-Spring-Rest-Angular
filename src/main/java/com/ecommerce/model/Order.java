package com.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ecommerce.model.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ORDER_ENTITY")
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private User client;
	
	@CreatedDate
	private LocalDateTime dateOrder;
	
	@EqualsAndHashCode.Exclude @ToString.Exclude
	@JsonManagedReference
	@Builder.Default
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LineOrder> lines = new HashSet<>();
	
	public BigDecimal getTotal() {
		Function<LineOrder, BigDecimal> subTotals = lineorder -> lineorder.getSubtotal();
		return lines.stream().map(subTotals).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void addLineOrder(LineOrder lo) {
		lines.add(lo);
		lo.setOrder(this);
	}
	
	public void removeLineOrder(LineOrder lo) {
		lines.remove(lo);
		lo.setOrder(null);
	}
	
}
