package com.java.bigdata.utils;

import com.java.bigdata.utils.conf.ConfigurationYmlParser;
import com.java.bigdata.utils.impala.ImpalaAccess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

/**
 * @author: brahim bahri
 * @date: 06/04/2017
 */
public class ImpalaAccessTest {
    @Rule
    public ExpectedException exceptions = ExpectedException.none();

    @BeforeClass
    public static void setupData() throws ExecutionException, InterruptedException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, FileNotFoundException {
        ConfigurationYmlParser.parse("src/main/resources/local_conf/config.yml");
    }

    @AfterClass
    public static void cleanData() throws ExecutionException, InterruptedException {
    }

    @Test
    public void tableExists_executeReq_ValidContent_BooleanReturned(){
        ImpalaAccess.executeReq("create table if not exists test (a int)");
        assertTrue(ImpalaAccess.tableExists("default", "test"));
        ImpalaAccess.executeReq("drop table test");
    }
}
