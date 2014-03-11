package com.example.meniu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	ListView listaMeniu;
	static final String[] FRUITS = new String[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	   FRUITS[0] =	this.getString(R.string.getTask);
	   FRUITS[1] =	this.getString(R.string.addTask);
	   FRUITS[2] =	this.getString(R.string.schedule);
		
		listaMeniu = (ListView) this.findViewById(R.id.ListView1);
	//	listaMeniu.setAdapter( new ArrayAdapter<String>(this, R.layout.activity_main,FRUITS));
		 listaMeniu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,FRUITS));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
