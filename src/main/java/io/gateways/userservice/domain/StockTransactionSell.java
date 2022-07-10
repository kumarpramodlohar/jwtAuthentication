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
	private int id;
	private String symbol;
	private String username;
	private int qty;
	private double rate;
	private double net_rate;
	private LocalDateTime date;
	private double gain;
}