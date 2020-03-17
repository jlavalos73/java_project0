package com.bankofavalos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Users implements Serializable {
	
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private UserTypes userType;
	private List<Integer> accounts;



	public Users() {
		super();
	}


	public Users(int userId, String firstName, String lastName, String email, String password, UserTypes userType, ArrayList<Integer> accounts) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userType = userType;
		this.accounts = accounts;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public UserTypes getUserType() {
		return userType;
	}


	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}


	public List<Integer> getAccounts() {
		return accounts;
	}


	public void setAccounts(List<Integer> accounts) {
		this.accounts = accounts;
	}


	@Override
	public int hashCode() {
		return Objects.hash(accounts, email, firstName, lastName, password, userId, userType);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Users)) {
			return false;
		}
		Users other = (Users) obj;
		return Objects.equals(accounts, other.accounts) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && userId == other.userId && userType == other.userType;
	}


	@Override
	public String toString() {
		return "Users [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", userType=" + userType + ", accounts=" + accounts + "]";
	}
	
}
