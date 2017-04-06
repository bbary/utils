package com.java.bigdata.utils.impala;

import com.java.bigdata.utils.KrbHandler;
import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.log4j.Logger;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Brahim on 10/02/2017.
 * Singleton Class to create Hive Connection and to make it accessible from everywhere
 */
public class ImpalaConnection {
    final static Logger logger = Logger.getLogger(ImpalaConnection.class.getName());
    private static Connection impalaCon=null;

    private static void open(){
        KrbHandler.getUgi().doAs(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                logger.info("Ouverture de la connexion Impala");

                try {
                    Class.forName("org.apache.hive.jdbc.HiveDriver");
                    impalaCon = DriverManager.getConnection(String.format(
                            "jdbc:hive2://%s:%s/default;principal=%s",
                            ConfigurationYmlParser.conf.impala.server,
                            ConfigurationYmlParser.conf.impala.port,
                            ConfigurationYmlParser.conf.impala.principal
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
     * this function must be called only once at the job end because Impala connection is a singleton
     */

    public static void close(){
        if(impalaCon != null) {
            try {
                logger.info("Fermeture de la connexion Impala");
                impalaCon.close();
            } catch (SQLException e) {
                logger.error(e);
                System.exit(1);
            }
        }else{
            logger.info("Aucune connexion Impala n'est ouverte");
        }
    }

    public static Connection getConnection(){
        if(impalaCon ==null){
            open();
        }
        return impalaCon;
    }

}
