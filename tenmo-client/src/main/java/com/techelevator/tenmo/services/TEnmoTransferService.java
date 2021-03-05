package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;

public class TEnmoTransferService {
	
	private RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser AuthenticatedUser;
	private String API_BASE_URL;
	public static String AUTH_TOKEN = "";
	
	public TEnmoTransferService(String url, AuthenticatedUser AuthenticatedUser) {
		this.API_BASE_URL = url;
		this.AuthenticatedUser = AuthenticatedUser;
		
	}
	
	public Transfer[] transferList() {
		Transfer[] output = null;
		
		String path = API_BASE_URL + "account/transfers/" + AuthenticatedUser.getUser().getId();

		output = restTemplate.exchange(path, HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		
		
		
		System.out.println("-------------------------------------------\r\n" + 
							"Transfers\r\n" + 
							"ID          From/To                 Amount\r\n" + 
							"-------------------------------------------\r\n"); 
		
		
		return output;
		
		
	}


	
	 
	
	
	private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
