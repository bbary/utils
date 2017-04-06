package com.java.bigdata.utils.hbase;

import com.java.bigdata.utils.KrbHandler;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.PrivilegedAction;

/**
 * Created by SMA on 28/11/2016.
 */
public class HBaseConnectionSecure {
    final static Logger logger = Logger.getLogger(HBaseConnectionSecure.class.getName());
    private static Connection hbaseCon = null;


    /**
     * Check if an Hbase table exists
     * @param hbTable: Hbase table name
     * @return
     */
    public static boolean tableExists(String hbTable)  {
        HBaseAdmin hbaseAdmin = null;
        Boolean flag=false;
        try {
          hbaseAdmin = new HBaseAdmin(KrbHandler.getConf());
          flag = hbaseAdmin.tableExists(hbTable);
        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
        return flag;
    }


    /**
     * Hack function to set the global variable hbaseConn which
     cannot be done inside a closure function
     * @param con
     */
    public static void setHBaseConnectionVariable( Connection con){
        hbaseCon=con;
    }

    /**
     * Opens HBase connection
     */
    private static void open() {
        KrbHandler.getUgi().doAs(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Connection con = ConnectionFactory.createConnection(KrbHandler.getConf());
                    setHBaseConnectionVariable(con);
                } catch (IOException e) {
                    logger.error(e);
                    System.exit(1);
                }
                return null;
            }
        });
    }

    public static void close(){
        try {
            logger.info("Fermeture de la connexion HBase");
            hbaseCon.close();
        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
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
}
