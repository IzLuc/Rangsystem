package de.ragecores.rangsystem.utils;

public class LogHandler {

	private static String errorPrefix;
	private static String consolePrefix;

	public LogHandler() {
		errorPrefix = "[RangSystem-Error] ";
		consolePrefix = "[RangSystem-Console] ";
	}

	public static void err(String message) {
		System.err.println(errorPrefix + "Exception: " + message);
	}

	public static void out(String message) {
		System.out.println(consolePrefix + message);
	}

	public static String getErrorPrefix() {
		if (errorPrefix == null) {
			return null;
		}
		return errorPrefix;
	}

	public static String getConsolePrefix() {
		if (consolePrefix == null) {
			return "";
		}
		return consolePrefix;
	}
	
	

}
