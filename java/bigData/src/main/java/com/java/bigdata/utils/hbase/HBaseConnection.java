package com.java.bigdata.utils.hbase;

import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author: brahim bahri
 */
public class HBaseConnection {
    final static Logger logger = Logger.getLogger(HBaseConnection.class.getName());
    private static Connection hbaseCon=null;
    private static Configuration confHbase;

    /**
     * Opens HBase connection
     */
    private static void open(){
        logger.info("Overture de la connexion HBase");
        confHbase = HBaseConfiguration.create();
        confHbase.addResource(new Path(ConfigurationYmlParser.conf.hbase.hbase_site));
        confHbase.addResource(new Path(ConfigurationYmlParser.conf.hbase.core_site));
        try {
            hbaseCon = ConnectionFactory.createConnection(confHbase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close function closes the connection
     * this function must be called only once at the job end because hbase connection is a singleton
     */

    public static void close(){
        if(hbaseCon != null) {
            try {
                logger.info("Fermeture de la connexion HBase");
                hbaseCon.close();
            } catch (IOException e) {
                logger.error(e);
                System.exit(1);
            }
        }else {
            logger.info("Aucune connexion HBase n'est ouverte");
        }
    }

    /**
     * Creates a secure HBase connection using Kerberos and return it
     * @return Hbase connection object
     */
    public static Connection getConnection(){
        if (hbaseCon == null) {
            open();
        }
        return hbaseCon;
    }

    /**
     * Check if an Hbase table exists
     * @param hbTable: Hbase table name
     * @return
     */
    public static boolean tableExists(String hbTable)  {
        HBaseAdmin hbaseAdmin = null;
        Boolean flag=false;
        try {
            hbaseAdmin = new HBaseAdmin(confHbase);
            flag = hbaseAdmin.tableExists(hbTable);
        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
        return flag;
    }
}
