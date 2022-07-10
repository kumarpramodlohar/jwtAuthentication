package io.gateways.userservice.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.gateways.userservice.domain.IndustryDetails;
import io.gateways.userservice.domain.StockCodes;
import io.gateways.userservice.domain.StockDetails;
import io.gateways.userservice.domain.StockStatus;
import io.gateways.userservice.fivePaisa.AppConfig;
import io.gateways.userservice.fivePaisa.RestClient;
import io.gateways.userservice.repo.IndustryDetailsRepo;
import io.gateways.userservice.repo.MarketFeedRepo;
import io.gateways.userservice.repo.StockCodesRepo;
import io.gateways.userservice.repo.StockDetailsRepo;
import io.gateways.userservice.repo.StockStatusRepo;
import io.gateways.userservice.service.StockTransactionService;
import okhttp3.Response;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/live")
public class LiveDataController {
	
	static int state = 0;
	private  WebClient webClient;
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private StockDetailsRepo stockRepo;
	
	@Autowired
	private StockCodesRepo stockCodeRepo;
	@Autowired
	private IndustryDetailsRepo industryRepo; 
	
	@Autowired
	private MarketFeedRepo marketFeedRepo;
	
	@Autowired
	private StockStatusRepo stockStatusRepo;
	@Autowired
	private StockTransactionService stockTransactionService;
	
	String marketFeed=null;
	String industryFeed=null;
	
	String baseToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWJoYW1heSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjQ1NDUwNjgyfQ.56O-U_I1biR55Q-D-a9YfRSD1QU-wRWanmvooxl1xjY";
			//"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWJoYW1heSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjQ1NDQ5Nzk2fQ.LLovuOVF-XbWaastYI4dpuW_qELTkYi4dpbH4yd6jkE";
			//"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdWJoYW1heSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpL2xvZ2luIiwiZXhwIjoxNjQ1NDQ4NDI2fQ.Q59dbFjvIu3wWlVWs-QLu_Czf0CEjpTN-CmpdIRdgp0";
	
	@Autowired
	public void init(WebClient.Builder webClientBuilder) {
		//this.webClient=webClientBuilder.baseUrl("http://localhost:8080").defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE).build();
	}
	@GetMapping("/market-feed")
	public String marketFeed() {
		//System.out.println("In test api");
		return marketFeed;
	}
	//@GetMapping("/industry-feed")
	public String industryFeed() {
		//System.out.println("In test api");
		return industryFeed;
	}
	@GetMapping("/stockCodeSearch")
	public ResponseEntity<List<StockCodes>> searchStockCodes()
	{
		return ResponseEntity.ok().body(stockTransactionService.getStockCodes());
		
	}
	//@GetMapping("/market-feed")
	@Scheduled(fixedRate = 5000)
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
		
		for(int i=0;i<50;i++) {
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
		marketFeed = jsonobject2.get("Data").toString();
		
		
		//System.out.println("\n Response Result >> " + marketFeed);
		return marketFeed;
}
	//@Scheduled(fixedRate = 1000)
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
		
		for(int i=0;i<50;i++) {
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
		industryFeed = jsonobject2.get("Data").toString();
		System.out.println("industry share ..........................."+industryFeed);		
		return industryFeed;
}	
	@Transactional
	@Scheduled(fixedRate=5000)
	public void updateData()throws IOException,ParseException{
		AppConfig config = new AppConfig();
		RestClient apis = new RestClient(config);
		config.setAppName("5P59121219");
		config.setAppVer("1.0");
		config.setOsName("Windows");
		config.setEncryptKey("HDm7aeWXP3WQ4YXN2j7sv3keV7YINoDh");
		config.setKey("dIkoL2RinAQ1zFyWD5N758tFiGIHFoDb");
		config.setLoginId("59121219");
		config.setUserId("iucjDKv7tJV");
		config.setPassword("S2Rw8QOgOGO");
		
		
		List<StockCodes> details = stockCodeRepo.findAll();
		JSONObject obj3 = new JSONObject();
		
		List<JSONObject> ordStatusListReqObj = new ArrayList<JSONObject>();
		int start = 0,end=0;
		state = state+1;
		if(state == 1) {
			start=0;
			end=50;
		}else if(state ==2){
			start=51;
			end=100;
		}else if(state==3) {
			start=101;
			end=150;
		}
		else if(state==4) {
			start=151;
			end=200;
		}
		else if(state==5) {
			start=201;
			end=250;
		}
		else if(state==6) {
			start=251;
			end=300;
		}
		else if(state==7) {
			start=301;
			end=350;
		}
		else if(state==8) {
			start=351;
			end=400;
		}else if(state==9) {
			start=401;
			end=450;
		}
		else if(state==10) {
			start= 451;
			end=500;
		}
		else if(state==11) {
			start=501;
			end=550;
		}
		else if(state==12) {
			start=551;
			end=600;
		}
		else if(state==13) {
			start=601;
			end=650;
		}
		else if(state==14) {
			start=651;
			end=700;
		}
		else if(state==15) {
			start=701;
			end=750;
		}
		else if(state==16) {
			start=751;
			end=800;
		}else if(state==17) {
			start=801;
			end=850;
		}else if(state==18) {
			start=851;
			end=900;
		}else if(state==19) {
			start=901;
			end=950;
		}else if(state==20) {
			start=951;
			end=1000;
		}else if(state==21) {
			start=1001;
			end=1050;
		}else if(state==22) {
			start=1051;
			end=1100;
		}else if(state==23) {
			start=1101;
			end=1150;
		}else if(state==24) {
			start=1151;
			end=1200;
		}else if(state==25) {
			start=1201;
			end=1250;
		}
		else if(state==26) {
			start=1251;
			end=1300;
		}else if(state==27) {
			start=1301;
			end=1350;
		}else if(state==28) {
			start=1351;
			end=1400;
		}else if(state==29) {
			start=1401;
			end=1450;
		}else if(state==30) {
			start=1451;
			end=1500;
		}else if(state==31) {
			start=1501;
			end=1550;
		}else if(state==32) {
			start=1551;
			end=1600;
		}else if(state==32) {
			start=1601;
			end=1650;
		}else if(state==34) {
			start=1651;
			end=1700;
		}else if(state==35) {
			start=1701;
			end=1750;
		}else {
			start=1751;
			end=1800;
			state =0;
		}
		
		
		for(int i=start;i<end;i++) {
			JSONObject ordStatusReqObj = new JSONObject();
			StockCodes stock = details.get(i);
			ordStatusReqObj.put("Exch", stock.getExch());
			ordStatusReqObj.put("ExchType", stock.getExchType());
			ordStatusReqObj.put("Symbol", stock.getSymbol());
			ordStatusReqObj.put("Expiry", "");
			ordStatusReqObj.put("StrikePrice",0);
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
		JSONArray jsonArray = (JSONArray) jsonobject2.get("Data");

		Iterator<JSONObject> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
        	JSONObject item = iterator.next();
//        	System.out.println("Item============"+item);
        	String Symbol = ""+ item.get("Symbol");
//        	System.out.println(Symbol);
        	if(!Symbol.equals("null")) {
        		String Chg = ""+ item.get("Chg");
        		String ChgPcnt = ""+ item.get("ChgPcnt");
        		String LastRate = ""+item.get("LastRate");
        		String Pclose = ""+item.get("PClose");
        		System.out.println("Chg====="+Chg+"ChgPcnt======"+ChgPcnt+"LastRate======="+LastRate+"PClose======="+Pclose);
        		System.out.println(Symbol);
        		stockCodeRepo.stockDataUpdate(Chg,ChgPcnt,LastRate,Pclose,Symbol);
        		
        	}
        	
           
        }

		
		
	}
	@GetMapping("/stockName")
	public ResponseEntity<List<StockDetails>> getStockNames() {		
		return ResponseEntity.ok().body(stockRepo.findAll());
	}
	@GetMapping("/UserStocksQuantity")
	public String getStocks(@RequestParam("username")String username,@RequestParam("symbol")String symbol) {
		String qty = stockStatusRepo.getRecordCountForSell(username, symbol);
		return qty;
		
		
		
		
	}
}
