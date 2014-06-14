package Salesman;

import java.util.ArrayList;

/**
 * represents an individual in the population
 * @author ${Vlad Herescu}
 *
 */
public class Individual {

	private ArrayList<Integer> orderTasks; 
	
	private int startTime;
	
	
	private float fitnessValue;
	
	
	Individual()
	{
		orderTasks = new ArrayList<Integer>();
	}


	public int getStartTime() {
		return startTime;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}



	public float getFitnessValue() {
		return fitnessValue;
	}


	public void setFitnessValue(float fitnessValue) {
		this.fitnessValue = fitnessValue;
	}


	public ArrayList<Integer> getOrderTasks() {
		return orderTasks;
	}


	public void setOrderTasks(ArrayList<Integer> orderTasks) {
		this.orderTasks = orderTasks;
	}
	
	
	
}
