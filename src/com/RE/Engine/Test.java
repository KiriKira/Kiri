package com.RE.Engine;


public class Test {
    public static void main(String[] argv){
        @SuppressWarnings("resource")
        Re re1 = new Re("12((a|b)(c|d))+");
        System.out.println(re1.match("12acadbd"));
        System.out.println(re1.match("12aa"));
        Re re2 = new Re("efg");
        System.out.println(re2.search("abcdefghikl"));
        Re re3 = new Re("{D}x{D}"," D :huster");
        System.out.println(re3.contains("https://husterxhuster.ml"));
        Re re4 = new Re("a.b");
        System.out.println(re4.match("a b"));
    }


}
