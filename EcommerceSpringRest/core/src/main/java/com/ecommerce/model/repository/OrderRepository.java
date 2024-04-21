package com.ecommerce.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Order;
import com.ecommerce.model.user.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Page<Order> findByClient(User client, Pageable pageable);
}
