package Context;



/** location where the task shall be executed
 * @author ${Vlad Herescu}
 *
 */
public class Location {

	/**
	 * name of the location
	 */
	private String name;
	
	/**
	 * 
	 */
	Location(){
		
		name = "currentLocation";
		
	}

	/**
	 * @return : the name of the location
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name : sets a new name for the location
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
