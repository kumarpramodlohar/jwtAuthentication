package io.gateways.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "marketfeed")
public class Marketfeed {

			   @Id
			   @GeneratedValue(strategy= GenerationType.AUTO)
			   private long  id;
			   
			  private String high;
			  private String tick_dt;
			  private String chg;
			  private String total_qty;
			  public long getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			public String getHigh() {
				return high;
			}
			public void setHigh(String high) {
				this.high = high;
			}
			public String getTick_dt() {
				return tick_dt;
			}
			public void setTick_dt(String tick_dt) {
				this.tick_dt = tick_dt;
			}
			public String getChg() {
				return chg;
			}
			public void setChg(String chg) {
				this.chg = chg;
			}
			public String getTotal_qty() {
				return total_qty;
			}
			public void setTotal_qty(String total_qty) {
				this.total_qty = total_qty;
			}
			public String getSymbol() {
				return symbol;
			}
			public void setSymbol(String symbol) {
				this.symbol = symbol;
			}
			public String getLast_rate() {
				return last_rate;
			}
			public void setLast_rate(String last_rate) {
				this.last_rate = last_rate;
			}
			public String getTime() {
				return time;
			}
			public void setTime(String time) {
				this.time = time;
			}
			public String getToken() {
				return token;
			}
			public void setToken(String token) {
				this.token = token;
			}
			public String getP_close() {
				return p_close;
			}
			public void setP_close(String p_close) {
				this.p_close = p_close;
			}
			public String getChg_pcnt() {
				return chg_pcnt;
			}
			public void setChg_pcnt(String chg_pcnt) {
				this.chg_pcnt = chg_pcnt;
			}
			public String getLow() {
				return low;
			}
			public void setLow(String low) {
				this.low = low;
			}
			public String getExch() {
				return exch;
			}
			public void setExch(String exch) {
				this.exch = exch;
			}
			private String symbol;
			  private String last_rate;
			  private String time;
			  private String token;
			  private String p_close;
			  private String chg_pcnt;
			  private String low;
			  private String exch;
			 
			
	
	
}
