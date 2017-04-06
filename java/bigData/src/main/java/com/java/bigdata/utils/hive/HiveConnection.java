package com.java.bigdata.utils.hive;

import com.java.bigdata.utils.KrbHandler;
import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.log4j.Logger;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author: brahim bahri
 * Singleton Class to create Hive Connection and to make it accessible from everywhere
 */
public class HiveConnection {
    final static Logger logger = Logger.getLogger(HiveConnection.class.getName());

    private static Connection hiveCon = null;

    private static void open(){
        KrbHandler.getUgi().doAs(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                logger.info("Ouverture de la connexion Hive");

                try {

                    Class.forName("org.apache.hive.jdbc.HiveDriver");
                    hiveCon = DriverManager.getConnection(String.format(
                            "jdbc:hive2://%s:%s/default;principal=%s",
                            ConfigurationYmlParser.conf.hive.server,
                            ConfigurationYmlParser.conf.hive.port,
                            ConfigurationYmlParser.conf.hive.principal
                    ));

                } catch (Exception e) {
                    logger.error(e);
                    System.exit(1);
                }

                return null;
            }
        });
    }

    /**
     * Close function closes the connection
     * this function must be called only once at the job end because Hive connection is a singleton
     */
    public static void close(){
        if(hiveCon != null) {
            try {
                logger.info("Fermeture de la connexion Hive");
                hiveCon.close();
            } catch (SQLException e) {
                logger.error(e);
                System.exit(1);
            }
        }else{
            logger.info("Aucune connexion Hive n'est ouverte");
        }
    }

    public static Connection getConnection(){
        if(hiveCon ==null){
            open();
        }
        return hiveCon;
    }

}
