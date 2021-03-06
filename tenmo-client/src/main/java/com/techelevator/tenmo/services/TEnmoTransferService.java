package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class TEnmoTransferService {

	private RestTemplate restTemplate = new RestTemplate();
	private AuthenticatedUser authenticatedUser;
	private String API_BASE_URL;
	public static String AUTH_TOKEN = "";

	public TEnmoTransferService(String url, AuthenticatedUser authenticatedUser) {
		this.API_BASE_URL = url;
		this.authenticatedUser = authenticatedUser;

	}

	public Transfer[] transferList() {
		Transfer[] output = null;

		String path = API_BASE_URL + "account/transfers/" + authenticatedUser.getUser().getId();

		try {
			output = restTemplate.exchange(path, HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		} catch (Exception e) {
			System.out.println("Service fail");
		}

		System.out.println("-------------------------------------------\r\n" + "Transfers\r\n"
				+ "ID          From/To                 Amount\r\n" + "-------------------------------------------\r\n");

		return output;

	}

	public void sendMoney() {
		User[] usersToSend = null;
		Transfer transfer = new Transfer();
		try {
			Scanner scn = new Scanner(System.in);
			usersToSend = restTemplate.exchange(API_BASE_URL + "listusers", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
			System.out.println("-------------------------------------------\r\n" + "Users\r\n" + "ID\t\tName\r\n"
					+ "-------------------------------------------");
			for (User i : usersToSend) {
				if (i.getId() != authenticatedUser.getUser().getId()) {
					System.out.println(i.getId() + "\t\t" + i.getUsername());
				}
			}
			System.out.print("-------------------------------------------\r\n" + 
					"Enter ID of user you are sending to (0 to cancel): ");
			transfer.setReceivingAccount(Integer.parseInt(scn.nextLine()));
			transfer.setSendingAccount(authenticatedUser.getUser().getId());
			if(transfer.getReceivingAccount() != 0) {
				System.out.print("Enter amount: ");
				try {
					transfer.setAmount(new BigDecimal(Double.parseDouble(scn.nextLine())));
					
				} catch(NumberFormatException ex) {
					System.out.println("That amount is invalid");
				}
				String output = restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.POST, makeTransferEntity(transfer), String.class).getBody();
				System.out.println(output);
			}
			
			
		} catch (Exception e) {
			System.out.println("bad input");
		}
	}
	
	private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setBearerAuth(authenticatedUser.getToken());
	    HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
	    return entity;
	  }

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authenticatedUser.getToken());
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
