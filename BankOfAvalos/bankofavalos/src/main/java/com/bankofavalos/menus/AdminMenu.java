package com.bankofavalos.menus;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.models.Account;
import com.bankofavalos.models.Users;
import com.bankofavalos.services.AccountServices;
import com.bankofavalos.services.SelectionService;
import com.bankofavalos.services.UsersService;
import com.bankofavalos.util.LoggerUtil;
import com.bankofavalos.util.ScannerUtil;

public class AdminMenu {
	public static Logger log = LoggerUtil.getLogger();
	public static final Scanner scan = ScannerUtil.getScanner();
	public static UsersService usrServ = new UsersService();
	public static AccountServices acServ = new AccountServices();
	
	public static void runAdminMenu(Users usr){
		
		boolean loggedIn = true;
		
		while(loggedIn) {
			log.info(
					"\n"
				  + "========================================\n"
				  + "            Bank of Avalos              \n"
				  + "========================================\n"
				  + "\nPlease make a selection:"
				  + "\n1) Approve pending accounts"
				  + "\n2) View accounts"
				  + "\n3) View users"
				  + "\n0) Exit");
			
			int choice = SelectionService.getOption(4);
			
			switch(choice) {
				case 0:
					loggedIn = false;
					log.info("Thank you for banking with Bank of Avalos.");
					break;
				case 1:
					approveAccounts();
					break;
				case 2:
					viewAccounts();
					break;
				case 3:
					viewUsers();
					break;
				default:
					System.out.println("You have selected an unavailable option.");
			}
		}
	}
	

	private static void approveAccounts() {
		List<Account> pending = acServ.findByStatus();
		int li = 1;
		System.out.println("Which application would you like to view?");
		for(Account acc : pending) {
			System.out.println(li++ + ": "  + acc.toString());
		}
		System.out.println(li + ": Back");
		int option = SelectionService.getOption(li);
		if(li == option) {
			return;
		}
		System.out.println("What would you like to do?");
		System.out.println("1: Approve\n"
				+ "2: Deny\n"
				+ "3: Cancel\n");
		int choice = SelectionService.getOption(3);
		
		switch (choice) {
		case 1:
			acServ.activate(pending.get(option-1).getAccountNumber());
			log.info("Application approved!");
		case 2:
			acServ.cancel(pending.get(option-1).getAccountNumber());
			log.info("Application denied!");
		default:
			LoggerUtil.log.info("Unavailable option");
		}
		
	}
	
	public static void viewAccounts() {
		List<Account> accts = acServ.findAll();
		Account acSel;
		int li = 1;
		int option;
		
		log.info(
				"\n"
			  + "========================================\n"
			  + "            Bank of Avalos              \n"
			  + "========================================\n"
			  + "========Number=============Balance======\n");
		
		for(Account acc: accts) {
			System.out.println(li++ + ": "  + acc.toString());
			}
			if (li == 0) {
				System.out.println("No accounts found!");
			}
			System.out.println(li + ": Back");
			option = SelectionService.getOption(li);
			acSel = acServ.findAccount(accts.get(option-1).getAccountNumber());
			if(option != li) {
				try {
					log.info(acSel.getAccountNumber());
					transactions(acSel.getAccountNumber());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.info("Exception here", e);
				}
			}
	}
	
	public static void viewUsers() {
		List<Users> usrs = usrServ.findAll();
		Users usrSel;
		int li = 1;
		int option;
		
		log.info(
				"\n"
			  + "========================================\n"
			  + "            Bank of Avalos              \n"
			  + "========================================\n"
			  + "========Number=============Balance======\n");
		
		for(Users usr: usrs) {
			System.out.println(li++ + ": "  + usr.toString());
			}
			if (li == 0) {
				System.out.println("No accounts found!");
			}
			System.out.println(li + ": Back");
			option = SelectionService.getOption(li);
			usrSel = usrServ.findById(usrs.get(option-1).getUserId());
			if(option != li) {
				try {
					log.info(usrSel.getUserId());
					editUser(usrSel.getUserId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.info("Exception here", e);
				}
			}
	}

	private static void editUser(int userId) {
		Users usr = usrServ.findById(userId);
		boolean inMenu = true;
		while(inMenu) {
			System.out.println("What would you like to do?\n"
					+ "1: Update Email\n"
					+ "2: Update Password\n"
					+ "3: Back");
			int option = SelectionService.getOption(3);
			if( option == 1) {
				System.out.print("Enter new email:");
				usr.setEmail(scan.nextLine());
				usrServ.update(usr);
				LoggerUtil.getLogger().info("Email successfully updated!");

			}else if (option == 2){
				System.out.print("Enter new password:");
				usr.setPassword(scan.nextLine());
				usrServ.update(usr);
				LoggerUtil.getLogger().info("Password Successfully Updated!");
			} else {
				inMenu = false;
			}
		}
		
	}


	private static void transactions(int accID)  {
		Account acc = acServ.findAccount(accID);
		int option;
		int amt;
		boolean inWithdraw = true;
		while(inWithdraw) {
			System.out.println(acc.toString());
			System.out.println("What would you like to do?");
			System.out.println("1: Withdraw");
			System.out.println("2: Deposit"
							+ "\n3: Transfer funds to another account"
							+ "\n4: Close account");
			System.out.println("5: Back");
			option = SelectionService.getOption(5);
		
			switch(option) {
		
			case 1: 
				System.out.println("How much would you like to withdraw?");
				amt = SelectionService.getInteger();
				acServ.withdraw(accID, acc.getAccountBalance(), amt);
				LoggerUtil.getLogger().info("$" + amt + " successfully withdrawn from Account: " + acc.toString());
				acc.setAccountBalance(acc.getAccountBalance() - amt);

				
				break;
			case 2:
				System.out.println("How much would you like to deposit?");
				amt = SelectionService.getInteger();
				acServ.deposit(accID, acc.getAccountBalance(), amt);
				LoggerUtil.getLogger().info("$" + amt + " successfully deposited to Account: " + acc.toString());
				acc.setAccountBalance(acc.getAccountBalance() + amt);

				break;
			case 3:
				System.out.println("Which account would you like to transfer to?");
				Account toacc = acServ.findAccount(SelectionService.getInteger());
				if (toacc == null) {
					System.out.println("Account not found!");
					break;
				}
				System.out.println("How much would you like to transfer?");
				amt = SelectionService.getInteger();
				acServ.transfer(acc.getAccountNumber(), toacc.getAccountNumber(), amt);
				LoggerUtil.getLogger().info("$" + amt + " successfully transferred from " + acc.toString() + " to " + toacc.toString());
				acc.setAccountBalance(acc.getAccountBalance() - amt);
				break;
			case 4:
				acServ.cancel(accID);
				LoggerUtil.getLogger().info(accID + " has been cancelled.");
				break;
			case 5:
				inWithdraw = false;
				break;
			default:
				LoggerUtil.log.info("Invalid menu option in transaction menu!");
			}
		
		}
	}
	

}
