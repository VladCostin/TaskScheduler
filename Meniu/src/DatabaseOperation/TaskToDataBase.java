package DatabaseOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;

public class TaskToDataBase {
	
	private int id;
	
	private String location;
	
	private String calendar;
	

	public TaskToDataBase(String location, String calendar) {
		this.location = location;
		this.calendar = calendar;
	}
	
	public TaskToDataBase() {
		// TODO Auto-generated constructor stub
	}

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		
		this.calendar = calendar;
		
	/*	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		date = sdf.parse(calendar);
		this.calendar = date;
		
		*/
		
		
		
		
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
