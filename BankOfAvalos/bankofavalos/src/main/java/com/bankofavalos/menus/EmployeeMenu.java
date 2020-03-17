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

public class EmployeeMenu {
	public static Logger log = LoggerUtil.getLogger();
	public static final Scanner scan = ScannerUtil.getScanner();
	public static UsersService usrServ = new UsersService();
	public static AccountServices acServ = new AccountServices();
	
	public static void runEEMenu(Users usr){
		boolean loggedIn = true;
		
		while(loggedIn) {
			log.info(
					"\n"
				  + "========================================\n"
				  + "            Bank of Avalos              \n"
				  + "========================================\n"
				  + "\nPlease make a selection:"
				  + "\n1) Approve pending accounts"
				  + "\n2) Search for users"
				  + "\n3) Exit");
			
			int choice = SelectionService.getOption(4);
			
			switch(choice) {
				case 1:
					approveAccounts();
					break;
				case 2:
					search();
					break;
				case 3:
					loggedIn = false;
					log.info("Thank you for banking with Bank of Avalos.");
					break;
				default:
					System.out.println("You have selected an unavailable option.");
			}
		}
	}

	private static void search() {
		List<Users> customers = usrServ.findCustomers();
		Users usrSel = new Users();
		int li = 1;
		for(Users usr : customers) {
			System.out.println(li++ + ": "  + usr.toString());
		}
		System.out.println(li + ": Back");
		int option = SelectionService.getOption(li);
		if(li != option) {
			System.out.println("Which account would you like to view?");
			usrSel = customers.get(option-1);
			viewAccounts(usrSel);
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
	
	public static void viewAccounts(Users usr) {
		List<Integer> accts = usrServ.findAccounts(usr.getUserId());
		Account acSel = new Account();
		int li = 1;
		
		for(Integer acc : accts) {
			System.out.println(li++ + ": "  + acc.toString());
		}
		System.out.println(li + ": Back");
		int option = SelectionService.getOption(li);
			if (li == 0) {
				System.out.println("No accounts found!");
			}
			System.out.println(li + ": Back");
			option = SelectionService.getOption(li);
			if (option == li) {
				acSel = acServ.findAccount(li);
				System.out.println(acSel);
			} 
	}
}


