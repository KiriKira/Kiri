package com.test.demo;
import java.util.regex.*;

public class retest {
    public static void main(String args[]){
        String content = "I am noob" ;

        String pattern = ".*\\s.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob.' 子字符串? " + isMatch);
    }
}
