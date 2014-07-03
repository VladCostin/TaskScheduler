package com.example.meniu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Task.Task;
import Clusters.KMeansDistances;
import Clusters.KMeansTitle;
import ContextElements.ContextElementType;
import ContextElements.DeviceContext;
import ContextElements.PeopleContext;
import DeviceData.Device;

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
	
	
	/**
	 * contains the center after clustering
	 */
	private static ArrayList<Task> centersDuration;
	
	
	/**
	 * contains the center task after clustering tasks after their titles
	 */
	private static ArrayList<Task> centersTitles;
	
	
	/**
	 * class that clusters the tasks to determine the main titles
	 */
	private static KMeansTitle clusteringLocation; 
	
	Core(){
		
		prioritiesValues    = new HashMap<String,Integer>();
		durationMinutes     = new HashMap<String,Integer>();
		distancesCalculator = new KMeansDistances();
		days				= new HashMap<Integer, DaysOfWeek>();
		clusteringLocation = new KMeansTitle();
		centersDuration	   = new ArrayList<Task>();
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
		days.put(Calendar.SATURDAY, DaysOfWeek.SATURDAY);
		days.put(Calendar.SUNDAY, DaysOfWeek.SUNDAY);
	}

	/**
	 * shows the string in the interface
	 * uses the numbers for ordering the tasks
	 */
	public static void initPriorities()
	{
		prioritiesValues.put("Minor", 0);
		prioritiesValues.put("Average", 1);
		prioritiesValues.put("Major", 2);
		prioritiesValues.put("Critical", 3);
		prioritiesValues.put("Unknown", 4);
	}
	
	
	public static DaysOfWeek getDayOfWeek()
	{
		Calendar c = Calendar.getInstance();
		return days.get(c.get(Calendar.DAY_OF_WEEK));
	}
	
	public static String	getDayOfWeekString()
	{
		return getDayOfWeek().toString();
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
	
	
	public static int calculateDistanceTravel(double lat1, double long1, double lat2, double long2)
	{
		double l1 = Math.toRadians(lat1);
		double l2 = Math.toRadians(lat2);
		double g1 = Math.toRadians(long1);
		double g2 = Math.toRadians(long2);
		double distanta;
		double dist;
		
		
		 dist = Math.acos(Math.sin(l1) * Math.sin(l2) + Math.cos(l1) * Math.cos(l2) * Math.cos(g1 - g2));
		 if(dist < 0) {
		        dist = dist + Math.PI;
		 }
		 
		 
		 distanta = Math.round(dist * 6378100);
		 
		 return (int) distanta;
	}
	
	public static int calculateDurationTravel(double lat1, double long1, double lat2, double long2)
	{
		int distance = calculateDistanceTravel(lat1, long1, lat2, long2);
		
		
		double durationTravel = distance * 1.5  /60;
		
		
		return (int) durationTravel;
		
	}
	
	
	/**
	 * @return : the titles from the centers calculated
	 */
	public static ArrayList<String> titlesOfCenters()
	{
		ArrayList<String> titles = new ArrayList<String>();
		for(Task task : centersTitles) 
		{
			System.out.println("CENTROID" + task.getNameTask());
			titles.add(task.getNameTask());
		}

		return titles;

	}
	
	
	public static void calculateClusters()
	{
		clusteringLocation.calculateKlusters();
		Core.setCentersTitle(clusteringLocation.getFinalCenters()); 
	}
	
	
	public static void changeTasksDevices() {
		
		List<Device> devices = MainActivity.getDatabase().getAllDevices();
		
		
		for(Task centroid : centersTitles)
			changeTaskDevice(centroid, devices);
		
		for(Task centroid : centersDuration)
			changeTaskDevice(centroid,devices);
		
		
		
	}
	
	
	public static void changeTaskDevice(Task centroid, List<Device> devices)
	{
		DeviceContext deviceC = (DeviceContext) centroid.getInternContext().
		getContextElementsCollection().get(ContextElementType.DEVICES_ELEMENT);
				
		PeopleContext peopleC = (PeopleContext) centroid.getInternContext().
		getContextElementsCollection().get(ContextElementType.PEOPLE_ELEMENT);
				
				
		for(Device device : devices)
		{
			if(device.getOwnerDevice().equals(Constants.myDevice) &&
			peopleC.getPeopleTask().contains(device.getMacAddress())) 
			{
				deviceC.getDeviceTask().add(device.getMacAddress());
				peopleC.getPeopleTask().remove(device.getMacAddress());
			}
					
			if(device.getOwnerDevice().equals(Constants.myDevice)== false &&
			deviceC.getDeviceTask().contains(device.getMacAddress()))
			{
				peopleC.getPeopleTask().add(device.getMacAddress());
				deviceC.getDeviceTask().remove(device.getMacAddress());
			}
					
					
					
		}
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

	public static ArrayList<Task> getCentersDuration() {
		return centersDuration;
	}

	public static void setCentersDuration(ArrayList<Task> centersDurationRecv) {
		centersDuration = centersDurationRecv; 
	}

	public static ArrayList<Task> getCentersTitle() {
		return centersTitles;
	}

	public static void setCentersTitle(ArrayList<Task> centersTitle) {
		Core.centersTitles = centersTitle;
	}

	public static KMeansTitle getClusteringLocation() {
		return clusteringLocation;
	}

	public static void setClusteringLocation(KMeansTitle clusteringLocation) {
		Core.clusteringLocation = clusteringLocation;
	}


	
}
