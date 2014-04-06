package Task;



/**
 * 
 * 
 * @author ${Vlad Herescu}
 *
 */
public class Task {
	
	/**
	 * the priority's value for the task
	 */
	private int priority;
	
	
	/**
	 * name of the task that must be executed
	 */
	private String nameTask;

	
	/**
	 * the conditions the user needede to execute the task
	 */
	private Context internalContext;
	
	/**
	 * the conditions the environment offers to execute the task
	 */
	private Context externalContext;
	
	/**
	 * 
	 */
	private Context scheduledContext;
	
	
	Task(){
		
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



	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}