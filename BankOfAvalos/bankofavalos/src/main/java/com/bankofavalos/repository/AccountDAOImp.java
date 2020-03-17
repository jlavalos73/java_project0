package com.bankofavalos.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bankofavalos.models.Account;
import com.bankofavalos.models.AccountStatus;
import com.bankofavalos.models.UserTypes;
import com.bankofavalos.models.Users;
import com.bankofavalos.util.ConnectionUtil;
import com.bankofavalos.util.LoggerUtil;

public class AccountDAOImp implements AccountDAO {

	@Override
	public boolean insert(Account acct, Users usr) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "{CALL insert_account(?, ?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setInt(1, acct.getAccountNumber());
			stmt.setInt(2, usr.getUserId());
			
			stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.insert()", e);
		}
		return false;
	}
	
	@Override
	public boolean insertOwner(int acct, Users usr) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "{CALL insert_owner(?, ?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setInt(1, acct);
			stmt.setInt(2, usr.getUserId());
			
			stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.insertOwner()", e);
		}
		return false;
	}

	@Override
	public List<Account> findAll() {
		List<Account> list = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM account";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int accountNumber = rs.getInt("account_number");
				double accountBalance = rs.getDouble("account_balance");
				AccountStatus status = AccountStatus.valueOf(rs.getString("account_status"));
				
				
				Account acct = new Account(accountNumber, accountBalance, status, new ArrayList<Integer>());
				
				List<Integer> owners = new AccountDAOImp().findOwners(acct.getAccountNumber());
				acct.setAccountOwners(owners);
				
				list.add(acct);
			}
			
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.findAll()", e);
		}
		return list;
	}

	@Override
	public Account findAccount(int accountNumber) {
		Account acct = new Account();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE account_number = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, accountNumber);
			
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()) {
				return null;
			} else {
				int acctNum = rs.getInt("account_number");
				double accountBalance = rs.getDouble("account_balance");
				AccountStatus status = AccountStatus.valueOf(rs.getString("account_status"));
				
				
				acct = new Account(acctNum, accountBalance, status, new ArrayList<Integer>());
				
				List<Integer> owners = new AccountDAOImp().findOwners(acct.getAccountNumber());
				acct.setAccountOwners(owners);
				
			}
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.findAccount()");
		}
		return acct;
	}
	
	@Override
	public List<Account> findByStatus() {
		List<Account> list = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account WHERE account_status = 'Pending'";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int accountNumber = rs.getInt("account_number");
				double accountBalance = rs.getDouble("account_balance");
				AccountStatus status = AccountStatus.valueOf(rs.getString("account_status"));
				
				
				Account acct = new Account(accountNumber, accountBalance, status, new ArrayList<Integer>());
				
				List<Integer> owners = new AccountDAOImp().findOwners(acct.getAccountNumber());
				acct.setAccountOwners(owners);
				
				list.add(acct);
			}
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.findByStatus()", e);
		}
		
		return list;
	}

	@Override
	public boolean deposit(int accountNumber, double balance, double funds) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "UPDATE account SET account_balance = (? + ? ) WHERE account_number = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, accountNumber);
			stmt.setDouble(2, balance);
			stmt.setDouble(3, funds);
			
			stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.deposit()", e);

		}
		return false;
	}

	@Override
	public boolean withdraw(int accountNumber, double balance, double funds) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "UPDATE account SET account_balance = (? - ? ) WHERE account_number = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, balance);
			stmt.setDouble(2, funds);
			stmt.setInt(3, accountNumber);
			
			stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.withdraw()", e);

		}
		return false;
	}

	@Override
	public boolean transfer(int sender, int reciever, double funds) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{CALL transfer_funds(?, ?, ?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setInt(1, sender);
			stmt.setInt(2, reciever);
			stmt.setDouble(3, funds);
			
			return stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.transfer()", e);

		}
		return false;
	}

	@Override
	public boolean cancel(int accountNumber) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "{CALL delete_account(?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setInt(1, accountNumber);
			
			return stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.cancel()");
		}
		return false;
	}

	@Override
	public List<Integer> findOwners(int accountNumber) {
		ArrayList<Integer> owners = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT account_owner FROM account_owners WHERE account_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, accountNumber);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				owners.add(rs.getInt("account_owner"));
			}
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.findOwners()", e);
		}
		return owners;
	}

	@Override
	public boolean activate(int accountNumber) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "{CALL activate_acct (?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setInt(1, accountNumber);
			
			return stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in AccountDAOImp.activate()");
		}
		return false;
	}



}
