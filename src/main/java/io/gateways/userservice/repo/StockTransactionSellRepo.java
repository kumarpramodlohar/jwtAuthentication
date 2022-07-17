package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.gateways.userservice.domain.StockStatus;
import io.gateways.userservice.domain.StockTransactionSell;

public interface StockTransactionSellRepo extends JpaRepository<StockTransactionSell, Long>{
	
	@Query(value = "select * from stock_transaction_sale u where u.username= ?1 ",nativeQuery = true)
	List<StockTransactionSell> findSelldetailsByUsername(@Param("username") String username);

//	@Query(value = "SELECT SUM(net_rate) FROM stock_transaction_sale s where s.username=?1",nativeQuery = true)
//	Integer getTotalSold(@Param("username") String username);
	
	@Modifying
	@Query(value = "update onlinetradingdb.stock_transaction_sale t1 set t1.gain=?1 where t1.username=?2 and t1.symbol=?3 and t1.trn_id=?4", nativeQuery=true)
	int updateGain(Double gain, String username,String symbol,Integer trn_id);
	
	
}
