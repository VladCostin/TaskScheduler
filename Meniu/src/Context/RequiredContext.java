package Context;

import java.util.GregorianCalendar;

/**
 * contains the external conditions in which the task must be executed
 * @author ${Vlad Herescu}
 *
 */
public class RequiredContext extends Context {

	
	/**
	 * specifies the last day and time the task can be executed
	 */
	GregorianCalendar deadline;
	
	/**
	 * 
	 */
	public RequiredContext() {
	
		deadline = null;
		
	}

}
