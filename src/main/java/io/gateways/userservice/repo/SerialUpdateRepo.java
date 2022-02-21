package io.gateways.userservice.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.gateways.userservice.domain.SerialUpdate;

@Transactional
public interface SerialUpdateRepo extends JpaRepository<SerialUpdate, Long> {
@Query(value="select sl_no from serial_update where sl_type=:sl_type",nativeQuery = true)
	Integer getSerial(String sl_type);
		// TODO Auto-generated method stub
@Modifying
@Query(value="update serial_update set sl_no=:sl_no where sl_type=:sl_type",nativeQuery = true)
    void updateSerial(String sl_type,int sl_no);

	
}
