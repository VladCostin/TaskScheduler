package Clusters;

import Task.Task;

/**
 * contains the header used to cluster data and to detect a a task's property value
 * depending on other values 
 * @author ${Vlad Herescu}
 *
 */
public interface KMeans {

	/**
	 * receive data from Adding a new Task and getting the clusters
	 * from database
	 */
	public void receiveData();
	
	/**
	 * calculates the centroids based on the executed tasks data
	 */
	public void calculateKlusters();
	
	
	/**
	 * detect centroid by comparing the data introduced by the user with the data
	 * contained in database
	 */
	public void detectCentroid();
	
	
	/**
	 * chooses clusters that are far one from each other
	 */
	public void chooseCentroid();
	
	
	
	
	/**
	 * calculates the string distance between titles
	 * @param title1 : the first title used to calculate distance
	 * @param title2 : the second title used to calculate distance
	 * @return : the string distance between title1 and title2
	 */
	public int calcualteStringDistance(String title1, String title2);
	
	
	/**
	 * calculates the final distance taking into consideration from
	 * task name length, locations distance, time difference , etc
	 * @param t1 : the first task to calcualte distance
	 * @param t2 : the second task to calcualte distance
	 */
	public void calcualteDistance(Task t1, Task t2);
	
	
}
