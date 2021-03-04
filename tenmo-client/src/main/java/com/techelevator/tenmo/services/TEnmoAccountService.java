package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;

public class TEnmoAccountService {

	public RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser authenticatedUser;
	private final String API_BASE_URL;
	public static String AUTH_TOKEN = "";

	public TEnmoAccountService(String url, AuthenticatedUser authenticatedUser) {

		this.API_BASE_URL = url;
		this.authenticatedUser = authenticatedUser;
	}

	public BigDecimal getBalance(int userID) throws TEnmoAccountServiceException {
		Account theAccount = null;
		try {
			theAccount = restTemplate.exchange(API_BASE_URL + "balance/" + userID, HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
		} catch (RestClientResponseException ex) {
			
			throw new TEnmoAccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
			
		}

		return theAccount.getBalance();
	}
	
	
	
	 private HttpEntity makeAuthEntity() {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(AUTH_TOKEN);
	        HttpEntity entity = new HttpEntity<>(headers);
	        return entity;
	    }
}
