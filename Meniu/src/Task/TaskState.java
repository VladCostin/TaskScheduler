package Task;

/**
 * mentions whether the Task has been executed, is the current Task, or is in the TO DO List
 * @author ${Vlad Herescu}
 *
 */
public enum TaskState {

	/**
	 * the task was executed
	 */
	EXECUTED,
	
	/**
	 * the tas is currently executed
	 */
	CURRENT_TASK,
	
	/**
	 * the task is not executed, but it is among the 
	 */
	AMONG_TO_DO_LIST;
	
}
