package com.example.meniu;

import java.util.ArrayList;

public class ParametersToModify
{
	private int id;
	
	int year;
	
	int month;
	
	int day;
	
	/**
	 * the old devices received as parameter from the intent that started this activity
	 */
	String devices[];
	
	
	/**
	 * the old people received as a parameter from the intent that started this activity
	 */
	String people[];
	
	
	/**
	 * for every item in the list of devices, true if it was in the string received 
	 * from modify task
	 */
	boolean oldDevices[];
	
	/**
	 * for every item in the list of people, true if it was in the string received 
	 * from modify task
	 */
	boolean oldPeople[];
	
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
	
	public void changePeople(String people[])
	{
		this.people = people;
	}
	
	public boolean[] detectOldDevicesSelected(ArrayList<String> myDevicesAll, ArrayList<Integer> itemsId)
	{
		int i,j;
		boolean isOld;
		
		if(devices == null)
			return null;
		
		
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
			if(isOld == true)
				itemsId.add(i);
		}
		devices = null;
		
		return oldDevices;
		
		
	}
	
	
	public boolean[] detectOldPeopleSelected(ArrayList<String> peopleDatabase, ArrayList<Integer> itemsId)
	{
		int i,j;
		boolean isOld;
		
		if(people == null)
			return null;
		
		
		
		oldPeople = new boolean[peopleDatabase.size()];
		
		for(i= 0; i < peopleDatabase.size(); i++)
		{
			isOld = false;
			for(j = 0; j < people.length; j++)
			{
				if(people[j].equals(peopleDatabase.get(i)))
				{
					isOld = true;
					break;
				}
			}
			
			
			oldPeople[i] = isOld;
			if(isOld == true)
				itemsId.add(i);
		}
		people = null;
		
		return oldPeople;
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
}
