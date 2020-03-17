# Project 0: Banking App
First Java project for Revature

### Requirements

**Description**
Leveraging Java 8, create an application that simulates simple banking transactions

## Agile Requirements
- [ ] Develop using the agile methodologies taught in class
- - [ ] You should be using TDD (see JUnit requirements)
- - [ ] All transactions should be logged (see Log4J requirements)

## Java Requirements
- [x]	Build the application using Java 8
- [ ]	All interaction with the user should be done through the console using the `Scanner` class

## User Accounts
- [ ]	Customers of the bank should be able to register with a username and password, and apply to open an account.
- - [ ] Customers should be able to apply for joint accounts
- [ ] Mandatory User Information
- - [ ] Personal information

## Bank Account Specification
- [ ]	Once the account is open, customers should be able to withdraw, deposit, and transfer funds between accounts
- - [ ] All basic validation should be done, such as trying to input negative amounts, overdrawing from accounts etc.
- [ ] Mandatory Account information
- - [ ] Account information
- - [ ] Account balances

## Bank Customer Functions
- [ ] View and edit their personal information
- [ ] View account information and balances, but not edit

## Bank Employee Functions
- [ ]	Employees of the bank should be able to view all of their customers information.
- - [ ] May not edit customer information or account balances
- [ ]	Employees should be able to approve/deny open applications for accounts

## Bank Admin Functions
- [ ]	Bank admins should be able to view and edit all accounts
- - [ ] Approving/denying accounts (see Employee)
- - [ ] withdrawing, depositing, transferring from all accounts
- - [ ] canceling accounts

## JUNit
- [ ]	100% test coverage for the service layer is expected using JUnit

## Log4J
- [ ]	Logging should be accomplished using Log4J
- - [ ] Logging will also be stored in JDBC/SQL (requirement SQL 1)

## SQL Requirements
- [ ] All information will be stored through JDBC/SQL
- [ ] Create an SQL script that will create:
- - [ ] table schema for storing all user and account information
- - [ ] method for creating a new user in the database
- [ ] Your database should include at least 1 stored procedure.
- [ ] You should use the DAO design pattern for data connectivity.
