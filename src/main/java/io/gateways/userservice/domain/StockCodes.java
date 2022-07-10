package io.gateways.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="stockcodes")
@Data
public class StockCodes {
	
	@Id
	@GeneratedValue
	int id;
	String Exch;
	String ExchType;
	Integer Scripcode;
	String Symbol;
	String Name;
	String Chg;
	String ChgPcnt;
	String LastRate;
	String PClose;
}