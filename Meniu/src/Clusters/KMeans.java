package Clusters;

import java.util.ArrayList;

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
	 * detect center by comparing the data introduced by the user with the data
	 * contained in database
	 * @param data : the data used to determine the most closest cluster
 	 * @return : the most similar task with the data specified by user
	 */
	public Task detectCentroid( ArrayList<String> data  );
	
	
	/**
	 * chooses clusters that are far one from each other

	 */
	public void chooseCentroid( );
	
	
	/**
	 * calculates the new centers based on the abs of the points
	 */
	public void calculateNewCentroizi();
	
	
	/**
	 * checks whether the centers have changed their values
	 * @return : if the centers have changed their values
	 */
	public boolean checkCentroiziNotChanged();
	
	
	/**
	 * @return : the sum of sum of distances between all points from a cluster
	 */
	public float calculateError();
	
	
	/**
	 * calculates the final distance taking into consideration from
	 * task name length, locations distance, time difference , etc
	 * @param t1 : the first task to calcualte distance
	 * @param t2 : the second task to calcualte distance
	 * @return the distance between the 2 tasks
 	 */
	public int calculateDistance(Task t1, Task t2);
	
	
}
