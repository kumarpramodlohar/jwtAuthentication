package io.gateways.userservice.domain;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_details")
public class BankDetails implements Serializable{
	private static final long serialVersionUID = 471512149777116797L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String client_id;
	private String account_number;
	private String account_name;
	private String ifsc_code;
	private String micr_code;
	private String bank_name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getMicr_code() {
		return micr_code;
	}
	public void setMicr_code(String micr_code) {
		this.micr_code = micr_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
