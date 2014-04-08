package com.example.meniu;

import android.util.Log;
import android.view.View.OnClickListener;

import java.util.Calendar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
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
							GooglePlayServicesClient.OnConnectionFailedListener
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
	 * contains the location introduces by the user
	 */
	private EditText location;
	
	
	/**
	 * for selecting objects from a list or suggesting one
	 */
	private Button addObjectsNeeded;
	
	
	/**
	 * for selecting a deadline
	 */
	Button addDeadline;
	
	
	/**
	 * button for saving the task in the database
	 */
	Button saveButton;
	
	/**
	 * contains the deadline established by the user
	 */
	private TextView date;
	
	/**
	 *  used to set the location of the task
	 */
	private GoogleMap map;
	
	
	/**
	 * used to get the user's location
	 */
	LocationClient mLocationClient;
	
	static final int DATE_DIALOG_ID = 999;
	
	
	/**
	 * to cease motion on scroll when the map is touched
	 */
	ScrollView scroll;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		domain 	 = (Spinner) this.findViewById(R.id.spinner1);
		priority = (Spinner) this.findViewById(R.id.spinner2);	
		title    = (EditText) this.findViewById(R.id.title);
		
		scroll   = (ScrollView) this.findViewById(R.id.ScrollView01);
		scroll.requestDisallowInterceptTouchEvent(true);
		
		
		addDeadline = (Button) this.findViewById(R.id.setDeadline);
		date = (TextView) this.findViewById(R.id.deadline);
		
		date.setText("No deadline");
		
		addDeadline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showDialog(DATE_DIALOG_ID);
				
			}
		});
		
		
		saveButton = (Button) this.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new ButtonTouched(this));
		
		
		mLocationClient = new LocationClient(this, this, this);

		

		
	//	 Try to obtain the map from the SupportMapFragment.
     //   map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
      //          .getMap();
        
        
        
    //    map.setOnMapClickListener(this);
		
		
		
		
		 map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		 map.setOnMapClickListener(this); 
			
        
        
        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
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
			
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
		   // set date picker as current date
		   return new DatePickerDialog
		   (this, datePickerListener, year, month,day);
		}
		return null;
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
	public void onMapClick(LatLng arg0) {
		
		

		Log.d("CACAT", arg0.toString());

		map.addMarker(new MarkerOptions().position(arg0).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		  // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        Location l =  mLocationClient.getLastLocation(); 
       
        
        LatLng position = new LatLng(l.getLatitude(), l.getLongitude());
        
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
    	map.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
       
		
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

public Button getAddObjectsNeeded() {
	return addObjectsNeeded;
}

public void setAddObjectsNeeded(Button addObjectsNeeded) {
	this.addObjectsNeeded = addObjectsNeeded;
}

public EditText getLocation() {
	return location;
}

public void setLocation(EditText location) {
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



}




