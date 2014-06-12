package com.example.meniu;

import java.util.ArrayList;

import Task.Task;

import com.google.android.gms.maps.model.LatLng;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import ContextElements.TemporalContext;
import DatabaseOperation.FixedTaskInformation;
import DeviceData.Device;

/**
 * contains the methods to create the current context 
 * @author ${Vlad Herescu}
 *
 */
public class CalculateCurrentContext {
	
	public static ShowTasks activityShowTask;
	
	public CalculateCurrentContext() {
		// TODO Auto-generated constructor stub
	}
	
	public CalculateCurrentContext(ShowTasks activShowTasks )
	{
		this.activityShowTask = activShowTasks;
	}
	

	
	 /**
     * comparing the people detected with the ones from database using the MAC
     * addresses associated to the people's devices
     * @return : the people around the user detected
     */
    public static ArrayList<String> detectPeople() {
		
    	ArrayList<String> people = new ArrayList<String>();
    	
    	for(Device d : activityShowTask.devices)
    		if(activityShowTask.getDeviceInfo().containsKey(d.getMacAddress()) 
    			&& d.getOwnerDevice().compareTo(Constants.myDevice) != 0  )
    				people.add(d.getOwnerDevice());
    	
    	
    	System.out.println("Persoanele cunoscute sunt" + people);
		return people;
	}
    
    
    /** comparing the devices detected with the ones from database to see which ones
	 * belong to me
	 * @return : the user's devices detected
	 */
	public static ArrayList<String> detectMyDevices() {
		
		ArrayList<String> devicesDetected = new ArrayList<String>();
		
    	for(Device d : activityShowTask.devices)
    		if(activityShowTask.getDeviceInfo().containsKey(d.getMacAddress()) && 
    			d.getOwnerDevice().compareTo(Constants.myDevice) == 0  )
    				devicesDetected.add(d.getNameDevice());
    	
    	
    	System.out.println("Dispozitivele mele sunt" + devicesDetected);
		return devicesDetected;
		
	}

	/**
	 * @return : the list of intervals set by the user for each fixed task
	 */
	public static ArrayList<LocationInterval> obtainIntervals() {
		
		ArrayList<LocationInterval> intervals = new ArrayList<LocationInterval>();
		String location[];
		LatLng locationCoor;
		
		for(FixedTaskInformation task : activityShowTask.fixedTasks)
		{
			location = task.getLocation().split(",");
			locationCoor = new LatLng(Double.parseDouble( location[0]), (Double.parseDouble( location[1])));
			
			intervals.add(new LocationInterval(locationCoor, task.getStartHour(), task.getStartMinute(),
						  task.getEndHour(), task.getEndMinute()));
		}
		
		return intervals;
	}
	
	
	/**
	 * calculates the duration for a task, if it has not been specified by the user
	 * @param task : the task for which the duration is calculated
	 */
	public static void prepareDurationTask(Task task){
		
		DurationContext duration = (DurationContext)
		task.getScheduledContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		LocationContext location = (LocationContext)
		task.getScheduledContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		double l1 = Math.toRadians(location.getLatitude());
		double l2 = Math.toRadians(activityShowTask.getCurrentPosition().latitude);
		double g1 = Math.toRadians(location.getLongitude());
		double g2 = Math.toRadians(activityShowTask.getCurrentPosition().longitude);
		double durationTravel, distanta;
		double dist;
		
				
		task.setStartTime(Core.currentTimeParseToString());
				
		if(duration.getDuration() == Constants.noTimeSpecified)
		{
					
					
			Task centerTask = activityShowTask.durationAlg.detectCentroid(task);
			DurationContext durationCenterTask = (DurationContext)
			centerTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
					
					
			if(durationCenterTask == null)
				System.out.println("DURATION CENTER TASK ESTE NULL");
					
			duration.setDuration( durationCenterTask.getDuration() );
					
			

		}
		
		

		 dist = Math.acos(Math.sin(l1) * Math.sin(l2) + Math.cos(l1) * Math.cos(l2) * Math.cos(g1 - g2));
		 if(dist < 0) {
		        dist = dist + Math.PI;
		 }
		 
		 
		 distanta = Math.round(dist * 6378100);
		 durationTravel = distanta * 1.5  /60;
		 
		 
		 
		 duration.setTemporalDurationTravel( (int) durationTravel  ) ;
		 
		 

		
		
		
	}

	/**
	 * @param task : the optional task taken into consideration, to calculate the
	 *  possible start time of each fixed task
	 * 
	 */
	public void prepareIntervals(Task task) {
		
		
		TemporalContext contextInterval;
		DurationContext contextDuration; 
		ArrayList<LocationContext> intervals = new ArrayList<LocationContext>();
		int i, nrFixedTasks;
		int startHour, startMinute, endHour, endMinute, minutes;
		String currentTime[];
		
		contextInterval = (TemporalContext) task.getScheduledContext().
		getContextElementsCollection().get(ContextElementType.TIME_CONTEXT_ELEMENT);
		
		contextDuration = (DurationContext) task.getScheduledContext().
		getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		
		nrFixedTasks = activityShowTask.fixedTasks.size();
		
		currentTime =  Core.currentTimeParseToString().split("/");
		
		startHour = Integer.parseInt(currentTime[3]);
		startMinute = Integer.parseInt(currentTime[4]);
		
		minutes = startHour * 60 + startMinute;
		
		minutes += contextDuration.getTemporalDurationTravel() + contextDuration.getDuration();
		
		
		endHour = minutes / 60;
		endMinute = minutes % 60;
		
		//System.out.println("TIMPII SUNT " + endHour + " " + endMinute + " " + startHour + " " + startMinute + " " + contextDuration.getTemporalDurationTravel() + " " + contextDuration.getDuration());
		System.out.println("End HOUR " + endHour);
		System.out.println("Minute" + minutes );
		System.out.println("End Minute " + endMinute);
		System.out.println("Start Hour " + startHour);
		System.out.println("Start Minute " + startMinute);
		System.out.println("Durata travel " +  contextDuration.getTemporalDurationTravel() );
		System.out.println("Durata task " + contextDuration.getDuration());
		
		
		
		contextInterval.setStartHour(startHour);
		contextInterval.setStartMinute(startMinute);
		contextInterval.setEndHour(endHour);
		contextInterval.setEndMinute(endMinute);
		

	}
	
}
