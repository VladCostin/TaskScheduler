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
    private static final String TABLE_CONTACTS = "tasks";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_Title = "title";
    private static final String KEY_Location = "location";
    private static final String KEY_Date = "date"; 
    private static final String KEY_Priority = "priority"; 
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + 
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    public void addContact(String name, String priority, String location, String date ) {
    	
    	
        SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_Title, name);  
    	values.put(KEY_Priority, priority);
    	values.put(KEY_Location, location);
    	values.put(KEY_Date, date);
    	
    	db.insert(TABLE_CONTACTS, null, values);
    	db.close();
    	
    	Log.w("am introdus niste date si nu a dat eroare", "e totul ok?");
    	
    /*    SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_Location, contact.getLocation()); // Contact Name
        values.put(KEY_Date, contact.getCalendar());
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
        
        
        Log.w("am introdus niste date si nu a dat eroare", "e totul ok?");*/
    }
 
    // Getting single contact
    /**
     * @param id : the id of the record that must be returned
     * @return : the record identified by the id 
     */
    public Task getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = 
        db.query(TABLE_CONTACTS, new String[]
        { KEY_ID, KEY_Title, KEY_Priority,KEY_Location, KEY_Date }, KEY_ID + "=?",
         new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        Task  oneTask = new Task();
        
        oneTask.setPriority(cursor.getString(1));
        oneTask.setPriority(cursor.getString(2));
        
        oneTask.getInternContext().getContextElementsCollection().
        put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationContext(cursor.getString(3)));
        
        
        oneTask.getExternContext().getContextElementsCollection().
        put(ContextElementType.TIME_CONTEXT_ELEMENT, new DeadlineContext(cursor.getString(4)));
        

        
        // return contact
        return oneTask;
    }
     
    // Getting All Contacts
    public List<TaskToDataBase> getAllContacts() {
        List<TaskToDataBase> contactList = new ArrayList<TaskToDataBase>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	TaskToDataBase contact = new TaskToDataBase();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setLocation(cursor.getString(1));
                contact.setCalendar(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
   
 
}