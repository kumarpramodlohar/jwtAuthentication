package io.gateways.userservice.domain;

import javax.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

    @Entity
	@Table(name = "stock_status")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
	public class StockStatus {
		@Id
		@GeneratedValue
		private Integer id;
		private String username;
		private String symbol; 
		private Integer qty;
		private Double total_buy_amt;
		private Double total_sale_amt;
}
