package com.example.meniu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import CheckCompatibility.Compatibility;
import CheckCompatibility.DeviceCompatibility;
import CheckCompatibility.LocationCompatibility;
import CheckCompatibility.PeopleCompatibility;
import CheckCompatibility.TemporalCompatibility;
import Comparators.PriorityComparator;
import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.DeviceContext;
import ContextElements.LocationContext;
import ContextElements.PeopleContext;
import ContextElements.TemporalContext;
import DatabaseOperation.AlterateTask;
import DeviceData.Device;
import Task.Context;
import Task.Task;
import Task.TaskState;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShowAllTasks extends Activity
	 implements GooglePlayServicesClient.ConnectionCallbacks,
	  GooglePlayServicesClient.OnConnectionFailedListener {
	
	
	
	/**
	 * used to add dynamically data about each task from the database
	 */	
	RelativeLayout layout;
	
	
	/**
	 * used to get the user's location
	 */
	LocationClient mLocationClient;
	
	
	/**
	 * devices taken from database
	 */
	List<Device> devices;
	
	/**
	 * the number of views belonging to the layout
	 */
	int numberOfView;
	
	
	/**
	 * the tasks from to-do list retrieved from database
	 */
	private List<Task> tasks;
	
	/**
	 * to each id from database it is associated the value of the erase buton
	 */
	private HashMap<Integer,Integer> idTasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_all_tasks);


		layout = (RelativeLayout) findViewById(R.id.RelativeLayoutShowAllTasks);
		idTasks = new HashMap<Integer,Integer>();
		mLocationClient = new LocationClient(this,this,this);
		devices = MainActivity.getDatabase().getAllDevices();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_all_tasks, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		showUpdate();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	

	/*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }
	
	/**
	 * called to show the tasks , at the beginning and after the erase of a task
	 */
	public void showUpdate()
	{
	
		 Location l =  mLocationClient.getLastLocation();    
	     LatLng position = new LatLng(l.getLatitude(), l.getLongitude());

	     
	     if(layout == null)
	    	 System.out.println("ESTE NULL");
	     
	     layout.removeAllViews();
	     numberOfView = 0;
	     
	     checkAllTasksCompatibility( );  
		
	}
	
	
	/**
	 * for each task i determine the current state
	 * and then i call methods that check the compatibility 
	 * with the task state
	 */
	private void checkAllTasksCompatibility() {
	
		ArrayList<TaskState> statesToShow = new ArrayList<TaskState>();
		statesToShow.add(TaskState.AMONG_TO_DO_LIST);
		
		tasks = MainActivity.getDatabase().getFilteredTasks(statesToShow);
		
		
		if(tasks.size() == 0)
		{
			TextView showMessageTask = new TextView(this);
			
			RelativeLayout.LayoutParams params_title = 
			           new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
			                                           RelativeLayout.LayoutParams.MATCH_PARENT);
			showMessageTask.setGravity(Gravity.CENTER); 
			showMessageTask.setText(Constants.noTaskMessage);
			showMessageTask.setTextColor(Color.BLUE);
			showMessageTask.setTextSize(20);
		    showMessageTask.setLayoutParams(params_title);
		    
		    
		    
			layout.addView(showMessageTask);
			
			return;
			
			
		}
		

		for(Task task : tasks)
		{
			
			task.getScheduledContext().getContextElementsCollection().
			putAll(task.getExternContext().getContextElementsCollection());
			
			task.getScheduledContext().getContextElementsCollection().
			putAll(task.getInternContext().getContextElementsCollection());

	
			addTaskToInterface(task) ;
		}	
		
	}
	
	
	/**
	 * adding views to screen
	 * @param task : the task which can be executed
	 */
	private void addTaskToInterface(Task task) 
	{
		  
	
		   TextView title,priority,  deadline,people, devices;
		   TextView titleValue, priorityValue ,  deadlineValue, peopleValue, devicesValue;
		   Button butonErase, butonModifyData;
		   
		   
		   View line;
		   RelativeLayout.LayoutParams params_title = 
           new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
		   RelativeLayout.LayoutParams params_title_value = 
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		   
		   RelativeLayout.LayoutParams params_priority = 
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		    RelativeLayout.LayoutParams params_priority_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    
		 		   
		 		   
		    RelativeLayout.LayoutParams params_deadline = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    RelativeLayout.LayoutParams params_deadline_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    
		    RelativeLayout.LayoutParams params_people = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    RelativeLayout.LayoutParams params_people_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    RelativeLayout.LayoutParams params_devices = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    RelativeLayout.LayoutParams params_devices_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    
		    RelativeLayout.LayoutParams params_line = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2);
		    
		    RelativeLayout.LayoutParams params_erase = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    
		    RelativeLayout.LayoutParams params_modify = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						 				            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    
				   
				   
			
			
			title  = new TextView(this);
			priority = new TextView(this);
			deadline = new TextView(this);
			people = new TextView(this);
			devices = new TextView(this);
			
			titleValue  =   new TextView(this);
			priorityValue = new TextView(this);
			deadlineValue = new TextView(this);
			peopleValue =   new TextView(this);
			devicesValue = new TextView(this);
			
			butonErase = new Button(this);
			butonModifyData	= new Button(this);
			line = new View(this);
			
			title.setText(R.string.taskTitle);
			title.setPadding(20, 30, 0, 0);
			title.setTextSize(20);
			title.setId(++ numberOfView);  // devine 1
		    params_title.addRule(RelativeLayout.BELOW, numberOfView - 1 );  // fata de 0
		    title.setLayoutParams(params_title);
		    
    
			
			titleValue.setText(task.getNameTask());
			titleValue.setPadding(40, 30, 0, 0);
			titleValue.setTextSize(20);
			titleValue.setId( ++ numberOfView);
		    params_title_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_title_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
		    titleValue.setLayoutParams(params_title_value);
			
			
			priority.setText(R.string.textPriority);
			priority.setPadding(20, 10, 0, 0);
			priority.setTextSize(20);	
			priority.setId( ++ numberOfView);
			params_priority.addRule(RelativeLayout.BELOW, numberOfView - 1);
			priority.setLayoutParams(params_priority);
			
			priorityValue.setText(task.getPriority());
			priorityValue.setPadding(30, 10, 0, 0);
			priorityValue.setTextSize(20);	
			priorityValue.setId( ++ numberOfView);
			params_priority_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_priority_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			priorityValue.setLayoutParams(params_priority_value);
			
			
			
			deadline.setText(R.string.buttonSetDeadline);
			deadline.setPadding(20, 10, 0, 0);
			deadline.setTextSize(20);
			deadline.setId( ++ numberOfView);
			params_deadline.addRule(RelativeLayout.BELOW, numberOfView - 1);
			deadline.setLayoutParams(params_deadline);
			
			
			
			DeadlineContext  deadlineTask = (DeadlineContext) 
			task.getExternContext().getContextElementsCollection().get(ContextElementType.DEADLINE_ELEMENT);
			
			
			deadlineValue.setText(deadlineTask.getDeadline());
			deadlineValue.setPadding(20, 10, 0, 0);
			deadlineValue.setTextSize(20);
			deadlineValue.setId( ++ numberOfView);
			params_deadline_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_deadline_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			deadlineValue.setLayoutParams(params_deadline_value);
			
			
			people.setText(R.string.buttonChoosePeople);
			people.setPadding(20, 10, 0, 0);
			people.setTextSize(20);
			people.setId( ++ numberOfView);
			params_people.addRule(RelativeLayout.BELOW, numberOfView - 1);
			people.setLayoutParams(params_people);
				
			
			PeopleContext  peopleTask = (PeopleContext) 
			task.getInternContext().getContextElementsCollection().get(ContextElementType.PEOPLE_ELEMENT);
			

			peopleValue.setText(peopleTask.getPeopleTaskString());
			peopleValue.setPadding(20, 10, 0, 0);
			peopleValue.setTextSize(20);
			peopleValue.setId( ++ numberOfView);
			params_people_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_people_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			peopleValue.setLayoutParams(params_people_value);
			
			
			devices.setText(R.string.buttonChooseDevices);
			devices.setPadding(20, 10, 0, 0);
			devices.setTextSize(20);
			devices.setId( ++ numberOfView);
			params_devices.addRule(RelativeLayout.BELOW, numberOfView - 1);
			devices.setLayoutParams(params_devices);
				
			
			DeviceContext  deviceTask = (DeviceContext) 
			task.getInternContext().getContextElementsCollection().get(ContextElementType.DEVICES_ELEMENT);
			

			devicesValue.setText(deviceTask.getDeviceTaskString());
			devicesValue.setPadding(20, 10, 0, 0);
			devicesValue.setTextSize(20);
			devicesValue.setId( ++ numberOfView);
			params_devices_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_devices_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			devicesValue.setLayoutParams(params_devices_value);
			
			
		

			
			butonErase.setText(R.string.ERASE);
			butonErase.setId( ++ numberOfView);
			butonErase.setOnClickListener(new AlterateTask(this));
			idTasks.put(numberOfView, task.getID());
			
			params_erase.addRule(RelativeLayout.BELOW, numberOfView - 1);
			butonErase.setLayoutParams(params_erase);
			
			
			butonModifyData.setText(R.string.Modify);
			butonModifyData.setId( ++ numberOfView);
			butonModifyData.setOnClickListener(new AlterateTask(this));
			idTasks.put(numberOfView, task.getID());
			
			params_modify.addRule(RelativeLayout.BELOW, numberOfView - 1);
			params_modify.setMargins(0, 10, 0, 0);
			butonModifyData.setLayoutParams(params_modify);
			
			
			
			line.setBackgroundColor(Color.BLUE);
			line.setId( ++ numberOfView);
		
			params_line.addRule(RelativeLayout.BELOW, numberOfView - 1);
			line.setLayoutParams(params_line);
			
			
			layout.addView(title);
			layout.addView(titleValue);
			layout.addView(priority);
			layout.addView(priorityValue);
			layout.addView(deadline);
			layout.addView(deadlineValue);
			layout.addView(people);
			layout.addView(peopleValue);
			layout.addView(devices);
			layout.addView(devicesValue); 
			layout.addView(butonErase);
			layout.addView(butonModifyData ); 
			layout.addView(line);
			
		
	}

	public HashMap<Integer,Integer> getIdTasks() {
		return idTasks;
	}

	public void setIdTasks(HashMap<Integer,Integer> idTasks) {
		this.idTasks = idTasks;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
