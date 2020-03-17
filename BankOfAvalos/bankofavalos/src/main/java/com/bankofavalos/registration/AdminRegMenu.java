package com.bankofavalos.registration;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.models.UserTypes;
import com.bankofavalos.models.Users;
import com.bankofavalos.services.UsersService;
import com.bankofavalos.util.LoggerUtil;
import com.bankofavalos.util.ScannerUtil;

public class AdminRegMenu {
	
	public static Logger log = LoggerUtil.getLogger();
	
	public static final Scanner scan = ScannerUtil.getScanner();

	private static final String key = "adminPass";
		
	public static void runAdminRegMenu(){
		Users usr = new Users();
		UsersService usrServ = new UsersService();
		
		System.out.println("Please enter admin registration code: ");
		
		if(key.equals(scan.nextLine())) {
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
				usr.setUserType(UserTypes.Admin);
				usrServ.insert(usr);
				log.info("Admin has been successfully registered.");
			}
		}else {
			System.out.println("Registration code was incorrect. \nYou will be redirected to main menu.");
		}
	}
}
