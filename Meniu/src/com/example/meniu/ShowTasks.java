package com.example.meniu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;

import CheckCompatibility.Compatibility;
import CheckCompatibility.LocationCompatibility;
import CheckCompatibility.TemporalCompatibility;
import Comparators.PriorityComparator;
import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.DeviceContext;
import ContextElements.LocationContext;
import ContextElements.PeopleContext;
import ContextElements.TemporalContext;
import DatabaseOperation.EraseTask;
import Task.Context;
import Task.Task;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * shows the tasks the user has introduced
 * @author ${Vlad Herescu}
 *
 */
public class ShowTasks extends Activity
					   implements GooglePlayServicesClient.ConnectionCallbacks,
					   			  GooglePlayServicesClient.OnConnectionFailedListener{
	
	/**
	 * used to add dynamically data about each task from the database
	 */	
	RelativeLayout layout;
	
	
	/**
	 * used to get the user's location
	 */
	LocationClient mLocationClient;
	
	
	/**
	 * the checkers to check whether the task is comaptible
	 */
	HashMap<ContextElementType,Compatibility> checkers;
	
	/**
	 * the number of the view to add to the relative layout
	 */
	int numberOfView;
	
	/**
	 * to each id from database it is associated the value of the erase buton
	 */
	private HashMap<Integer,Integer> idTasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_tasks);
		


		layout = (RelativeLayout) findViewById(R.id.RelativeLayoutShow);
		
		checkers = new HashMap<ContextElementType,Compatibility>();
		idTasks = new HashMap<Integer,Integer>();
		
		checkers.put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationCompatibility());
		checkers.put(ContextElementType.TIME_CONTEXT_ELEMENT, new TemporalCompatibility());
		
		mLocationClient = new LocationClient(this,this,this);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_tasks, menu);
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
	
	/**
	 * called to show the tasks , at the beginning and after the erase of a task
	 */
	public void showUpdate()
	{
	
		 Location l =  mLocationClient.getLastLocation();    
	     LatLng position = new LatLng(l.getLatitude(), l.getLongitude());

	     layout.removeAllViews();
	     numberOfView = 0;
	     
	     checkAllTasksCompatibility( createCurrentState(position));  
		
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
    
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
    
    /**
     * create a context regarding the current state of the user
     * @param position : the position of the user
     * @return : the current context of the user
     */
    private Context createCurrentState(LatLng position) {
		
    	Context currentContext = new Context();
    	currentContext.getContextElementsCollection().
    	put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationContext(position));
    	currentContext.getContextElementsCollection().
    	put(ContextElementType.TIME_CONTEXT_ELEMENT, new TemporalContext() );
    	
    /*	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    	Date date = new Date();
    	System.out.println(sdf.format(calendar.getTime()));
    	
    	Calendar cal = new GregorianCalendar();*/
    	
    	return currentContext;
    	
		
	}
    
    /**
	 * for each task i determine the current state
	 * and then i call methods that check the compatibility 
	 * with the task state
     * @param currentContext : the current context of the user : the current time and place
	 */
	private void checkAllTasksCompatibility(Context currentContext) {
	
		List<Task> tasks = MainActivity.getDatabase().getAllTasks();
		
		
		Collections.sort(tasks, new PriorityComparator());
	
		
		boolean isTaskCompatible = true;
		for(Task task : tasks)
		{
			
			
			task.getScheduledContext().getContextElementsCollection().
			putAll(task.getExternContext().getContextElementsCollection());
			
			task.getScheduledContext().getContextElementsCollection().
			putAll(task.getInternContext().getContextElementsCollection());

			
			for(ContextElementType elementType: checkers.keySet())
			{
				
				Compatibility  checker = checkers.get(elementType);
				
				if( checker.check(task.getScheduledContext().getContextElementsCollection().get(elementType), 
					               currentContext.getContextElementsCollection().get(elementType), task) == false){
					
					isTaskCompatible = false;
					break;
				}
				
			}
			
			if(isTaskCompatible == true)	
				addTaskToInterface(task) ;
		}	
		
	}
	/**
	 * adding views to screen
	 * @param task : the task which can be executed
	 */
	private void addTaskToInterface(Task task) 
	{
		  
	
		   TextView title,priority, distance, deadline,people, devices;
		   TextView titleValue, priorityValue , distanceValue, deadlineValue, peopleValue, devicesValue;
		   Button butonErase;
		   
		   
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
		    
		    
		    RelativeLayout.LayoutParams params_distance = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    RelativeLayout.LayoutParams params_distance_value = 
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
		    
		    
				   
				   
			
			
			title  = new TextView(this);
			priority = new TextView(this);
			distance = new TextView(this);
			deadline = new TextView(this);
			people = new TextView(this);
			devices = new TextView(this);
			
			titleValue  =   new TextView(this);
			priorityValue = new TextView(this);
			distanceValue = new TextView(this);
			deadlineValue = new TextView(this);
			peopleValue =   new TextView(this);
			devicesValue = new TextView(this);
			
			butonErase = new Button(this);
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
			
			
			priority.setText(R.string.priority);
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
			
			
			distance.setText(R.string.Distance);
			distance.setPadding(20, 10, 0, 0);
			distance.setTextSize(20);	
			distance.setId( ++ numberOfView);
			params_distance.addRule(RelativeLayout.BELOW, numberOfView - 1);
			distance.setLayoutParams(params_distance);
			
			distanceValue.setText(Integer.toString(task.getDistance()));
			distanceValue.setPadding(20, 10, 0, 0);
			distanceValue.setTextSize(20);	
			distanceValue.setId( ++ numberOfView);
			params_distance_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_distance_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			distanceValue.setLayoutParams(params_distance_value);
			
			
			
			
			deadline.setText(R.string.setDeadline);
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
			
			
			people.setText(R.string.choosePeople);
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
			
			
			devices.setText(R.string.chooseDevices);
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
			butonErase.setOnClickListener(new EraseTask(this));
			idTasks.put(numberOfView, task.getID());
			
			params_erase.addRule(RelativeLayout.BELOW, numberOfView - 1);
			butonErase.setLayoutParams(params_erase);
			
			
			line.setBackgroundColor(Color.BLUE);
			line.setId( ++ numberOfView);
		
			params_line.addRule(RelativeLayout.BELOW, numberOfView - 1);
			line.setLayoutParams(params_line);
			
			
			layout.addView(title);
			layout.addView(titleValue);
			layout.addView(priority);
			layout.addView(priorityValue);
			layout.addView(distance);
			layout.addView(distanceValue);
			layout.addView(deadline);
			layout.addView(deadlineValue);
			layout.addView(people);
			layout.addView(peopleValue);
			layout.addView(devices);
			layout.addView(devicesValue); 
			layout.addView(butonErase);
			layout.addView(line);
			
		
	}



	public HashMap<Integer,Integer> getIdTasks() {
		return idTasks;
	}



	public void setIdTasks(HashMap<Integer,Integer> idTasks) {
		this.idTasks = idTasks;
	}

}
