package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransfersDAO {

	List<User> getAvailableUsers();
	
	List<Transfer> getAllTransfers(int userID);
	
	int createTransfer(Transfer newTransfer);
	
	Transfer getTransfer(int transferID);
	
	
	
}
