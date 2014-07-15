package com.example.meniu;

import java.util.ArrayList;
import java.util.HashMap;

import DatabaseOperation.FixedTaskInformation;
import DatabaseOperation.fixedTasks;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

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
	
	
	/**
	 * contains list of id's for each task in the activity
	 * as key : the id of the view
	 * as value : the position of the view or -1 in case the task is newly added
	 */
	HashMap<Integer,Integer>  tasksId;
	

	
	
	

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

		
		
		
		tasksId = new HashMap<Integer,Integer>();
		
		showFixedTasksForADay();
		
		
		
		
	}

	/**
	 * showing the established tasks for the current day shown
	 */
	public void showFixedTasksForADay() {
		
		ArrayList<FixedTaskInformation> tasksFromDataBase;
		
	
		if(layout.getChildCount() > 4 )
			layout.removeViews(4, layout.getChildCount() - 4);
		tasksId.clear();
		
		numberOfView = idAddButton;
		tasksFromDataBase = MainActivity.getDatabase().getFixedTasks(currentDayShown.toString());
		
		
		for(FixedTaskInformation task : tasksFromDataBase)
			addNewTaskEnt(task.getStartHour(), task.getStartMinute(),
			task.getEndHour(), task.getEndMinute(), task.getIdTask());
		

		
	}
	
	public void onPause()
	{
		super.onPause();
		addTasksToDataBase();
	}

	

	

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_fixed_task, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		Button buton = (Button) v;
		
		if(v.getId() == buttonNextDay.getId() )
		{
			addTasksToDataBase();
			
			int position = (week.indexOf(currentDayShown) + 1 ) % week.size() ;
			
			textViewCurrentDay.setText(week.get(position).toString());
			
			currentDayShown = week.get(position);
			
			showFixedTasksForADay() ;
			
			
			
			
		}
		
		if(v.getId() == buttonPreviousDay.getId() )
		{
			
			addTasksToDataBase();
			
			int position = (week.indexOf(currentDayShown) - 1 + week.size() ) % week.size() ;
			
			textViewCurrentDay.setText(week.get(position).toString());
			
			currentDayShown = week.get(position);
			
			showFixedTasksForADay();
			
			
			
		}
		
		if(v.getId() == buttonAddTask.getId() )
		{
			addNewTaskEnt();
		}
		
		if(buton.getText().toString().equals("ERASE TASK"))
		{
			
			ArrayList<FixedTaskInformation> tasks = new ArrayList<FixedTaskInformation>();
			int position = v.getId()  - this.idAddButton;
			int nextPosition = position + 3 + 1 + 3;
			numberOfView = v.getId() - 1;
			
			MainActivity.getDatabase().deleteFixedTask(tasksId.get(v.getId() ));
			tasksId.remove(v.getId() );
			
			System.out.println(tasksId.toString());
			
			
			while(nextPosition < layout.getChildCount())
			{
				TimePicker picker = (TimePicker) layout.getChildAt(nextPosition);
				TimePicker picker2 = (TimePicker) layout.getChildAt(nextPosition + 1);
				
				
				tasks.add(new FixedTaskInformation(  tasksId.remove(picker.getId() -1),   picker.getCurrentHour(), 
				picker.getCurrentMinute(), picker2.getCurrentHour(), picker2.getCurrentMinute() , "" )); 
				
				
				
				nextPosition = nextPosition + 3;
				
				
				
			}

			
			if(position + 3  < layout.getChildCount())
			{

				
				layout.removeViews(position + 3, layout.getChildCount() - 3 - position);
				
				
				for(FixedTaskInformation task : tasks)
					addNewTaskEnt(task.getStartHour(), task.getStartMinute(),
					task.getEndHour(), task.getEndMinute(), task.getIdTask());
				
			}
			
			
			
			
		
		}
		
		
		
		
	}

	/**
	 * inserts the tasks into the database;
	 */
	public void addTasksToDataBase() {

		
		for(Integer taskId : tasksId.keySet())
		{
			if(tasksId.get(taskId) == -1)
			{
				int position = taskId - this.idAddButton;
				position = position + 3;
				
				TimePicker picker1 = (TimePicker) layout.getChildAt(position + 1);
				TimePicker picker2 = (TimePicker) layout.getChildAt(position + 2);
				
				
				
				MainActivity.getDatabase().addFixedTask(currentDayShown.toString(),
				Integer.toString(picker1.getCurrentHour()),
				Integer.toString(picker1.getCurrentMinute()), 
				Integer.toString(picker2.getCurrentHour()),
				Integer.toString(picker2.getCurrentMinute()), "");
			}
			else
			{
				ArrayList<String> attribute = new ArrayList<String>();
				ArrayList<String> stringKeyValue = new ArrayList<String>();
				int position = taskId - this.idAddButton;
				position = position + 3;
				
				TimePicker picker1 = (TimePicker) layout.getChildAt(position + 1);
				TimePicker picker2 = (TimePicker) layout.getChildAt(position + 2);
				
				
				attribute.add(fixedTasks.KEY_Start_Hour);
				attribute.add(fixedTasks.KEY_Start_Minute);
				attribute.add(fixedTasks.KEY_End_Hour);
				attribute.add(fixedTasks.KEY_End_Minute);
				
				
				stringKeyValue.add(Integer.toString(picker1.getCurrentHour()));
				stringKeyValue.add(Integer.toString(picker1.getCurrentMinute()));
				
				stringKeyValue.add(Integer.toString(picker2.getCurrentHour()));
				stringKeyValue.add(Integer.toString(picker2.getCurrentMinute()));
				
				
				MainActivity.getDatabase().updateFixedTask(tasksId.get(taskId), attribute, stringKeyValue);
				
			}
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
		eraseTask.setText(R.string.ERASE); 
		params_removeTask.addRule(RelativeLayout.BELOW, numberOfView  );  
		eraseTask.setId(++numberOfView);
		params_removeTask.setMargins(0, 50, 0, 0); 
		eraseTask.setLayoutParams(params_removeTask);
		eraseTask.setOnClickListener(this); 
		
		tasksId.put(numberOfView, -1);
		
		
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
		
		if(layout.getChildCount() > 4)
		{
			
			TimePicker lastPicker = (TimePicker) layout.getChildAt(layout.getChildCount() - 1);
			
			startTime.setCurrentHour(lastPicker.getCurrentHour());
			startTime.setCurrentMinute(lastPicker.getCurrentMinute());
			
			endTime.setCurrentHour(lastPicker.getCurrentHour());
			endTime.setCurrentMinute(lastPicker.getCurrentMinute());
			
		}
		if(layout.getChildCount() == 4)
		{
			startTime.setCurrentHour(Constants.startHour);
			startTime.setCurrentMinute(Constants.startMinute);
			
			endTime.setCurrentHour(Constants.startHour);
			endTime.setCurrentMinute(Constants.startMinute);
		}
	
		
		
		layout.addView(eraseTask);
		layout.addView(startTime);
		layout.addView(endTime);

		

	}
	
	
	public void addNewTaskEnt(int startHour, int startMinute, int endHour, int endMinute, int idTask)
	{		
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
		eraseTask.setText(R.string.ERASE); 
		params_removeTask.addRule(RelativeLayout.BELOW, numberOfView  );  
		eraseTask.setId(++numberOfView);
		params_removeTask.setMargins(0, 50, 0, 0); 
		eraseTask.setLayoutParams(params_removeTask);
		eraseTask.setOnClickListener(this); 
		
		tasksId.put(numberOfView, idTask);
		
		
		startTime = new TimePicker(this);
		params_startTime.addRule(RelativeLayout.RIGHT_OF, numberOfView  );
	    params_startTime.addRule(RelativeLayout.BELOW, numberOfView -2 ); 
		params_startTime.setMargins(50, 50, 0, 0); 
	    startTime.setId(++numberOfView);
	    startTime.setCurrentHour(startHour);
	    startTime.setCurrentMinute(startMinute);
		startTime.setLayoutParams(params_startTime);
		
		
		endTime = new TimePicker(this);
		params_endTime.addRule(RelativeLayout.RIGHT_OF, numberOfView  );
		params_endTime.addRule(RelativeLayout.BELOW, numberOfView -3  );
		params_endTime.setMargins(50, 50, 0, 0); 
		endTime.setId(++numberOfView);
	    endTime.setCurrentHour(endHour);
	    endTime.setCurrentMinute(endMinute);
		endTime.setLayoutParams(params_endTime); 
		
		
		layout.addView(eraseTask);
		layout.addView(startTime);
		layout.addView(endTime);

	}


}




