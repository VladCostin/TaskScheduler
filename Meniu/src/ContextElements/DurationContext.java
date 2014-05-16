package ContextElements;

/**
 * the duration estimated by the intelligent agent based on the location and hour
 * 
 * @author ${Vlad Herescu}
 *
 */
public class DurationContext extends ContextElement{
	
	private Integer duration;
	
	public DurationContext(Integer duration)
	{
		this.duration = duration;
	}
	
	public DurationContext(String duration)
	{
		this.duration = Integer.parseInt(duration);
	}
	


	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}



	
	
}
