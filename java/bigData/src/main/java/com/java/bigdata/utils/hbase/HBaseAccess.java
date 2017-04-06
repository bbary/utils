package com.java.bigdata.utils.hbase;


import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;



/**
 * @author: brahim bahri
 * date: 06/04/2017
 *   This class contains all the functions that access HBase databases
 */
public class HBaseAccess {
    final static Logger logger = Logger.getLogger(HBaseAccess.class.getName());


    /**
     * Check if an Hbase table exists
     * @param hbTable: Hbase table name
     * @return
     */
    public static boolean tableExists(String hbTable)  {
        Configuration confHbase = HBaseConfiguration.create();
        confHbase.addResource(new Path(ConfigurationYmlParser.conf.hbase.hbase_site));
        confHbase.addResource(new Path(ConfigurationYmlParser.conf.hbase.core_site));
        HBaseAdmin hbaseAdmin = null;
        Boolean flag=false;
        try {
            hbaseAdmin = new HBaseAdmin(confHbase);
            flag= hbaseAdmin.tableExists(hbTable);

        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
        return flag;
    }


    /**
     * This function can be used to get a String or Integer value from HBase
     * @param tableName
     * @param rowKey
     * @param clmnFam
     * @param clmn
     * @return
     */
    public static String getStringVal(String tableName, String rowKey, String clmnFam, String clmn) {
        Connection hbaseCon = HBaseConnection.getConnection();
        String val="";

        try {
            Table table = hbaseCon.getTable(TableName.valueOf(tableName));
            //give the rowkey
            Get g = new Get(Bytes.toBytes(rowKey));
            Result r = table.get(g);
            byte [] value = r.getValue(Bytes.toBytes(clmnFam),
                    Bytes.toBytes(clmn));
            val = Bytes.toString(value);
            table.close();

        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
        return val;
    }

    /**
     * This function can be used to get a String or Integer value from HBase
     * @param tableName
     * @param rowKey
     * @param clmnFam
     * @param clmn
     * @return
     */
    public static Integer getIntegerVal(String tableName, String rowKey, String clmnFam, String clmn) {
        Connection hbaseCon = HBaseConnection.getConnection();
        int val = 0;

        try {
            Table table = hbaseCon.getTable(TableName.valueOf(tableName));
            //give the rowkey
            Get g = new Get(Bytes.toBytes(rowKey));
            Result r = table.get(g);
            byte [] value = r.getValue(Bytes.toBytes(clmnFam),
                    Bytes.toBytes(clmn));
            val = Bytes.toInt(value);
            table.close();

        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }

        return val;
    }

    /**
     * This function can be used to get a Boolean value from HBase
     * @param tableName
     * @param rowKey
     * @param clmnFam
     * @param clmn
     * @return
     */
    public static Boolean getBoolVal(String tableName, String rowKey, String clmnFam, String clmn) {
        Connection hbaseCon=HBaseConnection.getConnection();
        Boolean val = false;

        try {
            Table table = hbaseCon.getTable(TableName.valueOf(tableName));
            //give the rowkey
            Get g = new Get(Bytes.toBytes(rowKey));
            Result r = table.get(g);
            byte [] value = r.getValue(Bytes.toBytes(clmnFam),
                    Bytes.toBytes(clmn));
            val = Bytes.toBoolean(value);
            table.close();
        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }

        return val;

    }

    /**
     * Verify if a row key exists in HBase
     * @param tableName
     * @param key
     * @return
     */
    public static Boolean keyExists(final String tableName, final String key){
        Connection hbaseCon=HBaseConnection.getConnection();
        Boolean keyExistsFlag=false;
        Table table = null;
        try {
            table = hbaseCon.getTable(TableName.valueOf(tableName));
            //give the rowkey
            Get g = new Get(Bytes.toBytes(key));
            Result r = table.get(g);

            if (r.isEmpty()){
                keyExistsFlag=false;
            } else{
                keyExistsFlag=true;
            }

        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
        return keyExistsFlag;
    }



    /**
     * This function can be used to set a String or Integer value into HBase
     * @param tableName
     * @param rowKey
     * @param clmnFam
     * @param clmn
     * @param newValue
     * @return
     */
    public static void setVal(String tableName, String rowKey, String clmnFam, String clmn, String newValue) {
        Connection hbaseCon = HBaseConnection.getConnection();
        String val = null;

        try {
            Table table = hbaseCon.getTable(TableName.valueOf(tableName));
            Put p = new Put(Bytes.toBytes(rowKey));
            p.add(Bytes.toBytes(clmnFam), Bytes.toBytes(clmn),Bytes.toBytes(newValue));
            table.put(p);

            table.close();

        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }

    }

    /**
     * This function can be used to set a Bool value into HBase
     * @param tableName
     * @param rowKey
     * @param clmnFam
     * @param clmn
     * @param newValue
     * @return
     */
    public static void setBoolVal(String tableName, String rowKey, String clmnFam, String clmn, Boolean newValue) {
        Connection hbaseCon = HBaseConnection.getConnection();
        String val = null;

        try {
            Table table = hbaseCon.getTable(TableName.valueOf(tableName));
            Put p = new Put(Bytes.toBytes(rowKey));
            p.add(Bytes.toBytes(clmnFam), Bytes.toBytes(clmn),Bytes.toBytes(newValue));
            table.put(p);

            table.close();

        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }

    }

    /**
     * delete one row from an hbase table
     * @param tableName
     * @param row
     */
    public static Boolean deleteRow(String tableName, String row){
        Connection hbaseCon = HBaseConnection.getConnection();
        Delete d = new Delete(Bytes.toBytes(row));
        Boolean isDeleted=false;
        try {
            Table table = hbaseCon.getTable(TableName.valueOf(tableName));
            table.delete(d);
            isDeleted=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isDeleted;

    }

}