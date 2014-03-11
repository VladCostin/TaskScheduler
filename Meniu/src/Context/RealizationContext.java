package Context;
import java.util.ArrayList;


/**
 * a set of parameters which describes the conditions the user needs to execute the task
 * @author ${Vlad Herescu}
 *
 */
public class RealizationContext extends Context{
	
	
	/**
	 * list of devices needed : computer, android, tablet
	 */
	private ArrayList<String> devices;
	
	/**
	 * conditions detected by sensors : if it has wireless, if
	 * it is noisy, 
	 */
	private ArrayList<String> environmentConditions;
	
	/**
	 * if i have to execute the task with a person
	 */
	private ArrayList<String> people;
	
	/**
	 * if the task can be executed in several steps
	 */
	private int granularity;
	
	/**
	 * initializing the members
	 */
	public RealizationContext(){
		
		location = new Location();
		devices = new ArrayList<String>();
		environmentConditions = new ArrayList<String>();
		granularity = 1;
		
	}

	/**
	 * @return : the devices needed to execute the task
	 */
	public ArrayList<String> getDevices() {
		return devices;
	}

	/**
	 * @param devices : the devices needed to execute the task
	 */
	public void setDevices(ArrayList<String> devices) {
		this.devices = devices;
	}

	/**
	 * @return : the conditions of the environment needed to execute the task
	 */
	public ArrayList<String> getEnvironmentConditions() {
		return environmentConditions;
	}

	/**
	 * @param environmentConditions : the conditions of the environment
	 */
	public void setEnvironmentConditions(ArrayList<String> environmentConditions) {
		this.environmentConditions = environmentConditions;
	}

	public int getGranularity() {
		return granularity;
	}

	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}

	public ArrayList<String> getPeople() {
		return people;
	}

	public void setPeople(ArrayList<String> people) {
		this.people = people;
	}
	
	
	
}
