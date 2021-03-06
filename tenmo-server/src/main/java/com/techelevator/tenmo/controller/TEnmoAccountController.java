package com.techelevator.tenmo.controller;

import java.math.BigDecimal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.AccountSqlDAO;

@PreAuthorize("isAuthenticated()")
@RestController
public class TEnmoAccountController {

	private AccountDAO accountDAO;

	public TEnmoAccountController(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
		
	}

	
	@RequestMapping(path = "balance/{id}", method = RequestMethod.GET)
	public BigDecimal getBalance(@PathVariable int id) {
		return accountDAO.getBalance(id);
		
	}

}
