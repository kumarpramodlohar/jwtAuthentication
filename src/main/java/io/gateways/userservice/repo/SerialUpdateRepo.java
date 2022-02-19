package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.gateways.userservice.domain.SerialUpdate;

public interface SerialUpdateRepo extends JpaRepository<SerialUpdate, Long> {
@Query(value="from SerialUpdate where sl_type=?1")
	Integer getserial(String sl_type);
		// TODO Auto-generated method stub


	
}
