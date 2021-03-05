package com.techelevator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.dao.TransfersSqlDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public class TransferSqlDAOTest {

	
	private static SingleConnectionDataSource dataSource;
	private AccountSqlDAO dao;
	private TransfersSqlDAO transfersDAO;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String SqlDummyUser = "INSERT INTO users (user_id, username, password_hash) VALUES (4, '', '')";
		String sqlDummyAccount = "INSERT INTO accounts (account_id, user_id, balance) VALUES (69, 4, 1200)";
		String sqlDummyTransfer = "Insert Into transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) values (default, 2, 2, 69, 3, 1000)";
		dao = new AccountSqlDAO(jdbcTemplate);
		transfersDAO = new TransfersSqlDAO(jdbcTemplate);
		jdbcTemplate.update(SqlDummyUser);
		jdbcTemplate.update(sqlDummyAccount);
		jdbcTemplate.update(sqlDummyTransfer);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void getAvailableUsers_returns_a_list() {
		
		List<User> testObj = new ArrayList<>();
		
		testObj = transfersDAO.getAvailableUsers();
		
		Assert.assertNotNull(testObj);
		Assert.assertEquals(4, testObj.size());
		
	}
	
	@Test
	public void getAllTransfers_returns_a_list_of_transfers() {
		List<Transfer> testObj = new ArrayList<>();
		
		testObj = transfersDAO.getAllTransfers(3);
		
		Assert.assertNotNull(testObj);
		Assert.assertEquals(1, testObj.size());
		Assert.assertEquals(69, testObj.get(0).getSendingAccount());
	}
	
	@Test
	public void sendMoney_sends_money_and_reduces_balances() {
		
		BigDecimal testAmount = BigDecimal.valueOf(1000);
		
		String results = transfersDAO.sendMoney(69, 3, testAmount);
		
		Assert.assertEquals("Transfer Approved", results);
	}
}
