package com.bankofavalos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Account implements Serializable {
	
	private int accountNumber;
	private double accountBalance;
	private AccountStatus status;
	private List<Integer> accountOwners = new ArrayList<>();

	
	public Account() {
		super();
	}

	public Account(int accountNumber, double accountBalance, AccountStatus status, 
			List<Integer> accountOwners) {
		super();
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.status = status;
		this.accountOwners = accountOwners;
	}

	public static int createAccountNumber() {
        Random rand = new Random();
        int num = rand.nextInt(9000000) + 1000000;
        return num;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public List<Integer> getAccountOwners() {
		return accountOwners;
	}

	public void setAccountOwners(List<Integer> accountOwners) {
		this.accountOwners = accountOwners;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountBalance, accountNumber, accountOwners, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
		return Double.doubleToLongBits(accountBalance) == Double.doubleToLongBits(other.accountBalance)
				&& accountNumber == other.accountNumber && Objects.equals(accountOwners, other.accountOwners)
				&& status == other.status;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", accountBalance=" + accountBalance + ", status=" + status
				+ ", accountOwners=" + accountOwners + "]";
	}

	
}
