package CheckCompatibility;

import Task.Task;
import android.location.Location;
import ContextElements.ContextElement;
import ContextElements.LocationContext;

/**
 * checks if a task'a location is compatible with the current context, based 
 * on the wireless sensors, GPS, name
 * @author ${Vlad Herescu}
 *
 */
public class LocationCompatibility extends Compatibility  {
	


	@Override
	public boolean check(ContextElement task, ContextElement current, Task taskdetails) {
		
		float results[] = new float[3];
		
		LocationContext currentContext = (LocationContext) current;
		LocationContext taskContext = (LocationContext) task;
		
		
		System.out.println("CACAT3 " + currentContext.getLatitude() + " " + currentContext.getLongitude());
		System.out.println("CACAT3 " + taskContext.getLatitude() + " " + taskContext.getLongitude());
		
		Location.distanceBetween(currentContext.getLatitude(), currentContext.getLongitude(),
				                 taskContext.getLatitude(), taskContext.getLongitude(), results);
		
		System.out.println("DIMENSIUNEA ESTE : " + results.length);
		System.out.println("DISTANTA IN METRI ESTE : " + results[1]);
		
		taskdetails.setDistance(results[0]);
		
		
		return true;
	}
	
	
	
	

}
