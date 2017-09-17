package com.java;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author: brahim bahri
 * date: 04/04/17
 */
public class HelloWorld {

    public static void main(String[] args){
        System.out.println("Hello world §");
        String userName = System.getProperty("user.name");
        System.out.println(userName);

        Properties props = new Properties();
        props.put("p1", "hi");
        props.put("p2", "hello");
        Map<String, String> map = (Map)props;
        System.out.println(map);

        String date_deb="20170504";
        String date_fin="20170505";
        System.out.println(date_deb.compareTo(date_fin));

    }

}
