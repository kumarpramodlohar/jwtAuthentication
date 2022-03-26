package io.gateways.userservice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.gateways.userservice.domain.IndustryDetails;
import io.gateways.userservice.domain.Marketfeed;
import io.gateways.userservice.domain.StockDetails;
import io.gateways.userservice.domain.User;
import io.gateways.userservice.fivePaisa.AppConfig;
import io.gateways.userservice.fivePaisa.RestClient;
import io.gateways.userservice.repo.IndustryDetailsRepo;
import io.gateways.userservice.repo.MarketFeedRepo;
import io.gateways.userservice.repo.StockDetailsRepo;
import okhttp3.Response;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/live")
public class LiveDataController {
	
	private  WebClient webClient;
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private StockDetailsRepo stockRepo;
	
	@Autowired
	private IndustryDetailsRepo industryRepo; 
	
	@Autowired
	private MarketFeedRepo marketFeedRepo;
	
	String baseToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWJoYW1heSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjQ1NDUwNjgyfQ.56O-U_I1biR55Q-D-a9YfRSD1QU-wRWanmvooxl1xjY";
			//"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWJoYW1heSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjQ1NDQ5Nzk2fQ.LLovuOVF-XbWaastYI4dpuW_qELTkYi4dpbH4yd6jkE";
			//"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWJoYW1heSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjQ1NDQ4NDI2fQ.Q59dbFjvIu3wWlVWs-QLu_Czf0CEjpTN-CmpdIRdgp0";
	
	@Autowired
	public void init(WebClient.Builder webClientBuilder) {
		//this.webClient=webClientBuilder.baseUrl("http://localhost:8080").defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE).build();
	}

	
	@GetMapping("/market-feed")
	public String getAllFeed() throws IOException, ParseException {
		
		
		AppConfig config = new AppConfig();
		RestClient apis = new RestClient(config);
		config.setAppName("5P58598283");
		config.setAppVer("1.0");
		config.setOsName("Windows");
		config.setEncryptKey("huYC5GA05MzDAJVJpSbtinpkJXIpMzCS");
		config.setKey("bp2o8LWSx6r5rbjkQHrVuDeMZPzYc1M6");
		config.setLoginId("56565401");
		config.setUserId("VLipfwWw4Ay");
		config.setPassword("s0mxmkIGK8Q");
		

		List<StockDetails> details = stockRepo.findAll();	
		
		JSONObject obj3 = new JSONObject();
		
		List<JSONObject> ordStatusListReqObj = new ArrayList<JSONObject>();
		
		for(int i=0;i<details.size();i++) {
			JSONObject ordStatusReqObj = new JSONObject();
			StockDetails stock = details.get(i);
			ordStatusReqObj.put("Exch", stock.getExch());
			ordStatusReqObj.put("ExchType", stock.getExchType());
			ordStatusReqObj.put("Symbol", stock.getSymbol());
			ordStatusReqObj.put("Expiry", "");
			ordStatusReqObj.put("StrikePrice", stock.getStrikePrice());
			ordStatusReqObj.put("OptionType", "");
	
			ordStatusListReqObj.add(ordStatusReqObj);
		}
		
		
		obj3.put("Count", 2);
		obj3.put("ClientLoginType", 0);
		obj3.put("LastRequestTime", "/Date(1645867288)/");
		obj3.put("RefreshRate", "H");
		obj3.put("MarketFeedData", ordStatusListReqObj);

		Response response = apis.MarketFeed(obj3);
		
		String returnResponse = response.body().string();
		
		
		

		
					 
		JSONParser parser = new JSONParser(); 
		JSONObject objectjson = (JSONObject) parser.parse(returnResponse);
		
		
		JSONObject jsonobject2 = (JSONObject) objectjson.get("body");
		String returnvalue = jsonobject2.get("Data").toString();
		
		
		//System.out.println("\n Response Result >> " + result);
		return returnvalue;
}
	@GetMapping("/industry-feed")
	public String getAllIndustryFeed() throws IOException, ParseException {
		
		
		AppConfig config = new AppConfig();
		RestClient apis = new RestClient(config);
		config.setAppName("5P58598283");
		config.setAppVer("1.0");
		config.setOsName("Windows");
		config.setEncryptKey("huYC5GA05MzDAJVJpSbtinpkJXIpMzCS");
		config.setKey("bp2o8LWSx6r5rbjkQHrVuDeMZPzYc1M6");
		config.setLoginId("56565401");
		config.setUserId("VLipfwWw4Ay");
		config.setPassword("s0mxmkIGK8Q");
		

		List<IndustryDetails> details = industryRepo.findAll();	
		
		JSONObject obj3 = new JSONObject();
		
		List<JSONObject> ordStatusListReqObj = new ArrayList<JSONObject>();
		
		for(int i=0;i<details.size();i++) {
			JSONObject ordStatusReqObj = new JSONObject();
			IndustryDetails stock = details.get(i);
			ordStatusReqObj.put("Exch", stock.getExch());
			ordStatusReqObj.put("ExchType", stock.getExchType());
			ordStatusReqObj.put("Symbol", stock.getSymbol());
			ordStatusReqObj.put("Expiry", "");
			ordStatusReqObj.put("StrikePrice", stock.getStrikePrice());
			ordStatusReqObj.put("OptionType", "");
	
			ordStatusListReqObj.add(ordStatusReqObj);
		}
		
		
		obj3.put("Count", 2);
		obj3.put("ClientLoginType", 0);
		obj3.put("LastRequestTime", "/Date(1645867288)/");
		obj3.put("RefreshRate", "H");
		obj3.put("MarketFeedData", ordStatusListReqObj);

		Response response = apis.MarketFeed(obj3);
		
		String returnResponse = response.body().string();
		
		
		

		
					 
		JSONParser parser = new JSONParser(); 
		JSONObject objectjson = (JSONObject) parser.parse(returnResponse);
		
		
		JSONObject jsonobject2 = (JSONObject) objectjson.get("body");
		String returnvalue = jsonobject2.get("Data").toString();
		
		
		//System.out.println("\n Response Result >> " + result);
		return returnvalue;
}	
	@GetMapping("/stockName")
	public ResponseEntity<List<StockDetails>> getStockNames() {		
		return ResponseEntity.ok().body(stockRepo.findAll());
	}
}
