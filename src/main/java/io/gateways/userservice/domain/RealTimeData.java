package io.gateways.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RealTimeData {
	
	 private String type;
	 private String message;
	 private Object data;
	    
	

}
