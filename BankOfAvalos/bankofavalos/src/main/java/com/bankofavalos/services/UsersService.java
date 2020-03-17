package com.bankofavalos.services;

import java.util.List;

import com.bankofavalos.models.Users;
import com.bankofavalos.repository.UsersDAO;
import com.bankofavalos.repository.UsersDAOImp;

public class UsersService {
	
	UsersDAO repository = null;
	
	public UsersService() {
		super();
		this.repository = new UsersDAOImp();
	}
	
	public UsersService(UsersDAO repository) {
		super();
		this.repository = repository;
	}
	
	public List<Users> findAll(){
		return repository.findAll();
	}
	
	public boolean insert (Users usr) {
		return repository.insert(usr);
	}
	
	public Users findById(int userId) {
		return repository.findById(userId);
	}
	
	public Users findByEmail(String email) {
		return repository.findByEmail(email);
		
	}
	
	public boolean update(Users usr) {
		return repository.update(usr);
	}
	
	public boolean delete(int userId) {
		return repository.delete(userId);
	}
	
	public List<Integer> findAccounts(int userId){
		return repository.findAccounts(userId);
	}
	
	public List<Users> findCustomers(){
		return repository.findCustomers();
	}
}

