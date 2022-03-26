package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.gateways.userservice.domain.Wallet;

public interface WalletRepo extends JpaRepository<Wallet, Long>{

	@Query(value = "select * from Wallet u where u.username= ?1 and u.status= 'Y'",nativeQuery = true)
	List<Wallet> findByUsername(@Param("username") String username);

}
