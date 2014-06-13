package com.example.meniu;

import java.util.ArrayList;

public class ParametersToModify
{
	private int id;
	
	int year;
	
	int month;
	
	int day;
	
	String devices[];
	
	
	boolean oldDevices[];
	
	public ParametersToModify(int id) {
		this.id = id;
	}
	
	
	public void changeDeadline(String deadline )
	{
		String data[] = deadline.split("-");
		
		year = Integer.parseInt(data[0]);
		
		day = Integer.parseInt(data[2]);
		
		month = Integer.parseInt(data[1]);
		
	}
	
	public void changeDevices(String devices[])
	{
		this.devices = devices;
	}
	
	public boolean[] detectOldDevicesSelected(ArrayList<String> myDevicesAll, ArrayList<Integer> itemsId)
	{
		//
		
		if(devices == null)
			return null;
		
		int i,j;
		boolean isOld;
		oldDevices = new boolean[myDevicesAll.size()];
		
		for(i= 0; i < myDevicesAll.size(); i++)
		{
			isOld = false;
			for(j = 0; j < devices.length; j++)
			{
				if(devices[j].equals(myDevicesAll.get(i)))
				{
					isOld = true;
					break;
				}
			}
			
			oldDevices[i] = isOld;
			itemsId.add(i);
		}
		devices = null;
		
		return oldDevices;
		
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
}
