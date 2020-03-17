package com.bankofavalos.util;

import java.util.Scanner;

public class ScannerUtil {
	
	public static Scanner scan = new Scanner(System.in);
	
	private ScannerUtil() {
		super();
	}
	
	public static Scanner getScanner() {
		return scan;
	}
	
	public static void closeScanner() {
		scan.close();
	}
	

}
