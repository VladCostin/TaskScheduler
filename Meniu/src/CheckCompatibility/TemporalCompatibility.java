package CheckCompatibility;

import java.util.ArrayList;

import com.example.meniu.Core;
import com.example.meniu.LocationInterval;
import com.google.android.gms.maps.model.LatLng;

import Task.Task;
import ContextElements.ContextElement;
import ContextElements.ContextElementType;
import ContextElements.LocationContext;
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
		
		TemporalContext taskTimeParameters = (TemporalContext) task;
		TemporalContext currentParameters  = (TemporalContext) current;
		
		LocationContext location = (LocationContext)  taskDetails.getInternContext().
		getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		LatLng taskLocation, currentLocation;
		
		int minutes_StartTask, minutes_EndTask,  minutes_StartCurrent, minutes_EndCurrent,  minutesTravel;
		
		
		taskLocation = location.getPositionLatLng();
	//	System.out.println(" A INTRAT AICI, in temporal context");
		
		
		minutes_EndTask = taskTimeParameters.getEndHour() * 60 + taskTimeParameters.getEndMinute();
		minutes_StartTask = taskTimeParameters.getStartHour() * 60 + taskTimeParameters.getStartMinute();
		
		
		System.out.println("####################################################");
		System.out.println("numele este " + taskDetails.getNameTask());
		System.out.println("##################################################");
		
		System.out.println("incepe la : " + minutes_StartTask);
		System.out.println("se termina la : " + minutes_EndTask);
		
		for(LocationInterval interval : currentParameters.getIntervals()  )
		{
			minutes_StartCurrent = interval.getStartHour() * 60 + interval.getStartMinute();
			minutes_EndCurrent   = interval.getEndHour() * 60 + interval.getEndMinute();
			
			if(minutes_EndCurrent < minutes_StartTask){
				System.out.println(" A INTRAT AICI, in comparaita asta");
				continue;
			}
			
			minutesTravel = Core.calculateDurationTravel(location.getLatitude(),
			location.getLongitude(), interval.getPosition().latitude, interval.getPosition().longitude);
			
			
			minutes_EndTask += minutesTravel;
			
			System.out.println("MINUTELE DE TRAVEL sunt " + minutesTravel + "  " + interval.getStartHour() + " " + interval.getStartMinute() + " " + interval.getEndHour() + " " + interval.getEndMinute());
			System.out.println("SE TERMINA LA : " + minutes_EndTask);
			System.out.println("INCEPE LA : " + minutes_StartCurrent);
			
			if(minutes_StartCurrent < minutes_EndTask){
				System.out.println(" Taskul se termina dupa ce incepe celalalt");
				return false;
			}
			
		}
		
		
		
		return true;
	}

	
	
	
}



