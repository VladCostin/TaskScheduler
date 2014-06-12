package com.example.meniu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import Clusters.KMeansDistances;

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
	
	
	private static KMeansDistances distancesCalculator; 
	
	
	private static HashMap<Integer, DaysOfWeek> days;
	
	Core(){
		
		prioritiesValues    = new HashMap<String,Integer>();
		durationMinutes     = new HashMap<String,Integer>();
		distancesCalculator = new KMeansDistances();
		days				= new HashMap<Integer, DaysOfWeek>();
	}
	
	/**
	 * initializing the core's objects
	 */
	public static void init()
	{
		
		initPriorities();
		initDuration();
		initDays();
		
		  
	}
	
	/**
	 * for each day of the week there is a number associated to it
	 */
	public static void initDays() {
		
		days.put(Calendar.MONDAY, DaysOfWeek.MONDAY);
		days.put(Calendar.TUESDAY, DaysOfWeek.TUESDAY);
		days.put(Calendar.WEDNESDAY, DaysOfWeek.WEDNESDAY);
		days.put(Calendar.THURSDAY, DaysOfWeek.THURSDAY);
		days.put(Calendar.FRIDAY, DaysOfWeek.FRIDAY);
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
	
	/**
	 * @return : the current time in the format defined in Constants
	 */
	public static String currentTimeParseToString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.parseTime);
		return sdf.format(new Date()); 
	}
	
	public static Date timeParseToDate(String StartTime)
	{
		SimpleDateFormat format = new SimpleDateFormat(Constants.parseTime);
		try {
			return format.parse(StartTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
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

	public static HashMap<Integer, DaysOfWeek> getDays() {
		return days;
	}

	public static void setDays(HashMap<Integer, DaysOfWeek> days) {
		Core.days = days;
	}
	
	
}
