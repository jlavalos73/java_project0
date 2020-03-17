package com.bankofavalos.services;


import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bankofavalos.util.ScannerUtil;
import com.bankofavalos.util.LoggerUtil;

public class SelectionService {

	public static int getOption(int mItem) {
		Scanner scan = ScannerUtil.getScanner();
		Logger log = LoggerUtil.getLogger();
		boolean available = true;
		String option = scan.nextLine();
		while (available) {
			try {
				Integer.parseInt(option);
				available = false;
				if (Integer.valueOf(option) > mItem || Integer.valueOf(option) <= 0) {
					log.info("Please enter an available of the menu option:");
					option = scan.nextLine();
					available = true;
				}
			}catch(NumberFormatException e) {
				log.info("Please enter the number of the menu option", e);
				option = scan.nextLine();
			}
		}
		return Integer.valueOf(option);
	}

	public static int getInteger() {
		Scanner scan = ScannerUtil.getScanner();
		Logger log = LoggerUtil.getLogger();
		boolean available = true;
		String option = scan.nextLine();
		while (available) {
			try {
				Integer.parseInt(option);
				if(Integer.valueOf(option) <= 0)
				{
					System.out.println("Can't choose a negative number!");
					option = scan.nextLine();
				}else {
				available = false;
				}
				}
			catch(NumberFormatException e) {
				log.info("Please enter a valid account Id");
				option = scan.nextLine();
			}
		}
		return Integer.valueOf(option);
	}
}
