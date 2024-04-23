package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.model.user.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query("update User u set u.password =?2 where u.username =?1")
	int	setNewPassword(String username, String newPassword);
}
