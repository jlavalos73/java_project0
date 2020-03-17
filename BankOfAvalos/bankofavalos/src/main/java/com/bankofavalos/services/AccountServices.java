package com.bankofavalos.services;

import java.util.List;

import com.bankofavalos.models.Account;
import com.bankofavalos.models.Users;
import com.bankofavalos.repository.AccountDAO;
import com.bankofavalos.repository.AccountDAOImp;

public class AccountServices {
	
	AccountDAO repository = null;
	
	public AccountServices() {
		super();
		this.repository = new AccountDAOImp();
	}
	
	public AccountServices(AccountDAO repository) {
		super();
		this.repository = repository;
	}
	
	public boolean insert(Account acct, Users usr) {
		return repository.insert(acct, usr);
	}
	
	public boolean insertOwner(int acct, Users usr) {
		return repository.insertOwner(acct, usr);
	}
	
	public boolean activate(int accountNumber) {
		return repository.activate(accountNumber);
	}
	
	public List<Account> findAll(){
		return repository.findAll();
	}
	
	public Account findAccount(int accountNumber) {
		return repository.findAccount(accountNumber);
	}
	
	public boolean deposit(int accountNumber, double balance, double funds) {
		return repository.deposit(accountNumber, balance, funds);
	}
	
	public boolean withdraw(int accountNumber, double balance, double funds) {
		return repository.withdraw(accountNumber, balance, funds);
	}
	
	public boolean transfer(int sender, int receiver, double funds) {
		return repository.transfer(sender, receiver, funds);
	}
	
	public boolean cancel(int accountNumber) {
		return repository.cancel(accountNumber);
	}
	
	public List<Integer> findOwners(int accountNumber){
		return repository.findOwners(accountNumber);
	}
	
	public List<Account> findByStatus(){
		return repository.findByStatus();
	}
}
