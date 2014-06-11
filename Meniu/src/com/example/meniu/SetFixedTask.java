package com.example.meniu;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class SetFixedTask extends Activity implements OnClickListener {
	
	
	/**
	 * used to add dynamically fixed tasks
	 */
	RelativeLayout layout;
	
	
	/**
	 * used to show the tasks set for a day
	 */
	DaysOfWeek currentDayShown;
	

	/**
	 * contains all the days of the week
	 */
	ArrayList<DaysOfWeek> week;
	
	
	/**
	 * to show the tasks fixed on the previous day
	 */
	Button buttonPreviousDay;
	
	
	/**
	 * to show the tasks fixed in the next day
	 */
	Button buttonNextDay;
	
	/**
	 * for adding the start time and the end time for a task
	 */
	Button buttonAddTask;
	
	
	/**
	 * shows the name for the day for which the fixed tasks are shown
	 */
	TextView textViewCurrentDay; 
	
	
	/**
	 * the number of the view to add to the relative layout
	 */
	int numberOfView;
	
	/**
	 * represents the id of the add task button
	 */
	int idAddButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_fixed_task);
		
		layout = (RelativeLayout) findViewById(R.id.layoutRelativeFixedSchedule);
		currentDayShown = DaysOfWeek.MONDAY;
		
		buttonPreviousDay = (Button) findViewById(R.id.buttonPreviousDay);
		buttonNextDay     = (Button) findViewById(R.id.buttonNextDay);
		buttonAddTask     = (Button) findViewById(R.id.buttonAddTask);
		textViewCurrentDay = (TextView) findViewById(R.id.textViewDay);
		
		
		buttonNextDay.setOnClickListener(this);
		buttonPreviousDay.setOnClickListener(this);
		buttonAddTask.setOnClickListener(this);
		
		idAddButton = buttonAddTask.getId();
		
		
		week = new ArrayList<DaysOfWeek>();
		week.add(DaysOfWeek.MONDAY);
		week.add(DaysOfWeek.TUESDAY);
		week.add(DaysOfWeek.WEDNESDAY);
		week.add(DaysOfWeek.THURSDAY);
		week.add(DaysOfWeek.FRIDAY);
		
		
		showFixedTasksForADay();
		
		
		
		
	}

	/**
	 * showing the established tasks for the current day shown
	 */
	public void showFixedTasksForADay() {
		
	
		if(layout.getChildCount() > 4 )
			layout.removeViews(4, layout.getChildCount() - 1);
		
		
		numberOfView = idAddButton;
		
		  
		 
		
		
	}

	

	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_fixed_task, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == buttonNextDay.getId() )
		{
			int position = (week.indexOf(currentDayShown) + 1 ) % week.size() ;
			
			textViewCurrentDay.setText(week.get(position).toString());
			
			currentDayShown = week.get(position);
			
			
			
		}
		
		if(v.getId() == buttonPreviousDay.getId() )
		{
			int position = (week.indexOf(currentDayShown) - 1 + week.size() ) % week.size() ;
			
			textViewCurrentDay.setText(week.get(position).toString());
			
			currentDayShown = week.get(position);
			
		}
		
		if(v.getId() == buttonAddTask.getId() )
		{
			addNewTaskEnt();
		}
		
		
		
	}

	/**
	 * adds a start time, an end time, a location edit text for a task
	 */
	public void addNewTaskEnt() {
		
		TimePicker startTime, endTime;
		Button eraseTask;

		
		
		RelativeLayout.LayoutParams params_startTime = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params_endTime = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		RelativeLayout.LayoutParams params_removeTask = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                           RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		
		
		eraseTask = new Button(this);
		params_removeTask.addRule(RelativeLayout.BELOW, numberOfView  );  
		eraseTask.setId(++numberOfView);
		params_removeTask.setMargins(0, 50, 0, 0); 
		eraseTask.setLayoutParams(params_removeTask);
		
		
		startTime = new TimePicker(this);
		params_startTime.addRule(RelativeLayout.RIGHT_OF, numberOfView  );
	    params_startTime.addRule(RelativeLayout.BELOW, numberOfView -2 ); 
		params_startTime.setMargins(50, 50, 0, 0); 
	    startTime.setId(++numberOfView);
		startTime.setLayoutParams(params_startTime);
		
		
		endTime = new TimePicker(this);
		params_endTime.addRule(RelativeLayout.RIGHT_OF, numberOfView  );
		params_endTime.addRule(RelativeLayout.BELOW, numberOfView -3  );
		params_endTime.setMargins(50, 50, 0, 0); 
		endTime.setId(++numberOfView);
		endTime.setLayoutParams(params_endTime); 
		
		
		layout.addView(eraseTask);
		layout.addView(startTime);
		layout.addView(endTime);
		
		
		
		
		
		
	}

}
