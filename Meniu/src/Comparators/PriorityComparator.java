package Comparators;

import java.util.Comparator;

import com.example.meniu.Core;

import Task.Task;

public class PriorityComparator implements Comparator{
	
	

	@Override
	public int compare(Object arg0, Object arg1) {
		
		Task task1 = (Task) arg0;
		Task task2 = (Task) arg1;
		
		int value_task1 = Core.getPrioritiesValues().get(task1.getPriority());
		int value_task2 = Core.getPrioritiesValues().get(task2.getPriority());
		
		return value_task2 - value_task1;
	}

}
