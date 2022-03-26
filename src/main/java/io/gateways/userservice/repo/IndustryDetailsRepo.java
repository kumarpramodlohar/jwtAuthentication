package io.gateways.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.gateways.userservice.domain.BankDetails;
import io.gateways.userservice.domain.IndustryDetails;

public interface IndustryDetailsRepo extends JpaRepository<IndustryDetails, Long> {

}
