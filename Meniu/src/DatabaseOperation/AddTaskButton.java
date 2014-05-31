package DatabaseOperation;

import com.example.meniu.AddTask;
import com.example.meniu.Core;
import com.example.meniu.MainActivity;
import com.example.meniu.R;

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
	 * the message to be shown after trying to insert data
	 */
	String messageToShow;
	
	
	/**
	 * @param task : 
	 */
	public AddTaskButton(AddTask task) {
		this.task = task;
	}
	

	@Override
	public void onClick(View v) {
		
		 if(checkDatas() == false ){
			 
			 task.getMessageInsert().setText( messageToShow );
			 task.getMessageInsert().setFocusableInTouchMode(true);
			 task.getMessageInsert().requestFocus();
			 return;
		 }
		 
	
		 task.getMessageInsert().setText( messageToShow );
		 task.getMessageInsert().setFocusableInTouchMode(true);
		 task.getMessageInsert().requestFocus(); 
		
		
		 String date= task.getDate().getText().toString();
		 String location = task.getLocation();
		 String priority = task.getPriority().getSelectedItem().toString();
		 String name = task.getTitleTask().getText().toString();
		 String people = task.getPeople().getText().toString();
		 String device = task.getDevices().getText().toString();
		 String duration = task.getDuration().getSelectedItem().toString();
		 
		 
		 
		 
		 Log.w("information",date + " " + location + " " + priority + " " + name + " " + people + " " + device);
		
		MainActivity.getDatabase().addTask
		(name,priority,location,date, people, device, 
		Core.getDurationMinutes().get(duration).toString(), "");      

	}


	/**
	 * @return : true if the data have been inserted
	 */
	public boolean checkDatas() {
		
		if(task.getLocation() == null ||
		   task.getTitleTask().getText().toString().compareTo("") == 0 )
		{
			messageToShow = task.getResources().getString(R.string.Error) ;
			
			if(task.getLocation() == null)
				messageToShow += "  LOCATION ";

			if(task.getTitleTask().getText().toString().compareTo("") == 0 )
				messageToShow += " TITLE ";
			
			return false;
			
			
		}

	
		messageToShow = task.getResources().getString(R.string.Succes);
		
		return true;
	}

}
