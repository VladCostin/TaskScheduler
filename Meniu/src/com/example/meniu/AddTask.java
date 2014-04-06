package com.example.meniu;

import android.view.View.OnClickListener;
import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * offers the interface to add data which defines a new task
 * @author ${Vlad Herescu}
 *
 */
public class AddTask extends Activity {
	
	
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
	
	static final int DATE_DIALOG_ID = 999;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		domain 	 = (Spinner) this.findViewById(R.id.spinner1);
		priority = (Spinner) this.findViewById(R.id.spinner2);	
		title    = (EditText) this.findViewById(R.id.title);
		location = (EditText) this.findViewById(R.id.Location);
		
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
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.add_task, menu);
	return true;
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



}
