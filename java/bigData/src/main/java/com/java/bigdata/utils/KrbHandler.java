package com.java.bigdata.utils;

import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author Brahim Bahri
 *
 * Handle connection to Kerberos + centralize configuration
 */
public class KrbHandler {

    final static Logger logger = Logger.getLogger(KrbHandler.class.getName());

    private static Configuration conf = null;
    private static UserGroupInformation ugi = null;

    /**
     * Login to Kerberos using given configuration, implicitly called on first getUgi()
     *
     * @return logged UGI
     */
    private static UserGroupInformation login() {
        if (conf == null) {
            conf = new Configuration();

            logger.info("Préparation de la configuration Kerberos");
            System.setProperty("java.security.krb5.conf", ConfigurationYmlParser.conf.kerberos.krb5);

            // Set HDFS + HBase conf file paths
            conf.addResource(new Path("file://" + ConfigurationYmlParser.conf.hadoop.hadoop_core_site));
            conf.addResource(new Path("file://" + ConfigurationYmlParser.conf.hadoop.hdfs_site));
            conf.addResource(new Path("file://" + ConfigurationYmlParser.conf.hbase.hbase_site));

            // Set auth type
            conf.set("hadoop.security.authentication", "kerberos");

            logger.info("Authentification auprès de Kerberos");
            UserGroupInformation.setConfiguration(conf);
            try {
                ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(
                        ConfigurationYmlParser.conf.kerberos.principal,
                        ConfigurationYmlParser.conf.kerberos.keytab
                );
            } catch (IOException e) {
                logger.error("Erreur lors de la connexion à Kerberos");
                e.printStackTrace();
                System.exit(1);
            }
        }

        return ugi;
    }

    public static Configuration getConf() { return conf; }

    public static UserGroupInformation getUgi() {
        if (ugi == null) return login();
        else return ugi;
    }
}
