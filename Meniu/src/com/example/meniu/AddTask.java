package com.example.meniu;

import ContextElements.ContextElementType;
import ContextElements.LocationContext;
import DatabaseOperation.AddTaskButton;
import DatabaseOperation.AlterateTask;
import DeviceData.Device;
import Task.Task;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
							    com.google.android.gms.location.LocationListener, TextWatcher, OnClickListener
{
	
	
	/** 
	 * the title of the task
	 */
	private AutoCompleteTextView autoTitle;
	
	
	/**
	 * show list for choosing a domain
	 */
	private Spinner spinnerPriority;
	
	
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
	private TextView textViewDate;
	
	
	/**
	 * shows the people selected
	 */
	private TextView textViewPeople;
	
	
	
	/**
	 * shows a message whether the data have been successfully inserted
	 */
	private TextView messageInsert;
	
	
	/**
	 * shows the devices selected by the user
	 */
	private TextView textViewDevices;
	
	
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
	 * to cease motion on scroll when the map is touched
	 */
	ScrollView scroll;
	
	
	/**
	 * the user writes the name of the location with the help
	 * of auto complete
	 */
	private AutoCompleteTextView autoLocationSearch;
	

	
	/**
	 * search the location taped 
	 */
	private Button buttonSearch;
	
	
	
	/**
	 * the position detected bu the device
	 */
	LatLng  positionCurrent;
	
	/**
	 * the key to the list of locations tapped
	 */
	String keyLocationSharedPreferences;
	
	
	/**
	 * the locations tapped and searched
	 */
	String locationsString;
	
	
	/**
	 * the locationsSearched used for AutoComplete
	 */
	String locationsSearched[];
	

	
	
	
	/**
	 * if a location has been detected, then don't try to move again
	 * on map ( it's already moving)
	 */
	boolean booleanHasDetectedLocation;
	
	
	
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
	 * true if the activity has been started from Modify Task Activity
	 */
	private boolean booleanGetFromTaskToModify;
	
	/**
	 * contains the parameters obtained from Modify Task Activity
	 */
	private ParametersToModify   oldParamatersTask;
	
	
	/**
	 * array of pairs key -value, with the key = MAC address, and the value = name of the device
	 * contains all the devices belonging to the user
	 */
	private LinkedHashMap<String,String> MAP_myDevices_name_Mac;
	
	
	/**
	 * array of pairs key -value, with the key = MAC address, and the value = name of the people
	 * contains all the devices belonging to the user
	 */
	private LinkedHashMap<String,String> MAP_people_devices_name_Mac;
	
	
	/**
	 * contains the devices selected by the user when saving the task
	 * automatically changed when closing the choose Device dialog
	 */
	private ArrayList<Integer> IntegerDevicesCheckedItems;
	
	/**
	 * contains the people selected by the user when saving the task
	 * automatically changed when closing the choose People dialog
	 */
	private ArrayList<Integer> IntegerPeopleCheckeditems; 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		

		
		spinnerPriority = (Spinner)  findViewById(R.id.spinnerPriority);	
		duration = (Spinner)  findViewById(R.id.spinnerDuration);
		autoTitle    = (AutoCompleteTextView) findViewById(R.id.titleAutoComplete);
		autoLocationSearch = (AutoCompleteTextView) findViewById(R.id.locationAutoComplete);
		
		
		scroll   = (ScrollView) findViewById(R.id.ScrollView01);
		scroll.requestDisallowInterceptTouchEvent(true);
		
		
		textViewPeople = (TextView)   findViewById(R.id.choosePeopleText);
		textViewDate     = (TextView) findViewById(R.id.deadline);
		textViewDevices = (TextView)  findViewById(R.id.chooseDevices);
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
		
		
		buttonSearch = (Button) findViewById(R.id.buttonSearchLocation);
		buttonSearch.setOnClickListener( this);
			

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
        

   /* 	List<Task> tasks =  MainActivity.getDatabase().getAllTasks();
		ArrayList<String>  allWords = new ArrayList<String>();
		allWords.addAll(Core.titlesOfCenters());
		
		
		for(Task task : tasks)
		{
			String taskWords[]  = task.getNameTask().split(" ");
			for(String word : taskWords)
				if(allWords.contains(word) == false)
					allWords.add(word);
			
			
		}
		String[] wordsList = new String[allWords.size()];
		allWords.toArray(wordsList);
		
		autoTitle.addTextChangedListener(this);
		autoTitle.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, wordsList));*/
        
        
        IntegerDevicesCheckedItems = new ArrayList<Integer>();
        IntegerPeopleCheckeditems = new ArrayList<Integer>();
		
		
		loadSharedPreferences();
		loadDevices();
		
		
		
	}
	
	
	

	/**
	 * loads the devices contained in the databse
	 */
	public void loadDevices() {
		
		MAP_myDevices_name_Mac = new LinkedHashMap<String, String>();
		MAP_people_devices_name_Mac = new LinkedHashMap<String, String>();
		
		List<Device> devices = MainActivity.getDatabase().getAllDevices();
		

		for(Device device : devices)
			if(device.getOwnerDevice().equals(this.getResources().getString(R.string.myDeviceConstant)))
				MAP_myDevices_name_Mac.put(device.getMacAddress(), device.getNameDevice());
			else
				MAP_people_devices_name_Mac.put(device.getMacAddress(), device.getOwnerDevice());
		
		
		
	
	}




	/**
	 * checks who has started the ativity
	 */
	private void checkIntentStarter() {

		Intent intent = getIntent();
		Integer messageId = intent.getIntExtra(AlterateTask.ID_MESSAGE, -1);
		
		if(messageId == -1){
			this.booleanGetFromTaskToModify = false;
			return;
		}
		
		String title,deadline,priorityString, people[], devices[];
		double locationDouble[]; 
		String devicesString = "";
		String peopleString  = "";
		LatLng position;
		int i, id;
		
		
		title 	= intent.getStringExtra(AlterateTask.TITLE_MESSAGE);
		people  = intent.getStringArrayExtra(AlterateTask.PEOPLE_MESSAGE);
		devices = intent.getStringArrayExtra(AlterateTask.DEVICE_MESSAGE);
		deadline = intent.getStringExtra(AlterateTask.DEADLINE_MESSAGE);
		locationDouble = intent.getDoubleArrayExtra(AlterateTask.LOCATION_MESSAGE);
		priorityString = intent.getStringExtra(AlterateTask.PRIORITY_MESSAGE);
		 
		
		booleanGetFromTaskToModify = true;
		
		oldParamatersTask = new ParametersToModify(messageId); 
		
		if(deadline.equals(Constants.noChoose) == false)
			oldParamatersTask.changeDeadline(deadline);
		oldParamatersTask.changeDevices(devices);
		oldParamatersTask.changePeople(people);
		
		spinnerPriority.setSelection( Core.getPrioritiesValues().get(priorityString) ); 
		
		System.out.println("TITLUL ESTE " + title);
		
		
		autoTitle.setText(title); 
		textViewDate.setText(deadline);
		
		if(devices.length != 0 )
		{
			for( i = 0; i < devices.length -1 ; i++){
				devicesString += MAP_myDevices_name_Mac.get( devices[i]) + ",";
				if(MAP_myDevices_name_Mac.containsKey(devices[i]))
					IntegerDevicesCheckedItems.add(i); 
			}
		
			devicesString +=  MAP_myDevices_name_Mac.get(devices[i]);
			if(MAP_myDevices_name_Mac.containsKey(devices[i]))
				IntegerDevicesCheckedItems.add(i); 
		}
		else
			devicesString = Constants.noChoose;
		if(people.length != 0)
		{
			for(i = 0; i < people.length -1 ; i++){
				peopleString += MAP_people_devices_name_Mac.get( people[i]) + ",";
				if(MAP_people_devices_name_Mac.containsKey(people[i]))
					IntegerPeopleCheckeditems.add(i); 
			}
		
			peopleString += MAP_people_devices_name_Mac.get( people[i]);
			if( MAP_people_devices_name_Mac.containsKey(people[i]))
				IntegerPeopleCheckeditems.add(i); 
		
		}
		else
			peopleString = Constants.noChoose;
		System.out.println("Persoanele din lista " + IntegerPeopleCheckeditems.toString());
		System.out.println("Dispozitivele din lista " + IntegerDevicesCheckedItems.toString());
		
		
		textViewDevices.setText(devicesString);
		textViewPeople.setText(peopleString);
		
		
		

		System.out.println("DEADLINE" + deadline);
		
		
		location = Double.toString( locationDouble[0]) + " " + Double.toString(locationDouble[1]);
		position = new LatLng(locationDouble[0], locationDouble[1]);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));


    	map.addMarker(new MarkerOptions().position(position).
    	icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_location_place)));
		
		
	}




	/**
	 * loads the strings search by the user
	 */
	public void loadSharedPreferences() {
		keyLocationSharedPreferences = "LocationsStrings";
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		String defaultString="";
		
		locationsString  = sharedPreferences.getString(keyLocationSharedPreferences, defaultString);
		locationsSearched  = locationsString.split("###");
		
		
		autoLocationSearch.addTextChangedListener(this);
		autoLocationSearch.setAdapter
		(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locationsSearched));
		

		
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
		int year, month, day;
		
		if(booleanGetFromTaskToModify == true)
		{
			year = this.oldParamatersTask.year;
			month = this.oldParamatersTask.month;
			day = this.oldParamatersTask.day;
		}
		else
		{
		
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
		}
		
		
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
		
		ArrayList<String> contacts = new ArrayList<String>(MAP_people_devices_name_Mac.values());
		ArrayList<String> keyDevicesMac = new ArrayList<String>(MAP_people_devices_name_Mac.keySet());
		
		final ArrayList<Integer> itemsId = new ArrayList<Integer>();
		final CharSequence[] items =  contacts.toArray( new CharSequence[contacts.size()]);
		
		boolean checkedItems[];
		
		
		
		if(booleanGetFromTaskToModify == true){
			checkedItems = oldParamatersTask.detectOldPeopleSelected( keyDevicesMac, itemsId);
			System.out.println("ItemsId la people este " + itemsId);
		}
		else
			checkedItems = null;


		builder.setMultiChoiceItems(items, checkedItems,
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
            	
            	textViewPeople.setText(peopleString);
            	addPeopleNeeded.setFocusableInTouchMode(true);
            	addPeopleNeeded.requestFocus();
            	
            	IntegerPeopleCheckeditems = itemsId;
            	
            	
            	
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
		ArrayList<String> devicesMy = new ArrayList<String>(MAP_myDevices_name_Mac.values());
		ArrayList<String> keyDevicesMac = new ArrayList<String>(MAP_myDevices_name_Mac.keySet());
		
		final ArrayList<Integer> itemsId = new ArrayList<Integer>();
		final CharSequence[] items =  devicesMy.toArray( new CharSequence[devicesMy.size()]);
		boolean checkedItems[];
		
		
		if(booleanGetFromTaskToModify == true){
			checkedItems = oldParamatersTask.detectOldDevicesSelected( keyDevicesMac, itemsId);
			System.out.println("ItemsId la devices este " + itemsId);
		}
		else
			checkedItems = null;
		
		System.out.println("itemsId :" + itemsId.size() + " " + itemsId.toString());
		
		builder.setMultiChoiceItems(items, checkedItems,
                   new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int indexSelected,
							boolean isChecked) {
						
						System.out.println("itemsId :" + itemsId.size() + " " + itemsId.toString());
						
						if(isChecked == true)
							itemsId.add(indexSelected);
						else
							//itemsId.remove(indexSelected);
							itemsId.remove(new Integer(indexSelected));
						
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
            	
            	
            	textViewDevices.setText(deviceString);
            	addDevicesNeeded.setFocusableInTouchMode(true);
            	addDevicesNeeded.requestFocus();
            	
            	
            	IntegerDevicesCheckedItems = itemsId;
            	
            	System.out.println("ID-URILE DISPOZITIVELOR SELECTATE" + IntegerDevicesCheckedItems);
             
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
				
					textViewDate.setText(Integer.toString(selectedYear) + "-" + 
					Integer.toString(selectedMonth+ 1) +"-" + Integer.toString(selectedDay) );
				
			}
	};
	
		@Override
	public void onMapClick(LatLng locationMap) { 
		
		changeMarkers(locationMap);
		
		
		Geocoder geocoder = new Geocoder(this);
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocation(locationMap.latitude ,locationMap.longitude, 1);
			autoLocationSearch.setText(  addresses.get(0).getAddressLine(0) ) ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onConnected(Bundle arg0) {
        
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
        
        
        
        Location l =  mLocationClient.getLastLocation(); 
       
       
        if(l != null){
      
        	setPositionOnMap(l);
       
        }
        mLocationClient.requestLocationUpdates(locationRequest, this);
        
        
        
        
	}
	

	@Override
	public void onLocationChanged(Location arg0) {
		setPositionOnMap(arg0);
		checkIntentStarter();
	}
	
	/**
	 * @param l : the location detected from updating or from lastKnownlocation
	 * it is then shown on the map
	 */
	public void setPositionOnMap(Location location){
		
		positionCurrent = new LatLng(location.getLatitude(), location.getLongitude());
         
    	System.out.println("POZITIA ACTUALA" + positionCurrent.latitude + " " + positionCurrent.longitude);
    	Log.w("Pozitie actuala", positionCurrent.latitude + " " + positionCurrent.longitude); 
    
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(positionCurrent, 15));

    	// Zoom in, animating the camera.
    	map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    	map.addMarker(new MarkerOptions().position(positionCurrent).
    	icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
	}
	
	
	
	

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

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.buttonSearchLocation)
		{
			//autoLocationSearch.getText().toString();
			String searchPattern = autoLocationSearch.getText().toString();
			
			
			
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
						if(addresses.size()  == 0)
						{
							autoLocationSearch.setText("Not found");
							return;
						}
						System.out.println("LOCATIA CAUTATA ESTE" + searchPattern + "---"+ addresses.toString()+"----");
					
						Address closest = determineClosestAddress(addresses);
						
						Log.w("Pozitie", closest.getLatitude() + " " + closest.getLongitude());
						LatLng position = new LatLng(closest.getLatitude(), closest.getLongitude());
						changeMarkers(position);
						saveLocationNameToSharedPreferences(searchPattern);
						
					}
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		
	
	}
	
	
	public Address determineClosestAddress(List<Address> addresses) {
		
		Address closestAdress = addresses.get(0);
		double distanceMax = 10000;
		double distance;
		
		System.out.println("Pozitia curenta este" + positionCurrent.latitude + "  " + positionCurrent.longitude);
		
		for(Address adresa : addresses)
		{
			System.out.println(adresa.getLatitude() + " " + adresa.getLongitude());
			
			
			distance = (float) Math.pow(positionCurrent.latitude - adresa.getLatitude(), 2) +
					           Math.pow(positionCurrent.longitude - adresa.getLongitude(), 2);
			
			if(distance < distanceMax){
				closestAdress  = adresa;
				distanceMax = distance;
			}
			
			
		}
		
		return closestAdress;
		
	}




	/**
	 * @param location : the location search by the user, to be included in sharedpreferences
	 */
	public void saveLocationNameToSharedPreferences(String location) {
		
		System.out.println( "LOCATIILE SUNT" + locationsString);
		
		Log.w("Location", locationsString);
		
		ArrayList<String> arrayLocations = new ArrayList<String>(Arrays.asList(locationsSearched));
		if(arrayLocations.contains(location) == true)
			return;
		
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = sharedPreferences.edit();
		
		
		locationsString = locationsString + "###" + location;
		
//		System.out.println( "LOCATIILE SUNT" + locationsString);
	
		Log.w("Location", locationsString);
		
		edit.putString(keyLocationSharedPreferences, locationsString);
		
		edit.commit();
		
		
		locationsSearched  = locationsString.split("###");
		autoLocationSearch.setAdapter
		(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locationsSearched));
		
		
	}




	/**
	 * @param position : the position selected bu the user
	 */
	public void changeMarkers(LatLng position)
	{

		
		location = Double.toString( position.latitude) + " " + Double.toString(position.longitude);
		map.clear();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));


    	map.addMarker(new MarkerOptions().position(positionCurrent).
		icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
    	map.addMarker(new MarkerOptions().position(position).
    	icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_location_place)));
	}
	
	

public Spinner getPriority() {
	return spinnerPriority;
}

public void setPriority(Spinner priority) {
	this.spinnerPriority = priority;
}


public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public TextView getDate() {
	return textViewDate;
}

public void setDate(TextView date) {
	this.textViewDate = date;
}



public GoogleMap getMap() {
	return map;
}

public void setMap(GoogleMap map) {
	this.map = map;
}





public TextView getPeople() {
	return textViewPeople;
}




public void setPeople(TextView people) {
	this.textViewPeople = people;
}




public TextView getDevices() {
	return textViewDevices;
}




public void setDevices(TextView devices) {
	this.textViewDevices = devices;
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




public AutoCompleteTextView getTitleTask() {
	return autoTitle;
}




public void setTitle(AutoCompleteTextView title) {
	this.autoTitle = title;
}




@Override
public void afterTextChanged(Editable s) {
	

	if(autoTitle.getEditableText()  == s)
	{
		//clustering.detectCentroid(s.toString());
		
		
		Task currentTask = new Task();
		currentTask.setNameTask(s.toString());
		
		
		Task returnTask =  Core.getClusteringLocation().detectCentroid(currentTask);
		if(returnTask != null && booleanHasDetectedLocation == false)
		{
			booleanHasDetectedLocation = true;
			LocationContext location = (LocationContext)returnTask.getInternContext().
			getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			

			changeMarkers(location.getPositionLatLng());
			
		}
		else
			if(returnTask != null && booleanHasDetectedLocation == true )
				booleanHasDetectedLocation = false;
	}
		
	
	
}




@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	// TODO Auto-generated method stub
	
}




@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
	// TODO Auto-generated method stub
	
}




public boolean isBooleanGetFromTaskToModify() {
	return booleanGetFromTaskToModify;
}




public void setBooleanGetFromTaskToModify(boolean booleanGetFromTaskToModify) {
	this.booleanGetFromTaskToModify = booleanGetFromTaskToModify;
}




public ParametersToModify getOldParamatersTask() {
	return oldParamatersTask;
}




public void setOldParamatersTask(ParametersToModify oldParamatersTask) {
	this.oldParamatersTask = oldParamatersTask;
}




public LinkedHashMap<String,String> getMy_devices_name_Mac() {
	return MAP_myDevices_name_Mac;
}




public void setMy_devices_name_Mac(LinkedHashMap<String,String> my_devices_name_Mac) {
	this.MAP_myDevices_name_Mac = my_devices_name_Mac;
}




public LinkedHashMap<String,String> getPeople_devices_name_Mac() {
	return MAP_people_devices_name_Mac;
}




public void setPeople_devices_name_Mac(LinkedHashMap<String,String> people_devices_name_Mac) {
	this.MAP_people_devices_name_Mac = people_devices_name_Mac;
}




public ArrayList<Integer> getIntegerDevicesCheckedItems() {
	return IntegerDevicesCheckedItems; 
}




public void setBooleanDevicesCheckedItems(ArrayList<Integer> booleanDevicesCheckedItems) {
	IntegerDevicesCheckedItems = booleanDevicesCheckedItems;
}




public ArrayList<Integer> getIntegerPeopleCheckeditems() { 
	return IntegerPeopleCheckeditems;
}




public void setBooleanPeopleCheckeditems(ArrayList<Integer> booleanPeopleCheckeditems) {
	IntegerPeopleCheckeditems = booleanPeopleCheckeditems;
}


}







