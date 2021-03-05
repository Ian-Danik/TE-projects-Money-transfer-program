package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfer;

@RestController
@PreAuthorize("isAuthenticated()")
public class TEnmoTransferController {

	private TransfersDAO transferDAO;
	@PreAuthorize("permitAll")
	@RequestMapping(value = "account/transfers/{id}", method = RequestMethod.GET)
	public List<Transfer> getAllMyTransfers(@PathVariable int id) {
		List<Transfer> output = transferDAO.getAllTransfers(id);
		return output;
	}
}
