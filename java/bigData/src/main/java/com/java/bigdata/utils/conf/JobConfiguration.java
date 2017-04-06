package com.java.bigdata.utils.conf;

/**
 * Created by SMA on 01/08/2016.
 */
public class JobConfiguration {

    public KerberosConf kerberos = new KerberosConf();
    public HBaseConf hbase = new HBaseConf();
    public HadoopConf hadoop = new HadoopConf();
    public HiveConf hive = new HiveConf();
    public ImpalaConf impala = new ImpalaConf();
    public KafkaConf kafka = new KafkaConf();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String indent = "  ";

        sb.append("\nJobConfiguration:\n");
        sb.append(String.format("%skerberos:\n%s\n", indent, kerberos.toString(indent + "  ")));
        sb.append(String.format("%shbase:\n%s\n", indent, hbase.toString(indent + "  ")));
        sb.append(String.format("%shadoop:\n%s\n", indent, hadoop.toString(indent + "  ")));
        sb.append(String.format("%shive:\n%s\n", indent, hive.toString(indent + "  ")));
        sb.append(String.format("%simpala:\n%s\n", indent, impala.toString(indent + "  ")));
        sb.append(String.format("%skafka:\n%s\n", indent, kafka.toString(indent + "  ")));
        return sb.toString();
    }
}
