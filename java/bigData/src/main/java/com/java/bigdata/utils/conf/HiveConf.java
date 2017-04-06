package com.java.bigdata.utils.conf;

/**
 * Created by SMA on 01/08/2016.
 */
public class HiveConf {
    public String principal;
    public String exec_engine;
    public String mr_memory;
    public String server;
    public String port;
    public String db_integ;

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%sprincipal: %s\n", indent, principal));
        sb.append(String.format("%sexec_engine: %s\n", indent, exec_engine));
        sb.append(String.format("%smr_memory: %s\n", indent, mr_memory));
        sb.append(String.format("%sserver: %s\n", indent, server));
        sb.append(String.format("%sport: %s\n", indent, port));
        sb.append(String.format("%sdb_integ: %s\n", indent, db_integ));

        return sb.toString();
    }
}
