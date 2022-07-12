package io.gateways.userservice.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="stock_transaction_sale")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransactionSell {

	@Id
	@GeneratedValue
	private Integer trn_id;
	private String symbol;
	private String username;
	private Integer qty;
	private Double rate;
	private Double net_rate;
	private LocalDateTime date;
	//private Double gain;
	//private Integer buy_trn_id;
}