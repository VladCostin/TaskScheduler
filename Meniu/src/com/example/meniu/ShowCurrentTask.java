package com.example.meniu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
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
	private int nrSeconds;
	private int nrMinutes;
	private int nrHours;
	
	private TextView seconds;
	private TextView minutes; 
	private TextView hours; 
	
	
	Button addMinutesButton;
	Button addHoursButton;
	Button finalizeTask;
	
	

	Task currentTask;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_current_task);
		
		
		selectCurrentTasks();
		
		
		RelativeLayout  rl = (RelativeLayout )  this.findViewById(R.id.currentTaskLayout);
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
		
		if(currentTask == null)
			System.out.println("TASKUL CURRENT ESTE NULL");
		else
			System.out.println("TASKUL CURRENT NU ESTE NULL");
		
		
		
		
		
		
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
		
		DurationContext durationTask = (DurationContext)
		currentTask.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
		
		if(durationTask == null)
			System.out.println("DURATION TASK ESTE NULL");
		else{
			System.out.println("DURATION TASK NU ESTE NULL");
			System.out.println(durationTask.getDuration());
		}
		System.out.println(Core.getDurationMinutes());
		
		
		nrMinutes = Core.getDurationMinutes().get(durationTask.getDuration()) - 1 ;
		nrHours   = nrMinutes / 60; 
		nrMinutes = nrMinutes % 60; 
		nrSeconds = 59;
		
		hours.setText(Integer.toString( nrHours));
		minutes.setText( Integer.toString(nrMinutes ));
		seconds.setText(Integer.toString(nrSeconds));
		
	}


	public void onPause()
	{
		super.onPause();
		isRunning = false;
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
					hours.setText(Integer.toString(nrHours++));
					break;
					
			case R.id.addMinutes : System.out.println("ADAUGA 10 minute");
					nrMinutes += 10;
					if(nrMinutes >= 60){
						nrMinutes -= 60;
						minutes.setText(Integer.toString(nrMinutes));
						hours.setText(Integer.toString(nrHours++));
					}
					else
						minutes.setText(Integer.toString(nrMinutes));
					break;
					
			case R.id.taskFinalizat : System.out.println("S-a terminat taskul");
					break;
		
		}
		
		
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
					
					if(taskActivity.getNrSeconds() == 0 && taskActivity.getNrHours() == 0 && taskActivity.getNrMinutes() == 0)
						return;
					
					taskActivity.setNrSeconds(taskActivity.getNrSeconds() - 1); 
					if(taskActivity.getNrSeconds() == 0)
					{
						taskActivity.setNrSeconds(59);
						taskActivity.setNrMinutes(taskActivity.getNrMinutes() - 1);
						taskActivity.getMinute().setText(Integer.toString(taskActivity.getNrMinutes()));
					}
					taskActivity.getSecunde().setText(Integer.toString(taskActivity.getNrSeconds()));
					
					
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
