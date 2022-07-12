package io.gateways.userservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.simple.parser.ParseException;
import io.gateways.userservice.domain.StockDetails;
import io.gateways.userservice.fivePaisa.AppConfig;
import io.gateways.userservice.fivePaisa.RestClient;
import io.gateways.userservice.repo.MarketFeedRepo;
import io.gateways.userservice.repo.StockDetailsRepo;
import okhttp3.Response;

@Service
public class MarketFeedService {


	
	@Autowired
	private StockDetailsRepo stockRepo;
	
	@Autowired
	private MarketFeedRepo marketrepo;
	
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
		
		@PersistenceContext
		private EntityManager em;
		
		
//		@Scheduled(fixedRate = 1000)
		public void save() throws Exception {
			
			//System.out.println("Getting Called every 1 second");
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
			
			for(int i=0;i<1;i++) {
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
			Long timeNow = System.currentTimeMillis();
			obj3.put("LastRequestTime", "/Date("+timeNow+")/");
			obj3.put("RefreshRate", "H");
			obj3.put("MarketFeedData", ordStatusListReqObj);

			Response response = apis.MarketFeed(obj3);
			
//			String returnResponse = response.body().string();
			
			
			
			
			//System.out.println("response ================"+response.body().string());

			

		}
		
		public String getLiveData(List<StockDetails> details) {
			
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

			Response response;String returnResponse;JSONObject objectjson = null;JSONParser parser = new JSONParser(); 
			try {
				response = apis.MarketFeed(obj3);
				returnResponse = response.body().string();
				objectjson = (JSONObject) parser.parse(returnResponse);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		 
			
			
			JSONObject jsonobject2 = (JSONObject) objectjson.get("body");
			String returnvalue = jsonobject2.get("Data").toString();
			
			return returnvalue;
		}
		
	
}
