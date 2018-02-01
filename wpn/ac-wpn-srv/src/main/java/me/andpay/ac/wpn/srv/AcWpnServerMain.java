package me.andpay.ac.wpn.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by cen on 16/11/4.
 */
public class AcWpnServerMain {



    private static Logger LOG = LoggerFactory.getLogger(AcWpnServerMain.class);

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring-config/ac-wpn-srv-config.xml" });
        context.registerShutdownHook();

        LOG.info("Ac-Wpn-Srv Server started.");
        System.out.println("Ac-Wpn-Srv Server started.");
    }
}
