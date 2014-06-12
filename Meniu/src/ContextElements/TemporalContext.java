package ContextElements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.meniu.LocationInterval;

/**
 * defines the time when the context can be executed
 * @author ${Vlad Herescu}
 *
 */
public class TemporalContext extends ContextElement{

	/**
	 * the hour when the task would start
	 */
	private int startHour;
	
	/**
	 * the minute when the task would start
	 */
	private int startMinute;
	
	/**
	 * the hour when the task would stop
	 */
	private int endHour;
	
	/**
	 * the minute when the task would stop
	 */
	private int endMinute;
	
	
	/**
	 * each fixed task has a location, a start time, end time
	 *
	 */
	private ArrayList<LocationInterval> intervals;
	
	public TemporalContext()
	{
		intervals = new ArrayList<LocationInterval>();
	}

	public TemporalContext(ArrayList<LocationInterval> intervals) {
		this.intervals	= intervals;
	}

	public ArrayList<LocationInterval> getIntervals() {
		return intervals;
	}

	public void setIntervals(ArrayList<LocationInterval> intervals) {
		this.intervals = intervals;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	
	
	
	
	
}
