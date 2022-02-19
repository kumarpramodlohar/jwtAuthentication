package io.gateways.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.gateways.userservice.domain.User;
import io.gateways.userservice.domain.UserDetails;

public interface UserdetailsRepo extends JpaRepository<UserDetails, Long> {

	
}
