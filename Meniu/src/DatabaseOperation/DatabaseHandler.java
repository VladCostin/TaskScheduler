package DatabaseOperation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 




import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.DeviceContext;
import ContextElements.LocationContext;
import ContextElements.PeopleContext;
import ContextElements.TemporalContext;
import DeviceData.Device;
import Task.Task;
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
         			+ Tasks.KEY_Title + " " + TypeAttribute.COLLUMN_STRING + ","
         		    + Tasks.KEY_Priority + " " + TypeAttribute.COLLUMN_STRING + ","
         		    + Tasks.KEY_Location + " " + TypeAttribute.COLLUMN_STRING + ","
                    + Tasks.KEY_Date + " " + TypeAttribute.COLLUMN_STRING + ","
                    + Tasks.KEY_People + " " + TypeAttribute.COLLUMN_STRING + "," 
                    + Tasks.KEY_Device + " " + TypeAttribute.COLLUMN_STRING + 
                ")";
         
         String CREATE_DEVICE_TABLE = "CREATE TABLE " + TABLE_DEVICES + 
         		"(" + DeviceData.KEY_ID + " " + TypeAttribute.COLUMN_INTEGER_PK + ","
         			+ DeviceData.KEY_MAC + " " + TypeAttribute.COLLUMN_STRING + ","
         			+ DeviceData.KEY_DEVICE + " " + TypeAttribute.COLLUMN_STRING + ","
                    + DeviceData.KEY_OWNER + " " + TypeAttribute.COLLUMN_STRING +  ")";
         
         db.execSQL(CREATE_CONTACTS_TABLE);
         db.execSQL(CREATE_DEVICE_TABLE);
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
     */
    public void addTask(String name, String priority, String location, String date, String people , String devices) { 
 
    	ContentValues values = new ContentValues();
    	values.put(Tasks.KEY_Title, name);  
    	values.put(Tasks.KEY_Priority, priority);
    	values.put(Tasks.KEY_Location, location);
    	values.put(Tasks.KEY_Date, date);
    	values.put(Tasks.KEY_People, people); 
    	values.put(Tasks.KEY_Device, devices);
    	

    	insertRegistreation(values, TABLE_TASKS);
    	
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
    	
    	insertRegistreation(values, TABLE_DEVICES);

    }
    
    
    /**
     * @param content : the data to be inserted
     * @param nameTable : the table the data will be inserted to
     */
    public void insertRegistreation(ContentValues content, String nameTable)
    {
    	  SQLiteDatabase db = this.getWritableDatabase();
    	  db.insert(nameTable, null, content);
          db.close();
          
          Log.w("am introdus niste date si nu a dat eroare", "e totul ok?");
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
        { Tasks.KEY_ID, Tasks.KEY_Title, Tasks.KEY_Priority,Tasks.KEY_Location, Tasks.KEY_Date, Tasks.KEY_People, Tasks.KEY_Device },
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
 
        // return contact list
        return contactList;
    }
    
     
    /**
     * @param cursor : object containing the data from the database
     * @return : the object Task
     */
    public Task  takeTaskFromDataBase( Cursor cursor){
    	
    	Task  oneTask = new Task();
    	ArrayList<String> peopleList;
    	 ArrayList<String> deviceList;
    	
    	System.out.println(cursor.getColumnCount());
    	int i,n = cursor.getColumnCount();
    	
/*    	for(i=0; i < n; i++)
    		System.out.print(cursor.getString(i) + " ");
    	
    	System.out.println("\n\n");*/
    	
    
    	oneTask.setID(Integer.parseInt(cursor.getString(0))); 
    	oneTask.setNameTask(cursor.getString(1));
        oneTask.setPriority(cursor.getString(2));
        
        
          
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationContext(cursor.getString(3)));
          
        oneTask.getExternContext().getContextElementsCollection().
        put(ContextElementType.DEADLINE_ELEMENT, new DeadlineContext(cursor.getString(4)));
        
        oneTask.getExternContext().getContextElementsCollection().
        put(ContextElementType.TIME_CONTEXT_ELEMENT, new TemporalContext());
        
        

        peopleList = new ArrayList( Arrays.asList(cursor.getString(5).split(","))   );
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.PEOPLE_ELEMENT, new PeopleContext(peopleList));
        
        deviceList = new ArrayList( Arrays.asList(cursor.getString(6).split(","))   );
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.DEVICES_ELEMENT, new DeviceContext(deviceList));
       
       
   //     System.out.println( "Oamenii necesari sunt " + peopleList.toString());
    //    System.out.println("Dispozitivele necesare sunt" + deviceList.toString());

        
        return oneTask;
    	   	
    }
  
  
    /**
     * @param id : the task's id to be deleted
     */
    public void deleteContact(Integer id) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	//db.delete(TABLE_TASKS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
    	
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
    
}