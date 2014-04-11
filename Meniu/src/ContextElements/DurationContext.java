package ContextElements;

/**
 * the duration estimated by the intelligent agent based on the location and hour
 * 
 * @author ${Vlad Herescu}
 *
 */
public class DurationContext extends ContextElement{
	
	
	/**
	 * the task's duration is calculatated in minutes
	 */
	private int minutes;

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	
	
}
