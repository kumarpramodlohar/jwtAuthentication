package io.gateways.userservice.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gateways.userservice.domain.StockCodes;
import io.gateways.userservice.domain.StockStatus;
import io.gateways.userservice.domain.StockTransaction;
import io.gateways.userservice.domain.StockTransactionSell;
import io.gateways.userservice.repo.StockCodesRepo;
import io.gateways.userservice.repo.StockStatusRepo;
import io.gateways.userservice.repo.StockTransactionRepo;
import io.gateways.userservice.repo.StockTransactionSellRepo;
import io.gateways.userservice.repo.WalletRepo;

@Service
@Transactional
public class StockTransactionServiceimpl implements StockTransactionService {

	@Autowired
	private StockTransactionRepo stockTransactionRepo;
	
	@Autowired
	private WalletRepo walletRepo;
	@Autowired
	private StockTransactionSellRepo stockTransactionSellRepo;
	@Autowired
	private StockStatusRepo stockStatusRepo;
	@Autowired
	private StockCodesRepo stockCodesRepo;
	
	@Override
	public String buyStock(StockTransaction stockTransaction) {
		// TODO Auto-generated method stub
		
		long milliseconds = System.currentTimeMillis();
		LocalDateTime Date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
		stockTransaction.setDate(Date);	
		stockTransaction.setStatus("N");
		stockTransaction.setSold_qty(0);
		double netRate = stockTransaction.getNet_rate();
		netRate += netRate*0.02;
		String username = stockTransaction.getUsername();
		int count=stockStatusRepo.getRecordCount(stockTransaction.getUsername(),stockTransaction.getSymbol());
		if(count>0) {
			stockStatusRepo.quantityUpdate(stockTransaction.getQty(), username,stockTransaction.getSymbol());
		}else {
			StockStatus obj = new StockStatus();
			obj.setUsername(username);
			obj.setSymbol(stockTransaction.getSymbol());
			obj.setQty(stockTransaction.getQty());
			stockStatusRepo.save(obj);
		}		
		stockTransactionRepo.save(stockTransaction);
		walletRepo.walletUpdate(""+netRate, username);
		return "success";
	}
//	@Override
//	public String sellStock(StockTransactionSell stockTransactionSell) {
//		
//		long milliseconds = System.currentTimeMillis();
//		LocalDateTime Date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
//		stockTransactionSell.setDate(Date);
//		double netRate = stockTransactionSell.getNet_rate();
//		netRate -= netRate*0.02;
//		String username = stockTransactionSell.getUsername();
//		List<StockTransaction> buyData= stockTransactionRepo.findQtyAndRate(username);
//		 
//		for(StockTransaction data : buyData){
//			StockTransaction objBuy = new StockTransaction();
//			Double gain=stockTransactionSell.getRate() - data.getRate();
//			String symbol=stockTransactionSell.getSymbol();
//			//stockTransactionSell.setGain(gain);
//			stockTransactionSellRepo.updateGain(gain,username,symbol);
//			stockTransactionSellRepo.save(stockTransactionSell);
//			
//			String status="Y";
//		
//			Integer sold_qty=stockTransactionSell.getQty();
//			
//			stockTransactionRepo.updateStatusAndSoldQty(status,sold_qty,username);
//		}
//		stockTransactionSellRepo.save(stockTransactionSell);
//		walletRepo.walletUpdateAdd(""+netRate, username);
//		return "success";
//	}
	@Override
	public String sellStock(StockTransactionSell stockTransactionSell) {
		// TODO Auto-generated method stub
		long milliseconds = System.currentTimeMillis();
		LocalDateTime Date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
		stockTransactionSell.setDate(Date);
		double netRate = stockTransactionSell.getNet_rate();
		netRate -= netRate*0.02;
		String username = stockTransactionSell.getUsername();
		stockTransactionSellRepo.save(stockTransactionSell);
		int records = stockStatusRepo.getRecordCount(username, stockTransactionSell.getSymbol());
		if((records-stockTransactionSell.getQty()) == 0) {
			stockStatusRepo.deleteRecord(stockTransactionSell.getSymbol(), username);
		}else {
			stockStatusRepo.quantityReduce(stockTransactionSell.getQty(), username,stockTransactionSell.getSymbol());
		}
		
		walletRepo.walletUpdateAdd(""+netRate, username);
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