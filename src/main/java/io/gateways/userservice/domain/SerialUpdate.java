package io.gateways.userservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "serial_update")
public class SerialUpdate {
	@Id
	@GeneratedValue
	private int id;
	private String sl_no;
	private String sl_type;
	private String active;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSl_no() {
		return sl_no;
	}
	public void setSl_no(String sl_no) {
		this.sl_no = sl_no;
	}
	public String getSl_type() {
		return sl_type;
	}
	public void setSl_type(String sl_type) {
		this.sl_type = sl_type;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

}
