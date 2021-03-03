package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

	List<Account> getAccountsByUser(int userID);
	
	Account getAccountByID(int accountID);
	
	BigDecimal getBalance(int userID);				//may want to change param to accountID
	
	BigDecimal increaseBalance(int accountID, BigDecimal amtToAdd);
	
	BigDecimal decreaeBalance(int accountID, BigDecimal amtToSubtract);
}
