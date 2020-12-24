package com.example.demo.utils;

public class DeveloperTestPrinter {
    private static int COUNTER;

    public static void print(String msg){
        System.out.println(String.format("Developer test #%d - %s",COUNTER++,msg));
    }
}
