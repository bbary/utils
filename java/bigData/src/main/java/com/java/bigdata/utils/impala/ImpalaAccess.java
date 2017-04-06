package com.java.bigdata.utils.impala;


import org.apache.log4j.Logger;
import java.sql.*;

/**
 * @author brahim bahri
 * date: 08/02/2017
 */

public class ImpalaAccess {

    final static Logger logger = Logger.getLogger(ImpalaAccess.class.getName());

    /**
     * refresh impala metadata after an Hive insertion or alter
     * @param table: you can give the database as well: database.table
     */
    public static void refreshImpala(String table){
        Connection impalaConn= ImpalaConnection.getConnection();
        Statement ps = null;
        try {
            ps = impalaConn.createStatement();
            ps.execute("refresh "+table);
        } catch (SQLException e) {
            logger.error(e);
            System.exit(1);
        }
    }

    /**
     * Verifies if a specific impala table exists in the required database
     * @param db
     * @param tableName
     * @return
     */
    public static Boolean tableExists(String db, String tableName){
        Connection impalaConn = ImpalaConnection.getConnection();
        Boolean flag=false;
        try {
            PreparedStatement qShowTables=impalaConn.prepareStatement("show tables in " +db+ " like "+ "\'" + tableName.toLowerCase() +"\'");
            ResultSet rs = qShowTables.executeQuery();

            while ( rs.next()) {
                if(rs.getString(1).equalsIgnoreCase(tableName)){
                    flag= true;
                    break;
                }else{
                    flag =false;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.exit(1);
        }
        return flag;
    }


    /**
     * Executes a request
     * @param req: request to execute
     *
     */
    public static void executeReq(String req) {
        logger.info("Exécution de la requête Impala: "+req);
        Connection impalaConn= ImpalaConnection.getConnection();
        try {

            Statement ps = impalaConn.createStatement();
            ps.execute(req);

        } catch (SQLException e) {
            logger.error(e);
            System.exit(1);
        }
    }

}
