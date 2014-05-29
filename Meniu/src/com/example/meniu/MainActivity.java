package com.example.meniu;



import java.util.List;

import Clusters.KMeansDuration;
import DatabaseOperation.DatabaseHandler;
import DeviceData.Device;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
	static final String[] MenUApp = new String[5];
	
	
	/**
	 * object where the tasks are stored
	 */
	private static DatabaseHandler database;
	
	
	/**
	 * obtaining the general information of the application
	 */
	private static Core core;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		database = new DatabaseHandler(this);
		core = new Core();
		Core.init();
		
		
	    MenUApp[0] =	this.getString(R.string.getTask);
	    MenUApp[1] =	this.getString(R.string.addTask);
	    MenUApp[2] =	this.getString(R.string.schedule);
	    MenUApp[3] =	this.getString(R.string.discoverDevice);
	    MenUApp[4] =	this.getString(R.string.current_task);
		
		listaMeniu = (ListView) this.findViewById(R.id.ListView1);
		listaMeniu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,MenUApp));
		listaMeniu.setOnItemClickListener( new MenuItemTouched(this) );
		
		
	//	addTasksforCheck();
		KMeansDuration clustering = new KMeansDuration();
		clustering.calculateKlusters();
		

		
		
	//	System.out.println("DIMENSIUNEA ESTE : " + database.getAllTasks().size());
	//	database.getReadableDatabase().execSQL("DROP TABLE IF EXISTS tasks");
	//	database.getReadableDatabase().execSQL("DROP TABLE IF EXISTS devices");
	//	database.createTable();
		
		
	/*	List<Device> devices = database.getAllDevices();
		for(Device d : devices)
		{
			System.out.println(d.getNameDevice() + " " + d.getOwnerDevice() + " " +d.getMacAddress());
		}*/
		
		
		 
	}

	private void addTasksforCheck() {
		
		
		MainActivity.database.addTaskPriori
		("ma duc sa imi fac abonament", "44.427 26.102", "45", "28/05/2014/17/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101",  "35", "28/05/2014/10/34");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou lunar",  "44.428 26.103", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103", "12", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("ma duc sa imi fac abonament","44.427 26.102", "55", "28/05/2014/16/05");
		
		MainActivity.database.addTaskPriori
		("abonament m luna", "44.43 26.101",  "65", "28/05/2014/09/10");
		
		
		MainActivity.database.addTaskPriori
		("metrou ab", "44.428 26.103", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("Metrou abonament", "44.428 26.103", "6", "28/05/2014/14/35");
		
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/21");
		
		MainActivity.database.addTaskPriori
		("abonament",  "44.43 26.101", "20", "28/05/2014/13/35");
		
		
		MainActivity.database.addTaskPriori
		("metrou",  "44.428 26.103",  "10", "28/05/2014/14/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103", "24", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("metro abonare", "44.427 26.102", "5", "28/05/2014/14/20");
		
		MainActivity.database.addTaskPriori
		("abonament m luna",  "44.43 26.101", "43", "28/05/2014/10/55");
		
		
		MainActivity.database.addTaskPriori
		("metrou ab", "44.428 26.103",  "5", "28/05/2014/13/10");
		
		
		MainActivity.database.addTaskPriori
		("Metrou abonament", "44.428 26.103",  "30", "28/05/2014/16/35");
		
		
	
		
		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "17", "28/05/2014/14/27");
		
		MainActivity.database.addTaskPriori
		("abonament m luna",  "44.43 26.101", "43", "28/05/2014/10/05");
		
		
		MainActivity.database.addTaskPriori
		("metrou ab", "44.428 26.103",  "15", "28/05/2014/13/15");
		
		
		MainActivity.database.addTaskPriori
		("Metrou abonament", "44.428 26.103",  "35", "28/05/2014/16/15");
		
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static DatabaseHandler getDatabase() {
		return database;
	}

	public static void setDatabase(DatabaseHandler database) {
		MainActivity.database = database;
	}

	public static Core getCore() {
		return core;
	}

	public static void setCore(Core core) {
		MainActivity.core = core;
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
		
		 
		 if(mainActivity.MenUApp[arg2].compareTo(mainActivity.getString(R.string.getTask)) == 0)	 
			  intent = new Intent(mainActivity, ShowTasks.class); 
			
		 
		 if(mainActivity.MenUApp[arg2].compareTo(mainActivity.getString(R.string.addTask)) == 0)
			intent = new Intent(mainActivity, AddTask.class);
			
			 
		 if(mainActivity.MenUApp[arg2].compareTo(mainActivity.getString(R.string.schedule)) == 0)
			  intent = new Intent(mainActivity, ShowAllTasks.class);
		 
		 if(mainActivity.MenUApp[arg2].compareTo(mainActivity.getString(R.string.discoverDevice)) == 0)
			  intent = new Intent(mainActivity, AddDeviceActivity.class);
		 
		 if(mainActivity.MenUApp[arg2].compareTo(mainActivity.getString(R.string.current_task)) == 0)
			  intent = new Intent(mainActivity, ShowCurrentTask.class);
		 
		 mainActivity.startActivity(intent);
		 
		
	}
	
}


