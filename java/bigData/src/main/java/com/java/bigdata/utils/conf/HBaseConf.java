package com.java.bigdata.utils.conf;


public class HBaseConf {
    public String hbase_site;
    public String core_site;
    public String table_objets;

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%shbase_site: %s\n", indent, hbase_site));
        sb.append(String.format("%score_site: %s\n", indent, core_site));
        sb.append(String.format("%stable_objets: %s\n", indent, table_objets));

        return sb.toString();
    }


}
