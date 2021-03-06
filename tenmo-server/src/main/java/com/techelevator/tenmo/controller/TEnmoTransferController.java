package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.dao.TransfersSqlDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
public class TEnmoTransferController {

	private TransfersDAO transferDAO;
	private UserDAO userDAO;

	public TEnmoTransferController(TransfersDAO transferDAO, UserDAO userDAO) {
		this.transferDAO = transferDAO;
		this.userDAO = userDAO;
	}

	@RequestMapping(path = "account/transfers/{id}", method = RequestMethod.GET)
	public List<Transfer> getAllMyTransfers(@PathVariable int id) {
		List<Transfer> output = transferDAO.getAllTransfers(id);
		return output;
	}

	@RequestMapping(path = "transfers/{id}", method = RequestMethod.GET)
	public Transfer getSelectedTransfer(@PathVariable int id) {
		Transfer transfer = transferDAO.getTransfer(id);
		return transfer;
	}
	
	@RequestMapping(path = "transfer", method = RequestMethod.POST)
	public String sendTransfer(@RequestBody Transfer transfer) {
		String results = transferDAO.sendMoney(transfer);
		return results;
	}

	@RequestMapping(path = "listusers", method = RequestMethod.GET)
	public List<User> listUsers() {
		List<User> users = userDAO.findAll();
		return users;
	}
}
