package CheckCompatibility;

import Task.Task;
import ContextElements.ContextElement;


/**
 * contains the header of mthod which checks wheter the task can be executed
 * @author ${Vlad Herescu}
 *
 */
public interface Checker {

	/**
	 * @param task : the task's current time and location
	 * @param current : the current time and location
	 * @param taskDetails : object which contains details of the task such as the priority, duration
	 * @return : if the task is suitable to be executed
	 */
	public boolean check(ContextElement task, ContextElement current , Task taskDetails );
	
}
