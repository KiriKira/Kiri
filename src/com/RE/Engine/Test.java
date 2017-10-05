package com.RE.Engine;


public class Test {
    public static void main(String[] argv){
        @SuppressWarnings("resource")
        Re re = new Re(".a.+");
        System.out.println(re.match("ba"));
    }


}
