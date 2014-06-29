package ContextElements;

import java.util.ArrayList;

import com.example.meniu.Constants;

public class DeviceContext extends ContextElement{

	/**
	 * contains the peope needed to execute the task
	 */
	private ArrayList<String> deviceTask;

	
	/**
	 * @param people_Task : the people names taken frm database
	 */
	public DeviceContext(ArrayList<String> device_Task)
	{
		deviceTask = device_Task;
		
	//	System.out.println("DEVICETASK ESTE" + deviceTask);
	}

	public ArrayList<String> getDeviceTask() {
		return deviceTask;
	}

	public void setDeviceTask(ArrayList<String> peopleTask) {
		this.deviceTask = peopleTask;
	}

	public CharSequence getDeviceTaskString() {
		
		String deviceString="";
		int i;
		
		if(deviceTask.size() == 0)
			return Constants.noChoose;
		
		for(i = 0; i < deviceTask.size() -1 ; i++)
			deviceString += deviceTask.get(i) + " ,";
		deviceString += deviceTask.get(i);
		
	//	System.out.println("deviceString este " + deviceString);
		
		return deviceString;
	}
	
}
