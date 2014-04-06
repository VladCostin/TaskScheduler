package com.example.meniu;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;

public class ShowTasks extends Activity {
	
	
	RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_tasks);
		
		
		layout = (RelativeLayout)  this.findViewById(R.id.showTasks);
		
		
		List<TaskToDataBase> contacts = MainActivity.database.getAllContacts();   
		 
		 
		 Log.w("Dimensiune: ",  Integer.toString(contacts.size()));
         
	     for (TaskToDataBase cn : contacts) {
	            String log = "Id: "+cn.getId()+" ,Name: " + cn.getLocation() + " ,Phone: " + cn.getCalendar();
	                // Writing Contacts to log
	            
	            Log.w("Name: ", log);
	        } 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_tasks, menu);
		return true;
	}

}
