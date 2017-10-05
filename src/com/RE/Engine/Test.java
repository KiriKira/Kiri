package com.RE.Engine;


public class Test {
    public static void main(String[] argv){
        @SuppressWarnings("resource")
        Re re = new Re("bc");
        System.out.println(re.search("abbbcd"));
    }


}
