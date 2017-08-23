package com.java.dataFormatting;

/**
 * Created by brahim on 23/08/17.
 */
public class Conversions {

    public static void main(String[] args){
        String s = "123";

        Long l = Long.parseLong(s);
        Integer i = Integer.parseInt(s);
        System.out.println(l);
        System.out.println(i);
    }
}
