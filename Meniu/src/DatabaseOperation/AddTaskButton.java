package DatabaseOperation;

import com.example.meniu.AddTask;
import com.example.meniu.MainActivity;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * class which process a task and its properties in the database
 * @author ${Vlad Herescu}
 *
 */
public class AddTaskButton implements OnClickListener {
	
	/**
	 * instance of class which has the data introduces by the user
	 */
	AddTask task;
	
	
	/**
	 * @param task : 
	 */
	public AddTaskButton(AddTask task) {
		this.task = task;
	}
	

	@Override
	public void onClick(View v) {
		
		
		
		 String date= task.getDate().getText().toString();
		 String location = task.getLocation();
		 String priority = task.getPriority().getSelectedItem().toString();
		 String name = task.getTitletask().getText().toString();
		 
		 
		 Log.w("information",date + " " + location + " " + priority + " " + name );
		
		 MainActivity.getDatabase().addTask(name,priority,location,date);      
		 
	}

}
