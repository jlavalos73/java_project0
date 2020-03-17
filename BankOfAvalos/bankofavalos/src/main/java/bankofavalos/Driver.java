package bankofavalos;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.models.Users;
import com.bankofavalos.registration.AdminRegMenu;
import com.bankofavalos.registration.EERegMenu;
import com.bankofavalos.registration.LogInMenu;
import com.bankofavalos.registration.RegMenu;
import com.bankofavalos.services.SelectionService;
import com.bankofavalos.services.UsersService;
import com.bankofavalos.util.LoggerUtil;
import com.bankofavalos.util.ScannerUtil;

public class Driver {
	
	public static Logger log = LoggerUtil.getLogger();
	public static final Scanner scan = ScannerUtil.getScanner();
	public static void main(String[] args) {
		
		boolean menu = true;
		
		while(menu) {
			Users usr = new Users();
			UsersService usrServ = new UsersService();
			
			log.info(
					"\n"
				  + "========================================\n"
				  + "            Bank of Avalos              \n"
				  + "========================================\n"
				  + "\nPlease make a selection:"
				  + "\n1) Register as a Customer"
				  + "\n2) Register as a Employee"
				  + "\n3) Register as an Admin"
				  + "\n4) Log In"
				  + "\n0) Exit");
		
			int choice = SelectionService.getOption(5);
			
			switch(choice) {
				case 0:
					menu = false;
					log.info("Thank you for banking with Bank of Avalos.");
					break;
				case 1:
					RegMenu.runRegMenu();
					break;
				case 2:
					EERegMenu.runEERegMenu();
					break;
				case 3:
					AdminRegMenu.runAdminRegMenu();
					break;
				case 4:
					LogInMenu.runLogInMenu();
					break;
				default:
					System.out.println("You have selected an unavailable option.");
			}
		}
	}
}
