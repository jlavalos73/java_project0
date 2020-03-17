package com.bankofavalos.menus;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.models.UserTypes;
import com.bankofavalos.models.Users;
import com.bankofavalos.services.UsersService;
import com.bankofavalos.util.LoggerUtil;
import com.bankofavalos.util.ScannerUtil;

public class MainMenu {
	
	public static Logger log = LoggerUtil.getLogger();
	
	public static final Scanner scan = ScannerUtil.getScanner();
	
	public static void runMainMenu(Users usr) {
		
		UserTypes type = usr.getUserType();
				
		switch(type) {
			case Customer:
				CustomerMenu.runCustomerMenu(usr);
				break;
			case Employee:
				EmployeeMenu.runEEMenu(usr);
				break;
			case Admin:
				AdminMenu.runAdminMenu(usr);
				break;
			default:
				System.out.println("You have selected an unavailable option.");
		}
	}

}
