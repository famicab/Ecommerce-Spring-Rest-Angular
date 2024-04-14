package com.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateBatchDTO {

	private String name;
	@Builder.Default
	private List<Long> products = new ArrayList<>();
}
