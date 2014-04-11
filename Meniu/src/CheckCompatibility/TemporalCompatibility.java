package CheckCompatibility;

import Task.Task;
import ContextElements.ContextElement;
import ContextElements.TemporalContext;

/**
 * 
 * checks if a task's start time and end time ic comaptible 
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
