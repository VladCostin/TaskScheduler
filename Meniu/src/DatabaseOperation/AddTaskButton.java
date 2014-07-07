package DatabaseOperation;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import com.example.meniu.AddTask;
import com.example.meniu.Constants;
import com.example.meniu.Core;
import com.example.meniu.MainActivity;
import com.example.meniu.ParametersToModify;
import com.example.meniu.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * class which process a task and its properties in the database
 * @author ${Vlad Herescu}
 *
 */
public class AddTaskButton implements OnClickListener {
	
	/**
	 * instance of class which has the data introduced by the user
	 */
	AddTask task;
	
	
	/**
	 * the message to be shown after trying to insert data
	 */
	String messageToShow;
	
	
	/**
	 * @param task : 
	 */
	public AddTaskButton(AddTask task) {
		this.task = task;
	}
	

	@Override
	public void onClick(View v) {
		
		 if(checkDatas() == false ){
			 
			 task.getMessageInsert().setText( messageToShow );
			 task.getMessageInsert().setFocusableInTouchMode(true);
			 task.getMessageInsert().requestFocus();
			 return;
		 }
		 if(task.isBooleanGetFromTaskToModify())
				 updateTask();
		 else
			 saveTask();
		 
	
		

	}
	
	public void updateTask()
	{
		 String device="";
		
		 ArrayList<Entry<String,String>> myDevicesData = new ArrayList<Entry<String,String>>
		 (task.getMy_devices_name_Mac().entrySet());
		 
		 ArrayList<Entry<String,String>> peopleData = new ArrayList<Entry<String,String>>
		 (task.getPeople_devices_name_Mac().entrySet());
		 
		 System.out.println("PeopleCheckedItems : " + task.getIntegerPeopleCheckeditems());
		 System.out.println("DevicesCheckedItems :" + task.getIntegerDevicesCheckedItems());
		 
		 
		 for(Integer position : task.getIntegerDevicesCheckedItems())
		 {
			 Entry<String,String> deviceData = myDevicesData.get(position);
			 device = device +  "," +  deviceData.getKey() ;
		 }
		 
		 for(Integer position : task.getIntegerPeopleCheckeditems())
		 {
			 Entry<String,String> deviceData = peopleData.get(position);
			 device = device + "," + deviceData.getKey() ;
		 }
		 if(device.length()> 0 )
			 device = device.substring(1);
		 
		 System.out.println("DevicesMac :" + device);
		
		
		
		ArrayList<String> attributes = new ArrayList<String>();
		ArrayList<String> values	 = new ArrayList<String>();
		
		
		attributes.add(Tasks.KEY_Location);
		attributes.add(Tasks.KEY_Device);
		attributes.add(Tasks.KEY_Date);
		attributes.add(Tasks.KEY_Title);
		attributes.add(Tasks.KEY_Priority);
		
		values.add(task.getLocation());
		values.add(device);
		values.add(task.getDate().getText().toString());
		values.add(task.getTitleTask().getText().toString());
		values.add(task.getPriority().getSelectedItem().toString());
		
		
		 MainActivity.getDatabase().updateTask(task.getIdTask(), attributes, values);
		 
		 task.getMessageInsert().setText( task.getResources().getString(R.string.UPDATEMessage) );
		 task.getMessageInsert().setFocusableInTouchMode(true);
		 task.getMessageInsert().requestFocus(); 
		 ThreadShowMessage myThread = new ThreadShowMessage(task);
		 myThread.start();
	}
	
	public void saveTask()
	{
		
		// Set<Entry<String,String>> myDevicesData = task.getMy_devices_name_Mac().entrySet(); 
		// Set<Entry<String,String>> peopleData	 = task.getPeople_devices_name_Mac().entrySet();
		 
		 ArrayList<Entry<String,String>> myDevicesData = new ArrayList<Entry<String,String>>
		 (task.getMy_devices_name_Mac().entrySet());
		 
		 ArrayList<Entry<String,String>> peopleData = new ArrayList<Entry<String,String>>
		 (task.getPeople_devices_name_Mac().entrySet());
		
		 task.getMessageInsert().setText( messageToShow );
		 task.getMessageInsert().setFocusableInTouchMode(true);
		 task.getMessageInsert().requestFocus(); 
		
		
		 String date= task.getDate().getText().toString();
		 String location = task.getLocation();
		 String priority = task.getPriority().getSelectedItem().toString();
		 String name = task.getTitleTask().getText().toString();
		 String device="";
		 String duration = task.getDuration().getSelectedItem().toString();
		 
		 
		 System.out.println("dispozitivele sunt saveTask + " + task.getIntegerDevicesCheckedItems());
		 
		 for(Integer position : task.getIntegerDevicesCheckedItems())
		 {
			 Entry<String,String> deviceData = myDevicesData.get(position);
			 device = device +  "," +  deviceData.getKey() ;
		 }
		 
		 for(Integer position : task.getIntegerPeopleCheckeditems())
		 {
			 Entry<String,String> deviceData = peopleData.get(position);
			 device = device + "," + deviceData.getKey() ;
		 }
		 if(device.length()> 0 )
			 device = device.substring(1);
		 
		 System.out.println("INTRODUC DISPOZITIVELE " + device);
		 

		 Log.w("information",date + " " + location + " " + priority + " " + name +  " " + device);
		
		 MainActivity.getDatabase().addTask
		 (name,priority,location,date, device, 
		 Core.getDurationMinutes().get(duration).toString(), "");    
		 
		 ThreadShowMessage myThread = new ThreadShowMessage(task);
		 myThread.start();
		 UpdateGUIData();
	}
	
	
	public void UpdateGUIData()
	{
		task.setBooleanHasDetectedLocation(false);
		task.getAutoTitle().setText(""); 
		task.getAutoLocationSearch().setText("");
		task.getPeople().setText(Constants.noChoose);
		task.getDevices().setText(Constants.noChoose);
		task.getDate().setText(Constants.noChoose);
		
		task.getIntegerDevicesCheckedItems().clear();
		task.getIntegerPeopleCheckeditems().clear();
		
		task.setOldParamatersTask(new ParametersToModify());
		
		task.getOldParamatersTask().devices = new String[0];
		task.getOldParamatersTask().people = new String[0];
		
		task.setPEOPLE_DIALOG_ID(task.getPEOPLE_DIALOG_ID()+ 2);
		task.setDEVICES_DIALOG_ID(task.getDEVICES_DIALOG_ID() + 2);

		
		
		task.onCreateDialog(task.getDEVICES_DIALOG_ID());
		task.onCreateDialog(task.getPEOPLE_DIALOG_ID());

		
		
		task.getPriority().setSelection(0);
		task.getDuration().setSelection(0);
		
		task.getMap().clear();    	
		task.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(task.getPositionCurrent(), 15));
    	task.getMap().addMarker(new MarkerOptions().position(task.getPositionCurrent()).
    	icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
		
	}


	/**
	 * @return : true if the data have been inserted
	 */
	public boolean checkDatas() {
		
		if(task.getLocation() == null ||
		   task.getTitleTask().getText().toString().compareTo("") == 0 )
		{
			messageToShow = task.getResources().getString(R.string.Error) ;
			
			if(task.getLocation() == null)
				messageToShow += "  LOCATION ";

			if(task.getTitleTask().getText().toString().compareTo("") == 0 )
				messageToShow += " TITLE ";
			
			return false;
			
			
		}

	
		messageToShow = task.getResources().getString(R.string.Succes);
		
		return true;
	}

}



class ThreadShowMessage extends Thread
{
	/**
	 * instance of class which has the data introduced by the user
	 */
	AddTask task;
	
	
	public ThreadShowMessage(AddTask task) {
			this.task = task;

	}
	
	
	public void run()
	{
		try {
			Thread.sleep(Constants.MessageAddTaskTimer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		task.runOnUiThread( new Runnable()
		{

			@Override
			public void run() {
				 task.getMessageInsert().setText("");
				
			}
			
		});
		
		
		
	}
	
	
}

