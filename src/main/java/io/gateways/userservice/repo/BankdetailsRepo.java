package io.gateways.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.gateways.userservice.domain.BankDetails;


public interface BankdetailsRepo extends JpaRepository<BankDetails, Long> {

	
}
