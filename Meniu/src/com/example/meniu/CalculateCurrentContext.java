package com.example.meniu;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

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
	public ArrayList<String> detectMyDevices() {
		
		ArrayList<String> devicesDetected = new ArrayList<String>();
		
    	for(Device d : activityShowTask.devices)
    		if(activityShowTask.getDeviceInfo().containsKey(d.getMacAddress()) && 
    			d.getOwnerDevice().compareTo(Constants.myDevice) == 0  )
    				devicesDetected.add(d.getNameDevice());
    	
    	
    	System.out.println("Dispozitivele mele sunt" + devicesDetected);
		return devicesDetected;
		
	}

	public ArrayList<LocationInterval> obtainIntervals() {
		
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
	
}
