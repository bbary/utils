package com.java.bigdata.utils.conf;


import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.yaml.snakeyaml.TypeDescription;

/**
 * Static container + parser for the YAML configuration
 */
public abstract class ConfigurationYmlParser {

    public static JobConfiguration conf;

    public static void parse(String fileName) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(new File(fileName));
        Constructor csr =new Constructor(JobConfiguration.class);

        TypeDescription ucDesc = new TypeDescription(JobConfiguration.class);
        ucDesc.putListPropertyType("kerberos", KerberosConf.class);
        ucDesc.putListPropertyType("hbase", HBaseConf.class);
        ucDesc.putListPropertyType("hadoop", HadoopConf.class);
        ucDesc.putListPropertyType("hive", HiveConf.class);
        ucDesc.putListPropertyType("impala", ImpalaConf.class);
        ucDesc.putListPropertyType("kafka", KafkaConf.class);

        Yaml yaml = new Yaml(csr);

        conf = (JobConfiguration)yaml.load(input);
    }
}
