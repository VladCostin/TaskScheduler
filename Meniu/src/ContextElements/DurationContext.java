package ContextElements;

/**
 * the duration estimated by the intelligent agent based on the location and hour
 * 
 * @author ${Vlad Herescu}
 *
 */
public class DurationContext extends ContextElement{
	
	private String duration;
	
	public DurationContext(String duration)
	{
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}



	
	
}
