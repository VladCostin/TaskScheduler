package Task;

import ContextElements.LocationContext;
import ContextElements.TemporalContext;



/**
 * the context in which the task can be executed : it represents a collection of
 * context Elements that define how,where,when the task can pe executed
 * 
 * @author ${Vlad Herescu}
 *
 */
public class Context {

	/**
	 * the location where the task can be executed
	 */
	LocationContext location;
	
	/**
	 * data refering to the duration and interval when the task can be executed
	 */
	TemporalContext time;
	
}
