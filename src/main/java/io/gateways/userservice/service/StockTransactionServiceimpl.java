package io.gateways.userservice.service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gateways.userservice.domain.StockCodes;
import io.gateways.userservice.domain.StockStatus;
import io.gateways.userservice.domain.StockTransactionBuy;
import io.gateways.userservice.domain.StockTransactionSell;
import io.gateways.userservice.repo.StockCodesRepo;
import io.gateways.userservice.repo.StockStatusRepo;
import io.gateways.userservice.repo.StockTransactionBuyRepo;
import io.gateways.userservice.repo.StockTransactionSellRepo;
import io.gateways.userservice.repo.WalletRepo;

@Service
@Transactional
public class StockTransactionServiceimpl implements StockTransactionService {

	@Autowired
	private StockTransactionBuyRepo stockTransactionBuyRepo;
	
	@Autowired
	private WalletRepo walletRepo;
	@Autowired
	private StockTransactionSellRepo stockTransactionSellRepo;
	@Autowired
	private StockStatusRepo stockStatusRepo;
	@Autowired
	private StockCodesRepo stockCodesRepo;

	
	@Override
	public String buyStock(StockTransactionBuy stockTransaction) {
		
		Long milliseconds = System.currentTimeMillis();
		LocalDateTime Date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
		stockTransaction.setDate(Date);	
		DecimalFormat df = new DecimalFormat("###.##");
		Double netRate = stockTransaction.getNet_rate() *0.02;
		df.format(netRate);
		Double finalNetAmount=stockTransaction.getNet_rate()+netRate;
		String username = stockTransaction.getUsername();
		Integer count=stockStatusRepo.getRecordCount(stockTransaction.getUsername(),stockTransaction.getSymbol());
		if(count>0) {
			stockStatusRepo.quantityUpdate(stockTransaction.getQty(), username,stockTransaction.getSymbol());
			stockStatusRepo.totalBuyUpdate(stockTransaction.getNet_rate(), username,stockTransaction.getSymbol());
		}else {
			StockStatus obj = new StockStatus();
			obj.setUsername(username);
			obj.setSymbol(stockTransaction.getSymbol());
			obj.setQty(stockTransaction.getQty());
			obj.setTotal_buy_amt(stockTransaction.getNet_rate());
			obj.setTotal_sale_amt(0.00);
			stockStatusRepo.save(obj);
		}		
		stockTransactionBuyRepo.save(stockTransaction);
		walletRepo.walletUpdate(""+finalNetAmount, username);
		return "success";
	}
	@Override
	public String sellStock(StockTransactionSell stockTransactionSell) {
		
		Long milliseconds = System.currentTimeMillis();
		LocalDateTime Date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
		stockTransactionSell.setDate(Date);
		DecimalFormat df = new DecimalFormat("###.##");
		Double netRate = stockTransactionSell.getNet_rate()*0.02;
		
		df.format(netRate);
		String username = stockTransactionSell.getUsername();
		Double finalNetAmount=stockTransactionSell.getNet_rate()+netRate;
		
		
			String stockQty=stockStatusRepo.getRecordCountForSell(username,stockTransactionSell.getSymbol());
			Integer finalQty=Integer.valueOf(stockQty) - stockTransactionSell.getQty();
			
			if(finalQty >=0) {
				stockTransactionSellRepo.save(stockTransactionSell);
	
				stockStatusRepo.quantityReduce(stockTransactionSell.getQty(), username,stockTransactionSell.getSymbol());
				stockStatusRepo.totalSaleUpdate(stockTransactionSell.getNet_rate(), username,stockTransactionSell.getSymbol());
				walletRepo.walletUpdateAdd(""+finalNetAmount, username);
			}
		
			/*
			 * if(finalQty < 0) {
			 * 
			 * return "faield"; }
			 */
			

		
	return "success";
	}
	

	@Override
	public List<StockStatus> getStockByUser(String username) {
		// TODO Auto-generated method stub
		return stockStatusRepo.getStockByUser(username);
	}
	@Override
	public List<StockCodes> getStockCodes() {
		// TODO Auto-generated method stub
		return stockCodesRepo.findAll();
	}
}