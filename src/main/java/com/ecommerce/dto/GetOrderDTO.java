package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetOrderDTO {

	private String fullName;
	private String email;
	private String avatar;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime date;
	private Set<GetLineOrderDTO> lines;
	private BigDecimal total;
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class GetLineOrderDTO {	
		private String productName;
		private int quantity;
		private BigDecimal unitPrice;
		private BigDecimal subTotal;
	}
}
