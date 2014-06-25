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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author ${Vlad Herescu}
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	
	
	
	/**
	 * object where the tasks are stored
	 */
	private static DatabaseHandler database;
	
	
	/**
	 * obtaining the general information of the application
	 */
	private static Core core;
	
	
	Button buttonAddTaskActivity;
	
	
	Button buttonViewTasksActivity;
	
	
	Button buttonViewCompatibleTasksActivity;
	
	
	Button buttonViewCurrentTaskActivity;
	
	
	Button buttonSetScheduleActivity;

	
	Button buttonSuggestScheduleActivity;
	
	
	Button buttonDetectDevicesActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		database = new DatabaseHandler(this);
		core = new Core();
		Core.init();
		
		initButtons();
		
		
		
	//	addTasksforCheckDuration(); 
	//	addTaskForCheckSameTitle();
	//	addTaskforCheckDurationDifferentTitle();
	//	addTaskforCheckDurationDifferentTitle_2();
	//  addTaskforCheckDurationSameLocation();
	//  addTaskforCheckDurationDifferentLocations();
		
		
		
		
	//	addTasksDifferentNamesDifferentTimes_abonament_metrou();
	//	addTasksFrizerie();
		
		
		KMeansDuration clustering = new KMeansDuration();
		clustering.calculateKlusters();
		
		
	//	addTasksDifferentTitlesForLocation();
		
		KMeansLocation clusteringLocation = new KMeansLocation();
		clusteringLocation.calculateKlusters();
		
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
	
	
	private void initButtons() {
		
		
		buttonAddTaskActivity = (Button)  findViewById(R.id.buttonActivityAddTask);
		buttonAddTaskActivity.setOnClickListener(this);
		
		
		buttonViewTasksActivity = (Button)  findViewById(R.id.buttonActivityViewTasks);
		buttonViewTasksActivity.setOnClickListener(this);
		
		
		buttonViewCompatibleTasksActivity = (Button)  findViewById(R.id.buttonActivityViewCompatibleTasks);
		buttonViewCompatibleTasksActivity.setOnClickListener(this);
		
		
		buttonViewCurrentTaskActivity =  (Button)  findViewById(R.id.buttonActivityRunningTask);
		buttonViewCurrentTaskActivity.setOnClickListener(this);
		
		
		buttonSetScheduleActivity  =  (Button)  findViewById(R.id.buttonActivityFixedSchedule);
		buttonSetScheduleActivity.setOnClickListener(this);
		
		
		buttonSuggestScheduleActivity = (Button)  findViewById(R.id.buttonActivitySuggestSchedule);
		buttonSuggestScheduleActivity.setOnClickListener(this);
		
		buttonDetectDevicesActivity = (Button)  findViewById(R.id.buttonActivityDetectDevices);
		buttonDetectDevicesActivity.setOnClickListener(this);
	}


	private void addTasksDifferentNamesDifferentTimes_abonament_metrou() {
		
		// metrou unirii
		
		
		//
		//  seara
		/////
				
				
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/17/46");
				
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","55", "28/05/2014/17/20");
				
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","40", "28/05/2014/18/30");
				
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","37", "28/05/2014/17/55");
				
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102","20", "28/05/2014/18/45");	
		
		
		// 
		//	dimineata - pranz
		//
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "12", "28/05/2014/12/30");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "24", "28/05/2014/12/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/11/11");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "17", "28/05/2014/11/33");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "20", "28/05/2014/12/45");
		
		
		//
		// pranz dupa amiaza
		//
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102",  "10", "28/05/2014/14/01");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "7", "28/05/2014/14/35");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "15", "28/05/2014/15/15");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "5", "28/05/2014/14/50");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102",  "20", "28/05/2014/15/53");
		
		
		
		//
		//	dimineata
		//
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou",  "44.427 26.102", "43", "28/05/2014/9/30");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/9/00");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "55", "28/05/2014/8/10");
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "45", "28/05/2014/8/45");
		
		
		MainActivity.database.addTaskPriori
		("abonament metrou", "44.427 26.102", "60", "28/05/2014/8/30");
		
		

		
		
	}
	
	
	
	private void addTasksFrizerie()
	{
		// 	tuns
		
		// dimineata
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie",  "44.4137 26.1057", "15", "28/05/2014/7/20");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "20", "28/05/2014/7/50");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "25", "28/05/2014/8/15");
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "20", "28/05/2014/8/55");
		
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie",  "44.4137 26.1057", "25", "28/05/2014/9/20");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "25", "28/05/2014/9/50");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "20", "28/05/2014/10/15");
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "20", "28/05/2014/10/40");
		
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie",  "44.4137 26.1057", "25", "28/05/2014/11/0");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "35", "28/05/2014/11/35");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "40", "28/05/2014/12/15");
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "30", "28/05/2014/12/40");
		
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie",  "44.4137 26.1057", "25", "28/05/2014/13/10");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "35", "28/05/2014/13/55");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "40", "28/05/2014/14/25");
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "30", "28/05/2014/14/57");
		
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie",  "44.4137 26.1057", "55", "28/05/2014/15/17");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "45", "28/05/2014/15/55");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "40", "28/05/2014/16/28");
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "53", "28/05/2014/16/57");
	}
	
	
	
	public void addTasksDispensar()
	{
		// dispensar
		
		
		MainActivity.database.addTaskPriori
		("la medicul de familie",  "44.4137 26.1057", "55", "28/05/2014/15/17");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "45", "28/05/2014/15/55");
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "40", "28/05/2014/16/28");
		
		
		MainActivity.database.addTaskPriori
		("tuns la frizerie", "44.4137 26.1057", "53", "28/05/2014/16/57");
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


	@Override
	public void onClick(View v) {
		
		Button b = (Button) v;
		Intent intentStartActivity;
		
		if( b == this.buttonAddTaskActivity){
			intentStartActivity = new Intent(this, AddTask.class);
			startActivity(intentStartActivity);
		}

		if( b == this.buttonViewTasksActivity){
			intentStartActivity = new Intent(this, ShowAllTasks.class);
			startActivity(intentStartActivity);
		}
		
		if( b == this.buttonViewCompatibleTasksActivity){
			intentStartActivity = new Intent(this, ShowTasks.class);
			startActivity(intentStartActivity);
		}
		
		if( b == this.buttonViewCurrentTaskActivity){
			intentStartActivity = new Intent(this, ShowCurrentTask.class);
			startActivity(intentStartActivity);
		}
		
		if( b == this.buttonDetectDevicesActivity)
		{
			intentStartActivity = new Intent(this, AddDeviceActivity.class);
			startActivity(intentStartActivity);
		}
		
		if( b == this.buttonSetScheduleActivity)
		{
			intentStartActivity = new Intent(this, SetFixedTask.class);
			startActivity(intentStartActivity);
		}
		
		if( b == this.buttonSuggestScheduleActivity)
		{
			intentStartActivity = new Intent(this, ActivityScheduler.class);
			startActivity(intentStartActivity);
		}
		
		 
		
		
		
		
		
	}

}





