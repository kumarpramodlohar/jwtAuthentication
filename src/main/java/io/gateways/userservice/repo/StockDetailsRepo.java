package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gateways.userservice.domain.StockDetails;

@Repository
public interface StockDetailsRepo extends JpaRepository<StockDetails, Long>{
	List<StockDetails> findAll();
}
