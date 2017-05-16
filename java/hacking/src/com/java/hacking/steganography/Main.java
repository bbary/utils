package com.java.hacking.steganography;

public class Main {

    public static void main(String[] args) {
        LSBEncDec lsb = new LSBEncDec();
        String hiddenMessage =  lsb.decode("/Users/brahim/tmp/logo.png");
        if(hiddenMessage == null){
            System.out.println("No hidden text found in the image");
            System.exit(0);
        }
        System.out.println(String.format("Hidden message: %s", hiddenMessage));
    }
}
