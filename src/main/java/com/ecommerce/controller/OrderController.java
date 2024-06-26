package com.ecommerce.controller;

import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecommerce.dto.CreateOrderDTO;
import com.ecommerce.dto.GetOrderDTO;
import com.ecommerce.dto.converter.OrderDTOConverter;
import com.ecommerce.error.exceptions.OrderNotFoundException;
import com.ecommerce.model.Order;
import com.ecommerce.model.user.User;
import com.ecommerce.model.user.UserRole;
import com.ecommerce.service.OrderService;
import com.ecommerce.util.pagination.PaginationLinksUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "4. Order", description = "Order Endpoint")
public class OrderController {

	private final OrderService orderService;
	private final PaginationLinksUtils paginationLinksUtils;
	private final OrderDTOConverter orderDTOConverter;
	
	@SecurityRequirement(name = "App Bearer Token")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "Retrieves orders",
			   description = "Retrieves all the orders associated to the user that performs the request. If the user is admin:Admin1, retrieves every order.")
	@GetMapping("/")
	public ResponseEntity<Page<GetOrderDTO>> orders(@ParameterObject Pageable pageable, HttpServletRequest request,
			@AuthenticationPrincipal User user) throws Exception {
		Page<Order> result = null;
		
		if(user.hasRole(UserRole.ADMIN)) {
			result = orderService.findAll(pageable);
		} else {
			result = orderService.findAllByUser(user, pageable);
		}
		
		if(result.isEmpty()) {
			throw new OrderNotFoundException();
		} else {
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
			
			Page<GetOrderDTO> dtoPage = result.map(orderDTOConverter::convertOrderToGetOrderDTO);
			return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(result, uriBuilder)).body(dtoPage);
		}
	}
	
	@SecurityRequirement(name = "App Bearer Token")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary="Create a new order",
			   description = "Every user can create a new order")
	@PostMapping("/")
	public ResponseEntity<GetOrderDTO> newOrder(@RequestBody CreateOrderDTO order, @AuthenticationPrincipal User user){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(orderDTOConverter.convertOrderToGetOrderDTO(orderService.newOrder(order, user)));
	}
	
	@SecurityRequirement(name = "App Bearer Token")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary="Delete an order",
	   description = "Only admin:Admin1 can delete orders")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long id, @AuthenticationPrincipal User user) throws OrderNotFoundException {
		Optional<Order> delete = orderService.findById(id);

		if(user.hasRole(UserRole.ADMIN) && delete.isPresent()) {
			orderService.delete(delete.get());
			
			return ResponseEntity.ok("Order deleted succesfully");
		} else {
			throw new OrderNotFoundException(id);
		}
	}
}
