package Salesman;

import java.util.HashMap;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import Task.Task;

import com.example.meniu.Constants;
import com.google.android.gms.maps.model.LatLng;

public class ComputationalMethods {
	
	static HashMap<Integer,Integer> durations;
	static HashMap<Integer,Integer> prioritiesValues;
	
	
	public ComputationalMethods() {
		
		durations = new HashMap<Integer,Integer>();
		prioritiesValues = new HashMap<Integer,Integer>();
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
	
	/**
	 * 
	 * @param lat1 : the latitude of the first location
	 * @param long1 : the longitude of the first location
	 * @param lat2 : the latitude of the second location
	 * @param long2 : the longitude of the second location
	 * @return : the duration between 2 locations
	 */
	public static int calculateDurationTravel(double lat1, double long1, double lat2, double long2)
	{
		int distance = calculateDistanceTravel(lat1, long1, lat2, long2);
		
		
		double durationTravel = distance * 1.5  /60;
		
		
		return (int) durationTravel;
		
	}


	/**
	 * saving the duration in a hashmap so that obtaining the context won't be necessary any longer
	 */
	public static void initDurations() {
		
		
		for(Task task : PopulationEvolution.shiftingTasks)
		{
			DurationContext duration = (DurationContext)
			task.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
			
			
			
			if(duration.getDuration() != Constants.noTimeSpecified )
				durations.put( PopulationEvolution.shiftingTasks.indexOf(task) , duration.getDuration());
		
		
		}

	}


	public static void initPrioritiesValues() {
		
	/*	prioritiesValues.put(0, 250);
		prioritiesValues.put(1, 300);
		prioritiesValues.put(2, 350);
		prioritiesValues.put(3, 400);
		prioritiesValues.put(4, 450);
	*/	
		
	/*	prioritiesValues.put(0, 30);
		prioritiesValues.put(1, 60);
		prioritiesValues.put(2, 180);
		prioritiesValues.put(3, 240);
		prioritiesValues.put(4, 90);
	*/
		
	/*	prioritiesValues.put(0, 64);
		prioritiesValues.put(1,  128);
		prioritiesValues.put(2, 256);
		prioritiesValues.put(3, 512);
		prioritiesValues.put(4, 90);
	*/	
		prioritiesValues.put(0, 50);
		prioritiesValues.put(1,  150);
		prioritiesValues.put(2, 600);
		prioritiesValues.put(3, 3000);
		prioritiesValues.put(4, 90);
	
		
		
		
	}


	
	
}
