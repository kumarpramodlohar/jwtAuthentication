package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.gateways.userservice.domain.StockTransaction;

@Repository
public interface StockTransactionRepo extends JpaRepository<StockTransaction, Long>{

	 @Query(value = "select * from stock_transaction u where u.username= ?1 ",nativeQuery = true)
		List<StockTransaction> findBuydetailsByUsername(@Param("username") String username);
	 @Query(value = "SELECT SUM(net_rate) FROM stock_transaction s where s.username=?1",nativeQuery = true)
	 Integer getTotalSpent(@Param("username") String username);
	 @Query(value = "SELECT * FROM onlinetradingdb.stock_transaction tt where tt.status='N' and tt.username=?1  order by tt.date asc", nativeQuery = true)
		List<StockTransaction> findQtyAndRate(String userName);

	 @Modifying
	 @Query
	 (value = "update onlinetradingdb.stock_transaction tt set tt.status=?1, tt.sold_qty=?2 where tt.username=?3", nativeQuery=true)
		int updateStatusAndSoldQty(String status,Integer sold_qty, String username);
	 
}
