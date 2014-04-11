package DatabaseOperation;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
 











import ContextElements.ContextElementType;
import ContextElements.DeadlineContext;
import ContextElements.LocationContext;
import ContextElements.TemporalContext;
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
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_Title = "title";
    private static final String KEY_Location = "location";
    private static final String KEY_Date = "date"; 
    private static final String KEY_Priority = "priority"; 
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    
    public void createTable()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASKS + 
        		"(" + KEY_ID + " INTEGER PRIMARY KEY," 
        			+ KEY_Title + " TEXT,"
        		    + KEY_Priority + " TEXT, "
        		    + KEY_Location + " TEXT,"
                    + KEY_Date + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASKS + 
        		"(" + KEY_ID + " INTEGER PRIMARY KEY," 
        			+ KEY_Title + " TEXT,"
        		    + KEY_Priority + " TEXT, "
        		    + KEY_Location + " TEXT,"
                    + KEY_Date + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     * 
     * adding a 
     * 
     * @param name 
     * @param priority 
     * @param location  
     * @param date 
     */
 
   
    public void addTask(String name, String priority, String location, String date ) { 
    	
    	
        SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_Title, name);  
    	values.put(KEY_Priority, priority);
    	values.put(KEY_Location, location);
    	values.put(KEY_Date, date);
    	
    	db.insert(TABLE_TASKS, null, values);
    	db.close();
    	
    	Log.w("am introdus niste date si nu a dat eroare", "e totul ok?");
    	
    }
 
    // Getting single contact
    /**
     * @param id : the id of the record that must be returned
     * @return : the record identified by the id 
     */
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = 
        db.query(TABLE_TASKS, new String[]
        { KEY_ID, KEY_Title, KEY_Priority,KEY_Location, KEY_Date }, KEY_ID + "=?",
         new String[] { String.valueOf(id) }, null, null, null, null);
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
    	
    	System.out.println(cursor.getColumnCount());
    	int i,n = cursor.getColumnCount();
    	
    	for(i=0; i < n; i++)
    		System.out.print(cursor.getString(i) + " ");
    	
    	System.out.println("\n\n");
    	
    	Task  oneTask = new Task();
    	oneTask.setID(Integer.parseInt(cursor.getString(0))); 
    	oneTask.setNameTask(cursor.getString(1));
        oneTask.setPriority(cursor.getString(2));
          
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationContext(cursor.getString(3)));
          
          
        oneTask.getExternContext().getContextElementsCollection().
        put(ContextElementType.TIME_CONTEXT_ELEMENT, new DeadlineContext(cursor.getString(4)));
        

        
        return oneTask;
    	
    	
    }
  
  
    /**
     * @param id : the task's id to be deleted
     */
    public void deleteContact(Integer id) {
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	//db.delete(TABLE_TASKS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
    	
    	db.delete(TABLE_TASKS, KEY_ID + " = ?",new String[] { Integer.toString(id) });
    	
    	
    	db.close();
    
    }
    
}