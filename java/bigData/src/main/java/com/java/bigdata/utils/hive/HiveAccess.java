package com.java.bigdata.utils.hive;


import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * @author: brahim bahri
 * date: 06/04/2017
 */
public class HiveAccess {
    final static Logger logger = Logger.getLogger(HiveAccess.class.getName());

      /**
     * Creates a Hive statement with all the required properties set
     * @return
     * @throws SQLException
     */
    public static Statement getHiveStatement() throws SQLException {
        Connection hiveConn = HiveConnection.getConnection();
        Statement stt = hiveConn.createStatement();
        stt.execute("set hive.execution.engine=" + ConfigurationYmlParser.conf.hive.exec_engine);
        stt.execute("set hive.auto.convert.join=true");
        stt.execute("set mapreduce.map.java.opts=" + ConfigurationYmlParser.conf.hive.mr_memory);
        return stt;
    }

    /**
     * Checks if a Hive data base exists
     * @param db
     * @return Boolean
     */
    public static Boolean hiveDbExists(String db){
        java.sql.Connection hiveCon = HiveConnection.getConnection();
        //String job
        Boolean flag=false;
        try {
            PreparedStatement qValidDbs=hiveCon.prepareStatement("show databases like "+"\'"+db+"\'");
            ResultSet rs = qValidDbs.executeQuery();

            while ( rs.next()) {
                if(rs.getString(1).equalsIgnoreCase(db)){
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
     * Verifies if a specific hive table exists in the required database
     * @param db
     * @param tableName
     * @return
     */
    public static Boolean hiveTableExists(String db, String tableName){
        java.sql.Connection hiveCon =HiveConnection.getConnection();
        Boolean flag=false;
        try {
            PreparedStatement qShowTables=hiveCon.prepareStatement("show tables in " +db+ " like "+ "\'" + tableName +"\'");
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
     * sample insert
     */
    public static void insertInto( ) {
        Connection hiveConn=HiveConnection.getConnection();
        try {

            Statement ps = hiveConn.createStatement();
            //Setting dynamic partition nonstrict
            ps.execute("set hive.exec.dynamic.partition.mode=nonstrict");

            //Setting dynamic partition true
            ps.execute("SET hive.exec.dynamic.partition = true");

            //executing the query
            ps.execute("INSERT INTO db.table  partition(part_yyyy, part_mm, part_dd ) ...");

        } catch (SQLException e) {
            logger.error(e);
            System.exit(1);
        }
    }

    /**
     * get the first found row from Hive with the id given in parameter (the id is unique)
     * the field here is a float
     * @param dataBase
     * @param table
     * @param field
     * @param value
     */
    public static ResultSet getObject(String dataBase, String table, String field, float value) {

        Connection hiveConn= HiveConnection.getConnection();

        try {
            Statement ps = hiveConn.createStatement();
            String req="select * from "+dataBase+"."+table+" where "+field+"="+value+" limit 1";
            ResultSet rs=ps.executeQuery(req);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e);
            System.exit(1);
        }
        return null;

    }


}

