package Clusters;

import java.util.ArrayList;

import ContextElements.ContextElementType;
import ContextElements.LocationContext;
import Task.Task;
import android.location.Location;

/**
 * contains the methods used for calculating different distances
 * @author ${Vlad Herescu}
 *
 */
public class KMeansDistances {
	
	/**
	 *  the maximum distance between two words that is not taken into consideration
	 */
	static int distanceStringError;
	
	
	/**
	 *  the maximum distance between two locations that is not taken into consideration
	 */
	static int distanceLocationError;
	
	
	/**
	 * initialises the members
	 */
	public KMeansDistances() {
		
		
		distanceStringError = 5;
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
		
		return distanceTotal;
	}
	
	
	/**
	 * @param a : a word from the first title
	 * @param b : a word from the second title
	 * @return : the distance between them
	 */
	public static int calcualteStringDistance(String a, String b){
		
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
        
        if( costs[b.length() ] < distanceStringError )
        	return 0;
        
        return costs[b.length()];
		
		
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
		

		
		
		Location.distanceBetween(location1.getLatitude(), location1.getLongitude(),
				                 location2.getLatitude(), location2.getLongitude(), results);
		
		
		if( results[0] < distanceLocationError )
			return 0;
		
		return  (int) results[0];
	}
	
}
