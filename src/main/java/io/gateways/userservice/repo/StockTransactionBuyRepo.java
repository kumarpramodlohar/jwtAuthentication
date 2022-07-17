package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.gateways.userservice.domain.StockTransactionBuy;

@Repository
public interface StockTransactionBuyRepo extends JpaRepository<StockTransactionBuy, Long>{

	 @Query(value = "select * from stock_transaction_buy u where u.username= ?1 ",nativeQuery = true)
	List<StockTransactionBuy> findBuydetailsByUsername(@Param("username") String username);
	 
//	 @Query(value = "SELECT SUM(net_rate) FROM stock_transaction_buy s where s.username=?1",nativeQuery = true)
//	 Integer getTotalSpent(@Param("username") String username);
	
	 @Query(value = "SELECT * FROM onlinetradingdb.stock_transaction_buy tt where tt.status='N' or tt.status='PS' and tt.username=?1 and tt.symbol=?2  order by tt.date asc", nativeQuery = true)
		List<StockTransactionBuy> findQtyAndRate(String userName,String symbol);

	 @Modifying
	 @Query(value = "update onlinetradingdb.stock_transaction_buy tt set tt.sold_qty=?1 where tt.username=?2 and tt.symbol=?3", nativeQuery=true)
		int updateSoldQty(Integer sold_qty, String username,String symbol);
	 
	 @Modifying
	 @Query(value = "update onlinetradingdb.stock_transaction_buy tt set tt.status=?1 where tt.username=?2 and tt.symbol=?3", nativeQuery=true)
		int updateStatus(String status, String username,String symbol);
	 
}
