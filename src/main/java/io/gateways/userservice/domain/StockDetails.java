package io.gateways.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_details")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDetails {
	@Id
	private Integer id;

	private String Exch;

	private String ExchType;
	private String Symbol;
	private String Expiry;
	private Integer StrikePrice;
	private String OptionType;

}
