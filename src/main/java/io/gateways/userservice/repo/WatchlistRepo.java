package io.gateways.userservice.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.gateways.userservice.domain.WatchlistBean;
public interface WatchlistRepo extends JpaRepository<WatchlistBean, Long> {

	@Modifying
	@Query(value = "select * from user_watchlist where user = ?1",nativeQuery = true)
	List<WatchlistBean> getWatchList(String username);
}
