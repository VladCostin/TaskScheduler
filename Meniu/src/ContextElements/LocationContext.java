package ContextElements;

import java.util.ArrayList;

/**
 * defines the location where the task must be executed
 * @author ${Vlad Herescu}
 *
 */
public class LocationContext extends ContextElement{
	
	/**
	 * list with the locations names where the task can be executed
	 */
	ArrayList<String> locations;
	
	/**
	 * the longitude of the locations
	 */
	ArrayList<Integer> longitude;
	
	/**
	 * the latitude of the locations
	 */
	ArrayList<Integer> latitude;

}
