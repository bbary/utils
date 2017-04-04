package com.java.exceptions;

/**
 * @author: brahim bahri
 * date: 04/04/2017
 */
public class Main {

    public static void main(String[] args){
        System.out.println("calling f");
        try {
            f();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void f() throws Exception {
        System.out.println("executing f");
        throw new Exception();
    }
}
