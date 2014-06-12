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
	 * for each location there is associated a start time and an end time
	 * the current context will have a start time with every retrieved from the tasks fixed
	 * and the tasks will have the start time computed as 
	 * current location -> location task duration +
	 * duration +
	 * location task - > fixed task location duration
	 */
	ArrayList<LocationInterval> intervals;
	
	public TemporalContext()
	{
		intervals = new ArrayList<LocationInterval>();
	}

	public TemporalContext(ArrayList<LocationInterval> intervals) {
		this.intervals	= intervals;
	}
	
	
	
	
	
}
