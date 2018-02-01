package me.andpay.ac.wpn.migrate;

import org.apache.commons.cli.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by QUYONG on 17/12/21.
 */
public class MigrateMain {

    @SuppressWarnings({"unchecked", "resource"})
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("t", true, "Table name.");
        options.addOption("u", true, "userName name.");
//        options.addOption("d", true, "ShareData name.");

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring-config/migrateApplicationContext.xml"});

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("t") == false) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("ac-wpn-migrate", options);
            System.exit(1);
        }

        String tableName = cmd.getOptionValue("t");
        if (tableName.equals("WxBindUser")) {
            WxBindUserMigrator wxBindUserMigrator = context.getBean(WxBindUserMigrator.class);
            if (cmd.hasOption("u")) {
                // ./xxx.sh -t WxBindUser -u 13700000000
                wxBindUserMigrator.migrateWxBindUser(cmd.getOptionValue("u"));
            } else {
                // ./xxx.sh -t WxBindUser
                wxBindUserMigrator.migrateAllWxBindUsers();
            }
        }
        System.exit(0);
    }
}

