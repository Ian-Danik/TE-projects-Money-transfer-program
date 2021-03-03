package com.techelevator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.model.Account;

//

public class AccountSqlDAOTest {

	private static SingleConnectionDataSource dataSource;
	private AccountSqlDAO dao;

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
		dao = new AccountSqlDAO(jdbcTemplate);
		jdbcTemplate.update(SqlDummyUser);
		jdbcTemplate.update(sqlDummyAccount);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void getAccountsByUser_returns_a_list_of_accounts() {
		List<Account> testObj = new ArrayList<>();

		testObj = dao.getAccountsByUser(4);

		Assert.assertNotNull(testObj);
		Assert.assertEquals(1, testObj.size());
		Assert.assertEquals("1200.00", testObj.get(0).getBalance().toString());
		Assert.assertEquals(4, testObj.get(0).getUserID());
		Assert.assertEquals(69, testObj.get(0).getAccountID());
	}

	@Test
	public void getAccountByID_returns_an_account() {
		Account testObj = new Account();

		testObj = dao.getAccountByID(69);

		Assert.assertNotNull(testObj);
		Assert.assertEquals("1200.00", testObj.getBalance().toString());
		Assert.assertEquals(69, testObj.getAccountID());
		Assert.assertEquals(4, testObj.getUserID());

	}

	@Test
	public void getBalance_returns_the_balance() {
		BigDecimal testObj = dao.getBalance(4);

		Assert.assertEquals("1200.00", testObj.toString());
	}

	@Test
	public void increaseBalance_increases_balance() {
		BigDecimal testObj = dao.increaseBalance(69, BigDecimal.valueOf(300));

		Assert.assertEquals("1500.00", testObj.toString());
	}

	@Test
	public void decreaseBalance_decreases_balance() {
		BigDecimal testObj = dao.decreaseBalance(69, BigDecimal.valueOf(300));

		Assert.assertEquals("900.00", testObj.toString());
	}
	
}