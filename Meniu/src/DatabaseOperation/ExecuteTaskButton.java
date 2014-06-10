package DatabaseOperation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.meniu.Constants;
import com.example.meniu.Core;
import com.example.meniu.MainActivity;
import com.example.meniu.ShowTasks;
import com.google.android.gms.internal.s;

import Task.TaskState;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExecuteTaskButton implements OnClickListener {

	/**
	 * activity where the possible tasks are shown
	 */
	ShowTasks executableTasksActivity;
	
	public ExecuteTaskButton(ShowTasks activityTasks)
	{
		executableTasksActivity = activityTasks;
	}
	
	@Override
	public void onClick(View v) 
	{
		
		 if( checkCurrentTaskExist() == true )
			 return;
		 
		 
		 setTaskToBeExecuted(v);
		
		

	}
	
	
	public void setTaskToBeExecuted(View v){
		ArrayList<String> attributes = new ArrayList<String>();
		ArrayList<String> values	 = new ArrayList<String>();
		Integer idTask;
		TextView duration;
		String time[];
		String timeMinutes;
		
		
		
		
		duration = (TextView)  executableTasksActivity.getLayout().getChildAt(v.getId() -2 );
		time = duration.getText().toString().split(":");	  
		
		System.out.println("ORA -" +  time[0] + "-  -" + " MINUTE" +  time[1] + "-");
		
		timeMinutes =  Integer.toString( Integer.parseInt( time[0]) * 60 + Integer.parseInt(time[1]) );
		
		attributes.add(Tasks.KEY_Status);
		attributes.add(Tasks.KEY_Begin_Hour);
		attributes.add(Tasks.KEY_Duration);
		
		
		values.add( TaskState.CURRENT_TASK.toString() );
		values.add( Core.currentTimeParseToString()); 
		values.add(timeMinutes);
		
		
		System.out.println("ora de executie este  " +   Core.currentTimeParseToString());
		
		System.out.println("1.DATA de executie " + values.get(1));
		
		
		idTask = executableTasksActivity.getIdTasks().get(v.getId());	
		
		
		System.out.println("ID_UL ESTE" + v.getId());
		

		System.out.println( "NOILE VALORI " + attributes +  "  " + values);
		
		
		MainActivity.getDatabase().updateTask(idTask,attributes, values);
		
		executableTasksActivity.showUpdate();
	}

	/**
	 * @return : true if there is a task already running, false if it doesn't
	 */
	public boolean checkCurrentTaskExist() {
		
		ArrayList<TaskState> statesToShow = new ArrayList<TaskState>();
		statesToShow.add(TaskState.CURRENT_TASK);
		
		if(statesToShow.size() != 0 )
		{
			RelativeLayout.LayoutParams params_message = 
			new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
					                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			
			TextView showMessageTask = new TextView(executableTasksActivity);
			
			
			showMessageTask.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL); 
			showMessageTask.setText(Constants.curretTaskExists);
			showMessageTask.setTextColor(Color.BLUE);
			showMessageTask.setTextSize(20);
		    showMessageTask.setLayoutParams(params_message);
		    
		    
			
			executableTasksActivity.getLayout().addView(showMessageTask); 
			return true;
		}

		return false;
		
	}

}
