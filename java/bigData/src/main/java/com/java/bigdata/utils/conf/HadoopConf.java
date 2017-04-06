package com.java.bigdata.utils.conf;

/**
 * @author  brahim bahri
 */
public class HadoopConf {
    public String hdfs_site;
    public String hadoop_core_site;

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%shdfs_site: %s\n", indent, hdfs_site));
        sb.append(String.format("%shadoop_core_site: %s\n", indent, hadoop_core_site));

        return sb.toString();
    }
}
