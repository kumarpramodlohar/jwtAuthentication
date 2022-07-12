package io.gateways.userservice.service;

import java.util.List;

import io.gateways.userservice.domain.StockCodes;
import io.gateways.userservice.domain.StockStatus;
import io.gateways.userservice.domain.StockTransactionBuy;
import io.gateways.userservice.domain.StockTransactionSell;

public interface StockTransactionService  {

	String buyStock(StockTransactionBuy stockTransaction);
	String sellStock(StockTransactionSell stockTransactionSell);
	List<StockStatus> getStockByUser(String username);
	List<StockCodes> getStockCodes();
	
}
