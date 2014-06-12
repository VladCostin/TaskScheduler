package CheckCompatibility;

import java.util.ArrayList;

import com.example.meniu.LocationInterval;
import com.google.android.gms.maps.model.LatLng;

import Task.Task;
import ContextElements.ContextElement;
import ContextElements.TemporalContext;

/**
 * 
 * checks if a task's start time and end time is compatible
 * with the current context
 * @author ${Vlad Herescu}
 *
 */
public class TemporalCompatibility extends Compatibility{



	@Override
	public boolean check(ContextElement task, ContextElement current, Task taskDetails) {
		
		
		
		
		return true;
	}

	
	
	
}



