package io.gateways.userservice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_watchlist")
@Data
public class WatchlistBean implements Serializable {

	private static final long serialVersionUID = 471512149777116797L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String symbol;
	private String username;
	private String watch_flag;
	private String status;
	
	
}