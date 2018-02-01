package me.andpay.ac.wpn.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AcWpnWebMain {
	final static Logger logger = LoggerFactory.getLogger(AcWpnWebMain.class);
	
	public static void main(String[] args) {
			AbstractApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring-config/ac-wpn-web-jetty-config.xml" });
			context.registerShutdownHook();

			logger.info("ac-wpn-web Server started.");
			System.out.println("ac-wpn-web Server started.");
	}
}