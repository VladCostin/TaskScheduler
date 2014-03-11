package com.example.meniu;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Spinner;

/**
 * offers the interface to add data which defines a new task
 * @author ${Vlad Herescu}
 *
 */
public class AddTask extends Activity {
	
	Spinner domain;
	Spinner priority;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		domain 	 = (Spinner) this.findViewById(R.id.spinner1);
		priority = (Spinner) this.findViewById(R.id.spinner2);
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_task, menu);
		return true;
	}

}
