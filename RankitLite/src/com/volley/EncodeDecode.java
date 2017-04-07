package com.volley;

import java.io.UnsupportedEncodingException;

public class EncodeDecode {
	private static String result;
	
	public static String  DecodeString(String text) {

		try {
			result=new String(text.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	return result;
	}

}
