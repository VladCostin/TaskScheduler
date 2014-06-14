package com.example.meniu;

import java.util.ArrayList;
import java.util.List;

import DatabaseOperation.FixedTaskInformation;
import Salesman.PopulationEvolution;
import Task.Task;
import Task.TaskState;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActivityScheduler extends Activity {
	
	
	ArrayList<FixedTaskInformation> fixedTasks;
	List<Task> shiftingTasks;
	PopulationEvolution population;
	

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
		population.startEvolution();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scheduler, menu);
		return true;
	}

}
