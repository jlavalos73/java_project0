package com.bankofavalos.util;
import org.apache.log4j.Logger;

public class LoggerUtil {

	public static Logger log = Logger.getLogger(LoggerUtil.class);
	
	private LoggerUtil() {
		super();
	}
	public static Logger getLogger() {
		return log;
	}
	
}
