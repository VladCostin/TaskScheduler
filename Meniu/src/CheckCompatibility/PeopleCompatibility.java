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
		
		System.out.println(nrRan + "Persoanele de care am nevoie sunt" + peopleNeeded);
		System.out.println(nrRan + "Persoanele pe care le am sunt" + peopleHave);
		
		
		
		if(peopleNeeded.get(0).compareTo(Constants.noChoose) == 0)
		{
			System.out.println(nrRan + "Nu am ales persoane, deci nu este nicio pretentie aici ");
			return true;
		}
		
		for(String personNeeded : peopleNeeded)
			if(peopleHave.contains(personNeeded) == false)
			{
				System.out.println(nrRan +  "Nu este gasita persoana " + personNeeded);
				return false;
			}
		
		System.out.println(nrRan + "Totul este ok privind persoanele");
		return true;
		
		
	}

}
