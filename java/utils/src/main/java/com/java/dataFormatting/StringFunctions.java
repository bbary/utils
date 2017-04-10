package com.java.dataFormatting;

import org.apache.commons.lang.StringUtils;

/**
 * @author: brahim
 * date: 10/04/2017
 *
 * Some howToUSe java functions to show how java is great to manage text formatting and parsing
 *
 */
public class StringFunctions {
    public static void main(String[] args){
        regEx();
    }

    /**
     * how to use Regular expression
     * How to use StringUtils in commons lang (library of java utilities)
     */
    public static void regEx(){
        String input1 = "AB,CD,EF";
        String input2 = "AB";
        String input3 = "";
        String input4 = "AB,";
        String input5 = "ABC";

        String regEx = "[A-z]{2}(,[A-z]{2})*";

        System.out.println("Regular expression: "+regEx);
        System.out.println(String.format("%s%s matches regular  expression %s", input1, StringUtils.repeat(" ", 20-input1.length()), input1.matches(regEx)));
        System.out.println(String.format("%s%s matches regular  expression %s", input2, StringUtils.repeat(" ", 20-input2.length()), input2.matches(regEx)));
        System.out.println(String.format("%s%s matches regular  expression %s", input3, StringUtils.repeat(" ", 20-input3.length()), input3.matches(regEx)));
        System.out.println(String.format("%s%s matches regular  expression %s", input4, StringUtils.repeat(" ", 20-input4.length()), input4.matches(regEx)));
        System.out.println(String.format("%s%s matches regular  expression %s", input5, StringUtils.repeat(" ", 20-input5.length()), input5.matches(regEx)));
    }
}
