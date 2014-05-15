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
	
	private static HashMap<String,Integer> durationMinutes;
	
	Core(){
		
		prioritiesValues = new HashMap<String,Integer>();
		durationMinutes  = new HashMap<String,Integer>();
		
	}
	
	/**
	 * initializing the core's objects
	 */
	public static void init()
	{
		
		initPriorities();
		initDuration();
		
		  
	}
	
	/**
	 * shows the string in the interface
	 * uses the numbers for ordering the tasks
	 */
	public static void initPriorities()
	{
		prioritiesValues.put("Unknown", 0);
		prioritiesValues.put("Minor", 1);
		prioritiesValues.put("Average", 2);
		prioritiesValues.put("Major", 3);
		prioritiesValues.put("Critical", 4);
	}
	
	/**
	 * shows the format : hours, minutes in GUI
	 * uses how many minutes really are 
	 */
	public static void initDuration()
	{
		durationMinutes.put("Unknown", -1);
		durationMinutes.put("5 minutes", 5);
		durationMinutes.put("10 minutes", 10);
		durationMinutes.put("30 minutes", 30);
		durationMinutes.put("one hour", 60);
		durationMinutes.put("2 hours", 120);
		durationMinutes.put("4 hours", 240);
		durationMinutes.put("8 hours", 480);
		durationMinutes.put("16 hours", 960);
		durationMinutes.put("one day", 1440);
		durationMinutes.put("two days", 2880);
		durationMinutes.put("four days", 5760);
		
		
		
	}
	

	public static HashMap<String,Integer> getPrioritiesValues() {
		return prioritiesValues;
	}

	public static void setPrioritiesValues(HashMap<String,Integer> prioritiesValues) {
		Core.prioritiesValues = prioritiesValues;
	}

	public static HashMap<String,Integer> getDurationMinutes() {
		return durationMinutes;
	}

	public static void setDurationMinutes(HashMap<String,Integer> durationMinutes) {
		Core.durationMinutes = durationMinutes;
	}
	
	
}
