package com.example.meniu;



import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * class which process a task and its properties in the database
 * @author ${Vlad Herescu}
 *
 */
public class ButtonTouched implements OnClickListener {
	
	/**
	 * instance of class which has the data introduces by the user
	 */
	AddTask task;
	
	
	/**
	 * @param task : 
	 */
	public ButtonTouched(AddTask task) {
		this.task = task;
	}
	

	@Override
	public void onClick(View v) {
		
		
		Log.w("pipi", "caca");
		
		 String date= task.date.getText().toString();
		 String location = task.getLocation().getText().toString();
	    
		
		  MainActivity.database.addContact(new TaskToDataBase(location, date));        
		
		
	//	Log.w(task.getPriority().getSelectedItem().toString(),"cucu" );
	//	Log.w("deadline", Integer.toString(task.getDeadLineSet().getMonth()));
	}

}
