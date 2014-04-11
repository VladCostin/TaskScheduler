package ContextElements;

/**
 * defines the deadline until which the task must be executed
 * @author ${Vlad Herescu}
 *
 */
public class DeadlineContext extends ContextElement{
	
	/**
	 * the task's deadline
	 */
	private String deadline;
	
	
	/**
	 * @param deadline : the deadline of the task set in the database
	 */
	public DeadlineContext(String deadline) {
		
		this.deadline = deadline; 
	}


	public String getDeadline() {
		return deadline;
	}


	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

}
