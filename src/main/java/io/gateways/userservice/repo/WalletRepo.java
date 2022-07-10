package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.gateways.userservice.domain.Wallet;

public interface WalletRepo extends JpaRepository<Wallet, Long>{

	@Query(value = "select * from Wallet u where u.username= ?1 ",nativeQuery = true)
	List<Wallet> findByUsername(@Param("username") String username);
	
	@Modifying
	@Query(value = "update Wallet w set w.balance =w.balance-?1 where  w.username= ?2",nativeQuery = true)
	void walletUpdate(@Param("net_rate") String net_rate,@Param("username")String username);
	
	@Modifying
	@Query(value = "update Wallet w set w.balance =w.balance+?1 where  w.username= ?2",nativeQuery = true)
	void walletUpdateAdd(@Param("net_rate") String net_rate,@Param("username")String username);

}
