package com.bes.common.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogDemo {
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("E:\\WorkZone\\demo\\src\\log.properties");
		Logger log = Logger.getLogger("basic");
		log.info("I am reading 	");
	}
}
