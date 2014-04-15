package com.example.meniu;

import java.util.HashMap;

/**
 * contains general information such as what does minor,major mean
 * @author ${Vlad Herescu}
 *
 */
public class Core {

	/**
	 * to each priority string it is associated a value for comparing the priorities
	 */
	private static HashMap<String,Integer> prioritiesValues;
	
	Core(){
		
		prioritiesValues = new HashMap<String,Integer>();
		
	}
	
	/**
	 * initializing the core's objects
	 */
	public static void init()
	{
		prioritiesValues.put("Unknown", 0);
		prioritiesValues.put("Minor", 1);
		prioritiesValues.put("Average", 2);
		prioritiesValues.put("Major", 3);
		prioritiesValues.put("Critical", 4);
		
		
		  
	}

	public static HashMap<String,Integer> getPrioritiesValues() {
		return prioritiesValues;
	}

	public static void setPrioritiesValues(HashMap<String,Integer> prioritiesValues) {
		Core.prioritiesValues = prioritiesValues;
	}
	
	
}
