package com.RE.Engine;


public class Test {
    public static void main(String[] argv){
        @SuppressWarnings("resource")
        Re re = new Re("ab");
        System.out.println(re.contains("abc"));
    }


}
