package com.java.bigdata.utils.hdfs;

import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

//import com.gfi.sherloqq.traitementOCRs.dor.metadata.*;

/**
 * @author: brahim bahri
 */
public class HdfsAccess {

    final static Logger logger = Logger.getLogger(HdfsAccess.class.getName());

    /**
     * Writes data to HDFS file
     */
    public static void writeToHFDS() {
        Configuration config = new Configuration();
        config.addResource(new Path(ConfigurationYmlParser.conf.hbase.core_site));
        //config.addResource(new Path(ConfigurationYmlParser.conf.hadoop.hdfs_site + "hdfs-site.xml"));
        Path pathOS = new Path("/tmp/testHDFSWrite");
        config.set("fs.hdfs.impl",
                DistributedFileSystem.class.getName()
        );
        config.set("fs.file.impl",
                LocalFileSystem.class.getName()
        );
        FileSystem fs = null;
        try {
            fs = FileSystem.get(new Configuration(config));

            BufferedWriter bwOS = new BufferedWriter(new OutputStreamWriter(fs.create(pathOS, true)));
            bwOS.write("hey");

            bwOS.flush();
            bwOS.close();


        } catch (IOException e) {
            logger.error(e);
            System.exit(1);

        }

    }

    /**
     * Checks if a HDFS file exists
     * @param pathDirStr
     * @return
     */
    public static boolean hdfsDirExists( String pathDirStr){
        Configuration config = new Configuration();
        config.addResource(new Path(ConfigurationYmlParser.conf.hbase.core_site));
        //config.addResource(new Path(ConfigurationYmlParser.conf.hadoop.hdfs_site + "hdfs-site.xml"));
       Path pathDir = new Path(pathDirStr);
        config.set("fs.hdfs.impl",
                DistributedFileSystem.class.getName()
        );
        config.set("fs.file.impl",
                LocalFileSystem.class.getName()
        );
        FileSystem fs = null;
        Boolean flag = false;

        try {
            fs = FileSystem.get(new Configuration(config));
            flag=fs.exists(pathDir) ;

        } catch (IOException e) {
            logger.error(e);
            System.exit(1);
        }
        return flag;
    }
}
