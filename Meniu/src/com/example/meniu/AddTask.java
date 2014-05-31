package com.example.meniu;

import DatabaseOperation.AddTaskButton;
import DeviceData.Device;
import android.util.Log;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;

/**
 * offers the interface to add data which defines a new task
 * @author ${Vlad Herescu}
 *
 */
public class AddTask extends   FragmentActivity 
					 implements OnMapClickListener,
							GooglePlayServicesClient.ConnectionCallbacks,
							GooglePlayServicesClient.OnConnectionFailedListener, 
							com.google.android.gms.location.LocationListener
{
	
	
	/** 
	 * the title of the task
	 */
	private EditText title;
	
	/**
	 * show list of domains
	 */
	private Spinner domain;
	
	/**
	 * show list for choosing a domain
	 */
	private Spinner priority;
	
	
	/**
	 * the location selected by the user
	 */
	private String location;
	
	
	
	/**
	 * for selecting objects from a list or suggesting one
	 */
	private Button addDevicesNeeded;
	
	
	
	/**
	 * for selecting people needed for the task
	 */
	private Button addPeopleNeeded;
	
	
	/**
	 * for selecting a deadline
	 */
	Button addDeadline;
	
	
	/**
	 * button for saving the task in the database
	 */
	Button saveButton;
	
	
	/**
	 * button for showing the estimated duration after clustering
	 */
	private Button clusterise;
	
	/**
	 * contains the deadline established by the user
	 */
	private TextView date;
	
	
	/**
	 * shows the people selected
	 */
	private TextView people;
	
	
	
	/**
	 * shows a message whether the data have been successfully inserted
	 */
	private TextView messageInsert;
	
	
	/**
	 * shows the devices selected by the user
	 */
	private TextView devices;
	
	
	/**
	 * to select the duration of the task
	 */
	private Spinner duration;
	
	/**
	 *  used to set the location of the task
	 */
	private GoogleMap map;
	
	
	/**
	 * used to get the user's location
	 */
	LocationClient mLocationClient;
	
	
	 /**
	 * used to get updates about the user's location
	 */
	LocationRequest locationRequest;
	
	/**
	 * used to determine which date to choose
	 */
	static final int DATE_DIALOG_ID = 999;
	
	/**
	 * used to determine which people to choose
	 */
	static final int PEOPLE_DIALOG_ID = 1000;
	
	
	/**
	 * used to determine which devices to choose
	 */
	static final int DEVICES_DIALOG_ID = 1001;
	
	
	/**
	 * to cease motion on scroll when the map is touched
	 */
	ScrollView scroll;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		domain 	 = (Spinner)  findViewById(R.id.spinner1);
		priority = (Spinner)  findViewById(R.id.spinnerPriority);	
		duration = (Spinner)  findViewById(R.id.spinnerDuration);
		title    = (EditText) findViewById(R.id.title);
		
		
		scroll   = (ScrollView) findViewById(R.id.ScrollView01);
		scroll.requestDisallowInterceptTouchEvent(true);
		
		
		people = (TextView)   findViewById(R.id.choosePeopleText);
		date     = (TextView) findViewById(R.id.deadline);
		devices = (TextView)  findViewById(R.id.chooseDevices);
		messageInsert = (TextView) this.findViewById(R.id.messageInsert);
		
		addPeopleNeeded = (Button) findViewById(R.id.choosePeopleButton);
		addPeopleNeeded.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showDialog(PEOPLE_DIALOG_ID);
				v.setFocusableInTouchMode(true);
				v.requestFocus();
				
			}
		});
		
	//	addPeopleNeeded.setOnClickListener(this);
		
		addDeadline = (Button) findViewById(R.id.setDeadline);
		addDeadline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				
				showDialog(DATE_DIALOG_ID);
				
				
				
				addDeadline.setFocusableInTouchMode(true);
				addDeadline.requestFocus();

				
			}
		});
		
		
		
		addDevicesNeeded = (Button) findViewById(R.id.chooseDevicesButton);
		addDevicesNeeded.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				System.out.println("TREBUIE SA AFISEZE DISPOZTIVELE");
				showDialog(DEVICES_DIALOG_ID);	
				
				
			}
		});
		
		
		saveButton = (Button) findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new AddTaskButton(this));
		
		
	//	addDevicesNeeded.setFocusable(true); 
		
		
		mLocationClient = new LocationClient(this, this, this);
		locationRequest = new LocationRequest();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(5000);
		locationRequest.setFastestInterval(1000);
		locationRequest.setNumUpdates(1);


		
		 map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		 map.setOnMapClickListener(this); 
			
        
        
        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).
        setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scroll.requestDisallowInterceptTouchEvent(true);
            }
        });
        
      
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}
	

	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			
			return dateDialog();
		   
		case PEOPLE_DIALOG_ID:
			
			return peopleDialog();
			
		case DEVICES_DIALOG_ID:
			
			return devicesDialog();
		}
		return null;
	}
	
	
	/**
	 * @return : the dialog where the user can choose the deadline of the task
	 */
	public Dialog dateDialog()
	{
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
	   // set date picker as current date
	   return new DatePickerDialog
	   (this, datePickerListener, year, month,day);
	}
	
	/**
	 * @return : the dialog where the user can choose the people he needs to execute the task
	 */
	public Dialog peopleDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose with which people the task must be executed");
		List<Device> devices = MainActivity.getDatabase().getAllDevices();
		ArrayList<String> contacts = createContactList(devices);
		final ArrayList<Integer> itemsId = new ArrayList<Integer>();
		final CharSequence[] items =  contacts.toArray( new CharSequence[contacts.size()]);

		builder.setMultiChoiceItems(items, null,
                   new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int indexSelected,
							boolean isChecked) {
						
						System.out.println("DIALOG AM APASAT PE "  +  indexSelected + " " +  isChecked);
						
						if(isChecked == true)
							itemsId.add(indexSelected);
						else
							itemsId.remove(Integer.valueOf( indexSelected));
						
					}
					
					 
		
		});
		
		 // Set the action buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
               
            	int i;
            	String peopleString="";

            	
            	if(itemsId.size() != 0)
            	{
            		for( i = 0; i < itemsId.size() - 1; i++)
            			peopleString = peopleString + items[itemsId.get(i)] + ",";
            	
            		peopleString = peopleString + items[itemsId.get(i)];
            	}
            	else
            		peopleString = getResources().getString(R.string.textNotChoosed);
            	
            	people.setText(peopleString);
            	addPeopleNeeded.setFocusableInTouchMode(true);
            	addPeopleNeeded.requestFocus();
            }
        });
		
	

		
		return builder.create();
	}
	
	/**
	 * @return : the dialog from which the user can select the necessary devices
	 */
	public Dialog devicesDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Choose which devices are needed for executing the task");
		List<Device> devicesDatabase = MainActivity.getDatabase().getAllDevices();
		ArrayList<String> devicesMy = createMyDevicesList(devicesDatabase);
		final ArrayList<Integer> itemsId = new ArrayList<Integer>();
		final CharSequence[] items =  devicesMy.toArray( new CharSequence[devicesMy.size()]);

		builder.setMultiChoiceItems(items, null,
                   new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int indexSelected,
							boolean isChecked) {
						
						
						if(isChecked == true)
							itemsId.add(indexSelected);
						else
							itemsId.remove(indexSelected);
						
					}
					
					 
		
		});
		
		 // Set the action buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
               
            	int i;
            	String deviceString="";
            	if(itemsId.size() != 0)
            	{
            		for( i = 0; i < itemsId.size() - 1; i++)
            			deviceString = deviceString + items[itemsId.get(i)] + ","; 
            		
            		deviceString = deviceString + items[itemsId.get(i)];
            		
            	}
            	else
            		deviceString = getResources().getString(R.string.textNotChoosed);
            	
            	
            	devices.setText(deviceString);
            	addDevicesNeeded.setFocusableInTouchMode(true);
            	addDevicesNeeded.requestFocus();
             
            }
        });
		
		return builder.create();
	}
	
	
	
	
	
	/**
	 * @param devices : list of devices from the database
	 * @return : myOwnDevices
	 */
	public ArrayList<String> createMyDevicesList(List<Device> devices) {
		
		ArrayList<String> myDevices = new ArrayList<String>();
		String myDeviceConstant = this.getResources().getString(R.string.myDeviceConstant);
		
		for(Device d : devices)
		{
			if(d.getOwnerDevice().compareTo(myDeviceConstant) == 0 )
			{
				myDevices.add(d.getNameDevice());
			}
		}
		
		
		
		return myDevices;
		
	}




	/**
	 * @param devices : list of the devices from the database
	 * @return : the contact list
	 */
	public ArrayList<String> createContactList(List<Device> devices) {
		
		ArrayList<String> contacts = new ArrayList<String>();
		String myDeviceConstant = this.getResources().getString(R.string.myDeviceConstant);
		
		for(Device d : devices)
			if(contacts.contains(d.getOwnerDevice()) == false &&
			   d.getOwnerDevice().compareTo(myDeviceConstant) != 0	)
				contacts.add(d.getOwnerDevice());
		
		
		
		return contacts;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener 
			= new DatePickerDialog.OnDateSetListener() {

			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
								  int selectedMonth, int selectedDay) {
				
					date.setText(Integer.toString(selectedYear) + "-" + 
					Integer.toString(selectedMonth+ 1) +"-" + Integer.toString(selectedDay) );
				
			}
	};
	
		@Override
	public void onMapClick(LatLng locationMap) { 
		
		location = Double.toString( locationMap.latitude) + " " + Double.toString(locationMap.longitude);
		map.addMarker(new MarkerOptions().position(locationMap).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		  // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        
        LocationManager     manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        
        manager.requestLocationUpdates(
         	    LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
         	        @Override
         	        public void onStatusChanged(String provider, int status, Bundle extras) {
         	        }
         	        @Override
         	        public void onProviderEnabled(String provider) {
         	        }
         	        @Override
         	        public void onProviderDisabled(String provider) {
         	        }
         	        @Override
         	        public void onLocationChanged(final Location location) {
         	        }
         });
        
        
        
    //    Location l =  mLocationClient.getLastLocation(); 
        
       
  /*      String searchPattern = "Politehnica Bucuresti";
        List<Address> addresses = null;
        try {
			addresses = (new Geocoder(this)).getFromLocationName(searchPattern, Integer.MAX_VALUE);
			Log.w("Pozitie", "Am extras pozitia adresei"); 
			if (addresses == null) {
		        // location service unavailable or incorrect address
		        // so returns null
		        Log.w("Pozitie", "Pozitia este nula");
		    }
			else
			{
				Address closest = addresses.get(0);
				Log.w("Pozitie", closest.getLatitude() + " " + closest.getLongitude());
				
				LatLng position = new LatLng(closest.getLatitude(), closest.getLongitude());
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

		    	// Zoom in, animating the camera.
		    	map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
		    	map.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
        
        */
       
       
     /*   if(l != null){
      
        	setPositionOnMap(l);
       
        }*/
        mLocationClient.requestLocationUpdates(locationRequest, this);
        
        
        
        
	}
	

	@Override
	public void onLocationChanged(Location arg0) {
		
		
		
		LatLng position = new LatLng(arg0.getLatitude(), arg0.getLongitude());
        
    	System.out.println("POZITIA ACTUALA" + position.latitude + " " + position.longitude);
    	Log.w("Pozitie actuala", position.latitude + " " + position.longitude); 
    
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

    	// Zoom in, animating the camera.
    	map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    	map.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
	
	}
	
	
	/**
	 * @param l : the location detected from updating or from lastKnownlocation
	 * it is then shown on the map
	 */
	/*public void setPositionOnMap(Location l)
	{
		LatLng position = new LatLng(l.getLatitude(), l.getLongitude());
        
    	System.out.println("POZITIA ACTUALA" + position.latitude + " " + position.longitude);
    	Log.w("Pozitie actuala", position.latitude + " " + position.longitude); 
    
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

    	// Zoom in, animating the camera.
    	map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    	map.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
	}
	*/

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
		
	}
	
	/*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }
    
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	



public Spinner getDomain() {
	return domain;
}

public void setDomain(Spinner domain) {
	this.domain = domain;
}

public Spinner getPriority() {
	return priority;
}

public void setPriority(Spinner priority) {
	this.priority = priority;
}


public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public TextView getDate() {
	return date;
}

public void setDate(TextView date) {
	this.date = date;
}

public EditText getTitletask() {
	return title;
}

public void setTitle(EditText title) {
	this.title = title;
}

public GoogleMap getMap() {
	return map;
}

public void setMap(GoogleMap map) {
	this.map = map;
}





public TextView getPeople() {
	return people;
}




public void setPeople(TextView people) {
	this.people = people;
}




public TextView getDevices() {
	return devices;
}




public void setDevices(TextView devices) {
	this.devices = devices;
}




public TextView getMessageInsert() {
	return messageInsert;
}




public void setMessageInsert(TextView messageInsert) {
	this.messageInsert = messageInsert;
}




public Spinner getDuration() {
	return duration;
}




public void setDuration(Spinner duration) {
	this.duration = duration;
}




public Button getClusterise() {
	return clusterise;
}




public void setClusterise(Button clusterise) {
	this.clusterise = clusterise;
}












}




