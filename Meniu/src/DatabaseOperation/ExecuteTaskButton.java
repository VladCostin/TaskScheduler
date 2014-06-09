package DatabaseOperation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.meniu.Constants;
import com.example.meniu.Core;
import com.example.meniu.MainActivity;
import com.example.meniu.ShowTasks;

import Task.TaskState;
import android.view.View;
import android.view.View.OnClickListener;

public class ExecuteTaskButton implements OnClickListener {

	ShowTasks executableTasksActivity;
	
	public ExecuteTaskButton(ShowTasks activityTasks)
	{
		executableTasksActivity = activityTasks;
	}
	
	@Override
	public void onClick(View v) 
	{
		
		ArrayList<String> attributes = new ArrayList<String>();
		ArrayList<String> values	 = new ArrayList<String>();
		Integer idTask;
		
		attributes.add(Tasks.KEY_Status);
		attributes.add(Tasks.KEY_Begin_Hour);
		
		
		values.add( TaskState.CURRENT_TASK.toString() );
		values.add( Core.currentTimeParseToString()); 
		
		
		System.out.println("ora de executie este  " +   Core.currentTimeParseToString());
		
		System.out.println("1.DATA de executie " + values.get(1));
		
		
		idTask = executableTasksActivity.getIdTasks().get(v.getId());	

		System.out.println( "NOILE VALORI " + attributes +  "  " + values);
		
		
		MainActivity.getDatabase().updateTask(idTask,attributes, values);
		
		executableTasksActivity.showUpdate();

	}

}
