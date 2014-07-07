package com.example.meniu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

import CheckCompatibility.Compatibility;
import CheckCompatibility.DeviceCompatibility;
import CheckCompatibility.LocationCompatibility;
import CheckCompatibility.PeopleCompatibility;
import CheckCompatibility.TemporalCompatibility;
import Clusters.KMeansDuration;
import Comparators.PriorityComparator;
import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.DeviceContext;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import ContextElements.PeopleContext;
import ContextElements.TemporalContext;
import DatabaseOperation.ExecuteTaskButton;
import DatabaseOperation.FixedTaskInformation;
import DeviceData.Device;
import Task.Context;
import Task.Task;
import Task.TaskState;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;


/**
 * shows the tasks the user has introduced
 * @author ${Vlad Herescu}
 *
 */
public class ShowTasks extends Activity
					   implements GooglePlayServicesClient.ConnectionCallbacks,
					   			  GooglePlayServicesClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, OnClickListener{
	
	

	
	/**
	 * used to add dynamically data about each task from the database
	 */	
	private RelativeLayout layout;
	
	
	/**
	 * calculates the current conditions such as devices, people, time
	 */
	CalculateCurrentContext currentConditions;
	
	
	/**
	 * used to get the user's location
	 */
	LocationClient mLocationClient;
	
	
	
	 /**
	 * used to get updates about the user's location
	 */
	LocationRequest locationRequest;
	
	
	/**
	 * the checkers to check whether the task is comaptible
	 */
	HashMap<ContextElementType,Compatibility> checkers;
	
	
	/**
	 * contains the boolean values for the checkers which show which filters are activated
	 */
	HashMap<String,Boolean> hashMapCheckBox;
	
	
	/**
	 * at showing the tasks, determine the value of the pair which is the name of the fitler
	 * then determine the boolean value;
	 * if it is true, take it into consideration, else, do not take it into consideration
	 */
	HashMap<ContextElementType,String> hashMapCheckBoxNames_contextTypes;
	
	/**
	 * the number of the view to add to the relative layout
	 */
	int numberOfView;
	
	/**
	 * to each id from database it is associated the value of the erase buton
	 */
	private HashMap<Integer,Integer> idTasks;
	
	
	/**
	 * receives the data about the devices nearby
	 */
	private MyBroadCastRecvShow mReceiver;
	

	
	/**
	 * adaptorul
	 */
	BluetoothAdapter mBluetoothAdapter;
	
	
	/**
	 * contains for each mac adress a name for the device to be recognized
	 */
	private HashMap<String,String> deviceInfo;
	

	
	/**
	 * the current position detected by GPS
	 */
	private LatLng currentPosition;
	
	
	/**
	 * calculates the centers for the the clusters
	 * determines which center is most similar to the current task
	 */
	KMeansDuration durationAlg;
	
	
	/**
	 * obtain all the fixed tasks required for today.
	 */
	ArrayList<FixedTaskInformation> fixedTasks;
	
	
	/**
	 * array of pairs key -value, with the key = MAC address, and the value = name of the device
	 * contains all the devices belonging to the user
	 */
	private LinkedHashMap<String,String> MAP_myDevices_name_Mac;
	
	
	/**
	 * array of pairs key -value, with the key = MAC address, and the value = name of the people
	 * contains all the devices belonging to the user
	 */
	private LinkedHashMap<String,String> MAP_people_devices_name_Mac;
	
	
	/**
	 * contains the information regarding the current context
	 */
	Context currentContext;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_tasks);
		
		
		// daca las asta la urma in metoda onCreate, o sa dea eroare la broadcast receiver
		// pentru ca probabil nu a avut timp sa faca ceva
		durationAlg = new KMeansDuration();
	//	durationAlg.calculateKlusters();
		
		currentConditions = new CalculateCurrentContext(this);

		layout = (RelativeLayout) findViewById(R.id.RelativeLayoutShow);
		
		checkers = new HashMap<ContextElementType,Compatibility>();
		hashMapCheckBox = new HashMap<String, Boolean>();
		idTasks = new HashMap<Integer,Integer>();
		deviceInfo = new HashMap<String,String>();
		hashMapCheckBoxNames_contextTypes = new HashMap<ContextElementType, String>();
		
		checkers.put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationCompatibility());
		checkers.put(ContextElementType.TIME_CONTEXT_ELEMENT, new TemporalCompatibility());
		checkers.put(ContextElementType.DEVICES_ELEMENT, new DeviceCompatibility());
		checkers.put(ContextElementType.PEOPLE_ELEMENT, new PeopleCompatibility());
		
		
		hashMapCheckBox.put(getResources().getString(R.string.checkBoxLocation), true);
		hashMapCheckBox.put(getResources().getString(R.string.checkBoxDevices), true);
		hashMapCheckBox.put(getResources().getString(R.string.checkBoxPeople), true);
		hashMapCheckBox.put(getResources().getString(R.string.checkBoxSchedule), true);
		
		
		hashMapCheckBoxNames_contextTypes.put(ContextElementType.LOCATION_CONTEXT_ELEMENT, 
				getResources().getString(R.string.checkBoxLocation));
		
		hashMapCheckBoxNames_contextTypes.put(ContextElementType.DEVICES_ELEMENT, 
				getResources().getString(R.string.checkBoxDevices));
		
		hashMapCheckBoxNames_contextTypes.put(ContextElementType.TIME_CONTEXT_ELEMENT, 
				getResources().getString(R.string.checkBoxSchedule));
		
		hashMapCheckBoxNames_contextTypes.put(ContextElementType.PEOPLE_ELEMENT, 
				getResources().getString(R.string.checkBoxPeople));
		
		
		
		
		mLocationClient = new LocationClient(this,this,this);
		locationRequest = new LocationRequest();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(5000);
		locationRequest.setFastestInterval(1000);
		locationRequest.setNumUpdates(1);
		
		
		mReceiver = new MyBroadCastRecvShow(this);
		
		
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    System.out.println("mBluetooth is null");
		}
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, 11);
		  
		}
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		mBluetoothAdapter.startDiscovery();
		currentPosition = null;
		
		
		getFixedTasksForToday();
		loadDevices();
		
		
	}
	
	/**
	 * loads the devices contained in the databse
	 */
	public void loadDevices() {
		
		MAP_myDevices_name_Mac = new LinkedHashMap<String, String>();
		MAP_people_devices_name_Mac = new LinkedHashMap<String, String>();
		
		List<Device> devices = MainActivity.getDatabase().getAllDevices();
		

		for(Device device : devices)
			if(device.getOwnerDevice().equals(this.getResources().getString(R.string.myDeviceConstant)))
				MAP_myDevices_name_Mac.put(device.getMacAddress(), device.getNameDevice());
			else
				MAP_people_devices_name_Mac.put(device.getMacAddress(), device.getOwnerDevice());
		

	}



	/**
	 * determines the tasks required for today
	 */
	public void getFixedTasksForToday() {
		
		
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		String dayString = Core.getDays().get(day).toString();
		
		fixedTasks = MainActivity.getDatabase().getFixedTasks(dayString);
		
		System.out.println("ACUM TREBUIE SA EXECUT" + fixedTasks.size());
		
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
		
		
		
		LocationManager     manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);    
	    manager.requestLocationUpdates(
	         	    LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
	         	        @Override
	         	        public void onStatusChanged(String provider, int status, Bundle extras) {
	         	        }
	         	        @Override
	         	        public void onProviderEnabled(String provider) {
	         	        }
	         	        @Override
	         	        public void onProviderDisabled(String provider) {
	         	        }
	         	        @Override
	         	        public void onLocationChanged(final Location location) {
	         	        }
	    });
	    
	    
	    mLocationClient.requestLocationUpdates(locationRequest, this);
		
		
		
	}
	
	
	
	@Override
	public void onLocationChanged(Location arg0) {
		
		currentPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());
		Log.w("Pozitie actuala", arg0.getLatitude() + " " + arg0.getLongitude()); 
		
		showUpdate();
	}
	
	

	/**
	 * called to show the tasks , at the beginning and after the erase of a task
	 */
	public void showUpdate()
	{
		
		
		if(currentPosition == null)
		{
			 mLocationClient.requestLocationUpdates(locationRequest, this);
			 
		}
		else
		{
		     layout.removeAllViews();
		     numberOfView = 0;
		     checkAllTasksCompatibility( createCurrentState()); 
		}
		
 
		
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
    	super.onStop();
        mLocationClient.disconnect();
     
        
        
    }
    
    protected void onDestroy(){
    	
		super.onDestroy();
		mBluetoothAdapter.cancelDiscovery();
		unregisterReceiver(mReceiver);
		
		mReceiver = null;
		mBluetoothAdapter = null;
    }
    
    /**
     * create a context regarding the current state of the user
     * @return : the current context of the user
     */
    private Context createCurrentState() {
		
    	ArrayList<String> myDevices = currentConditions.detectMyDevices();
    	ArrayList<String> peopleAround = currentConditions.detectPeople();
    	ArrayList<LocationInterval> intervals = currentConditions.obtainIntervals();

    	Context currentContext = new Context();
    	currentContext.getContextElementsCollection().
    	put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationContext(currentPosition));
    	currentContext.getContextElementsCollection().
    	put(ContextElementType.TIME_CONTEXT_ELEMENT, new TemporalContext(intervals) );
    	
    	
    	
    	
    	currentContext.getContextElementsCollection().
    	put(ContextElementType.PEOPLE_ELEMENT, new PeopleContext(peopleAround));
    	currentContext.getContextElementsCollection().
    	put(ContextElementType.DEVICES_ELEMENT, new DeviceContext(myDevices));
    	

    	
    	this.currentContext = currentContext; 
    	return currentContext;
    	
		
	}
    


	/**
	 * for each task i determine the current state
	 * and then i call methods that check the compatibility 
	 * with the task state
     * @param currentContext : the current context of the user : the current time and place
	 */
	private void checkAllTasksCompatibility(Context currentContext) {
	
		ArrayList<TaskState> statesToShow = new ArrayList<TaskState>();
		statesToShow.add(TaskState.AMONG_TO_DO_LIST);
		boolean noCompatibleTask = true;
		boolean isTaskCompatible = true;
		List<Task> tasks = MainActivity.getDatabase().getFilteredTasks(statesToShow);
	//	System.out.println("AFISEAZA TASKURILE AICI");
	//	System.out.println( "Dimensiune taskuri " + tasks.size());
		
		
	//	Collections.sort(tasks, new PriorityComparator());

		
		System.out.println("INCEP SA AFISEZ TASKURILE!!!!!!!!!");
		addCheckersToInterface(); 
		for(Task task : tasks)
		{
			
			isTaskCompatible = true;
			
			System.out.println("INAINTE AVEA START TIME EGAL CU" + task.getStartTime());
			
			prepareTask(task, currentContext); 
			
			
			System.out.println("ACUM ARE START TIME EGAL CU" + task.getStartTime());
			

			
			for(ContextElementType elementType: checkers.keySet())
			{
				String nameFilter = hashMapCheckBoxNames_contextTypes.get(elementType);
				Boolean checkTyped = hashMapCheckBox.get(nameFilter);
								
				Compatibility  checker = checkers.get(elementType);
				
				if( checker.check(task.getScheduledContext().getContextElementsCollection().get(elementType), 
					               currentContext.getContextElementsCollection().get(elementType), task) == false){
					
					
					if(checkTyped == false)
						continue;
					
					isTaskCompatible = false;
				}
				
			}
			
			System.out.println( "Am verificat compatibilitatea pentru " + task.getNameTask() + " " + isTaskCompatible);
			
			if(isTaskCompatible == true){
				
			//	if(noCompatibleTask == true)
			//		addCheckersToInterface(); 
				
				noCompatibleTask = false;
				addTaskToInterface(task) ;
			}
		}
		
		if(noCompatibleTask == true)
		{
			TextView showMessageTask = new TextView(this);
			
			RelativeLayout.LayoutParams params_title = 
			           new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
			                                           RelativeLayout.LayoutParams.MATCH_PARENT);
			
			
			
			showMessageTask.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL); 
			showMessageTask.setText(Constants.noExecutableTaskMessage);
			showMessageTask.setTextColor(Color.BLUE);
			showMessageTask.setTextSize(25);
			
			params_title.setMargins(0, 100, 0, 0);
			params_title.addRule(RelativeLayout.BELOW, numberOfView);
		    showMessageTask.setLayoutParams(params_title);
		    
		    
			layout.addView(showMessageTask);
		}
		
		
	}
	
	
	
	/**
	 *  - adds the collections from ExternalContext and from InternalContext into
	 * one single collection
	 * - calcualtes the duration of the task
	 * - calculates the intervals of execution, taking into consideration
	 * the time to arrive to the location of a fixed task
	 * @param task : one of task from to do list
	 * @param currentContext: calculates the distance between the task's location and the currentLocation
	 */
	public void prepareTask(Task task, Context currentContext){
		
		
		task.getScheduledContext().getContextElementsCollection().
		putAll(task.getExternContext().getContextElementsCollection());
		
		task.getScheduledContext().getContextElementsCollection().
		putAll(task.getInternContext().getContextElementsCollection());
		
		
		currentConditions.prepareDurationTask(task);
		currentConditions.prepareIntervals(task);
//		currentConditions.prepareDistance(task, currentContext);

		
	}
	
	
	public void addCheckersToInterface()
	{
		 RelativeLayout.LayoutParams params_location_checkBox = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                         RelativeLayout.LayoutParams.WRAP_CONTENT);
		 
		 params_location_checkBox.setMargins(30, 20, 0, 0);
		 
		 
		 RelativeLayout.LayoutParams params_devices_checkBox = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                         RelativeLayout.LayoutParams.WRAP_CONTENT);
		 
		 params_devices_checkBox.setMargins(30, 20, 0, 0);
		 
		 
		 RelativeLayout.LayoutParams params_people_checkBox = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                         RelativeLayout.LayoutParams.WRAP_CONTENT);
		 
		 
		 params_people_checkBox.setMargins(30, 20, 0, 0);
		 
		 RelativeLayout.LayoutParams params_schedule_checkBox = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                         RelativeLayout.LayoutParams.WRAP_CONTENT);
		 
		 params_schedule_checkBox.setMargins(30, 20, 0, 0);
		 
		 
		 CheckBox checkboxLocation = new CheckBox(this);
		 checkboxLocation.setTextSize(20);
		 checkboxLocation.setOnClickListener(this);
		 checkboxLocation.setChecked(hashMapCheckBox.get(getResources().getString(R.string.checkBoxLocation)));
		 checkboxLocation.setText(getResources().getString(R.string.checkBoxLocation)); 
		 checkboxLocation.setId(++ numberOfView);
		 
		 params_location_checkBox.addRule(RelativeLayout.BELOW, numberOfView - 1);
		 checkboxLocation.setLayoutParams(params_location_checkBox);
		 
		 
		 CheckBox checkboxDevices = new CheckBox(this);
		 checkboxDevices.setTextSize(20);
		 checkboxDevices.setOnClickListener(this);
		 checkboxDevices.setChecked(hashMapCheckBox.get(getResources().getString(R.string.checkBoxDevices)));
		 checkboxDevices.setText(getResources().getString(R.string.checkBoxDevices)); 
		 checkboxDevices.setId(++ numberOfView);
		 
		 params_devices_checkBox.addRule(RelativeLayout.BELOW, numberOfView - 1);
		 checkboxDevices.setLayoutParams(params_devices_checkBox);
		 
		 
		 CheckBox checkboxPeople = new CheckBox(this);
		 checkboxPeople.setTextSize(20);
		 checkboxPeople.setOnClickListener(this);
		 checkboxPeople.setChecked(hashMapCheckBox.get(getResources().getString(R.string.checkBoxPeople)));
		 checkboxPeople.setText(getResources().getString(R.string.checkBoxPeople)); 
		 checkboxPeople.setId(++ numberOfView);
		 
		 params_people_checkBox.addRule(RelativeLayout.BELOW, numberOfView - 1);
		 checkboxPeople.setLayoutParams(params_people_checkBox);
		 
		 CheckBox checkboxSchedule = new CheckBox(this);
		 checkboxSchedule.setTextSize(20);
		 checkboxSchedule.setOnClickListener(this);
		 checkboxSchedule.setChecked(hashMapCheckBox.get(getResources().getString(R.string.checkBoxSchedule)));
		 checkboxSchedule.setText(getResources().getString(R.string.checkBoxSchedule)); 
		 checkboxSchedule.setId(++ numberOfView);
		 
		 params_schedule_checkBox.addRule(RelativeLayout.BELOW, numberOfView - 1);
		 checkboxSchedule.setLayoutParams(params_schedule_checkBox);
		 
		 
		 layout.addView(checkboxLocation);
		 layout.addView(checkboxDevices);
		 layout.addView(checkboxPeople);
		 layout.addView(checkboxSchedule);
		 
		 
		 
		 
	}
	
	
	
	
	/**
	 * adding views to screen
	 * @param task : the task which can be executed
	 */
	private void addTaskToInterface(Task task) 
	{
		  
	
		   TextView priority, distance, deadline,people, devices, duration;
		   TextView titleValue, priorityValue , distanceValue, deadlineValue, peopleValue, devicesValue, durationValue;
		   Button buttonExecuteTask;
		   
		   
		   View line;
		   RelativeLayout.LayoutParams params_title_value = 
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
				                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
				   
		    params_title_value.setMargins(100, 30, 100, 0);
		   
		   
		   RelativeLayout.LayoutParams params_priority = 
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		   params_priority.setMargins(40, 20, 0, 0);
		   
		    RelativeLayout.LayoutParams params_priority_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_priority_value.setMargins(50, 20, 0, 0);
		    
		    RelativeLayout.LayoutParams params_distance = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_distance.setMargins(40, 10, 20, 0);
		    
		    RelativeLayout.LayoutParams params_distance_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_distance_value.setMargins(20, 10, 20, 0);
		 		   
		 		   
		    RelativeLayout.LayoutParams params_deadline = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_deadline.setMargins(40, 10, 0, 0);
		    
		    RelativeLayout.LayoutParams params_deadline_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    params_deadline_value.setMargins(39, 10, 0, 0);
		    
		    RelativeLayout.LayoutParams params_people = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    params_people.setMargins(40, 10, 0, 0);
		    
		    
		    RelativeLayout.LayoutParams params_people_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_people_value.setMargins(55, 10, 0, 0);
		    
		    RelativeLayout.LayoutParams params_devices = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_devices.setMargins(40, 10, 0, 0);
		    
		    RelativeLayout.LayoutParams params_devices_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_devices_value.setMargins(45, 10, 0, 0);
		    
		    
		    RelativeLayout.LayoutParams params_duration = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_duration.setMargins(40, 10, 0, 0);
		    
		    RelativeLayout.LayoutParams params_duration_value = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_duration_value.setMargins(45, 10, 0, 0);
		    
		    
		    RelativeLayout.LayoutParams params_execute = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		    params_execute.setMargins(40, 10, 0, 0);
		    
		    RelativeLayout.LayoutParams params_line = 
		    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2);
		    
		    params_line.setMargins(20, 10, 20, 0);
		    
			priority = new TextView(this);
			distance = new TextView(this);
			deadline = new TextView(this);
			people   = new TextView(this);
			devices  = new TextView(this);
			duration = new TextView(this);
			
			titleValue    = new TextView(this);
			priorityValue = new TextView(this);
			distanceValue = new TextView(this);
			deadlineValue = new TextView(this);
			peopleValue   = new TextView(this);
			devicesValue  = new TextView(this);
			durationValue = new TextView(this);
			
			buttonExecuteTask = new Button(this);
			line = new View(this);
    
			
			titleValue.setText(task.getNameTask());
			titleValue.setTextSize(30);
			titleValue.setBackgroundColor(Color.rgb(193, 215, 222));
			titleValue.setPadding(0, 5, 0, 5);
			titleValue.setId( ++ numberOfView);
			titleValue.setTextColor(Color.BLUE);
			titleValue.setGravity(Gravity.CENTER); 
			params_title_value.addRule(RelativeLayout.BELOW, numberOfView - 1);
		    titleValue.setLayoutParams(params_title_value);
			
			
			priority.setText(R.string.textPriority);
			priority.setTextSize(20);	
			priority.setId( ++ numberOfView);
			params_priority.addRule(RelativeLayout.BELOW, numberOfView - 1);
			priority.setLayoutParams(params_priority);
			
			priorityValue.setText(task.getPriority());
			priorityValue.setTextSize(20);	
			priorityValue.setId( ++ numberOfView);
			params_priority_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_priority_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			priorityValue.setLayoutParams(params_priority_value);
			
			
			distance.setText(R.string.Distance);
			distance.setTextSize(20);	
			distance.setId( ++ numberOfView);
			params_distance.addRule(RelativeLayout.BELOW, numberOfView - 1);
			distance.setLayoutParams(params_distance);
			
			
			
			
			distanceValue.setText(Integer.toString(task.getDistance()));
			distanceValue.setTextSize(20);	
			distanceValue.setId( ++ numberOfView);
			params_distance_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_distance_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			distanceValue.setLayoutParams(params_distance_value);
			
			
			
			deadline.setText(R.string.buttonSetDeadline);
			deadline.setTextSize(20);
			deadline.setId( ++ numberOfView);
			params_deadline.addRule(RelativeLayout.BELOW, numberOfView - 1);
			deadline.setLayoutParams(params_deadline);
			
			
			
			DeadlineContext  deadlineTask = (DeadlineContext) 
			task.getScheduledContext().getContextElementsCollection().get(ContextElementType.DEADLINE_ELEMENT);
			
			
			deadlineValue.setText(deadlineTask.getDeadline());
			deadlineValue.setTextSize(20);
			deadlineValue.setId( ++ numberOfView);
			params_deadline_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_deadline_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			deadlineValue.setLayoutParams(params_deadline_value);
			
			
			people.setText(R.string.buttonChoosePeople);
			people.setTextSize(20);
			people.setId( ++ numberOfView);
			params_people.addRule(RelativeLayout.BELOW, numberOfView - 1);
			people.setLayoutParams(params_people);
				
			
			PeopleContext  peopleTask = (PeopleContext) 
			task.getScheduledContext().getContextElementsCollection().get(ContextElementType.PEOPLE_ELEMENT);
			

			peopleValue.setText(detectPeopleNames(peopleTask));
			peopleValue.setTextSize(20);
			peopleValue.setId( ++ numberOfView);
			params_people_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_people_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			peopleValue.setLayoutParams(params_people_value);
			
			
			devices.setText(R.string.buttonChooseDevices);
			devices.setTextSize(20);
			devices.setId( ++ numberOfView);
			params_devices.addRule(RelativeLayout.BELOW, numberOfView - 1);
			devices.setLayoutParams(params_devices);
				
			
			DeviceContext  deviceTask = (DeviceContext) 
			task.getScheduledContext().getContextElementsCollection().get(ContextElementType.DEVICES_ELEMENT);
			

			devicesValue.setText(detectDevicesNames(deviceTask));
			devicesValue.setTextSize(20);
			devicesValue.setId( ++ numberOfView);
			params_devices_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_devices_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			devicesValue.setLayoutParams(params_devices_value);
			
			
			
			duration.setText(R.string.Duration);
			duration.setTextSize(20);	
			duration.setId( ++ numberOfView);
			params_duration.addRule(RelativeLayout.BELOW, numberOfView - 1);
			duration.setLayoutParams(params_duration);
			
			
			DurationContext durationTask = (DurationContext)
			task.getScheduledContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
			
			
			
			
			durationValue.setText(durationTask.getDuration() / 60 + ":" +  durationTask.getDuration() % 60   );
			durationValue.setTextSize(20);	
			durationValue.setId( ++ numberOfView);
			params_duration_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_duration_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			durationValue.setLayoutParams(params_duration_value);
			
			
		//	System.out.println("DURATA DETERMINATA ESTE " + durationValue.getText().toString());
			
			
			buttonExecuteTask.setText(Constants.executeTask);
			buttonExecuteTask.setOnClickListener(new ExecuteTaskButton(this));
			buttonExecuteTask.setTextSize(20);	
			buttonExecuteTask.setId( ++ numberOfView);
			
			params_execute.addRule(RelativeLayout.BELOW, numberOfView - 1);
			buttonExecuteTask.setLayoutParams(params_execute);
			
			idTasks.put(numberOfView, task.getID());
			
			line.setBackgroundColor(Color.BLUE);
			line.setId( ++ numberOfView);
		
			params_line.addRule(RelativeLayout.BELOW, numberOfView - 1);
			line.setLayoutParams(params_line);
			

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
			layout.addView(duration);
			layout.addView(durationValue);
			layout.addView(buttonExecuteTask);
			layout.addView(line);

			
		
	}
	
	public String detectDevicesNames(DeviceContext deviceTask) {
		
		String devicesNames= "";
		
		if(deviceTask.getDeviceTask().size() == 0)
			return Constants.noChoose;
		
		for(String device : deviceTask.getDeviceTask())
			devicesNames = devicesNames + "," + MAP_myDevices_name_Mac.get(device);
		
		devicesNames = devicesNames.substring(1);
		
		
		return devicesNames;
		
		
	}
	
	
	public String detectPeopleNames(PeopleContext people)
	{
		String peopleNames= ""; 
	
		if(people.getPeopleTask().size() == 0)
			return Constants.noChoose;
		
		for(String device : people.getPeopleTask())
			peopleNames = peopleNames + "," +  MAP_people_devices_name_Mac.get(device);
		
		peopleNames = peopleNames.substring(1);
		
		
		return peopleNames;
	}



	public HashMap<Integer,Integer> getIdTasks() {
		return idTasks;
	}



	public void setIdTasks(HashMap<Integer,Integer> idTasks) {
		this.idTasks = idTasks;
	}



	public HashMap<String,String> getDeviceInfo() {
		return deviceInfo;
	}



	public void setDeviceInfo(HashMap<String,String> deviceInfo) {
		this.deviceInfo = deviceInfo;
	}



	public RelativeLayout getLayout() {
		return layout;
	}



	public void setLayout(RelativeLayout layout) {
		this.layout = layout;
	}



	public LatLng getCurrentPosition() {
		return currentPosition;
	}



	public void setCurrentPosition(LatLng currentPosition) {
		this.currentPosition = currentPosition;
	}

	public LinkedHashMap<String,String> getMAP_myDevices_name_Mac() {
		return MAP_myDevices_name_Mac;
	}

	public void setMAP_myDevices_name_Mac(LinkedHashMap<String,String> mAP_myDevices_name_Mac) {
		MAP_myDevices_name_Mac = mAP_myDevices_name_Mac;
	}

	public LinkedHashMap<String,String> getMAP_people_devices_name_Mac() {
		return MAP_people_devices_name_Mac;
	}

	public void setMAP_people_devices_name_Mac(
			LinkedHashMap<String,String> mAP_people_devices_name_Mac) {
		MAP_people_devices_name_Mac = mAP_people_devices_name_Mac;
	}

	@Override
	public void onClick(View v) {
		
		CheckBox check = (CheckBox) v;
		String name = check.getText().toString();
		Boolean checkValue = hashMapCheckBox.get(name);
		
		hashMapCheckBox.remove(name);
		
		
		if(checkValue == true)
			hashMapCheckBox.put(name, false);
		else
			hashMapCheckBox.put(name, true);
		
		
		
		System.out.println("Am apasat pe :" + name);
		
		System.out.println("Mapul este " + hashMapCheckBox.toString());
		
	//	checkAllTasksCompatibility(currentContext);
		
		 showUpdate();
		
		
		
		
	}






}


/**
 * receives data about devices through Bluetooth
 * @author ${Vlad Herescu}
 *
 */
class MyBroadCastRecvShow extends BroadcastReceiver{
	
	
	ShowTasks myActivy;
	
	MyBroadCastRecvShow(ShowTasks activity)
	{

		myActivy = activity;
		
	}

	@Override
	public void onReceive(android.content.Context context, Intent intent) {
		 String action = intent.getAction();
		 System.out.println("CUCU");
		 
	        // When discovery finds a device
	     if (BluetoothDevice.ACTION_FOUND.equals(action)) {

	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            // Add the name and address to an array adapter to show in a ListView
	           // mArrayAdapter.add(device.getName() + "\n" + device.getAddress());

	      //      System.out.println("Sunt in noul broadcaster"  +" " +System.currentTimeMillis() + " " + device.getName() + "\n" + device.getAddress());
	            
	        if(myActivy.getDeviceInfo().containsKey(device.getAddress()) == false)
	        {
	           System.out.println("A RECEPTAT un NOU DEVICE " + myActivy.getDeviceInfo().toString());
	           myActivy.getDeviceInfo().put(device.getAddress(), device.getName());	
	       //    myActivy.mLocationClient.requestLocationUpdates(myActivy.locationRequest, myActivy);
	           myActivy.showUpdate();
	           
	        }
	           
	           
	      }
		
	}
	
}


