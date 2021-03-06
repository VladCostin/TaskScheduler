package DatabaseOperation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 











import com.example.meniu.Constants;

import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.DeviceContext;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import ContextElements.PeopleContext;
import ContextElements.TemporalContext;
import DeviceData.Device;
import Task.Task;
import Task.TaskState;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "Scheduler";
 
    // Contacts table name
    private static final String TABLE_TASKS = "tasks";
    
    private static final String TABLE_DEVICES = "devices";
    
    
    private static final String TABLE_FIXED_TASKS = "fixedTasks";
 

 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    
    public void createTable()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    	addTableComand(db);
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
  
    	addTableComand(db);
    }
    
    
    public void addTableComand(SQLiteDatabase db)
    {
    	 String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASKS + 
         		"(" + Tasks.KEY_ID + " " + TypeAttribute.COLUMN_INTEGER_PK + ","
         			+ Tasks.KEY_Title + " " + TypeAttribute.COLUMN_STRING + ","
         		    + Tasks.KEY_Priority + " " + TypeAttribute.COLUMN_STRING + ","
         		    + Tasks.KEY_Status + " " + TypeAttribute.COLUMN_STRING + ","
         		    + Tasks.KEY_Location + " " + TypeAttribute.COLUMN_STRING + ","
                    + Tasks.KEY_Date + " " + TypeAttribute.COLUMN_STRING + "," 
                    + Tasks.KEY_Device + " " + TypeAttribute.COLUMN_STRING + ","
                    + Tasks.KEY_Duration + " " + TypeAttribute.COLUMN_STRING + ","
                    + Tasks.KEY_Begin_Hour + " " + TypeAttribute.COLUMN_STRING +
                ")";
         
         String CREATE_DEVICE_TABLE = "CREATE TABLE " + TABLE_DEVICES + 
         		"(" + DeviceData.KEY_ID + " " + TypeAttribute.COLUMN_INTEGER_PK + ","
         			+ DeviceData.KEY_MAC + " " + TypeAttribute.COLUMN_STRING + ","
         			+ DeviceData.KEY_DEVICE + " " + TypeAttribute.COLUMN_STRING + ","
                    + DeviceData.KEY_OWNER + " " + TypeAttribute.COLUMN_STRING +  ")";
         
         
         
         String CREATE_FIXED_TASKS_TABLE = "CREATE TABLE " + TABLE_FIXED_TASKS + 
        		 "(" + fixedTasks.KEY_ID + " " + TypeAttribute.COLUMN_INTEGER_PK + ","
        		 	 + fixedTasks.KEY_DAY + " " + TypeAttribute.COLUMN_STRING + ","
        		 	 + fixedTasks.KEY_Location + " " + TypeAttribute.COLUMN_STRING + ","
        		 	 + fixedTasks.KEY_Start_Hour + " " + TypeAttribute.COLUMN_STRING + ","
        		 	 + fixedTasks.KEY_Start_Minute + " " + TypeAttribute.COLUMN_STRING + ","
        		 	 + fixedTasks.KEY_End_Hour + " " + TypeAttribute.COLUMN_STRING + ","
        		 	 + fixedTasks.KEY_End_Minute + " " + TypeAttribute.COLUMN_STRING + 
        		 ")";
        		 
         
         db.execSQL(CREATE_CONTACTS_TABLE);
         db.execSQL(CREATE_DEVICE_TABLE);
         db.execSQL(CREATE_FIXED_TASKS_TABLE); 
    }
    
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     * 
     * adding a task
     * 
     * @param name : task's name	
     * @param priority : task's priority
     * @param location : tasks's location
     * @param date : task's date
     * @param people : the people needed to execute the task
     * @param devices : the devices selected to execute the task
     * @param duration : the duration selected by the user
     * @param beginDurationEstimated : the duration estimated depending on the beginTimes
     */
    public void addTask(String name, String priority, String location, String date,
    				    String devices, String duration, String beginDurationEstimated ) { 

 
    	ContentValues values = new ContentValues();
    	values.put(Tasks.KEY_Title, name);  
    	values.put(Tasks.KEY_Priority, priority);
    	values.put(Tasks.KEY_Status, TaskState.AMONG_TO_DO_LIST.toString());
    	values.put(Tasks.KEY_Location, location);
    	values.put(Tasks.KEY_Date, date);
    	values.put(Tasks.KEY_Device, devices);
    	values.put(Tasks.KEY_Duration, duration); 
     	values.put(Tasks.KEY_Begin_Hour, "");
    	

    	insertRegistration(values, TABLE_TASKS);
    	
    }
    
    
    
    /**
     * used only to add hard coded tasks
     * @param name
     * @param location
     * @param duration
     * @param beginHour
     */
    public void addTaskPriori(String name, String location, String duration, String beginHour, String devices  )
    {
    	ContentValues values = new ContentValues();
    	values.put(Tasks.KEY_Title, name);  
    	values.put(Tasks.KEY_Priority, "");
    	values.put(Tasks.KEY_Status, TaskState.EXECUTED.toString());
    	values.put(Tasks.KEY_Location, location);
    	values.put(Tasks.KEY_Date, "");
    	values.put(Tasks.KEY_Device, devices);
    	values.put(Tasks.KEY_Duration, duration); 
     	values.put(Tasks.KEY_Begin_Hour, beginHour);
     	
     	
    	insertRegistration(values, TABLE_TASKS);
    	
    }
    
    
    /**
     * @param macAddress : devices's mac adress
     * @param nameDevice : device's name
     * @param owner : device's owner
     */
    public void addDevice(String macAddress, String nameDevice, String owner)
    {
    	ContentValues values = new ContentValues();
    	values.put(DeviceData.KEY_MAC, macAddress);
    	values.put(DeviceData.KEY_DEVICE, nameDevice);
    	values.put(DeviceData.KEY_OWNER, owner);
    	insertRegistration(values, TABLE_DEVICES); 

    }
    
    
    /**
     * @param content : the data to be inserted
     * @param nameTable : the table the data will be inserted to
     */
    public void insertRegistration(ContentValues content, String nameTable)
    {
    	  SQLiteDatabase db = this.getWritableDatabase();
    	  db.insert(nameTable, null, content);
          db.close();
          
       //   Log.w("am introdus niste date si nu a dat eroare", "e totul ok?");
    }
    
    
 
    
    /**
     * Getting single task
     * @param id : the id of the record that must be returned
     * @return : the record identified by the id 
     */
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = 
        db.query(TABLE_TASKS, new String[]
        { Tasks.KEY_ID, Tasks.KEY_Title, Tasks.KEY_Priority,Tasks.KEY_Location, Tasks.KEY_Date,
          Tasks.KEY_Device, Tasks.KEY_Duration, Tasks.KEY_Status, Tasks.KEY_Begin_Hour },
        Tasks.KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        

        return takeTaskFromDataBase(cursor);
    }
     


	/**
     * @return  Getting All Contacts
     */
    public List<Task> getAllTasks() { 
        List<Task> contactList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
                contactList.add( takeTaskFromDataBase( cursor) );
            } while (cursor.moveToNext());
        }

        return contactList;
    }
    
    
    /**
     * @param state : the state used to filter the tasks from database
     * @return	: the tasks having the state received as parameter
     */
    public List<Task> getFilteredTasks(ArrayList<TaskState> states)
    {
    	
    	 List<Task> contactList = new ArrayList<Task>();
    	 String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
    	 
    	 
    	 SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);
         
           // looping through all rows and adding to list
         if (cursor.moveToFirst()) {
             do {
               		if( states.contains( TaskState.valueOf(cursor.getString(3)) ))
               			contactList.add( takeTaskFromDataBase( cursor) );
               		
             } while (cursor.moveToNext());
         }

          return contactList;
    	
    	
    }
    
     
    /**
     * @param cursor : object containing the data from the database
     * @return : the object Task
     */
    public Task  takeTaskFromDataBase( Cursor cursor){
    	
    	Task  oneTask = new Task();
    	ArrayList<String> peopleList = new ArrayList<String>();
    	ArrayList<String> deviceList;    	
    
    	oneTask.setID(Integer.parseInt(cursor.getString(0))); 
    	oneTask.setNameTask(cursor.getString(1));
        oneTask.setPriority(cursor.getString(2));
        oneTask.setState(  TaskState.valueOf(cursor.getString(3)));
        oneTask.setStartTime(cursor.getString(8));
        
        
        
          
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationContext(cursor.getString(4)));
        
          
        oneTask.getExternContext().getContextElementsCollection().
        put(ContextElementType.DEADLINE_ELEMENT, new DeadlineContext(cursor.getString(5)));
        

        
        oneTask.getExternContext().getContextElementsCollection().
        put(ContextElementType.TIME_CONTEXT_ELEMENT, new TemporalContext());
        
        
        if(cursor.getString(6).equals(""))
        	deviceList = new ArrayList<String>();
        else
        	deviceList = new ArrayList( Arrays.asList(cursor.getString(6).split(","))   );
        peopleList = determinePeople(deviceList);
        
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.DEVICES_ELEMENT, new DeviceContext(deviceList));
        
        
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.PEOPLE_ELEMENT, new PeopleContext(peopleList));
        
        
        
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.DURATION_ELEMENT, new DurationContext(cursor.getString(7)));
       
        
        return oneTask;
    	   	
    }
    
    /**
     * the record containing the task's information
     * has a list of MAC addresses, without knowing if it is the user's device or 
     * or another's persons
     * @param devicesMac : the list of MAC addresses in the task information
     * @return : the Arraylist of the MAC addresses which belong to other people
     */
    public ArrayList<String> determinePeople(ArrayList<String> devicesMac)
    {
    	ArrayList<String> people = new ArrayList<String>();
    	List<Device> devices = getAllDevices();
    	int position;
    	for(Device device : devices)
    	{
    		if(devicesMac.contains(device.getMacAddress()) &&
    		   device.getOwnerDevice().equals(Constants.myDevice) == false)
    		{
    			position = devicesMac.indexOf(device.getMacAddress());
    			people.add(devicesMac.remove(position));
    		}
    	}
    	
    	return people;
    	
    }
  
  
    /**
     * @param id : the task's id to be deleted
     */
    public void deleteTask(Integer id) { 
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_TASKS, Tasks.KEY_ID + " = ?",new String[] { Integer.toString(id) });
    	db.close();
    
    }
    
    /**
     * @param adressMac : mac address usd to detect the corect registration in the table
     * @return : the Device which has the MacAdress received as a parameter
     */
    public Device  getDeviceData(String adressMac)
    {
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 Cursor cursor = 
         db.query(TABLE_DEVICES, new String[]
         { DeviceData.KEY_ID, DeviceData.KEY_MAC, DeviceData.KEY_DEVICE,DeviceData.KEY_OWNER }, DeviceData.KEY_MAC + "=?",
         new String[] { null , String.valueOf(adressMac) }, null, null, null);
    	 
    	 
    	 if (cursor != null)
             cursor.moveToFirst();
    	 
    	 return createDeviceObject(cursor);
    	 
    }
    
    /**
     * @return : all the devices detected with Bluetooth and saved in the application
     */
    public List<Device> getAllDevices() 
    {
    	 List<Device> contactList = new ArrayList<Device>();
         // Select All Query
         String selectQuery = "SELECT  * FROM " + TABLE_DEVICES;
  
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);
  
         // looping through all rows and adding to list
         if (cursor.moveToFirst()) {
             do {
             	
                 contactList.add( createDeviceObject(cursor) );
             } while (cursor.moveToNext());
         }
  
         // return contact list
         return contactList;
    }
    
    
    
    
    /**
     * @param cursor : object containing the data extracted from the table
     * @return : the object Device completed with the data extracted
     */
    public Device createDeviceObject(Cursor cursor) {
		
    	
    	return new Device( Integer.parseInt( cursor.getString(0)), 
        cursor.getString(1), cursor.getString(2), cursor.getString(3));
    	
	}


	/**
	 * @param idTask : the task to be updated
	 * @param attribute : the attribute whose value is going to be changed
	 * @param stringKeyValue : the new value of the attribute
	 */
	public void updateTask(Integer idTask, String attribute,
			String stringKeyValue) {
		
		  SQLiteDatabase db = this.getWritableDatabase();
		  
		  ContentValues values = new ContentValues();
		  values.put(attribute, stringKeyValue);
		 
		    // updating row
		   db.update(TABLE_TASKS, values, Tasks.KEY_ID + " = ?",
		   new String[] { Integer.toString(idTask) });
		
		
		   db.close();
		
	}


	/**
	 * @param idTask : the task to be updated
	 * @param attributes :  the attributes whose value is going to be changed
	 * @param values :  the new values of the attribute
	 */
	public void updateTask(Integer idTask, ArrayList<String> attributes,
			ArrayList<String> values) {
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		int i, nrUpdatat;
		
		for(i= 0; i < attributes.size(); i++)
			newValues.put(attributes.get(i), values.get(i));
		 
		    // updating row
		 nrUpdatat = db.update(TABLE_TASKS, newValues, Tasks.KEY_ID + " = ?",
		 new String[] { Integer.toString(idTask) });
		 
		
		 db.close();
	}
	
	
	/**
	 * @param dayWeek : the day of the week
	 * @param startHour : the hour when the task starts
	 * @param startMinute : the minute when the task starts
	 * @param endHour : the hour when the task ends
	 * @param endMinute : the minute when the task ends
	 * @param location : the location where it must be executed
	 */
	public void addFixedTask(String dayWeek, String startHour, String startMinute, String endHour, String endMinute, String location)
	{
	
		ContentValues values = new ContentValues();
		values.put(fixedTasks.KEY_DAY, dayWeek);
		values.put(fixedTasks.KEY_Start_Hour, startHour);
		values.put(fixedTasks.KEY_Start_Minute, startMinute);
		values.put(fixedTasks.KEY_End_Hour, endHour);
		values.put(fixedTasks.KEY_End_Minute, endMinute);
		values.put(fixedTasks.KEY_Location, location);
		insertRegistration(values, TABLE_FIXED_TASKS); 
	}
	
	
	
	public ArrayList<FixedTaskInformation> getFixedTasks(String dayWeek)
	{
		 ArrayList<FixedTaskInformation> tasksList = new ArrayList<FixedTaskInformation>(); 
         // Select All Query
         String selectQuery = "SELECT  * FROM " + TABLE_FIXED_TASKS;
  
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);
  
         // looping through all rows and adding to list
         if (cursor.moveToFirst()) {
             do {
             		
             		if(cursor.getString(1).equals(dayWeek))
             			tasksList.add( createFixedTaskObject(cursor) );
             } while (cursor.moveToNext());
         }
  
         db.close();
         return tasksList;
	}


	/**
	 * @param cursor : the object containing the values extracted from the database
	 * @return : a fixed task
	 */
	public FixedTaskInformation createFixedTaskObject(Cursor cursor) {
		FixedTaskInformation task = new FixedTaskInformation();
		
		task.setIdTask(Integer.parseInt( cursor.getString(0)));
		task.setDayWeek(cursor.getString(1)); 
		task.setLocation("44.4384,26.049");
		task.setStartHour(Integer.parseInt(cursor.getString(3)));
		task.setStartMinute(Integer.parseInt(cursor.getString(4)));
		
		task.setEndHour(Integer.parseInt(cursor.getString(5)));
		task.setEndMinute(Integer.parseInt(cursor.getString(6)));
		
		
		
		return task;
	}
	

	/**
	 * @param idTask : the task to be updated
	 * @param attributes :  the attributes whose value is going to be changed
	 * @param values :  the new values of the attribute
	 */
	public void updateFixedTask(Integer idTask, ArrayList<String> attributes,
			ArrayList<String> values) {
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		int i;
		
		for(i= 0; i < attributes.size(); i++)
			newValues.put(attributes.get(i), values.get(i));
		
		 
		    // updating row
		 db.update(TABLE_FIXED_TASKS, newValues, Tasks.KEY_ID + " = ?",
		 new String[] { Integer.toString(idTask) });
		 
		 db.close();
		
	}


	/**
	 * @param idTask : the task's id to be deleted
	 */
	public void deleteFixedTask(Integer idTask) {
		
		SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_FIXED_TASKS, fixedTasks.KEY_ID + " = ?",new String[] { Integer.toString(idTask) });
    	db.close();
		
	}
	
	/**
	 * @param idTask : the task to be updated
	 * @param attribute : the attribute whose value is going to be changed
	 * @param stringKeyValue : the new value of the attribute
	 */
	public void updateDevice(Integer idDevice, String attribute,
			String stringKeyValue) {
		
		  SQLiteDatabase db = this.getWritableDatabase();
		  
		  ContentValues values = new ContentValues();
		  values.put(attribute, stringKeyValue);
		 
		    // updating row
		   db.update(TABLE_DEVICES, values, DeviceData.KEY_ID + " = ?",
		   new String[] { Integer.toString(idDevice) });
		
		
		   db.close();
		
	}
	
	
	/**
	 * @param idDevice : the id of the device to be removed
	 */
	public void deleteDevice(Integer idDevice)
	{
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_DEVICES, DeviceData.KEY_ID + " = ?",new String[] { Integer.toString(idDevice) });
    	db.close();
	}
	
	
	
    
}