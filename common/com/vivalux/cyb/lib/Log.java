package com.vivalux.cyb.lib;

import java.io.PrintStream;

public class Log {

	public static boolean debug = true;

	private PrintStream out = System.out;

	public Log(PrintStream out) {
		if (out != null) {
			this.out = out;
		}
	}

	public Log() {

	}

	public static void log(String str) {
		System.out.println(str);
	}

	public static void debug(String str) {
		if (debug)
			log("[Cybernetica:DEBUG] " + str);
	}

	public static void warning(String str) {
		log("[Cybernetica:WARNING] " + str);
	}

	public static void info(String str) {
		log("[Cybernetica:INFO] " + str);
	}

}
