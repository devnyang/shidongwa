package com.stone.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

	public static String removeBlankString(String input){
		String output = input;
		
		if(input != null){
			output = output.replaceAll("&#xD;", "");
			output = output.replaceAll("\\n", "");			
			output = output.replaceAll("\\r", "");	
			output = output.trim();
		}

		return output;
	}
	
	public static void main(String[] args){
		Map<String, Integer> map = new HashMap<String,Integer>();
		System.out.println(map.get("empty") == null);
	}
}
