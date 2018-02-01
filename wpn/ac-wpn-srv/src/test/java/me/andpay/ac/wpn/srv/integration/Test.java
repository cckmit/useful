package me.andpay.ac.wpn.srv.integration;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tangzhiqiang on 17/9/13.
 */
public class Test {


    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring-config/ac-wpn-srv-config.xml" });

    }
}
