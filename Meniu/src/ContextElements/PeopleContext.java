package ContextElements;

import java.util.ArrayList;

import com.example.meniu.Constants;

/**
 * contains an array with the people needed to execute the task
 * @author ${Vlad Herescu}
 *
 */
public class PeopleContext extends ContextElement{

	/**
	 * contains the peope needed to execute the task
	 */
	private ArrayList<String> peopleTask;

	
	/**
	 * @param people_Task : the people names taken frm database
	 */
	public PeopleContext(ArrayList<String> people_Task)
	{
		peopleTask = people_Task;
		
	//	System.out.println("PEOPLETASK ESTE" + peopleTask);
	}

	public ArrayList<String> getPeopleTask() {
		return peopleTask;
	}

	public void setPeopleTask(ArrayList<String> peopleTask) {
		this.peopleTask = peopleTask;
	}

	public CharSequence getPeopleTaskString() {
		
		String peopleString="";
		int i;
		
		if(peopleTask.size() == 0)
			return Constants.noChoose;
		
		for(i = 0; i < peopleTask.size() -1 ; i++)
		{
			peopleString += peopleTask.get(i) + " ,";
		}
		peopleString += peopleTask.get(i);
		
		//System.out.println("peopleString este " + peopleString);
		
		return peopleString;
	}

	
}
