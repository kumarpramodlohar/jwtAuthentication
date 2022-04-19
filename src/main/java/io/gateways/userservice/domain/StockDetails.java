package io.gateways.userservice.domain;

import javax.persistence.Column;
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
	@Column(name="id")
	private Integer id;
	@Column(name="Exch")
	private String Exch;
	@Column(name="ExchType")
	private String ExchType;
	@Column(name="Symbol")
	private String Symbol;
	@Column(name="Expiry")
	private String Expiry;
	@Column(name="StrikePrice")
	private Integer StrikePrice;
	@Column(name="OptionType")
	private String OptionType;

}
