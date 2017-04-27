package com.java.logging;
import org.apache.log4j.Logger;

import java.util.NoSuchElementException;

/**
 * @author: brahim bahri
 * date: 04/04/17.
 */
public class Main {
    final static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args){
        logger.info("info message");
        logger.error("error message");
        try {
            f();
        }
        catch(NoSuchElementException e){
           logger.error("exception test", e);

        }
    }

    public static void f() throws NoSuchElementException{
        throw new NoSuchElementException();

    }
}
