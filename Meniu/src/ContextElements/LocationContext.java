package ContextElements;


import com.google.android.gms.maps.model.LatLng;



/**
 * defines the location where the task must be executed
 * @author ${Vlad Herescu}
 *
 */
public class LocationContext extends ContextElement{
	
	/**
	 * the latitudine of the task's location
	 */
	private Double latitude;
	
	/**
	 * the longitudine of the task's location
	 */
	private Double longitude;
	
	
	
	/**
	 * @param location : the task's location in String, from database
	 */
	public LocationContext(String location) 
	{
	
		String coordonates[] = location.split(" ");
		latitude = Double.valueOf(coordonates[0]);
		longitude = Double.valueOf(coordonates[1]);
		
	}
	/**
	 * @param location : the current user location
	 */
	public LocationContext(LatLng location)
	{
		latitude = location.latitude;
		
		longitude = location.longitude;
	
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	

}
