package com.example.meniu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author ${Vlad Herescu}
 *
 */
public class MainActivity extends Activity {
	
	/**
	 * represents the menu and its menuItem to view 
	 * schedule, add a task or suggest a task
	 */
	ListView listaMeniu;
	
	/**
	 * names of the menuItems
	 */
	static final String[] FRUITS = new String[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	   FRUITS[0] =	this.getString(R.string.getTask);
	   FRUITS[1] =	this.getString(R.string.addTask);
	   FRUITS[2] =	this.getString(R.string.schedule);
		
		listaMeniu = (ListView) this.findViewById(R.id.ListView1);
		listaMeniu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,FRUITS));
		listaMeniu.setOnItemClickListener( new MenuItemTouched(this) );
		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}


/**
 * @author ${Vlad Herescu}
 *
 */
class MenuItemTouched implements OnItemClickListener{
	
	/**
	 *  instance of the main activity class to gain access to its
	 *  members 
	 */
	MainActivity mainActivity;
	
	/**
	 * @param received : the mainActivity of the app
	 */
	MenuItemTouched(MainActivity received){
		mainActivity = received;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		
		// Log.w("apasare meniu", "am apasat un meniu" + mainActivity.FRUITS[arg2]);
		
		 Intent intent = null;
		
		 
		 if(mainActivity.FRUITS[arg2].compareTo(mainActivity.getString(R.string.getTask)) == 0){
			 
			  intent = new Intent(mainActivity, AddTask.class);
			
			 
			 
		 }
		 if(mainActivity.FRUITS[arg2].compareTo(mainActivity.getString(R.string.addTask)) == 0){
			  intent = new Intent(mainActivity, AddTask.class);
			 
		 }
		 if(mainActivity.FRUITS[arg2].compareTo(mainActivity.getString(R.string.schedule)) == 0){
			  intent = new Intent(mainActivity, AddTask.class);
			 
		 }
		 
		 mainActivity.startActivity(intent);
		 
		
	}
	
}


