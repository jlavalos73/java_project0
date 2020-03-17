package com.bankofavalos.repository;

import java.util.List;

import com.bankofavalos.models.Account;
import com.bankofavalos.models.Users;

public interface AccountDAO {

	public boolean insert(Account acct, Users usr);
	
	public boolean activate(int accountNumber);
	
	public List<Account> findAll();
	
	public Account findAccount(int accountNumber);
	
	public boolean transfer(int sender, int reciever, double funds);
	
	public boolean cancel(int accountNumber);
	
	public List<Integer> findOwners(int accountNumber);

	boolean deposit(int accountNumber, double balance, double funds);

	boolean withdraw(int accountNumber, double balance, double funds);

	boolean insertOwner(int acct, Users usr);

	List<Account> findByStatus();
}
