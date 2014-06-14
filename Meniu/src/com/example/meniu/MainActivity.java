package com.example.meniu;



import java.util.List;

import Clusters.KMeansDuration;
import Clusters.KMeansLocation;
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
	static final String[] MenUApp = new String[7];
	
	
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
	    MenUApp[5] = 	this.getString(R.string.activity_fixed_Tasks);
	    MenUApp[6] =	this.getString(R.string.activity_Scheduler);
		
		listaMeniu = (ListView) this.findViewById(R.id.ListView1);
		listaMeniu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,MenUApp));
		listaMeniu.setOnItemClickListener( new MenuItemTouched(this) );
		
		
	//	addTasksforCheckDuration(); 
	//	addTaskForCheckSameTitle();
	//	addTaskforCheckDurationDifferentTitle();
	//	addTaskforCheckDurationDifferentTitle_2();
	 // addTaskforCheckDurationSameLocation();
	//  addTaskforCheckDurationDifferentLocations();
		
		
		
	//	KMeansDuration clustering = new KMeansDuration();
	//	clustering.calculateKlusters();
		
		
	//	addTasksDifferentTitlesForLocation();
		
	//	KMeansLocation clustering = new KMeansLocation();
	//	clustering.calculateKlusters();
		
	//	clustering.titlesOfCenters();
		
	//	System.out.println("DIMENSIUNEA ESTE : " + database.getAllTasks().size());
	/*	database.getReadableDatabase().execSQL("DROP TABLE IF EXISTS tasks");
		database.getReadableDatabase().execSQL("DROP TABLE IF EXISTS devices");
		database.getReadableDatabase().execSQL("DROP TABLE IF EXISTS fixedTasks");
		database.createTable();
	*/	
		
	/*	List<Device> devices = database.getAllDevices();
		for(Device d : devices)
		{
			System.out.println(d.getNameDevice() + " " + d.getOwnerDevice() + " " +d.getMacAddress());
		}*/
		
		
		 
	}
	
	
	private void addTaskforCheckDurationDifferentLocations() {
		// metrou unirii
		
		
		//
		// dupa amiaza - seara
		/////
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrout","44.427 26.102", "55", "28/05/2014/17/00");
		
		MainActivity.database.addTaskPriori
		("Metrou abonament", "44.427 26.102",  "40", "28/05/2014/17/25");
		
	
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/17/35");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "35", "28/05/2014/18/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","35", "28/05/2014/18/25");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/17/46");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","20", "28/05/2014/17/20");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/30");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","37", "28/05/2014/17/55");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","32", "28/05/2014/17/10");
		
		
		
		
		// intre dimineata 
		// si pranz
		

		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "25", "28/05/2014/10/45");

		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/11/15");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "25", "28/05/2014/11/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "12", "28/05/2014/12/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "24", "28/05/2014/12/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/11/11");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "17", "28/05/2014/11/22");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "14", "28/05/2014/10/55");
		

		
		///////	
		// dimineata
		////////
				
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "43", "28/05/2014/9/30");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/8/00");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "55", "28/05/2014/8/10");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/8/45");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "35", "28/05/2014/9/10");
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "66", "28/05/2014/7/55");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "47", "28/05/2014/8/25");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "72", "28/05/2014/9/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "42", "28/05/2014/9/5");
		
		
		
		
		/////////
		// pranz
		////////////////
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "15", "28/05/2014/14/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/14/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "6", "28/05/2014/15/05");
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "5", "28/05/2014/14/50");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "7", "28/05/2014/14/53");
		

		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "5", "28/05/2014/14/57");
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "15", "28/05/2014/15/25");
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "1", "28/05/2014/14/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "2", "28/05/2014/14/22");
		

		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "5", "28/05/2014/14/33");
		
		
		
		//
		// politehnica
		//
		
		
		//seara
	
		MainActivity.database.addTaskPriori
		("abonament metrout","44.434 26.054", "55", "28/05/2014/17/00");
		
		MainActivity.database.addTaskPriori
		("Metrou abonament", "44.434 26.054",  "40", "28/05/2014/17/25");
		
	
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "45", "28/05/2014/17/35");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "35", "28/05/2014/18/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054","35", "28/05/2014/18/25");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054","40", "28/05/2014/17/46");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054","20", "28/05/2014/17/20");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054","40", "28/05/2014/18/30");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054","37", "28/05/2014/17/55");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054","32", "28/05/2014/17/10");
		
		
		
		
		// intre dimineata 
		// si pranz
		

		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054",  "25", "28/05/2014/10/45");

		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "20", "28/05/2014/11/15");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "25", "28/05/2014/11/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "12", "28/05/2014/12/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "24", "28/05/2014/12/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "20", "28/05/2014/11/11");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "17", "28/05/2014/11/22");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "20", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "14", "28/05/2014/10/55");
		

		
		///////	
		// dimineata
		////////
				
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "43", "28/05/2014/9/30");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "60", "28/05/2014/8/00");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "55", "28/05/2014/8/10");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "45", "28/05/2014/8/45");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054",  "35", "28/05/2014/9/10");
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "66", "28/05/2014/7/55");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "47", "28/05/2014/8/25");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "72", "28/05/2014/9/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054",  "42", "28/05/2014/9/5");
		
		
		
		
		/////////
		// pranz
		////////////////
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054",  "15", "28/05/2014/14/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054", "20", "28/05/2014/14/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "6", "28/05/2014/15/05");
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "5", "28/05/2014/14/50");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054",  "7", "28/05/2014/14/53");
		

		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.434 26.054", "5", "28/05/2014/14/57");
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "15", "28/05/2014/15/25");
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.434 26.054", "1", "28/05/2014/14/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.434 26.054",  "2", "28/05/2014/14/22");
		

		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.434 26.054", "5", "28/05/2014/14/33");
		
	}


	public void addTasksDifferentTitlesForLocation() {


		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "35", "28/05/2014/10/34");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "12", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrout","44.427 26.102", "55", "28/05/2014/16/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "65", "28/05/2014/09/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "6", "28/05/2014/14/35");
		
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/13/35");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "10", "28/05/2014/14/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "24", "28/05/2014/12/05");
		
		
		


		
		
		
		
		
		
		
		
		
		
		
		MainActivity.database.addTaskPriori
		("proiect licenta prezentare", "44.438 26.049", "45", "28/05/2014/17/21");
		
		MainActivity.database.addTaskPriori
		("proiect licenta lucru",  "44.438 26.049",  "35", "28/05/2014/10/34");
		
		
		MainActivity.database.addTaskPriori
		("proiect licenta discutie", "44.438 26.049", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("proiect licenta prezentare",  "44.438 26.049", "12", "28/05/2014/12/05");
		
		

		
		
		
		
		
		MainActivity.database.addTaskPriori
		("proiect licenta lucru", "44.438 26.049","40", "28/05/2014/18/21");
		
		MainActivity.database.addTaskPriori
		("proiect licenta", "44.438 26.049", "20", "28/05/2014/13/35");
		
		
		MainActivity.database.addTaskPriori
		("proiect licenta",  "44.438 26.049",  "10", "28/05/2014/14/23");
		
		
		MainActivity.database.addTaskPriori
		("proiect licenta",  "44.438 26.049", "24", "28/05/2014/12/05");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		MainActivity.database.addTaskPriori
		("alergat parc", "44.41 26.105", "45", "28/05/2014/17/21");
		
		MainActivity.database.addTaskPriori
		("alergat parc",  "44.41 26.105",  "35", "28/05/2014/10/34");
		
		
		MainActivity.database.addTaskPriori
		("parc alergare", "44.41 26.105", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("alergare paaarc",  "44.41 26.105", "12", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("alergareee parc","44.41 26.105", "55", "28/05/2014/16/05");
		
		MainActivity.database.addTaskPriori
		("la alergat parc", "44.41 26.105",  "65", "28/05/2014/09/10");
		

		
		

		
		
	}


	private void addTaskforCheckDurationSameLocation()
	{
		
		// metrou unirii
		
		
		//
		// dupa amiaza - seara
		/////
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrout","44.427 26.102", "55", "28/05/2014/17/00");
		
		MainActivity.database.addTaskPriori
		("Metrou abonament", "44.427 26.102",  "40", "28/05/2014/17/25");
		
	
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/17/35");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "35", "28/05/2014/18/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","35", "28/05/2014/18/25");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/17/46");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","20", "28/05/2014/17/20");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/30");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","37", "28/05/2014/17/55");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","32", "28/05/2014/17/10");
		
		
		
		
		// intre dimineata 
		// si pranz
		

		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "25", "28/05/2014/10/45");

		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/11/15");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "25", "28/05/2014/11/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "12", "28/05/2014/12/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "24", "28/05/2014/12/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/11/11");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "17", "28/05/2014/11/22");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "14", "28/05/2014/10/55");
		

		
		///////	
		// dimineata
		////////
				
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "43", "28/05/2014/9/30");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/8/00");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "55", "28/05/2014/8/10");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/8/45");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "35", "28/05/2014/9/10");
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "66", "28/05/2014/7/55");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "47", "28/05/2014/8/25");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "72", "28/05/2014/9/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "42", "28/05/2014/9/5");
		
		
		
		
		/////////
		// pranz
		////////////////
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "15", "28/05/2014/14/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/14/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "6", "28/05/2014/15/05");
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "5", "28/05/2014/14/50");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "7", "28/05/2014/14/53");
		

		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "5", "28/05/2014/14/57");
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "15", "28/05/2014/15/25");
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "1", "28/05/2014/14/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "2", "28/05/2014/14/22");
		

		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "5", "28/05/2014/14/33");
		
		
		
		
		
		
		

		

	}
	
	
	private void addTaskforCheckDurationDifferentTitle_2() {
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/17/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101",  "35", "28/05/2014/10/34");
		
		
		MainActivity.database.addTaskPriori
		("abonament tramvai",  "44.428 26.103", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103", "12", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrout","44.427 26.102", "55", "28/05/2014/16/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.43 26.101",  "65", "28/05/2014/09/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament tramvai", "44.428 26.103", "6", "28/05/2014/14/35");
		
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101", "20", "28/05/2014/13/35");
		
		
		MainActivity.database.addTaskPriori
		("abonament tramvai",  "44.428 26.103",  "10", "28/05/2014/14/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103", "24", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "5", "28/05/2014/14/20");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101", "30", "28/05/2014/10/55");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103",  "5", "28/05/2014/13/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament tramvai", "44.428 26.103",  "30", "28/05/2014/16/35");
		
		
	
		
		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "17", "28/05/2014/14/27");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101", "43", "28/05/2014/10/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103",  "15", "28/05/2014/13/15");
		
		
		MainActivity.database.addTaskPriori
		("abonament tramvai", "44.428 26.103",  "35", "28/05/2014/16/15");
		
		
	}


	private void addTaskforCheckDurationDifferentTitle() {
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/17/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "35", "28/05/2014/10/34");
		
		
		MainActivity.database.addTaskPriori
		("cumparaturi metro",  "44.427 26.102", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "12", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrout","44.427 26.102", "55", "28/05/2014/16/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "65", "28/05/2014/09/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("cumparaturi metro", "44.427 26.102", "6", "28/05/2014/14/35");
		
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/13/35");
		
		
		MainActivity.database.addTaskPriori
		("cumparaturi metro",  "44.427 26.102",  "10", "28/05/2014/14/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "24", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "5", "28/05/2014/14/20");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "30", "28/05/2014/10/55");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "5", "28/05/2014/13/10");
		
		
		MainActivity.database.addTaskPriori
		("cumparaturi metro", "44.427 26.102",  "30", "28/05/2014/16/35");
		
		
	
		
		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "17", "28/05/2014/14/27");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "43", "28/05/2014/10/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "15", "28/05/2014/13/15");
		
		
		MainActivity.database.addTaskPriori
		("cumparaturi metro", "44.427 26.102",  "35", "28/05/2014/16/15");
		
	}


	private void addTaskForCheckSameTitle()
	{
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/17/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101",  "35", "28/05/2014/10/34");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103", "15", "28/05/2014/11/46");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103", "12", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrout","44.427 26.102", "55", "28/05/2014/16/05");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.43 26.101",  "65", "28/05/2014/09/10");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103", "60", "28/05/2014/8/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103", "6", "28/05/2014/14/35");
		
		
		
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/21");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101", "20", "28/05/2014/13/35");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103",  "10", "28/05/2014/14/23");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.428 26.103", "24", "28/05/2014/12/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "5", "28/05/2014/14/20");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101", "30", "28/05/2014/10/55");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103",  "5", "28/05/2014/13/10");
		
		
		MainActivity.database.addTaskPriori
		("Metrou abonament", "44.428 26.103",  "30", "28/05/2014/16/35");
		
		
	
		
		
		MainActivity.database.addTaskPriori
		("metrou aonament", "44.427 26.102", "17", "28/05/2014/14/27");
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.43 26.101", "43", "28/05/2014/10/05");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103",  "15", "28/05/2014/13/15");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.428 26.103",  "35", "28/05/2014/16/15");
	}

	private void addTasksforCheckDuration() {
		
		
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
		("abonament m luna",  "44.43 26.101", "30", "28/05/2014/10/55");
		
		
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
		 
		 if(mainActivity.MenUApp[arg2].compareTo(mainActivity.getString(R.string.activity_fixed_Tasks)) == 0)
			  intent = new Intent(mainActivity, SetFixedTask.class);
		 
		 if(mainActivity.MenUApp[arg2].compareTo(mainActivity.getString(R.string.activity_Scheduler)) == 0)
			  intent = new Intent(mainActivity, ActivityScheduler.class);
		 
		 
		 mainActivity.startActivity(intent);
		 
		
	}
	
}


