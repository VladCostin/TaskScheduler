package com.example.meniu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
import DatabaseOperation.Tasks;
import Task.Task;
import Task.TaskState;
import android.os.Bundle;
import android.app.Activity;
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
	RelativeLayout  rl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_current_task);
		
		
		selectCurrentTasks();
		
		
		rl = (RelativeLayout )  this.findViewById(R.id.currentTaskLayout);
		seconds = (TextView) findViewById(R.id.seconds);
		minutes = (TextView) findViewById(R.id.minutes);
		hours = (TextView) findViewById(R.id.hours);
		
		addMinutesButton = (Button) findViewById(R.id.addMinutes);
		addHoursButton = (Button) findViewById(R.id.addHours);
		finalizeTask = (Button) findViewById(R.id.taskFinalizat);
		
		
		addMinutesButton.setOnClickListener(this);
		addHoursButton.setOnClickListener(this);
		finalizeTask.setOnClickListener(this);


	}
	
	
	/**
	 * selects from all the tasks those who were chosen to be executed in the current Context
	 */
	public void selectCurrentTasks() {
		List<Task> tasks = MainActivity.getDatabase().getAllTasks();
		ArrayList<Task> chosenTasks = new ArrayList<Task>();
		for(Task task :tasks)
			if(task.getState() == TaskState.CURRENT_TASK){
				chosenTasks.add(task);
			}
		
		
		
		
		
		currentTask = chosenTasks.get(0);
		
		
	}


	public void onResume()
	{
		super.onResume();
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
		
		DurationContext durationTask = (DurationContext)
		currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		
		System.out.println("DURATA ESTE :" + durationTask.getDuration());
		
		SimpleDateFormat format = new SimpleDateFormat(Constants.parseTime);
		
		
		Date currentDateTime = new Date();
		Date taskChosenTime;
		
		int nrMinutesFromHour;
		
		try {
			taskChosenTime = format.parse(currentTask.getStartTime());
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
 
			System.out.println( diffDays + " days, ");
			System.out.println( diffHours + " hours, ");
			System.out.println(diffMinutes + " minutes, ");
			System.out.println(diffSeconds + " seconds.");
			
			
			
			nrMinutesFromHour = durationTask.getDuration() % 60;
			if(nrMinutesFromHour <   diffMinutes)
			{
				nrHours = ( durationTask.getDuration() / 60  -1 ) -  (int) diffHours ;
				nrMinutes = (durationTask.getDuration() % 60 + 59)  - (int) diffMinutes;
			}
			else
			{
				nrHours = ( durationTask.getDuration() / 60   ) -  (int) diffHours ;
				nrMinutes = (durationTask.getDuration() % 60 )  - (int) diffMinutes;
			}
			
			
		
			nrSeconds = 60 - (int) diffSeconds;
			
			
			hours.setText(Integer.toString( nrHours));
			minutes.setText( Integer.toString(nrMinutes ));
			seconds.setText(Integer.toString(nrSeconds));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	


	public void onPause()
	{
		super.onPause();
		isRunning = false;
		
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
		
		MainActivity.getDatabase().updateTask(currentTask.getID(),Tasks.KEY_Status, TaskState.EXECUTED.toString());
		isRunning = false;
		rl.removeAllViews();
		
		
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
		
		
		System.out.println("MINUTE DURATA " + durationTask.getDuration());
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
		
		
		
		System.out.println("ORE DURATA ESTE ACUM" + durationTask.getDuration());
		
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
			
					System.out.println(taskActivity.getNrSeconds());
					
					
					
					if(taskActivity.getNrSeconds() <= 0 && taskActivity.getNrHours() <= 0
					&& taskActivity.getNrMinutes() <= 0)
						return;
					
					
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
