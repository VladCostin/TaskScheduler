package Context;

/**
 * general context inherited by the intern, required,scheduled contexts
 * 
 * @author ${Vlad Herescu}
 *
 */
abstract public class Context {

	/**
	 * the time the user can start to execute the task;
	 */
	int startTime;
	
	
	/**
	 * the time the user must stop to execute the task;
	 */
	int endTime;
	
	
	
	/**
	 * location where i have to execute the task
	 */
	Location location;



	public int getStartTime() {
		return startTime;
	}



	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}



	public int getEndTime() {
		return endTime;
	}



	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}



	public Location getLocation() {
		return location;
	}



	public void setLocation(Location location) {
		this.location = location;
	}
	
}
