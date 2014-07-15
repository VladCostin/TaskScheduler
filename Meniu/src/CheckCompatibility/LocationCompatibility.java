package CheckCompatibility;

import com.example.meniu.Constants;
import com.example.meniu.Core;

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
		
		
		int distance;
		LocationContext currentContext = (LocationContext) current;
		LocationContext taskContext = (LocationContext) task;
		
	
		
		distance = Core.calculateDistanceTravel
		(currentContext.getLatitude(), currentContext.getLongitude(), taskContext.getLatitude(), taskContext.getLongitude());
		
		taskdetails.setDistance(distance);
		
		
		if(taskdetails.getDistance() > Constants.thresHoldDistance)
			return false;
		
		
		return true;
	}
	
	
	
	

}
