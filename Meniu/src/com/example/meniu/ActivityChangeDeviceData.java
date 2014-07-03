package com.example.meniu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.DeviceContext;
import ContextElements.PeopleContext;
import DatabaseOperation.AlterateTask;
import DatabaseOperation.DeviceData;
import DatabaseOperation.Tasks;
import DeviceData.Device;
import Task.Task;
import Task.TaskState;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityChangeDeviceData extends Activity implements OnClickListener {
	
	
	RelativeLayout layout;
	
	/**
	 * associates an id for each view added
	 */
	int numberOfView;
	
	/**
	 * for each task there are three buttons of save, saveAsmine and erase
	 */
	HashMap<Integer,Integer> idView_idDevice;
	
	
	/**
	 * contains the mac addresses for each device id;
	 */
	HashMap<Integer,String> idDevice_Mac;
	
	
	/**
	 * contains the ids of the devices to be removed
	 * when the activity closes
	 */
	ArrayList<String> idDevicesToErase;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_change_device_data);
		
		layout = (RelativeLayout) findViewById(R.id.RelativeLayoutShowDeviceData);
		idView_idDevice = new HashMap<Integer, Integer>();
		idDevice_Mac = new HashMap<Integer,String>();
		idDevicesToErase = new ArrayList<String>();
		
		showDevices();
		
		

	}
	
	/**
	 * called to show the devices, when the activity starts or when a device is deleted
	 */
	public void showDevices()
	{
		layout.removeAllViews();
		numberOfView = 0;
		List<Device> devices = MainActivity.getDatabase().getAllDevices();
		idView_idDevice.clear();
		idDevice_Mac.clear();
		
		for(Device device :devices){
			addDeviceToInterface(device);
			idDevice_Mac.put(device.getIdDevice(), device.getMacAddress());
			
			System.out.println("Date hashmap : " +  device.getIdDevice() + "  ---- " + device.getMacAddress());
			
		}
	}
	
	
	
	/**
	 * adding views to screen
	 * @param task : the task which can be executed
	 */
	private void addDeviceToInterface(Device device) 
	{
		  
	
		   TextView nameDevice,nameOwner,  macAddress;
		   TextView nameDeviceValue ,   macAddressValue;
		   EditText nameOwnerValue;
		   Button butonErase, butonSaveAsMyDevice, butonSave;
		   
		   
		   View line;		 
		   
		   
		   System.out.println("NUme " + device.getNameDevice());
		   
		   RelativeLayout.LayoutParams params_nameDevice = 
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   

		   params_nameDevice.setMargins(0, 20, 0, 0); 
		   
		   RelativeLayout.LayoutParams params_nameDevice_value = 
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		   
		//   params_nameDevice_value.setMargins(20, 0, 0, 0);
		   params_nameDevice_value.setMargins(20, 20, 0, 0);
		   
		   RelativeLayout.LayoutParams params_macAddress=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                           				   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		   
		   params_macAddress.setMargins(0, 10, 0, 0); 
		   
		   
		   RelativeLayout.LayoutParams params_macAddress_value=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                           				   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		//   params_macAddress_value.setMargins(20, 0, 0, 0);
		   params_macAddress_value.setMargins(20, 10, 0, 0);
		   
		   
		   RelativeLayout.LayoutParams params_nameOwner=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                           				   RelativeLayout.LayoutParams.WRAP_CONTENT);
		    
		   params_nameOwner.setMargins(0, 10, 0, 0); 
		   
		   RelativeLayout.LayoutParams params_nameOwner_value=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
                           				   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		//   params_nameOwner_value.setMargins(20, 0, 0, 0);
		   params_nameOwner_value.setMargins(30, 0, 30, 0);
		   
		   
		   
		   RelativeLayout.LayoutParams   params_erase=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                           				   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		   params_erase.setMargins(0, 20, 0, 0);
		   
		   
		   RelativeLayout.LayoutParams   params_save=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                           				   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		   
		   params_save.setMargins(0, 20, 0, 0);
		   
		   RelativeLayout.LayoutParams   params_saveAsMyDevice=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                           				   RelativeLayout.LayoutParams.WRAP_CONTENT);
		   
		   
		   params_saveAsMyDevice.setMargins(0, 20, 0, 0);
		   
		   RelativeLayout.LayoutParams   params_New_Line=
		   new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2);
		   
		   
		   params_New_Line.setMargins(30, 50, 30, 50);
		   
		   

			nameDevice			= new TextView(this);
			nameOwner			= new TextView(this);
			macAddress			= new TextView(this);
			nameDeviceValue  	= new TextView(this);
			macAddressValue 	= new TextView(this);
			nameOwnerValue		= new EditText(this);
			
			butonErase = new Button(this);
			butonSave	= new Button(this);
			butonSaveAsMyDevice = new Button(this);

			line = new View(this);
			

			nameDevice.setText(getResources().getString(R.string.DeviceName));
			nameDevice.setTextSize(20);
			nameDevice.setId( ++ numberOfView);
			nameDevice.setTextColor(Color.BLUE);
		//	params_nameDevice.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_nameDevice.addRule(RelativeLayout.BELOW, numberOfView - 1);
		    nameDevice.setLayoutParams(params_nameDevice);
			
			
			nameDeviceValue.setText(device.getNameDevice());
			nameDeviceValue.setTextSize(20);
			nameDeviceValue.setId( ++ numberOfView);
		//	params_nameDevice_value.addRule(RelativeLayout.ALIGN_BASELINE, numberOfView - 1);
			params_nameDevice_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_nameDevice_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
		    nameDeviceValue.setLayoutParams(params_nameDevice_value);
			
		    macAddress.setText(getResources().getString(R.string.MacAdress));
		    macAddress.setTextSize(20);
		    macAddress.setId( ++ numberOfView);
		    macAddress.setTextColor(Color.BLUE);
	//	    params_macAddress.addRule(RelativeLayout.BELOW, numberOfView - 2);
			params_macAddress.addRule(RelativeLayout.BELOW, numberOfView - 1);
			macAddress.setLayoutParams(params_macAddress);
			
			
			macAddressValue.setText(device.getMacAddress());
			macAddressValue.setTextSize(20);
			macAddressValue.setId( ++ numberOfView);
		//	params_macAddress_value.addRule(RelativeLayout.ALIGN_BASELINE, numberOfView - 1);
			params_macAddress_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_macAddress_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			macAddressValue.setLayoutParams(params_macAddress_value);
			
			
		    nameOwner.setText(getResources().getString(R.string.textViewOwner));
		    nameOwner.setTextSize(20);
		    nameOwner.setId( ++ numberOfView);
		    nameOwner.setTextColor(Color.BLUE);
	//	    params_nameOwner.addRule(RelativeLayout.BELOW, numberOfView - 2);
		    params_nameOwner.addRule(RelativeLayout.BELOW, numberOfView - 1);
		    nameOwner.setLayoutParams(params_nameOwner);	
			
		    nameOwnerValue.setText(device.getOwnerDevice());
		    nameOwnerValue.setTextSize(20);
		    nameOwnerValue.setId( ++ numberOfView);
		//	params_macAddress_value.addRule(RelativeLayout.ALIGN_BASELINE, numberOfView - 1);
			params_nameOwner_value.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
			params_nameOwner_value.addRule(RelativeLayout.BELOW, numberOfView - 2);
			nameOwnerValue.setLayoutParams(params_nameOwner_value);
			
		    butonSave.setText(getResources().getString(R.string.buttonSaveNewOwnerDevice));
		    butonSave.setTextSize(20);
		    butonSave.setId( ++ numberOfView);
	//	    params_nameOwner.addRule(RelativeLayout.BELOW, numberOfView - 2);
		    params_save.addRule(RelativeLayout.BELOW, numberOfView - 1);
		    butonSave.setLayoutParams(params_save);
		    butonSave.setOnClickListener(this);
		    idView_idDevice.put(numberOfView, device.getIdDevice());
			
		    
		    butonSaveAsMyDevice.setText(getResources().getString(R.string.buttonSaveMyDevice));
		    butonSaveAsMyDevice.setTextSize(20);
		    butonSaveAsMyDevice.setId( ++ numberOfView);
	//		params_saveAsMyDevice.addRule(RelativeLayout.ALIGN_BASELINE, numberOfView - 1);
			params_saveAsMyDevice.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
		    params_saveAsMyDevice.addRule(RelativeLayout.BELOW, numberOfView - 2); 
		    butonSaveAsMyDevice.setLayoutParams(params_saveAsMyDevice);
		    butonSaveAsMyDevice.setOnClickListener(this);
		    
		    
		    butonErase.setText(getResources().getString(R.string.buttonEraseDevice));
		    butonErase.setTextSize(20);
		    butonErase.setId( ++ numberOfView);
	//		params_saveAsMyDevice.addRule(RelativeLayout.ALIGN_BASELINE, numberOfView - 1);
			params_erase.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
		    params_erase.addRule(RelativeLayout.BELOW, numberOfView - 3); 
		    butonErase.setLayoutParams(params_erase);
		    butonErase.setOnClickListener(this);
		    
		    
		    line.setId(++ numberOfView);
			params_New_Line.addRule(RelativeLayout.BELOW, numberOfView - 1);
			line.setBackgroundColor(Color.BLUE);
			line.setLayoutParams(params_New_Line);

		   
		    layout.addView(nameDevice);
		    layout.addView(nameDeviceValue);
		    layout.addView(macAddress);
		    layout.addView(macAddressValue);
		    layout.addView(nameOwner);
		    layout.addView(nameOwnerValue);
			layout.addView(butonSave);
			layout.addView(butonSaveAsMyDevice);
			layout.addView(butonErase);
			layout.addView(line);
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_change_device_data, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		Button b = (Button) v;
		
		if(b.getText().toString().equals(getResources().getString(R.string.buttonEraseDevice)))
			eraseDevice(b);
		if(b.getText().toString().equals(getResources().getString(R.string.buttonSaveMyDevice)))
			saveAsMyDevice(b);
		if(b.getText().toString().equals(getResources().getString(R.string.buttonSaveNewOwnerDevice)))
			saveNewOwner(b);
		
		
	}

	private void saveNewOwner(Button b)
	{

		int idDevice;
		String name;
		EditText nameOwner;
		
		idDevice = idView_idDevice.get(b.getId());
		nameOwner = (EditText) layout.getChildAt(b.getId() - 2);

		
		System.out.println("idDevice " + idDevice);
		MainActivity.getDatabase().updateDevice(idDevice, DeviceData.KEY_OWNER, nameOwner.getText().toString());
		
		
	}

	private void saveAsMyDevice(Button b) 
	{
		int idDevice;
		idDevice = idView_idDevice.get(b.getId() - 1);
		
		MainActivity.getDatabase().updateDevice(idDevice, DeviceData.KEY_OWNER, Constants.myDevice);
		showDevices();
	}

	/**
	 * @param b : the erase button pushed, associated to the device to be removed
	 */
	private void eraseDevice(Button b) 
	{
		int idDevice;
		idDevice = idView_idDevice.get(b.getId() - 2);
		
		
		
		
		idDevicesToErase.add(idDevice_Mac.get(idDevice)); 
		
		MainActivity.getDatabase().deleteDevice(idDevice);
		showDevices();
		

	}
	
	
	public void onStop()
	{
		super.onStop();
		
		ArrayList<TaskState> states = new ArrayList<TaskState>();
		states.add(TaskState.AMONG_TO_DO_LIST);
		List<Task> tasks = MainActivity.getDatabase().getFilteredTasks(states);
		ArrayList<String> devices;
		
		System.out.println("A INTRAT AICI");

		for(Task task : tasks)
		{
			DeviceContext devicesC = (DeviceContext) task.getInternContext().
			getContextElementsCollection().get(ContextElementType.DEVICES_ELEMENT);
			
			
			PeopleContext peopleC = (PeopleContext) task.getInternContext().
			getContextElementsCollection().get(ContextElementType.PEOPLE_ELEMENT);
			
			
			devices = devicesC.getDeviceTask();
			devices.addAll(peopleC.getPeopleTask());
			
			
			System.out.println("MACURILE SUNT " + devices);
			System.out.println("Dispozitivele de sters sunt" + idDevicesToErase);
			
			
			for(String macDevice : idDevicesToErase)
				devices.remove(macDevice);
			
			
			String devicesString="";
			for(String device : devices)
				devicesString += "," + device;
			
			if(devicesString.length() > 0)
			{
				devicesString = devicesString.substring(1);
				MainActivity.getDatabase().updateTask(task.getID(), Tasks.KEY_Device , devicesString);
			}
			
		}
		
	//	MainActivity.getDatabase().updateTask(1, Tasks.KEY_Device, "");
	//	MainActivity.getDatabase().updateTask(2, Tasks.KEY_Device, "");
		
		
	}
	
	
	

}
