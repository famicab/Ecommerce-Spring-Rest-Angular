package com.ecommerce.dto.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ecommerce.dto.GetOrderDTO;
import com.ecommerce.model.LineOrder;
import com.ecommerce.model.Order;

@Component
public class OrderDTOConverter {

	public GetOrderDTO convertOrderToGetOrderDTO(Order order) {

		return GetOrderDTO.builder().fullName(order.getClient().getFullName()).avatar(order.getClient().getAvatar())
				.email(order.getClient().getEmail()).date(order.getDateOrder()).total(order.getTotal()).lines(order
						.getLines().stream().map(this::convertLineOrderToGetLineOrderDTO).collect(Collectors.toSet()))
				.build();

	}

	public GetOrderDTO.GetLineOrderDTO convertLineOrderToGetLineOrderDTO(LineOrder line) {
		return GetOrderDTO.GetLineOrderDTO.builder().quantity(line.getQuantity()).unitPrice(line.getPrice())
				.productName(line.getProduct().getName()).subTotal(line.getSubtotal()).build();
	}
}
