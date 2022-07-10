package io.gateways.userservice.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gateways.userservice.domain.StockDetails;
import io.gateways.userservice.domain.StockStatus;
import io.gateways.userservice.domain.StockTransaction;
import io.gateways.userservice.domain.StockTransactionSell;
import io.gateways.userservice.domain.Wallet;
import io.gateways.userservice.service.StockTransactionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Transaction  {

	@Autowired
	private StockTransactionService stockTransactionService;
	
	@PostMapping("/buyStock")
	public String buyStock(@RequestBody StockTransaction stockTransaction ) {
        stockTransactionService.buyStock(stockTransaction);		
		return "Success";
		
	}
	@PostMapping("/sellStock")
	public String sellStock(@RequestBody StockTransactionSell stockTransactionSell ) {
        stockTransactionService.sellStock(stockTransactionSell);		
		return "Success";
		
	}
	@GetMapping("/fetchStockByUser/{username}")
	public ResponseEntity<List<StockStatus>> getStockByUser(@PathVariable("username")String username) {
		return ResponseEntity.ok().body(stockTransactionService.getStockByUser(username));
	}
}
