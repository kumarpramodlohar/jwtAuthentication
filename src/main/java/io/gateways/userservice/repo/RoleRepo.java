package io.gateways.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.gateways.userservice.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
