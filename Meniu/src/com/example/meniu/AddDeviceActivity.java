package com.example.meniu;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;








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
import android.view.Menu;
import android.widget.RelativeLayout;

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
	private BroadcastReceiver mReceiver;
	
	/**
	 * the view where the data will be shown
	 */
	private RelativeLayout layout;
	
	/**
	 * contains for each mac adress a name for the device to be recognized
	 */
	HashMap<String,String> deviceInfo;
	
	
	
	HashMap<Integer,Device> deviceToDataBase;
	
	/**
	 * listener for add Device information button
	 * inserts the data in the database
	 */
	AddDeviceButton	addDevice;
	
	
	List<Device> devices;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_device);
		
		deviceInfo = new HashMap<String,String>();
		deviceToDataBase = new HashMap<Integer,Device>();
		devices = MainActivity.getDatabase().getAllDevices();
		
		
		addDevice  = new AddDeviceButton(this);
		
		
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    System.out.println("mBluetooth is null");
		}
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, 11);
		  
		}
		
		layout = (RelativeLayout) findViewById(R.id.layoutAfisare);
		System.out.println("AFISEZ DISPOZITIVELE PAIRED");
		System.out.println("AM AFISAT DISPOZTIVELE PAIRED");
		System.out.println("APELEZ BROADCAST RECV");
		
		mReceiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        // When discovery finds a device
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            // Get the BluetoothDevice object from the Intent
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            // Add the name and address to an array adapter to show in a ListView
		           // mArrayAdapter.add(device.getName() + "\n" + device.getAddress());

		            System.out.println(device.getName() + "\n" + device.getAddress());
		            
		           if(checkIfExists(devices,device.getAddress()) == false)
		        	   deviceInfo.put(device.getAddress(), device.getName());
		           
		           
		        }
		    }

		    public boolean checkIfExists(List<Device> devices,String macAddress) {
				
				for(Device d : devices)
				{
					if(d.getMacAddress().compareTo(macAddress) == 0)
						return true;
				}
				
				return false;
			}
		};
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		
		boolean cucu = mBluetoothAdapter.startDiscovery();
		System.out.println(cucu);
		
		
		createThread();
		

		
	}

	/**
	 * waits for 10 seconds, after which the thread shows on the GUI the results
	 * of the boardcast receiver
	 */
	public void createThread() {
		Timer timer = new Timer();
		
		timer.schedule(new MyTimerAddTask(this),  10000);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onStop()
	{
		super.onStop();
		unregisterReceiver(mReceiver);
	}

	public RelativeLayout getLayout() {
		return layout;
	}

	public void setLayout(RelativeLayout layout) {
		this.layout = layout;
	}

}
