package com.bankofavalos.repository;

import java.util.ArrayList;
import java.util.List;

import com.bankofavalos.models.Users;

public interface UsersDAO {

	public boolean insert(Users usr);
	
	public List<Users> findAll();
	
	public Users findById(int userId);
	
	public Users findByEmail(String email);
	
	public boolean update(Users usr);
	
	public boolean delete(int userId);
	
	public List<Integer> findAccounts(int userId);
	
	public List<Users> findCustomers();
}



