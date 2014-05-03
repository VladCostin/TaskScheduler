package ContextElements;


/**
 * the types of context elements which define how, where, when the task has to be executed
 * @author ${Vlad Herescu}
 *
 */
public enum ContextElementType {

	/**
	 * defines the object location context
	 */
	LOCATION_CONTEXT_ELEMENT,

	/**
	 * defines the object temporal object
	 */
	TIME_CONTEXT_ELEMENT,
	
	
	/**
	 * defines the task's deadline
	 */
	DEADLINE_ELEMENT,
	
	
	/**
	 * people needed to execute the task
	 */
	PEOPLE_ELEMENT,
	
	/**
	 * devices needed to execute the task
	 */
	DEVICES_ELEMENT;
	
}
