package Clusters;

import java.util.ArrayList;

import ContextElements.ContextElementType;
import ContextElements.DeviceContext;
import ContextElements.LocationContext;
import ContextElements.PeopleContext;
import Task.Task;
import android.location.Location;

/**
 * contains the methods used for calculating different distances
 * @author ${Vlad Herescu}
 *
 */
public class KMeansDistances {
	
	/**
	 *  the maximum distance between two words to say if they are similar
	 */
	static float distanceStringError;
	
	
	/**
	 * the maximum distance between two titles to know if they refer to the same thing
	 */
	static float distanceTitleError;
	
	
	/**
	 *  the maximum distance between two locations that is not taken into consideration
	 */
	static int distanceLocationError;
	
	
	/**
	 * initialises the members
	 */
	public KMeansDistances() {
		
		
		distanceStringError = (float) 0.5;
		distanceTitleError  = (float) 0.25;
		
		distanceLocationError = 300;
	}

	
	
	/**
	 * @param nameTask : the title of the first Task
	 * @param nameTask2 : the titles of the second Task
	 * @return the Distance between the two titles
	 */
	public static int calculateDistanceTitles(String nameTask, String nameTask2) {
		
		ArrayList<Integer> wordsTakenIntoConsideration = new ArrayList<Integer>();
		int minim;
		int position = 0;
		int valueDistanceWords;
		int i;
		int distanceTotal = 0;
		float maxLength = Math.max(nameTask.length(), nameTask2.length());
		String wordsString1[];
		String wordsString2[]; 
		
		if(nameTask.split(" ").length < nameTask2.split(" ").length)
		{
			wordsString1  = nameTask.split(" ");
			wordsString2 = nameTask2.split(" ");
			
		}
		else
		{
			wordsString1  = nameTask2.split(" ");
			wordsString2 = nameTask.split(" ");
		}
		
		
		for(String word : wordsString1)
		{
			minim = 1000000;
			
			for(i = 0; i < wordsString2.length ; i++)
				if(wordsTakenIntoConsideration.contains(i) == false)
				{
					  valueDistanceWords = calcualteStringDistance(word, wordsString2[i]);
					
					  if( valueDistanceWords < minim)
					  {
						  minim = valueDistanceWords;
						  position = i;
						  
					  }
				}
			
			
			distanceTotal +=minim;
			wordsTakenIntoConsideration.add(position);
			
			
		}
		
		
		for(i= 0; i < wordsString2.length; i++)
		{
			if(wordsTakenIntoConsideration.contains(i) == false)
				distanceTotal += wordsString2[i].length();
		}
		
		if( (float)  distanceTotal / maxLength  < distanceTitleError   )
			return 0;
		
		
		return distanceTotal;
	}
	
	
	/**
	 * @param a : a word from the first title
	 * @param b : a word from the second title
	 * @return : the distance between them
	 */
	public static int calcualteStringDistance(String a, String b){
		
		
		int maxLength = Math.max(a.length(), b.length());
		float error;
		
	 	a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        
        error = (float)  costs[b.length() ] / (float) maxLength;
        
        if( error > distanceStringError )
        	return costs[b.length()];
        
        return 0;
		
		
	}
	
	
	/**
	 * @param startTime : the start time of the first task
	 * @param startTime2 : the start time of the second task
	 * @return : the distance represented by the number of minutes
	 */
	public static int calculateDistanceStartTime(String startTime, String startTime2) {
		
		int minutes1, hours1, minutes2, hours2;
		
		String startTimeMinutesAndAhours1[] = startTime.split("/");
		String startTimeMinutesAndAhours2[] = startTime2.split("/");
		
		minutes1 = Integer.parseInt(startTimeMinutesAndAhours1[4]);
		hours1   = Integer.parseInt(startTimeMinutesAndAhours1[3]);
		
		
		minutes2 = Integer.parseInt(startTimeMinutesAndAhours2[4]);
		hours2   = Integer.parseInt(startTimeMinutesAndAhours2[3]);
		
		
		
		
//		System.out.println(hours1 + " " + minutes1 + " "  + hours2 + " " + minutes2);
		return Math.abs((hours1 * 60 +  minutes1) - (hours2 * 60 +  minutes2) );
	}
	
	
	/**
	 * @param t1 : one of the task whose location is taken into consideration for distance	
	 * @param t2 : the second task whose location is taken into consideration for distance
	 * @return : the distance between the locations of the two tasks
	 */
	public static int calculateDistanceLocation(Task t1, Task t2) {
		
		
		float results[] = new float[3];
		
		LocationContext location1 =(LocationContext) 
		t1.getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		LocationContext location2 =(LocationContext) 
		t2.getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		 double l1 = Math.toRadians(location1.getLatitude());
		 double l2 = Math.toRadians(location2.getLatitude());
		 double g1 = Math.toRadians(location1.getLongitude());
		 double g2 = Math.toRadians(location2.getLongitude());

		 double dist = Math.acos(Math.sin(l1) * Math.sin(l2) + Math.cos(l1) * Math.cos(l2) * Math.cos(g1 - g2));
		 if(dist < 0) {
		        dist = dist + Math.PI;
		 }
		 
		 
	//	 System.out.println("Distanta este " +  Math.round(dist * 6378100));
		 
		    return (int)  Math.round(dist * 6378100);
		

		
		
	/*	Location.distanceBetween(location1.getLatitude(), location1.getLongitude(),
				                 location2.getLatitude(), location2.getLongitude(), results);
		
		
		System.out.println();
		
		System.out.println("DISTANTA CALCULATA ESTE " + location1.getLatitude() + " " + location1.getLongitude() + " " + location2.getLatitude() + " " + location2.getLongitude());
		
		for(int i = 0 ; i < results.length; i++)
			System.out.print( results[i] + " " );
		
		System.out.println();
		
		
		if( results[0] < distanceLocationError )
			return 0;
		
		return  (int) results[0]; */
	}
	
	
	/**
	 * @param t1 : one of the task whose people needed are taken into consideration to calculate the distance
	 * @param t2 : one of the task whose people needed are taken into consideration to calculate the distance
	 * @return : the distance between the lists of people
	 */
	public static int calculateDistancePeople(Task t1, Task t2)
	{
		PeopleContext peopleTask1 = (PeopleContext)
		t1.getInternContext().getContextElementsCollection().get(ContextElementType.PEOPLE_ELEMENT);
		PeopleContext peopleTask2 = (PeopleContext)
		t2.getInternContext().getContextElementsCollection().get(ContextElementType.PEOPLE_ELEMENT);
		
		
		
		
		
		return 0;
	}
	
	
	/**
	 * @param t1 : one of the task whose devices needed are taken into consideration to calculate the distance
	 * @param t2 : one of the task whose devices needed are taken into consideration to calculate the distance
	 * @return : the distance between the lists of devices
	 */
	public static int calculateDistanceDevices(Task t1, Task t2)
	{
		DeviceContext deviceTask1 = (DeviceContext)
		t1.getInternContext().getContextElementsCollection().get(ContextElementType.DEVICES_ELEMENT);
		DeviceContext deviceTask2 = (DeviceContext)
		t2.getInternContext().getContextElementsCollection().get(ContextElementType.DEVICES_ELEMENT);
		
		
		return 0;
	}
	
	
}
