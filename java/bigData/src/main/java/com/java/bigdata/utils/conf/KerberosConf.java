package com.java.bigdata.utils.conf;

/**
 * Created by SMA on 01/08/2016.
 */
public class KerberosConf {
    public String principal;
    public String keytab;
    public String krb5;

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%sprincipal: %s\n", indent, principal));
        sb.append(String.format("%skeytab: %s\n", indent, keytab));
        sb.append(String.format("%skrbFile: %s\n", indent, krb5));

        return sb.toString();
    }
}
