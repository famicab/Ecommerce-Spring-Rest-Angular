package com.ecommerce.service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CreateOrderDTO;
import com.ecommerce.model.LineOrder;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.repository.OrderRepository;
import com.ecommerce.model.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService extends BaseService<Order, Long, OrderRepository>{

	private final ProductService productService;
	
	public Order newOrder(CreateOrderDTO orderDTO, User client) {
		Order o = Order.builder()
		.client(client)
		.lines(orderDTO.getLines().stream()
				.map(lineDTO -> {
					Optional<Product> p = productService.findById(lineDTO.getProductId());
					if(p.isPresent()) {
						Product product = p.get();
						return LineOrder.builder()
								.order(null)
								.quantity(lineDTO.getQuantity())
								.price(product.getPrice())
								.product(product)
								.build();
					} else {
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toSet())
				)
				.build();
		
		o.getLines().forEach(line -> line.setOrder(o));
		
		return save(o);
	}
	
	public Page<Order> findAllByUser(User user, Pageable pageable){
		return repository.findByClient(user, pageable);
	}
}
