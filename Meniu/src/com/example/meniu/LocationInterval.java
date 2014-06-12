package com.example.meniu;

import com.google.android.gms.maps.model.LatLng;

public class LocationInterval
{
	/**
	 * the location where the task will be executed
	 */
	LatLng position;
	/**
	 * when it must begin (fixed task) or when it will begin ( not fixed task) - the hour
	 */
	Integer startHour;
	
	
	/**
	 * when it must begin (fixed task) or when it will begin ( not fixed task) - the minute
	 */
	Integer startMinute;
	
	
	/**
	 * when it must finish (fixed task) or when it will finish( not fixed task) - the hour
	 */
	Integer endHour;
	
	/**
	 *  when it must finish (fixed task) or when it will finish( not fixed task) - the minute
	 */
	Integer endMinute;
	
	/**
	 * initializing the object
	 */
	public LocationInterval() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param position
	 * @param startTime
	 * @param endTime
	 */
	public LocationInterval(LatLng position, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute)
	{
		this.position		= position;
		this.startHour  	= startHour;
		this.endHour		= endHour;
		
		this.startMinute	= startMinute; 
		this.endMinute		= endMinute;
	}
	
	
	
}