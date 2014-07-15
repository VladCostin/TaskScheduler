package ContextElements;

import com.example.meniu.Constants;

/**
 * the duration estimated by the intelligent agent based on the location and hour
 * 
 * @author ${Vlad Herescu}
 *
 */
public class DurationContext extends ContextElement{
	
	private Integer duration;
	
	private Integer temporalDurationTravel;
	
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

	public Integer getTemporalDurationTravel() {
		return temporalDurationTravel;
	}

	public void setTemporalDurationTravel(Integer temporalDurationTravel) {
		this.temporalDurationTravel = temporalDurationTravel;
	}
	
	
	public String getDurationString()
	{
		if(this.duration == -1)
			return Constants.noChoose;
		
		return Integer.toString(duration);
	}



	
	
}
