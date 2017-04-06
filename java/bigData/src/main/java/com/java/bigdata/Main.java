package com.java.bigdata;


import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;

/**
 * @author Brahim Bahri
 * Date: 06/04/2017
 *
 * In this project, you find exampmles of how to use:
 *    - Impala
 *    - Hive
 *    - Kerberos
 *    - HBase
 *    - Kafka
 *    - JUnit
 *    - Java options
 *    - log4j
 *    - yaml conf file
 *    - System.time
 *    - Java 8
 */
public class Main {

    final static Logger logger = Logger.getLogger(Main.class.getName());
    final static String version = "0.1";

    public static void main(String[] args) {
        logger.info("Lancement du job");
        long startTime = System.nanoTime();


        Options jobOptions = new Options();
        jobOptions.addOption("h", "help", false, "Affichage de help");
        jobOptions.addOption("conf", "conf-file", true, "yml configuration file");
        jobOptions.addOption("v", "version", false, " Affichage de la version du  project");

        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(jobOptions, args);
            if (cmd.hasOption("h")) {
                HelpFormatter f = new HelpFormatter();
                System.out.println("Format d’exécution valide :: java -jar -Dlog4j.configuration=file:log4j.properties <application-name>.jar --conf-file </path/to/confFile.conf>");
                f.printHelp("Options valides", jobOptions);
                System.exit(0);
            }

            if (cmd.hasOption("v")) {
                logger.info("Affichage de la version actuelle");
                System.out.println(version);
                System.exit(0);
            }

            if (!cmd.hasOption("conf")) {
                HelpFormatter f = new HelpFormatter();
                logger.error("2011-L’option obligatoire manquant: \"-conf\" ");
                logger.error("Format d’exécution valide : java -jar -Dlog4j.configuration=file:log4j.properties <application-name>.jar --conf-file </path/to/confFile.conf>");
                f.printHelp("Options valides", jobOptions);
                System.exit(1);

            }

            //Loading the configuration variables from YML configuration file
            logger.info("Lecture du fichier de configuration " + cmd.getOptionValue("conf"));
            ConfigurationYmlParser.parse(cmd.getOptionValue("conf"));
            logger.info(ConfigurationYmlParser.conf);
            // System.out.println(conf);


            long stopTime = System.nanoTime();
            long elapsedTime = stopTime - startTime;
            double elapsedTimeInSeconds = (double)elapsedTime / 1000000000.0;

            System.out.println("elapsedTimeInSeconds "+elapsedTimeInSeconds);
            System.exit(0);

        } catch (ParseException e) {
            logger.error("2012-Erreur de parsing des options de la commande " + e);
            System.exit(2012);

        } catch (FileNotFoundException e) {
            logger.error("2013-Le fichier de configuration non trouvé " + e);
            System.exit(2013);
        }
    }
}
