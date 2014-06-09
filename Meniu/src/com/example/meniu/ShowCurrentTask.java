package com.example.meniu;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Clusters.KMeansDuration;
import ContextElements.ContextElementType;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import DatabaseOperation.Tasks;
import Task.Task;
import Task.TaskState;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShowCurrentTask extends Activity implements OnClickListener {

	
	/**
	 * mentions whether the activity is running
	 */
	private boolean isRunning;
	
	/**
	 * number of seconds needed for the task to be finished 
	 */
	private int nrSeconds;
	/**
	 * number of minutes needed for the task to be executed
	 */
	private int nrMinutes;
	/**
	 * number of hours needed for the task to be executed
	 */
	private int nrHours;
	
	/**
	 * shos the number of seconds remained
	 */
	private TextView seconds;
	/**
	 * shows the number of minutes remained
	 */
	private TextView minutes; 
	/**
	 * shows the number of hours remained
	 */
	private TextView hours; 
	
	
	/**
	 * adds 10 minutes to the duration value
	 */
	Button addMinutesButton;
	/**
	 * adds one hour to the duration value
	 */
	Button addHoursButton;
	
	/**
	 * the task goes from currentTask state to executed state 
	 */
	Button finalizeTask;
	
	

	/**
	 * contains the details about the chosen task 
	 */
	Task currentTask;
	
	
	/**
	 * shows the task chosen to be executed or a message instead
	 */
	RelativeLayout  layout;
	
	
	/**
	 * the title of the current task
	 */
	TextView title;
	
	
	/**
	 * the location of the current task
	 */
	TextView location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_current_task);
		
		title = (TextView) findViewById(R.id.textViewTitle);
		location = (TextView) findViewById(R.id.textViewAddLocation);
		
		layout = (RelativeLayout )  this.findViewById(R.id.currentTaskLayout);
		seconds = (TextView) findViewById(R.id.seconds);
		minutes = (TextView) findViewById(R.id.minutes);
		hours = (TextView) findViewById(R.id.hours);
		
		addMinutesButton = (Button) findViewById(R.id.addMinutes);
		addHoursButton = (Button) findViewById(R.id.addHours);
		finalizeTask = (Button) findViewById(R.id.taskFinalizat);
		
		
		addMinutesButton.setOnClickListener(this);
		addHoursButton.setOnClickListener(this);
		finalizeTask.setOnClickListener(this);
		
		
		selectCurrentTasks();


	}
	
	
	/**
	 * selects from all the tasks those who were chosen to be executed in the current Context
	 */
	public void selectCurrentTasks() {
		List<Task> tasks = MainActivity.getDatabase().getAllTasks();
		List<Address> addresses = null;
		Geocoder geocoder = new Geocoder(this);
		ArrayList<Task> chosenTasks = new ArrayList<Task>();
		for(Task task : tasks)
			if(task.getState() == TaskState.CURRENT_TASK){
				chosenTasks.add(task);
			}
		
		
		
		if(chosenTasks.size() == 0)
		{
			layout.removeAllViews();
			TextView showMessageTask = new TextView(this);
				
			RelativeLayout.LayoutParams params_title = 
				           new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				                                           RelativeLayout.LayoutParams.MATCH_PARENT);
			showMessageTask.setGravity(Gravity.CENTER); 
			showMessageTask.setText(Constants.noCurrentTaskMessage);
			showMessageTask.setTextColor(Color.BLUE);
			showMessageTask.setTextSize(20);
			showMessageTask.setLayoutParams(params_title);
			    
			    
			    
			layout.addView(showMessageTask);	
			return;
				
				
			
		}
		
		
		currentTask = chosenTasks.get(0);
		title.setText(currentTask.getNameTask());
		LocationContext locationC = (LocationContext) currentTask.getInternContext().
		getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		
		try {
			addresses = geocoder.getFromLocation(locationC.getLatitude() ,locationC.getLongitude(), 1);
			location.setText(addresses.get(0).getAddressLine(0) ) ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}


	public void onResume()
	{
		super.onResume();
		
		if(currentTask == null)
			return;
		calculateTimeRemaining();
		
		isRunning = true;
		
		
		Thread timer = new Thread(new MyTimer(this));
		timer.start();
		
	}
	
	
	/**
	 * sets the initial values for hours and minutes remaining, depending
	 * on the time when it started to be executed
	 */
	private void calculateTimeRemaining() {
		
		long diff,  diffSeconds, diffMinutes, diffHours, diffDays;
		long totalTimePast;
		int nrMinutesFromHour;
		int deltaMinutesPast;
		
		
		
		
		DurationContext durationTask = (DurationContext)
		currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		
	/*	if(durationTask.getDuration() == -1 )
		{
			
			
			KMeansDuration durationAlg = new KMeansDuration();
			durationAlg.calculateKlusters();
			Task  centerDetected = durationAlg.detectCentroid(currentTask);
			
			DurationContext durationTaskCurrent = (DurationContext)
			currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
			
			durationTask.setDuration(durationTaskCurrent.getDuration()); 
			 
		}*/
		
		
		
		System.out.println("DURATA ESTE :" + durationTask.getDuration());
		
		
		
		
		Date currentDateTime = new Date();
		Date taskChosenTime;
		
		
		taskChosenTime = Core.timeParseToDate(currentTask.getStartTime());
		System.out.println(currentDateTime);
		System.out.println(taskChosenTime);
			
			
		//	nrMinutes = Core.getDurationMinutes().get(durationTask.getDuration()) - 1 ;
		//	nrHours   = nrMinutes / 60; 
		//	nrMinutes = nrMinutes % 60;
			
		diff = currentDateTime.getTime() - taskChosenTime.getTime();
			 
		diffSeconds = diff / 1000 % 60;
		diffMinutes = diff / (60 * 1000) % 60;
		diffHours = diff / (60 * 60 * 1000) % 24;
		diffDays = diff / (24 * 60 * 60 * 1000);
 
		System.out.println("Numarul de zile trecute de cand am pornit aplicatia  " +  diffDays );
		System.out.println("Numarul de ore trecute de cand am pornit aplicatia " + diffHours);
		System.out.println("Numarul de minute trecute de cand am pornit aplicatia " + diffMinutes );
		System.out.println("Numarul de secunde trecute de cand am pornit aplicatia " + diffSeconds);
			
			
			
		nrMinutesFromHour = durationTask.getDuration() % 60; // nrMinutesFromHour = the number of minutes without
			// the number of full hours
			
			
		System.out.println("Numarul de ore din durata " + durationTask.getDuration() / 60);
		System.out.println("Numarul de minute din durata" +  durationTask.getDuration() % 60);
		System.out.println("Numarul de minute ramase din task, fara ore" + nrMinutesFromHour);
			
			
			
			
			
			// daca a expirat timpul, pentru ca valorile sa nu imi dea cu -
			// adaug in baza de date la durata cat timp a trecut + 10 minute
			// pe interfata adaug 10 minute
			
		totalTimePast = diffMinutes + diffHours * 60;
			
			
		System.out.println("Timpul total trecut este " + totalTimePast);
		System.out.println("durata estimata este " + durationTask.getDuration());
			
		if(totalTimePast >= durationTask.getDuration())
		{
			deltaMinutesPast = (int) totalTimePast - durationTask.getDuration();
			nrSeconds = 59;
			nrMinutes   = 9;
			nrHours   = 0;
				
				
			hours.setText(Integer.toString( nrHours));
			minutes.setText( Integer.toString(nrMinutes ));
			seconds.setText(Integer.toString(nrSeconds));
				
			durationTask.setDuration(durationTask.getDuration() + deltaMinutesPast + 10); 
			return;
				
				
		}
			
	
			// daca de exemplu s-au dus 35 de minute, si durata avea 40 de minute, atunci intra pe else
						// daca in schimb durata avea 30 de minute atunci inseamna ca trebuie sczut si numarul de ore, si la minute se porneste de la 59 - diffMinutes
		if(nrMinutesFromHour <   diffMinutes)
		{

				
				
			// divide to 60 to get the number of hours then substract 1, which is given to minutes
			// substract then diffHours, which represents how much time I stayed with the activity not opened
			nrHours = ( durationTask.getDuration() / 60  -1 ) -  (int) diffHours ;
			nrMinutes = (durationTask.getDuration() % 60)  - (int) diffMinutes;
		}
		else
		{
			nrHours = ( durationTask.getDuration() / 60   ) -  (int) diffHours ;
			nrMinutes = (durationTask.getDuration() % 60 )  - (int) diffMinutes  - 1; // mai scad 1 minut de la minutul luat pentru a 
			// scadea din secunde
		}
			
			
		
		nrSeconds = 60 - (int) diffSeconds;
			
			
		hours.setText(Integer.toString( nrHours));
		minutes.setText( Integer.toString(nrMinutes ));
		seconds.setText(Integer.toString(nrSeconds));
		
		
		
	}
	
	


	public void onPause()
	{
		super.onPause();
		isRunning = false;
		
		if(currentTask  == null)
			return;
		
		DurationContext durationTask = (DurationContext)
		currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		
		MainActivity.getDatabase().updateTask
		(currentTask.getID(),Tasks.KEY_Duration , Integer.toString( durationTask.getDuration() ));
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_current_task, menu);
		return true;
	}


	public boolean isRunning() {
		return isRunning;
	}


	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}


	public int getNrSeconds() {
		return nrSeconds;
	}


	public void setNrSeconds(int nr) {
		this.nrSeconds = nr;
	}


	public TextView getSecunde() {
		return seconds;
	}


	public void setSecunde(TextView secunde) {
		this.seconds = secunde;
	}


	public int getNrMinutes() {
		return nrMinutes;
	}


	public void setNrMinutes(int nrMinutes) {
		this.nrMinutes = nrMinutes;
	}


	public int getNrHours() {
		return nrHours;
	}


	public void setNrHours(int nrHours) {
		this.nrHours = nrHours;
	}


	public TextView getMinute() {
		return minutes;
	}


	public void setMinute(TextView minute) {
		this.minutes = minute;
	}


	public TextView getHours() {
		return hours;
	}


	public void setHours(TextView hours) {
		this.hours = hours;
	}


	@Override
	public void onClick(View arg0) {
		
		switch(arg0.getId())
		{
			case R.id.addHours : 
					addHours();
					break;
					
			case R.id.addMinutes : 
					addMinutes();
					break;
					
			case R.id.taskFinalizat : 
					taskFinalize();
					break;
		
		}
		
		
	}


	/**
	 * if the client has finished the task
	 */
	public void taskFinalize() {
		
		int nrMinutesRemaining; 
		
		
		DurationContext durationTask = (DurationContext)
		currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		nrMinutesRemaining = durationTask.getDuration() - nrMinutes - nrHours * 6;
		
		MainActivity.getDatabase().updateTask
		(currentTask.getID(),Tasks.KEY_Duration , Integer.toString( nrMinutesRemaining ));
		
		MainActivity.getDatabase().updateTask(currentTask.getID(),Tasks.KEY_Status, TaskState.EXECUTED.toString());
		
		
		isRunning = false;
		layout.removeAllViews();
		
		
		
	}
	
	
	
	/**
	 * adds minutes to the task's duration, both in GUI and database
	 */
	public void addMinutes()
	{
		
		nrMinutes += 10;
		if(nrMinutes >= 60){
			nrMinutes -= 60;
			hours.setText(Integer.toString(nrHours++));
		}
		
		minutes.setText(Integer.toString(nrMinutes));
		
		
		
		DurationContext durationTask = (DurationContext)
		currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		
	//	System.out.println("MINUTE DURATA " + durationTask.getDuration());
		durationTask.setDuration( durationTask.getDuration() + 10);
		
	}
	
	
	/**
	 * adds hours to the task's duration, both in GUI and database
	 */
	public void addHours()
	{
		hours.setText(Integer.toString(nrHours++));
		
		DurationContext durationTask = (DurationContext)
		currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		
		durationTask.setDuration( durationTask.getDuration() + 60);
		
		
		
	//	System.out.println("ORE DURATA ESTE ACUM" + durationTask.getDuration());
		
	}

}



class MyTimer implements Runnable{

	ShowCurrentTask taskActivity;
	
	
	MyTimer(ShowCurrentTask taskActivity)
	{
		this.taskActivity = taskActivity;
	}
	
	@Override
	public void run() {
		
		while(taskActivity.isRunning() == true)
		{
		//	System.out.println(taskActivity.getNr());
		
			
			
			taskActivity.runOnUiThread( new Runnable()
			{
				public void run(){
			
			//		System.out.println(taskActivity.getNrSeconds());
					
					
					
					if(taskActivity.getNrSeconds() <= 0 && taskActivity.getNrHours() <= 0
					&& taskActivity.getNrMinutes() <= 0){
						taskActivity.addMinutes();
						return;
					}
					
					
					taskActivity.setNrSeconds(taskActivity.getNrSeconds() - 1); 
					
					if(taskActivity.getNrSeconds()  < 0)
					{
						taskActivity.setNrSeconds(59);
						
						if(taskActivity.getNrMinutes() - 1 < 0 ){
							taskActivity.setNrMinutes(59);
							
							if(taskActivity.getNrHours() > 0 )
								taskActivity.setNrHours(taskActivity.getNrHours() - 1);
							
						}
						else
							taskActivity.setNrMinutes(taskActivity.getNrMinutes() - 1);
						
					}
					taskActivity.getSecunde().setText(Integer.toString(taskActivity.getNrSeconds()));
					taskActivity.getMinute().setText(Integer.toString(taskActivity.getNrMinutes()));
					taskActivity.getHours().setText(Integer.toString(taskActivity.getNrHours())); 
					
					
					
					
				}
			});
			
			
			try {
				
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
}
