package DatabaseOperation;

public class FixedTaskInformation {

	private int idTask;
	
	private int startHour;
	
	private int startMinute;
	
	private int endHour;
	
	private int endMinute;
	
	private String dayWeek;
	
	
	private String location;
	
	
	public FixedTaskInformation() {
		// TODO Auto-generated constructor stub
	}
	
	public FixedTaskInformation(int startHour, int startMinute, int endHour, int endMinute){
		
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		
	}
	
	public FixedTaskInformation(int idTask, int startHour, int startMinute, int endHour, int endMinute,String day, String location){
		
		this.idTask      = idTask;
		this.startHour   = startHour;
		this.startMinute = startMinute;
		this.endHour     = endHour;
		this.endMinute   = endMinute;
		this.location    = location; 
		this.dayWeek     = day;
		
	}
	
	
	public FixedTaskInformation(int idTask, int startHour, int startMinute, int endHour, int endMinute, String location){
	
		this.idTask      = idTask;
		this.startHour   = startHour;
		this.startMinute = startMinute;
		this.endHour     = endHour;
		this.endMinute   = endMinute;
		this.location    = location; 

	}

	public int getStartHour() {
		return startHour;
	}


	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}


	public int getStartMinute() {
		return startMinute;
	}


	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}


	public int getEndHour() {
		return endHour;
	}


	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}


	public int getEndMinute() {
		return endMinute;
	}


	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}

	public int getIdTask() {
		return idTask;
	}

	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}

	public String getDayWeek() {
		return dayWeek;
	}

	public void setDayWeek(String dayWeek) {
		this.dayWeek = dayWeek;
	}
	
	
	
	
	
	
}
