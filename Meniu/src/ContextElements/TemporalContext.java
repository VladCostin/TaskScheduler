package ContextElements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * defines the time when the context can be executed
 * @author ${Vlad Herescu}
 *
 */
public class TemporalContext extends ContextElement{

//	ArrayList<Integer> startTime;
//	ArrayList<Integer> endTime;
	
	Date startTime;
	Date endTime;
	
	public TemporalContext()
	{
		startTime = new Date();
		endTime = new Date();
		
	}
	
	public TemporalContext(String date1, String date2)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		try {
			startTime = sdf.parse(date1);
			endTime = sdf.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	
	
}
