package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@Service
public class TransfersSqlDAO implements TransfersDAO {

	private JdbcTemplate jdbcTemplate;
	private AccountDAO accountDAO;

	public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.accountDAO = accountDAO;
	}
	
	@Override
	public List<User> getAvailableUsers() {
		
		List<User> users = new ArrayList<>();
		
		String sqlGetAvailableUsers = "Select * From users ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAvailableUsers);
		
		while(results.next()) {
			
			users.add(mapRowToUsers(results));
		}
		
		return users;
	}

	@Override
	public List<Transfer> getAllTransfers(int userID) {
		
		List<Transfer> transfers = new ArrayList<>();
		
		String sqlGetAllTransfers = "SELECT t.*, u.username AS sender_name, "
								  + "v.username AS receiver_name FROM transfers t "
								  + "JOIN accounts a ON t.account_from = a.account_id "
								  + "JOIN accounts b ON t.account_to = b.account_id "
								  + "JOIN users u ON a.user_id = u.user_id "
								  + "JOIN users v ON b.user_id = v.user_id "
								  + "WHERE a.user_id = ? OR b.user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllTransfers, userID, userID);
		
		while (results.next()) {
			transfers.add(mapRowToTransfers(results));
		}
		
		return transfers;
	}
	
	@Override
	public int createTransfer(Transfer newTransfer) {

		String sqlSendMoney = "Insert Into transfers(transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) "
				+ "Values(DEFAULT, 2, 2, ?, ?, ?)";
		newTransfer.setTransferID(getNextTransferId());
		newTransfer.setTransferType(2);
		newTransfer.setTransferStatus(2);
		int results =jdbcTemplate.update(sqlSendMoney, newTransfer.getSendingAccount(), newTransfer.getReceivingAccount(), newTransfer.getAmount());

		return results;
	}

	@Override
	public Transfer getTransfer(int transferID) {

		Transfer theTransfer = null;
		
		String sqlTransfer = "SELECT t.*, u.username AS sender_name, v.username AS receiver_name, ts.transfer_status_desc, tt.transfer_type_desc FROM transfers t " + 
				"JOIN accounts a ON t.account_from = a.account_id " + 
				"JOIN accounts b ON t.account_to = b.account_id " + 
				"JOIN users u ON a.user_id = u.user_id " + 
				"JOIN users v ON b.user_id = v.user_id " + 
				"JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id " + 
				"JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id " + 
				"WHERE t.transfer_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlTransfer, transferID);
		if(results.next()) {
			theTransfer = mapRowToTransfers(results);
		}
		
		return theTransfer;
	}

	private User mapRowToUsers(SqlRowSet rs) {
		
		User theUser = new User();
		theUser.setId(rs.getLong("user_id"));
		theUser.setUsername(rs.getString("username"));
		theUser.setPassword(rs.getString("password_hash"));
		return theUser;
	}
	
	private Transfer mapRowToTransfers(SqlRowSet rs) {
		Transfer theTransfer = new Transfer();
		theTransfer.setTransferID(rs.getInt("transfer_id"));
		theTransfer.setTransferType(rs.getInt("transfer_type_id"));
		theTransfer.setAmount(rs.getBigDecimal("amount"));
		theTransfer.setReceiverName(rs.getString("receiver_name"));
		theTransfer.setReceivingAccount(rs.getInt("account_to"));
		theTransfer.setSenderName(rs.getString("sender_name"));
		theTransfer.setSendingAccount(rs.getInt("account_from"));
		theTransfer.setTransferStatus(rs.getInt("transfer_status_id"));
		return theTransfer;
		
	}
	
	private int getNextTransferId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new transfer");
		}
	}
}
