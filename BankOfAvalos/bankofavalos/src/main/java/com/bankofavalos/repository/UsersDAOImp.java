package com.bankofavalos.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bankofavalos.models.UserTypes;
import com.bankofavalos.models.Users;
import com.bankofavalos.util.ConnectionUtil;
import com.bankofavalos.util.LoggerUtil;

public class UsersDAOImp implements UsersDAO {

	@Override
	public boolean insert(Users usr) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "{CALL insert_user(?, ?, ?, ?, ?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setString(1, usr.getFirstName());
			stmt.setString(2, usr.getLastName());
			stmt.setString(3, usr.getEmail());
			stmt.setString(4, usr.getPassword());
			stmt.setString(5, usr.getUserType().toString());
			
			return stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.insert()", e);
		}
		
		return false;
	}

	@Override
	public List<Users> findAll() {
		List<Users> list = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				String fName = rs.getString("first_name");
				String lName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				UserTypes userType = UserTypes.valueOf(rs.getString("user_type"));
				
				Users usr = new Users(userId, fName, lName, email, password, userType, new ArrayList<Integer>());
				List<Integer> accounts = new UsersDAOImp().findAccounts(usr.getUserId());
				usr.setAccounts(accounts);
				
				list.add(usr);
			}
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.findAll()", e);
		}
		
		return list;
	}
	
	@Override
	public Users findById(int userId) {
		Users usr = new Users();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE user_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(!rs.next()) {
				return null;
			} else {
				int id = rs.getInt("user_id");
				String fName = rs.getString("first_name");
				String lName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				UserTypes userType = UserTypes.valueOf(rs.getString("user_type"));
				
				usr = new Users(id, fName, lName, email, password, userType, new ArrayList<Integer>());
				usr.setAccounts(new UsersDAOImp().findAccounts(userId));
			}
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.findById()", e);
		}
		return usr;
	}

	@Override
	public Users findByEmail(String userEmail) {
		Users usr = new Users();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE email = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userEmail);
			ResultSet rs = stmt.executeQuery();
			if(!rs.next()) {
				return null;
			} else {
				int id = rs.getInt("user_id");
				String fName = rs.getString("first_name");
				String lName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				UserTypes userType = UserTypes.valueOf(rs.getString("user_type"));
				
				usr = new Users(id, fName, lName, email, password, userType, new ArrayList<Integer>());
				usr.setAccounts(new UsersDAOImp().findAccounts(id));
			}
			stmt.close();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.findByEmail()", e);
		}
	
		return usr;
		
	}


	@Override
	public boolean update(Users usr) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "{CALL update_user (?, ?, ?, ?, ?, ?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setInt(1, usr.getUserId());
			stmt.setString(2, usr.getFirstName());
			stmt.setString(3, usr.getLastName());
			stmt.setString(4, usr.getEmail());
			stmt.setString(5, usr.getPassword());
			stmt.setString(6, usr.getUserType().toString());
			
			return stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.update()", e);
		}
		
		return false;
	}

	@Override
	public boolean delete(int userId) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "{CALL delete_user (?)}";
			
			CallableStatement stmt = conn.prepareCall(sql);
			
			stmt.setInt(1, userId);
			
			return stmt.execute();
			
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.delete()", e);
		}
		return false;
	}

	@Override
	public ArrayList<Integer> findAccounts(int userId) {
		ArrayList<Integer> accts = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT account_id FROM account_owners WHERE account_owner = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				accts.add(rs.getInt("account_id"));
			}
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.findAccounts()", e);
		}
		return accts;
	}

	@Override
	public List<Users> findCustomers() {
		List<Users> list = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE user_type = 'Customer'";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				String fName = rs.getString("first_name");
				String lName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				UserTypes userType = UserTypes.valueOf(rs.getString("user_type"));
				
				Users usr = new Users(userId, fName, lName, email, password, userType, new ArrayList<Integer>());
				List<Integer> accounts = new UsersDAOImp().findAccounts(usr.getUserId());
				usr.setAccounts(accounts);
				
				list.add(usr);
			}
		} catch(SQLException e) {
			LoggerUtil.log.info("SQLException in UsersDAOImp.findAll()", e);
		}
		
		return list;
	}
}
