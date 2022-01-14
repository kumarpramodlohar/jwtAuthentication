package io.gateways.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.gateways.userservice.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
