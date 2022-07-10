package io.gateways.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.gateways.userservice.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	@Query(value = "select * from User u where u.username= ?1 and u.status= 'Y'",nativeQuery = true)
	User findByUsername(@Param("username") String username);
	
	@Modifying
	@Query(value = "update User p set p.status = 'N' where  p.client_id= ?1",nativeQuery = true)
	void userDeactivate (@Param("client_id") String client_id);

	@Modifying
	@Query(value = "update wallet w set w.status = 'Y' , w.balance=?2 where  w.client_id= ?1",nativeQuery = true)
	void updateWallet(@Param("client_id")String client_id, @Param("balance")int balance);
}
