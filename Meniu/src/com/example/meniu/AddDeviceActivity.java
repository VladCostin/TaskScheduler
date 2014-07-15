package com.example.meniu;

import java.util.HashMap;
import java.util.List;



import DatabaseOperation.AddDeviceButton;
import DeviceData.Device;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * discovers the devices, then the user can store devices detected 
 * in the database, associating an user for them
 * @author ${Vlad Herescu}
 *
 */
public class AddDeviceActivity extends Activity {


	/**
	 * receives the data about the devices nearby
	 */
	private MyBroadCastRecv mReceiver;
	
	/**
	 * the view where the data will be shown
	 */
	 private RelativeLayout layout;
	
	/**
	 * contains for each mac adress a name for the device to be recognized
	 */
	private HashMap<String,String> deviceInfo;
	
	
	/**
	 * for each task it is associated a numberOfView
	 */
	int numberOfView;
	
	/**
	 * listener for add Device information button
	 * inserts the data in the database
	 */
	AddDeviceButton	addDevice;
	
	
	/**
	 * adaptorul
	 */
	BluetoothAdapter mBluetoothAdapter;
	
	/**
	 * devices taken from database
	 */
	private List<Device> devices;
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_device);
		
		deviceInfo = new HashMap<String,String>();
		devices = MainActivity.getDatabase().getAllDevices();
		addDevice  = new AddDeviceButton(this);

		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    System.out.println("mBluetooth is null");
		}
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, 11);
		  
		}
		
		
		
		layout = (RelativeLayout) findViewById(R.id.layoutAfisare);
		mReceiver = new MyBroadCastRecv(this); 
		
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		mBluetoothAdapter.startDiscovery();
		
		

		 addInfoMethod();

		
	}
	
	

	/**
	 * for each device it is shown a set of GUI elements
	 */
	public void addInfoMethod() {
		

    	numberOfView = 0;
    	layout.removeAllViews();

    	if(getDeviceInfo().size() == 0)
    	{
    		addMessageNoDevice();
    		return;
    	}
    	
    	
    	for(String macAddress : getDeviceInfo().keySet())
    			addDevice(macAddress,  getDeviceInfo().get(macAddress));

		
	}
	
	/**
	 * message shown until a device is detected
	 */
	public void addMessageNoDevice() {
		
		TextView showNoDeviceMessage;
		RelativeLayout.LayoutParams params_message = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		
		showNoDeviceMessage = new TextView(this);
		showNoDeviceMessage.setText(R.string.noDevice);
		showNoDeviceMessage.setPadding(20, 30, 0, 0);
		showNoDeviceMessage.setTextSize(20);
		showNoDeviceMessage.setTextColor(Color.BLUE);
		showNoDeviceMessage.setId(++ numberOfView);  // devine 1

		showNoDeviceMessage.setLayoutParams(params_message);
		
		layout.addView(showNoDeviceMessage);
		
		
	}
	


	/**shows the GUI elements for a task 
	 * @param macAdress : the MAC of the detected device
	 * @param nameDevice : the name received of the detected device
	 */
	public void addDevice(String macAdress, String nameDevice) {
		
		TextView showDeviceName, showMacAdress, addOwnerDevice;
		TextView DeviceName, MacAdress;
		EditText writeOwner; 
		Button addDeviceOwnerButton;
		Button addMyDeviceButton;
		View line;
		
		RelativeLayout.LayoutParams params_name = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params_Mac = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		RelativeLayout.LayoutParams params_Owner = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		
		RelativeLayout.LayoutParams nameget = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams macGet = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		RelativeLayout.LayoutParams ownerGet = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
						                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		ownerGet.setMargins(50, 0, 0, 0); 
		
		RelativeLayout.LayoutParams params_buton = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		params_buton.setMargins(0, 10, 0, 0);
		
		RelativeLayout.LayoutParams params_buton_mine = 
		new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		params_buton_mine.setMargins(20, 10, 0, 0);
		
	    RelativeLayout.LayoutParams params_line = 
	    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2);
	    
	    params_line.setMargins(0, 10, 0, 0); 
		
		
		showDeviceName = new TextView(this);
		showMacAdress = new TextView(this);
		addOwnerDevice = new TextView(this);
		MacAdress = new TextView(this);
		DeviceName = new TextView(this);
		writeOwner = new EditText(this);
		addDeviceOwnerButton = new Button(this);
		addMyDeviceButton = new Button(this);
		line = new View(this);
		
		
		showDeviceName.setText(R.string.DeviceName);
		showDeviceName.setPadding(20, 30, 0, 0);
		showDeviceName.setTextSize(20);
		showDeviceName.setId(++ numberOfView);  // devine 1
		params_name.addRule(RelativeLayout.BELOW, numberOfView - 1 );  // fata de 0
		showDeviceName.setLayoutParams(params_name);
		
		
		DeviceName.setText(nameDevice);
		DeviceName.setPadding(40, 30, 0, 0);
		DeviceName.setTextSize(20);
		DeviceName.setId( ++ numberOfView);
		nameget.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
		nameget.addRule(RelativeLayout.BELOW, numberOfView - 2);
		DeviceName.setLayoutParams( nameget);
		
		showMacAdress.setText(R.string.MacAdress);
		showMacAdress.setPadding(20, 10, 0, 0);
		showMacAdress.setTextSize(20);	
		showMacAdress.setId( ++ numberOfView);
		params_Mac.addRule(RelativeLayout.BELOW, numberOfView - 1);
		showMacAdress.setLayoutParams(params_Mac);
		
		MacAdress.setText(macAdress);
		MacAdress.setPadding(40, 10, 0, 0);
		MacAdress.setTextSize(20);
		MacAdress.setId( ++ numberOfView);
		macGet.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
		macGet.addRule(RelativeLayout.BELOW, numberOfView - 2);
		MacAdress.setLayoutParams( macGet);
		
		addOwnerDevice.setText(R.string.addOwner);
		addOwnerDevice.setPadding(20, 10, 0, 0);
		addOwnerDevice.setTextSize(20);	
		addOwnerDevice.setId( ++ numberOfView);
		params_Owner.addRule(RelativeLayout.BELOW, numberOfView - 1);
		addOwnerDevice.setLayoutParams(params_Owner);
		
		writeOwner.setTextSize(20);
		writeOwner.setId( ++ numberOfView);
		ownerGet.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
		ownerGet.addRule(RelativeLayout.BELOW, numberOfView - 2);
		writeOwner.setLayoutParams( ownerGet);
		
		addDeviceOwnerButton.setText(R.string.buttonAddOwnerDevice);
		addDeviceOwnerButton.setTextSize(20);	
		addDeviceOwnerButton.setId( ++ numberOfView);
		params_buton.addRule(RelativeLayout.BELOW, numberOfView - 1);
		addDeviceOwnerButton.setLayoutParams(params_buton);
		
		addDeviceOwnerButton.setOnClickListener(addDevice);
		
		
		addMyDeviceButton.setText(R.string.buttonAddMyDevice);
		addMyDeviceButton.setTextSize(20);	
		addMyDeviceButton.setId( ++ numberOfView);
		params_buton_mine.addRule(RelativeLayout.BELOW, numberOfView - 2);
		params_buton_mine.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1); 
		addMyDeviceButton.setLayoutParams(params_buton_mine);
		
		addMyDeviceButton.setOnClickListener(addDevice);

		
		line.setBackgroundColor(Color.BLUE);
		line.setId( ++ numberOfView);
		params_line.addRule(RelativeLayout.BELOW, numberOfView - 1);
		line.setLayoutParams(params_line);
		
		
		layout.addView(showDeviceName);
		layout.addView(DeviceName);
		layout.addView(showMacAdress);
		layout.addView(MacAdress);
		layout.addView(addOwnerDevice);
		layout.addView(writeOwner);
		layout.addView(addDeviceOwnerButton);
		layout.addView(addMyDeviceButton);
		layout.addView(line);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		mBluetoothAdapter.cancelDiscovery();
		unregisterReceiver(mReceiver);
		
		mReceiver = null;
		mBluetoothAdapter = null;

	}
	

	public RelativeLayout getLayout() {
		return layout;
	}

	public void setLayout(RelativeLayout layout) {
		this.layout = layout;
	}

	public HashMap<String,String> getDeviceInfo() { 
		return deviceInfo;
	}

	public void setDeviceInfo(HashMap<String,String> deviceInfo) {
		this.deviceInfo = deviceInfo;
	}




	public List<Device> getDevices() {
		return devices;
	}




	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

}



class MyBroadCastRecv extends BroadcastReceiver{
	
	AddDeviceActivity myActivy;
	
	

	public MyBroadCastRecv(AddDeviceActivity activity) {
		myActivy = activity;
	}

	

	@Override
	public void onReceive(Context context, Intent intent) {
		 String action = intent.getAction();
		 System.out.println("AM RECEPTIONAT INCA CEVA");
		 
	        // When discovery finds a device
	     if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            // Get the BluetoothDevice object from the Intent
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	            // Add the name and address to an array adapter to show in a ListView
	            
	          if(checkIfExists(device.getAddress()) == false){
	        	   myActivy.getDeviceInfo().put(device.getAddress(), device.getName());	           
	           	   myActivy.addInfoMethod();
	           }
	           
	           
	           
	      }
		
	}
	
	 /**
	 * @param macAddress :the address of the detected device
	 * @return : if the device is in database
	 */
	public boolean checkIfExists(String macAddress) {
		 
			for(Device d : myActivy.getDevices())
				if(d.getMacAddress().compareTo(macAddress) == 0)
					return true;
			
		 	return false;
		}
	
	


}

