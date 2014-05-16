package Task;



/**
 * 
 * 
 * @author ${Vlad Herescu}
 *
 */
public class Task {
	
	/**
	 * the task's id in the database
	 */
	private int ID;
	
	/**
	 * the priority's value for the task
	 */
	private String priority;
	
	
	/**
	 * name of the task that must be executed
	 */
	private String nameTask;
	
	
	/**
	 * the distance from the current position to the location if this task
	 */
	private Integer distance;

	
	/**
	 * the conditions the user needede to execute the task
	 */
	private Context internalContext;
	
	/**
	 * the conditions the environment offers to execute the task
	 */
	private Context externalContext;
	
	/**
	 * the context containing the internal details and the external details as well
	 */
	private Context scheduledContext;
	
	
	/**
	 * the state of the task, in what condition is the task
	 */
	private TaskState state;
	
	
	/**
	 * when has the task been chosen to be executed 
	 */
	private String	 startTime;
	
	
	public Task(){
		
		internalContext = new Context();
		externalContext = new Context();
		scheduledContext = new Context();
		
		
	}

	

	public Context getInternContext() {
		return internalContext;
	}

	public void setInternContext(Context internContext) {
		this.internalContext = internContext;
	}

	public Context getExternContext() {
		return externalContext;
	}

	public void setExternContext(Context externContext) {
		this.externalContext = externContext;
	}

	public Context getScheduledContext() {
		return scheduledContext;
	}

	public void setScheduledContext(Context scheduledContext) {
		this.scheduledContext = scheduledContext;
	}



	public String getNameTask() {
		return nameTask;
	}



	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}



	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}



	public Integer getDistance() {
		return distance;
	}



	public void setDistance(float distance) {
		this.distance = (int) distance;
	}



	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
	}



	public TaskState getState() {
		return state;
	}



	public void setState(TaskState state) {
		this.state = state;
	}



	public String getStartTime() {
		return startTime;
	}



	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
}