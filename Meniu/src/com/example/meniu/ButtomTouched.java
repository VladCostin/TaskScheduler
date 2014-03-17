package com.example.meniu;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * class which process a task and its properties in the database
 * @author ${Vlad Herescu}
 *
 */
public class ButtomTouched implements OnClickListener {
	
	/**
	 * instance of class which has the data introduces by the user
	 */
	AddTask task;
	
	
	/**
	 * @param task : 
	 */
	public ButtomTouched(AddTask task) {
		this.task = task;
	}
	

	@Override
	public void onClick(View v) {
		
		
		Log.w(task.getPriority().getSelectedItem().toString(),"cucu" );
		Log.w("deadline", Integer.toString(task.getDeadLineSet().getMonth()));
	}

}
