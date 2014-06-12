package com.example.meniu;

import com.google.android.gms.maps.model.LatLng;

public class LocationInterval
{
	/**
	 * the location where the task will be executed
	 */
	private LatLng position;
	/**
	 * when it must begin (fixed task) or when it will begin ( not fixed task) - the hour
	 */
	private Integer startHour;
	
	
	/**
	 * when it must begin (fixed task) or when it will begin ( not fixed task) - the minute
	 */
	private Integer startMinute;
	
	
	/**
	 * when it must finish (fixed task) or when it will finish( not fixed task) - the hour
	 */
	private Integer endHour;
	
	/**
	 *  when it must finish (fixed task) or when it will finish( not fixed task) - the minute
	 */
	private Integer endMinute;
	
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
	public Integer getStartHour() {
		return startHour;
	}
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}
	public Integer getStartMinute() {
		return startMinute;
	}
	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}
	public Integer getEndHour() {
		return endHour;
	}
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}
	public Integer getEndMinute() {
		return endMinute;
	}
	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}
	public LatLng getPosition() {
		return position;
	}
	public void setPosition(LatLng position) {
		this.position = position;
	}
	
	
	
}