package com.bankofavalos.registration;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.models.UserTypes;
import com.bankofavalos.models.Users;
import com.bankofavalos.services.UsersService;
import com.bankofavalos.util.LoggerUtil;
import com.bankofavalos.util.ScannerUtil;

public class RegMenu {
	
	public static Logger log = LoggerUtil.getLogger();
	
	public static final Scanner scan = ScannerUtil.getScanner();
		
	public static void runRegMenu(){
		Users usr = new Users();
		UsersService usrServ = new UsersService();
		String email;
		
		System.out.print("Please enter your first name: ");
		usr.setFirstName(scan.nextLine());
		System.out.print("Please enter your last name: ");
		usr.setLastName(scan.nextLine());
		System.out.print("Please enter your email: ");
		email = scan.nextLine();
		
		
		if(usrServ.findByEmail(email) != null){
				
			log.info("This email has already been registered. \nPlease enter a different email.");

		} else {
			usr.setEmail(email);
			System.out.print("Please enter your desired password: ");
			usr.setPassword(scan.nextLine());
			usr.setUserType(UserTypes.Customer);
			usrServ.insert(usr);
			log.info("User has been successfully registered.");
		}

	}

}
