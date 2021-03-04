package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public interface TransfersDAO {

	List<User> getAvailableUsers();
	
	List<Transfer> getAllTransfers(int userID);
	
	String sendMoney(int senderID, int receiverID, BigDecimal amount);
	
	Transfer getTransfer(int transferID);
	
	
	
}
