package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDAO {

//	Account getUserById(int userID);
	BigDecimal getBalance(int userID);
	
}
