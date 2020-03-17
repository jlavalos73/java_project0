package com.bankofavalos.registration;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.menus.MainMenu;
import com.bankofavalos.models.Users;
import com.bankofavalos.services.UsersService;
import com.bankofavalos.util.LoggerUtil;
import com.bankofavalos.util.ScannerUtil;

public class LogInMenu {
	
	public static Logger log = LoggerUtil.getLogger();
	
	public static final Scanner scan = ScannerUtil.getScanner();

		
	public static void runLogInMenu(){
		Users usr = null;
		UsersService usrServ = new UsersService();
		
		System.out.println("Please enter your email: ");
		
		String email = scan.nextLine();
		
		if(usrServ.findByEmail(email) == null){
					
				System.out.println("This email has not been registered. \nPlease enter a different email.");

			} else {
				Users match = usrServ.findByEmail(email);
				
				System.out.println("Please enter your password: ");
				
				String pw = scan.nextLine();
				
				if(match.getPassword().equals(pw)) {
					usr = match;
				} else {
					System.out.println("Passwords did not match. \nPlease try again.");
				}
			}
		
		
		System.out.println("successfully logged in");
		MainMenu.runMainMenu(usr);
	} 

}
