package CheckCompatibility;

import java.util.ArrayList;
import java.util.Random;

import com.example.meniu.Constants;

import ContextElements.ContextElement;
import ContextElements.PeopleContext;
import Task.Task;

/**
 * checks the compatibility regarding the people needed to execute the task
 * @author ${Vlad Herescu}
 *
 */
public class PeopleCompatibility extends Compatibility{

	@Override
	public boolean check(ContextElement task, ContextElement current,
			Task taskDetails) {
		
		Random r = new Random();
		int nrRan = r.nextInt();
		
		PeopleContext currentContext = (PeopleContext) current;
		PeopleContext taskContext = (PeopleContext) task;
		
		ArrayList<String> peopleNeeded = taskContext.getPeopleTask();
		ArrayList<String> peopleHave   = currentContext.getPeopleTask();
	
		if(peopleNeeded.size() == 0)
			return true;
		
		for(String personNeeded : peopleNeeded)
			if(peopleHave.contains(personNeeded) == false)
				return false;

		return true;
		
		
	}

}
