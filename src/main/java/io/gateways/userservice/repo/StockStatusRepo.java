package io.gateways.userservice.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.gateways.userservice.domain.StockStatus;


@Repository
public interface StockStatusRepo extends JpaRepository<StockStatus, Long> {
	@Query(value = "select * from stock_status u where u.username= ?1 ",nativeQuery = true)
	List<StockStatus> getStockByUser(@Param("username") String username);
	
	@Query(value = "select count(*) from stock_status a where a.username=?1 and a.symbol= ?2",nativeQuery = true)
	int getRecordCount(@Param("username") String username, @Param("symbol")String symbol);
		
	@Modifying
	@Query(value = "update stock_status s set s.qty =s.qty+?1 where  s.username= ?2 and s.symbol=?3",nativeQuery = true)
	void quantityUpdate(@Param("qty") int qty,@Param("username")String username,@Param("symbol")String symbol);
	
	@Modifying
	@Query(value="update stock_status s set s.qty =s.qty-?1 where  s.username= ?2 and s.symbol=?3",nativeQuery = true)
	void quantityReduce(@Param("qty") int qty,@Param("username")String username,@Param("symbol")String symbol);
	
	@Modifying
	@Query(value="delete from stock_status s where s.symbol =?1 and s.username =?2",nativeQuery = true)
	void deleteRecord(@Param("symbol")String symbol,@Param("username")String username);
	
	@Query(value = "select qty from stock_status a where a.username=?1 and a.symbol= ?2",nativeQuery = true)
	String getRecordCountForSell(@Param("username") String username, @Param("symbol")String symbol);
	
}