package com.test.demo;

import java.util.StringTokenizer;

public class stringtokenizer{
	public static void main(String[] argv){
		String s = new String("TEST,test");
		StringTokenizer st = new StringTokenizer(s,",",true);
		while( st.hasMoreElements() )
			System.out.println( st.nextToken() );
	}
}