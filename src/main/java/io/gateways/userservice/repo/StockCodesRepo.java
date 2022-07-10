package io.gateways.userservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.gateways.userservice.domain.StockCodes;

public interface StockCodesRepo extends JpaRepository<StockCodes, Long>{
	
	@Modifying
	@Query(value = "update stockcodes s set s.Chg =?1,s.ChgPcnt=?2,s.LastRate=?3,s.PClose=?4 where  s.Symbol= ?5",nativeQuery = true)
	void stockDataUpdate(@Param("Chg") String Chg,@Param("ChgPcnt") String ChgPcnt,@Param("LastRate") String LastRate,@Param("PClose") String PClose,@Param("Symbol")String Symbol);

	@Modifying
	@Query(value = "select s.id,s.Exch,s.ExchType,s.Scripcode,s.Symbol,s.Name,s.Chg,s.ChgPcnt,s.LastRate,s.PClose from onlinetradingdb.stockcodes s left join onlinetradingdb.user_watchlist w on w.Symbol = s.Symbol where w.username = ?1",nativeQuery = true)
	List<StockCodes> stockFetchData(@Param("username")String username);
}