package com.bankofavalos.menus;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.models.Account;
import com.bankofavalos.models.UserTypes;
import com.bankofavalos.models.Users;
import com.bankofavalos.services.AccountServices;
import com.bankofavalos.services.SelectionService;
import com.bankofavalos.services.UsersService;
import com.bankofavalos.util.LoggerUtil;
import com.bankofavalos.util.ScannerUtil;

public class CustomerMenu {
	public static Logger log = LoggerUtil.getLogger();
	public static final Scanner scan = ScannerUtil.getScanner();
	public static UsersService usrServ = new UsersService();
	public static AccountServices acServ = new AccountServices();
	
	public static void runCustomerMenu(Users usr){
		boolean loggedIn = true;
		
		while(loggedIn) {
			log.info(
					"\n"
				  + "========================================\n"
				  + "            Bank of Avalos              \n"
				  + "========================================\n"
				  + "\nPlease make a selection:"
				  + "\n1) View accounts"
				  + "\n2) Edit profile"
				  + "\n3) Apply for a new account"
				  + "\n0) Exit");
			
			int choice = SelectionService.getOption(4);
			
			switch(choice) {
				case 0:
					loggedIn = false;
					log.info("Thank you for banking with Bank of Avalos.");
					break;
				case 1:
					viewAccounts(usr);
					break;
				case 2:
					editProfile(usr);
					break;
				case 3:
					apply(usr);
					break;
				default:
					System.out.println("You have selected an unavailable option.");
			}
		}
	}
	
	private static void apply(Users usr) {
		Account acct = new Account();
		int acNum = Account.createAccountNumber();
		acct.setAccountNumber(acNum);
		
		System.out.print("Is this a joint account? Y/N: ");
		String choice = scan.nextLine();
		if(choice.equals("Y")) {
			acServ.insert(acct, usr);
			Users usr2 = new Users();
			System.out.println("Is your partner a registered user? Y/N: ");
			choice = scan.nextLine();
			if(choice.equals("Y")) {
				System.out.println("What is your partner's email?");
				String u2mail = scan.nextLine();
				if(usrServ.findByEmail(u2mail) != null) {
					usr2 = usrServ.findByEmail(u2mail);
					acServ.insertOwner(acNum, usr2);
					System.out.println("Joint account application has been submited");
				} else {
					usr2.setEmail(u2mail);
					System.out.println("Email does not exist. Please register your partner");
					System.out.print("Please enter your partner's name: ");
					usr2.setFirstName(scan.nextLine());
					System.out.print("Please enter your partner's last name: ");
					usr2.setLastName(scan.nextLine());
					System.out.print("Please set your partner's password: ");
					usr2.setPassword(scan.nextLine());
					usr2.setUserType(UserTypes.Customer);
					usrServ.insert(usr2);
					acServ.insertOwner(acNum, usr2);
					System.out.println("Partner has been successfully registered");
					System.out.println("Joint account application has been submited");
				}
			} else {
				System.out.print("Please enter your partner's name: ");
				usr2.setFirstName(scan.nextLine());
				System.out.print("Please enter your partner's last name: ");
				usr2.setLastName(scan.nextLine());
				System.out.print("Please enter your partner's email: ");
				String email = scan.nextLine();
				usr2.setEmail(email);
				System.out.print("Please set your partner's password: ");
				usr2.setPassword(scan.nextLine());
				usr2.setUserType(UserTypes.Customer);
				usrServ.insert(usr2);
				usr2 = usrServ.findByEmail(email);
				acServ.insertOwner(acNum, usr2);
				System.out.println("Partner has been successfully registered");
				System.out.println("Joint account application has been submited");
			}
		} else {
			acServ.insert(acct, usr);
			log.info("Account application has been submitted.");
			runCustomerMenu(usr);	
		}
			
		
	}

	private static void editProfile(Users usr) {
		UsersService usrServ = new UsersService();
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

	public static void viewAccounts(Users usr) {
		int li = 1;
		int option;
		
		log.info(
				"\n"
			  + "========================================\n"
			  + "            Bank of Avalos              \n"
			  + "========================================\n"
			  + "========Number=============Balance======\n");
		
		for(int accountNumber: usr.getAccounts()) {
			System.out.println(li++ + ": " + accountNumber + "..........................");
			}
			if (li == 0) {
				System.out.println("No accounts found!");
			}
			System.out.println(li + ": Back");
			option = SelectionService.getOption(li);
			if(option != li) {
				transactions(usr.getAccounts().get(option-1), usr);
			}

	}

	private static void transactions(int id, Users usr) {
		AccountServices acServ = new AccountServices();
		Account acct = acServ.findAccount(id);
		int option;
		int amt;
		boolean inWithdraw = true;
		while(inWithdraw) {
			System.out.println(acct.toString());
			System.out.println("What would you like to do?");
			System.out.println("1: Withdraw");
			System.out.println("2: Deposit"
							+ "\n3: Transfer funds to another account");
			System.out.println("4: Back");
			option = SelectionService.getOption(4);
		
			switch(option) {
		
			case 1: 
				System.out.println("How much would you like to withdraw?");
				amt = SelectionService.getInteger();
				acServ.withdraw(id, acct.getAccountBalance(), amt);
				LoggerUtil.getLogger().info("$" + amt + " successfully withdrawn from Account: " + acct.toString());
				acct.setAccountBalance(acct.getAccountBalance() - amt);

				
				break;
			case 2:
				System.out.println("How much would you like to deposit?");
				amt = SelectionService.getInteger();
				acServ.deposit(id, acct.getAccountBalance(), amt);
				LoggerUtil.getLogger().info("$" + amt + " successfully deposited to Account: " + acct.toString());
				acct.setAccountBalance(acct.getAccountBalance() + amt);

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
				acServ.transfer(acct.getAccountNumber(), toacc.getAccountNumber(), amt);
				LoggerUtil.getLogger().info("$" + amt + " successfully transferred from " + acct.toString() + " to " + toacc.toString());
				acct.setAccountBalance(acct.getAccountBalance() - amt);
				break;
			case 4:
				inWithdraw = false;
				break;
			default:
				LoggerUtil.log.info("Invalid menu option in transaction menu!");
			}
		
		}
	}


}
