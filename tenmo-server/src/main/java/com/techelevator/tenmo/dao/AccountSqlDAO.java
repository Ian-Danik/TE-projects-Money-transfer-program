package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

@Service 
public class AccountSqlDAO implements AccountDAO {

	private JdbcTemplate jdbcTemplate;

	public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Account> getAccountsByUser(int userID) {

		List<Account> theAccounts = new ArrayList<>();

		String sqlGetAcountsByUser = "Select * From accounts " + "Where user_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAcountsByUser, userID);

		while (results.next()) {

			theAccounts.add(mapRowToAccount(results));
		}

		return theAccounts;
	}

	@Override
	public Account getAccountByID(int accountID) {

		Account theAccount = new Account();

		String sqlGetAccountByID = "Select * From accounts " + "Where account_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAccountByID, accountID);

		if (results.next()) {

			theAccount = mapRowToAccount(results);

		}

		return theAccount;
	}

	@Override
	public BigDecimal getBalance(int userID) {

		String sqlGetBalance = "Select * From accounts " + "Where user_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetBalance, userID);

		if (results.next()) {

			return mapRowToAccount(results).getBalance();

		} else {

			return null;
		}
	}

	@Override
	public BigDecimal increaseBalance(int accountID, BigDecimal amtToAdd) {

		Account theUpdatedAccount = getAccountByID(accountID);

		// The variable named dadShoes refers to the term "New Balance"

		BigDecimal dadShoes = theUpdatedAccount.getBalance().add(amtToAdd);

		String sqlIncreaseBalance = "Update accounts " + "Set balance = ? " + "Where account_id = ?";

		jdbcTemplate.update(sqlIncreaseBalance, accountID, dadShoes);

		return dadShoes;
	}

	@Override
	public BigDecimal decreaseBalance(int accountID, BigDecimal amtToSubtract) {

		Account theUpdatedAccount = getAccountByID(accountID);

		// The variable named dadShoes refers to the term "New Balance"

		BigDecimal dadShoes = theUpdatedAccount.getBalance().subtract(amtToSubtract);

		String sqlDecreaseBalance = "Update accounts " + "Set balance = ? " + "Where account_id = ?";

		jdbcTemplate.update(sqlDecreaseBalance, accountID, dadShoes);

		return dadShoes;
	}

	private Account mapRowToAccount(SqlRowSet rs) {
		Account account = new Account();
		account.setAccountID(rs.getInt("account_id"));
		account.setUserID(rs.getInt("user_id"));
		account.setBalance(rs.getBigDecimal("balance"));
		return account;
	}

}
