package DatabaseOperation;

import java.util.List;

import com.example.meniu.AddTask;
import com.example.meniu.MainActivity;
import com.example.meniu.R;
import com.example.meniu.ShowAllTasks;
import com.example.meniu.ShowTasks;

import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.DeviceContext;
import ContextElements.LocationContext;
import ContextElements.PeopleContext;
import Task.Task;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * erases the task associated with the buton pushed 
 * @author ${Vlad Herescu}
 *
 */
public class AlterateTask implements OnClickListener  { 
	
	
	 public final static String ID_MESSAGE	  		= "IdTask"; 
	 public final static String TITLE_MESSAGE 		= "TitleName";
	 public final static String PEOPLE_MESSAGE		= "PeopleName";
	 public final static String DEVICE_MESSAGE  	= "DeviceName";
	 public final static String LOCATION_MESSAGE 	= "LocationTask";
	 public final static String DEADLINE_MESSAGE    = "DeadlineTask";
	
	
	/**
	 * the activity containing the id of the tasks
	 */
	private ShowAllTasks show;

	public AlterateTask(ShowAllTasks showAllTasks)
	{
		show = showAllTasks;
			
	}
	
	@Override
	public void onClick(View arg0) {
		
		Button buton = (Button) arg0;
		
		System.out.println(buton.getText().toString());
		
		
		if(buton.getText().toString().equals(show.getResources().getString(R.string.ERASE)))
			deleteTask(arg0);
		if(buton.getText().toString().equals(show.getResources().getString(R.string.Modify)))
			modifyTask(arg0);
		
		
		show.showUpdate();
	}
	
	
	/**
	 * start the add task activity
	 * @param arg0 : the modify button associated to the task specified to be deleted
	 */
	public void modifyTask(View arg0) {
		
		System.out.println("Trebuie sa modifice un task");
		Integer idTask = show.getIdTasks().get(arg0.getId());
		
		List<Task> tasks = show.getTasks();
		Task taskToModify= null;
		String nameTask;
		Intent intentModify;
		LocationContext location;
		DeadlineContext deadline;
		PeopleContext people;
		DeviceContext devices;
		
		
		String peopleArray[], devicesArray[];
		double locationArray[];
		
		
		
		
		for(Task task : tasks)
		{
			if(task.getID()  == idTask )
			{
				taskToModify = task;
				break;
			}
		}
		
		 location = (LocationContext) taskToModify.getInternContext().
					getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		 deadline =  (DeadlineContext) taskToModify.getExternContext().
					getContextElementsCollection().get(ContextElementType.DEADLINE_ELEMENT);
		 people	  = (PeopleContext) taskToModify.getInternContext().
					getContextElementsCollection().get(ContextElementType.PEOPLE_ELEMENT);
		 
		 devices  = (DeviceContext) taskToModify.getInternContext().
					getContextElementsCollection().get(ContextElementType.DEVICES_ELEMENT);
		 
		 peopleArray = new String[people.getPeopleTask().size()];
		 people.getPeopleTask().toArray(peopleArray);
		 
		 devicesArray = new String[devices.getDeviceTask().size()];
		 devices.getDeviceTask().toArray(devicesArray);
		 
		 
		 
		 
		 locationArray = new double[2];
		 locationArray[0] = location.getLatitude();
		 locationArray[1] = location.getLongitude();
		 

		intentModify = new Intent(show, AddTask.class);
		intentModify.putExtra(ID_MESSAGE, idTask);
		intentModify.putExtra(TITLE_MESSAGE, taskToModify.getNameTask());
		intentModify.putExtra(PEOPLE_MESSAGE, peopleArray);
		intentModify.putExtra(DEVICE_MESSAGE, devicesArray);
		intentModify.putExtra(DEADLINE_MESSAGE, deadline.getDeadline());
		intentModify.putExtra(LOCATION_MESSAGE, locationArray);
		
		
		System.out.println("DEADLINE_UL ESTE " +  deadline.getDeadline());
		
		 show.startActivity(intentModify);
		
		
		
		
		
	}

	/**
	 * deletes a task
	 * @param arg0 : the delete button associated to the task specified to be deleted
	 */
	public void deleteTask(View arg0)
	{
		Integer idTask = show.getIdTasks().get(arg0.getId());
		MainActivity.getDatabase().deleteTask(idTask);
		
	}



}
