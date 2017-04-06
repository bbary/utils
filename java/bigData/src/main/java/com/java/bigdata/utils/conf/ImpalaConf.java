package com.java.bigdata.utils.conf;

/**
 * Created by brahim on 10/02/17.
 */
public class ImpalaConf { 

    public String principal;
    public String server;
    public String port;

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%sprincipal: %s\n", indent, principal));
        sb.append(String.format("%sserver: %s\n", indent, server));
        sb.append(String.format("%sport: %s\n", indent, port));

        return sb.toString();
    }
}