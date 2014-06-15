package com.example.meniu;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
import DatabaseOperation.ExecuteTaskButton;
import DatabaseOperation.FixedTaskInformation;
import Salesman.ConstantsPopulation;
import Salesman.Individual;
import Salesman.PopulationEvolution;
import Task.Task;
import Task.TaskState;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class ActivityScheduler extends Activity implements OnClickListener,
								GooglePlayServicesClient.ConnectionCallbacks,
			  GooglePlayServicesClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
	
	
	/**
	 * contains the tasks that cannot have the interval changed
	 */
	ArrayList<FixedTaskInformation> fixedTasks;
	/**
	 * contains the tasks that can have the intervals changed
	 */
	List<Task> shiftingTasks;
	/**
	 * instance of the object who calculates the schedule using genetic algorithm
	 */
	PopulationEvolution population;
	
	/**
	 * contains the views
	 */
	RelativeLayout layout;
	/**
	 * shows the beginning of the interval when to calculate the schedule
	 */
	TimePicker timePickerStartTime;
	/**
	 * shows the end of the interval when to calculate the schedule
	 */
	TimePicker timePickerEndTime;
	int idView; 
	int idRoot;
	int numberViews;
	int idPicker;
	
	TextView textViewPatienceMessage;
	
	
	
	/**
	 * used to get the user's location
	 */
	LocationClient mLocationClient;
	
	
	
	 /**
	 * used to get updates about the user's location
	 */
	LocationRequest locationRequest;
	
	
	 /**
	 * for selecting the interval between fixed tasks when the user can execute shifting tasks
	 */
	Button buttonExecuteSchedule;
	
	
	/**
	 * contains the coordinates of the user's current location
	 */
	LatLng currentPosition;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_scheduler);
		
		
		ArrayList<TaskState> states = new ArrayList<TaskState>();
		
		fixedTasks = MainActivity.getDatabase().
		getFixedTasks(Core.getDayOfWeekString());
		
		
		states.add(TaskState.AMONG_TO_DO_LIST);
		shiftingTasks = MainActivity.getDatabase().getFilteredTasks(states);
		
		
		population = new PopulationEvolution(fixedTasks, shiftingTasks);
		
		
		layout = (RelativeLayout) findViewById(R.id.layoutActivitySchedule);
		timePickerStartTime = (TimePicker) findViewById(R.id.timePickerStart);
		timePickerEndTime = (TimePicker) findViewById(R.id.timePickerEnd);
		
		
		idRoot = layout.getChildAt(0).getId();
		idPicker = layout.getChildAt(2).getId();
		idView = layout.getChildAt(2).getId();
		numberViews = 2;
		
		
		if(fixedTasks.size() == 0)
			initTimePickers(ConstantsPopulation.pickerCurrentStartHour, ConstantsPopulation.pickerCurrentStartMinute,
							ConstantsPopulation.pickerCurrentEndHour, ConstantsPopulation.pickerCurrentEndMinute);
							
		else
			initNextInterval();

		initExecuteButton();
		
		mLocationClient = new LocationClient(this,this,this);
		locationRequest = new LocationRequest();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(5000);
		locationRequest.setFastestInterval(1000);
		locationRequest.setNumUpdates(1);
		
		
	//	population.startEvolution();
		
	}
	
	/**
	 * @param startHour : the hour to be set on picker when the task begins
	 * @param startMinute : the minute to be set on picker when the task begins
	 * @param endHour : the hour to be set on picker when the task ends
	 * @param endMinute : the minute to be set on picker when the task ends
	 */
	public void initTimePickers(int startHour, int startMinute, int endHour, int endMinute)
	{
		timePickerStartTime.setCurrentHour(startHour);
		timePickerStartTime.setCurrentMinute(startMinute);
	
		timePickerEndTime.setCurrentHour(endHour);
		timePickerEndTime.setCurrentMinute(endMinute);
	}

	/**
	 * initialize the button for selecting intervals between fixed tasks
	 */
	public void initNextInterval() {
		
		 Button buttonNextInterval = new Button(this);
		 
		 
		 RelativeLayout.LayoutParams params_button_interval = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                 RelativeLayout.LayoutParams.WRAP_CONTENT);

		 
		 buttonNextInterval.setText(getResources().getString(R.string.buttonNextInterval)); 
		 buttonNextInterval.setOnClickListener(this);
		 buttonNextInterval.setTextSize(20);	
		 buttonNextInterval.setId(  ++idView );
		 
		 
		 
		 params_button_interval.addRule(RelativeLayout.BELOW, idRoot);
		 params_button_interval.addRule(RelativeLayout.RIGHT_OF, idView -1);
		 params_button_interval.setMargins(20, 50, 0, 0);
		 buttonNextInterval.setLayoutParams(params_button_interval);
			
			
		 layout.addView(buttonNextInterval);
		 numberViews++;
		 
	
	}
	
	
	/**
	 * initialize the button who triggers the algorithm for calculating the schedule
	 */
	public void initExecuteButton() {

		
		 buttonExecuteSchedule  = new Button(this);
		 
		 
		 RelativeLayout.LayoutParams params_button_interval = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                 RelativeLayout.LayoutParams.WRAP_CONTENT);

		 
		 buttonExecuteSchedule.setText(getResources().getString(R.string.buttonCalculateSchedule)); 
		 buttonExecuteSchedule.setOnClickListener(this);
		 buttonExecuteSchedule.setTextSize(20);	
		 buttonExecuteSchedule.setId( ++ idView);
		 
		 params_button_interval.addRule(RelativeLayout.BELOW, idRoot);
		 params_button_interval.addRule(RelativeLayout.RIGHT_OF, idView -1);
		 params_button_interval.setMargins(20, 50, 0, 0);
		 buttonExecuteSchedule.setLayoutParams(params_button_interval);
			
			
		 layout.addView(buttonExecuteSchedule);
		 numberViews++;
		 
	
	}
	
	/**
	 * shows the message for the user to be patient
	 */
	public void initMessagePatience()
	{
		 textViewPatienceMessage = new TextView(this); 
		 
		 
		 RelativeLayout.LayoutParams params_button_interval = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                 RelativeLayout.LayoutParams.WRAP_CONTENT);

		 
		 textViewPatienceMessage.setText(getResources().getString(R.string.messagePatience)); 
		 textViewPatienceMessage.setOnClickListener(this);
		 textViewPatienceMessage.setTextSize(20);	
		 textViewPatienceMessage.setId( ++ idView);
		 
		 
		 
		 params_button_interval.addRule(RelativeLayout.BELOW, idView - 2);
		 params_button_interval.setMargins(0, 10, 0, 0);
		 textViewPatienceMessage.setLayoutParams(params_button_interval);
			
			
		 layout.addView(textViewPatienceMessage);
	}
	
	/**
	 * @param index 
	 * 
	 */
	public void showTasks(Task task, LatLng positionBefore, int index)
	{
		
		  TextView title,priority, distance, duration, durationBetween ;
		  TextView titleValue, priorityValue , distanceValue, durationValue, durationBetweenValue;
		  

		  
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
		  
		  
		  RelativeLayout.LayoutParams params_duration = 
		  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 		                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		  RelativeLayout.LayoutParams params_duration_value = 
		  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				 				                    RelativeLayout.LayoutParams.WRAP_CONTENT);
		  
		  
		  
		  title    = new TextView(this);
		  priority = new TextView(this);
		  distance = new TextView(this);
		  duration = new TextView(this);
			
		  titleValue    = new TextView(this);
		  priorityValue = new TextView(this);
		  distanceValue = new TextView(this);;
		  durationValue = new TextView(this);
		

		  title.setText(index + ". " + this.getResources().getString((R.string.taskTitle)) + " : "); 
		  title.setTextSize(20);
		  title.setId( ++idPicker);
		  params_title.addRule(RelativeLayout.BELOW, idPicker - 1);
		  params_title.setMargins(0, 10, 0, 0); 
		  title.setLayoutParams(params_title);
		  
		   
		  titleValue.setText(task.getNameTask());
		  titleValue.setTextSize(20);
		  titleValue.setId( ++ idPicker);
		  params_title_value.addRule(RelativeLayout.RIGHT_OF, idPicker - 1);
		  params_title_value.addRule(RelativeLayout.BELOW, idPicker - 2);
		  params_title_value.setMargins(10, 10, 0, 0);
		  titleValue.setLayoutParams(params_title_value);
		  
		  priority.setText(R.string.textPriority);
		  priority.setTextSize(20);	
		  priority.setId( ++ idPicker);
		  params_priority.addRule(RelativeLayout.BELOW, idPicker - 1);
		  params_priority.setMargins(0, 10, 0, 0);
		  priority.setLayoutParams(params_priority);
			
		  priorityValue.setText(task.getPriority());
		  priorityValue.setTextSize(20);	
		  priorityValue.setId( ++ idPicker);
		  params_priority_value.addRule(RelativeLayout.RIGHT_OF, idPicker - 1);
		  params_priority_value.addRule(RelativeLayout.BELOW, idPicker - 2);
		  params_priority_value.setMargins(20, 10, 0, 0);
		  priorityValue.setLayoutParams(params_priority_value);
		  
		  
		  duration.setText(R.string.Duration);
		  duration.setTextSize(20);	
		  duration.setId( ++ idPicker);
		  params_duration.addRule(RelativeLayout.BELOW, idPicker - 1);
		  params_duration.setMargins(0, 10, 0, 0);
		  duration.setLayoutParams(params_duration);
			
			
		  DurationContext durationTask = (DurationContext)
		  task.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
			
			
			
			
		  durationValue.setText(durationTask.getDuration() / 60 + ":" +  durationTask.getDuration() % 60   );
		  durationValue.setTextSize(20);	
		  durationValue.setId( ++ idPicker);
		  params_duration_value.addRule(RelativeLayout.RIGHT_OF, idPicker - 1);
		  params_duration_value.addRule(RelativeLayout.BELOW, idPicker - 2);
		  params_duration_value.setMargins(20, 10, 0, 0);
		  durationValue.setLayoutParams(params_duration_value);
		  
		  
		  
		  layout.addView(title);
		  layout.addView(titleValue);
		  layout.addView(priority);
		  layout.addView(priorityValue);
		  layout.addView(duration);
		  layout.addView(durationValue);
	}
	
	
	/**
	 * specifies how much time it takes, from what time until what time 
	 * @param durationMinutes : how much it will take to execute the tasks
	 * @param startTimeMinutes : when does the set of task begin
 	 */
	public void addHeaderTime(int startTimeMinutes, float durationMinutes)
	{
		 TextView duration, startTime, endTime;
		 TextView durationValue, startTimeValue, endTimeValue;
		 int endTimeMinutes = startTimeMinutes + (int) durationMinutes;
		 
		 
		 RelativeLayout.LayoutParams params_duration = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
		 RelativeLayout.LayoutParams params_duration_value = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
				  
				  
		 RelativeLayout.LayoutParams params_startTime = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		 RelativeLayout.LayoutParams params_startTime_value = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		 
		 
		 RelativeLayout.LayoutParams params_endTime = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		 RelativeLayout.LayoutParams params_endTime_value = 
		 new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		 
		 
		 startTime    		= new TextView(this);
		 endTime 			= new TextView(this);
		 duration 			= new TextView(this);
			
		 startTimeValue  	= new TextView(this);
		 endTimeValue 	 	= new TextView(this);
		 durationValue 		= new TextView(this);
		 
		 duration.setText( "Total Duration : ");
		 duration.setTextSize(20);
		 duration.setTextColor(Color.BLUE);
		 duration.setId( ++ idPicker);
		 params_duration.addRule(RelativeLayout.BELOW, idPicker - 1);
		 params_duration.setMargins(0, 10, 0, 0);
		 duration.setLayoutParams(params_duration);
			
			
		 durationValue.setText( ( (int) durationMinutes / 60) + ":" +  durationMinutes % 60   );
		 durationValue.setTextSize(20);	
		 durationValue.setId( ++ idPicker);
		 durationValue.setTextColor(Color.BLUE);
		 params_duration_value.addRule(RelativeLayout.RIGHT_OF, idPicker - 1);
		 params_duration_value.addRule(RelativeLayout.BELOW, idPicker - 2);
		 params_duration_value.setMargins(20, 10, 0, 0);
		 durationValue.setLayoutParams(params_duration_value);
		 
		 
		 startTime.setText( "Start time        : ");
		 startTime.setTextSize(20);	
		 startTime.setId( ++ idPicker);
		 startTime.setTextColor(Color.BLUE);
		 params_startTime.addRule(RelativeLayout.BELOW, idPicker - 1);
		 params_startTime.setMargins(0, 10, 0, 0);
		 startTime.setLayoutParams(params_startTime);
			
			
		 startTimeValue.setText( ( (int) startTimeMinutes / 60) + ":" +  startTimeMinutes % 60   );
		 startTimeValue.setTextSize(20);	
		 startTimeValue.setId( ++ idPicker);
		 startTimeValue.setTextColor(Color.BLUE);
		 params_startTime_value.addRule(RelativeLayout.RIGHT_OF, idPicker - 1);
		 params_startTime_value.addRule(RelativeLayout.BELOW, idPicker - 2);
		 params_startTime_value.setMargins(20, 10, 0, 0);
		 startTimeValue.setLayoutParams( params_startTime_value);
		 
		 
		 endTime.setText( "End time          : ");
		 endTime.setTextSize(20);	
		 endTime.setId( ++ idPicker);
		 endTime.setTextColor(Color.BLUE);
		 params_endTime.addRule(RelativeLayout.BELOW, idPicker - 1);
		 params_endTime.setMargins(0, 10, 0, 0);
		 endTime.setLayoutParams(params_endTime);
			
			
		 endTimeValue.setText( ( (int) endTimeMinutes / 60) + ":" +  endTimeMinutes % 60   );
		 endTimeValue.setTextSize(20);	
		 endTimeValue.setId( ++ idPicker);
		 endTimeValue.setTextColor(Color.BLUE);
		 params_endTime_value.addRule(RelativeLayout.RIGHT_OF, idPicker - 1);
		 params_endTime_value.addRule(RelativeLayout.BELOW, idPicker - 2);
		 params_endTime_value.setMargins(20, 10, 0, 0);
		 endTimeValue.setLayoutParams( params_endTime_value);
		 
		 
		 layout.addView(duration);
		 layout.addView(durationValue);
		 layout.addView(startTime);
		 layout.addView(startTimeValue);
		 layout.addView(endTime);
		 layout.addView(endTimeValue);
		 

	}
	

	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scheduler, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		
		if(arg0.getId() == buttonExecuteSchedule.getId())
		{

			Individual chosenIndividual;
			ArrayList<Individual> populationObtained;
			LatLng startPosition;
			int index = 1;
			
		//	 if(layout.getChildCount() - numberViews - 1 > 0)
		//		layout.removeViews(numberViews + 1, layout.getChildCount() - numberViews);

		//	 initMessagePatience();
			
			 population.startEvolution();
			
			 populationObtained = population.getNewPopulation();
			 chosenIndividual   = populationObtained.get(0);
			 startPosition = this.currentPosition;
			 
		//	 System.out.println("INDIVIZII AICI SUNT " +  chosenIndividual.getOrderTasks() + " " + chosenIndividual.getFitnessValue() + " " + chosenIndividual.getDuration() + " " + chosenIndividual.getStartTime());
			 
			 addHeaderTime(chosenIndividual.getStartTime(), chosenIndividual.getDuration());
			 
			 for(Integer idTask : chosenIndividual.getOrderTasks())	
			 {
				 showTasks( shiftingTasks.get(idTask), startPosition , index ); 
				 index++;
			 }
			 	
		}
	//	layout.removeView(textViewPatienceMessage);
		
		
	}

	
	
	

	@Override
	public void onLocationChanged(Location arg0) {
		currentPosition = new LatLng(arg0.getLatitude(), arg0.getLongitude());
		
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
    
   

}
