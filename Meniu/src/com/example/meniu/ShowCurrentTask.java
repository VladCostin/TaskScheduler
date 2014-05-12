package com.example.meniu;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShowCurrentTask extends Activity {

	
	/**
	 * mentions whether the activity is running
	 */
	private boolean isRunning;
	private int nrSeconds;
	private int nrMinutes;
	private int nrHours;
	
	private TextView secunde;
	private TextView minute;
	private TextView hours; 
	
	
	Button addMinutesButton;
	Button addHoursButton;
	Button finalizeTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_current_task);
		
		RelativeLayout  rl = (RelativeLayout )  this.findViewById(R.id.currentTaskLayout);
		secunde = (TextView) findViewById(R.id.seconds);
		minute = (TextView) findViewById(R.id.minutes);
		hours = (TextView) findViewById(R.id.hours);
		
		nrSeconds = 60;
		nrMinutes = 34;
		nrHours = 0;
		
		minute.setText(Integer.toString(nrMinutes));
		hours.setText(Integer.toString(nrHours));

	}
	
	
	public void onResume()
	{
		super.onResume();
		isRunning = true;
		
		
		Thread timer = new Thread(new MyTimer(this));
		timer.start();
		
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


	public int getNr() {
		return nrSeconds;
	}


	public void setNr(int nr) {
		this.nrSeconds = nr;
	}


	public TextView getSecunde() {
		return secunde;
	}


	public void setSecunde(TextView secunde) {
		this.secunde = secunde;
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
		return minute;
	}


	public void setMinute(TextView minute) {
		this.minute = minute;
	}


	public TextView getHours() {
		return hours;
	}


	public void setHours(TextView hours) {
		this.hours = hours;
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
					
					if(taskActivity.getNr() == 0 && taskActivity.getNrHours() == 0 && taskActivity.getNrMinutes() == 0)
						return;
					
					taskActivity.setNr(taskActivity.getNr() - 1);
					if(taskActivity.getNr() == 0)
					{
						taskActivity.setNr(60);
						taskActivity.setNrMinutes(taskActivity.getNrMinutes() - 1);
						taskActivity.getMinute().setText(Integer.toString(taskActivity.getNrMinutes()));
					}
					taskActivity.getSecunde().setText(Integer.toString(taskActivity.getNr()));
					
					
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
