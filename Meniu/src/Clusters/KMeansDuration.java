package Clusters;

import java.util.ArrayList;
import java.util.Random;

import ContextElements.ContextElementType;
import ContextElements.LocationContext;
import Task.Task;
import Task.TaskState;

import com.example.meniu.MainActivity;
import com.google.android.gms.maps.model.LatLng;

/**
 * calculates the centers of the clusters
 * and compares the data from the task to be executed
 * to detect the most alike center
 * @author ${Vlad Herescu}
 *
 */
public class KMeansDuration implements KMeans{
	
	/**
	 * the number of clusters to be calculated
	 */
	int nrClusters;
	/**
	 * the new centers of the clusters
	 */
	ArrayList<Integer> idNewCentroid;
	/**
	 * the old centers of the clusters
	 */
	ArrayList<Integer> idCentroid;
	/**
	 * the tasks with the status executed
	 */
	ArrayList<Task> tasks; 
	/**
	 * the calculated centers
	 */
	ArrayList<Task> centroizi;
	
	
	
	
	/**
	 * parameter to get the title distance to the same value as the locationDistance 
	 */
	int sameNumitorTitle;
	/**
	 * parameter to get the location Distance to the same value as the other distances
	 */
	int sameNumitorLocation;
	/**
	 * parameter to get the start time distance to the same value as the locationDistance
	 */
	int sameNumitorStartTime;
	
	
	
	
	/**
	 * inits the members
	 */
	public KMeansDuration() {
		nrClusters = 3;
		
		sameNumitorTitle = 200; // in case max LocationDistance = 20000 and title is 100
		sameNumitorLocation = 1;
		sameNumitorStartTime = 28; // in case max LocationDistance = 20000 and start time is 720
	
		
		idCentroid = new ArrayList<Integer>();
		centroizi = new ArrayList<Task>();
		idNewCentroid = new ArrayList<Integer>();
		
		

		
	}
	

	@Override
	public void receiveData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculateKlusters() {
		
		int distanceMax; 
		int distance, i;
		int centroidNearest = 0;

		
		tasks =  (ArrayList<Task>)  MainActivity.getDatabase().getFilteredTasks(TaskState.EXECUTED);

		
		System.out.println( "DIMENSIUNEA ESTE   " + tasks.size());
		
		
		chooseCentroid();
		
		System.out.println("AFISEZ INDICII CENTROIZILOR");
		
		for(Task task : tasks)
		{
			distanceMax = 1000000000;
			for(Task taskCentroid : centroizi)
			{
				distance = calculateDistance(task, taskCentroid) ;
				if(distance < distanceMax)
				{
					distanceMax = distance;
					centroidNearest = centroizi.indexOf(taskCentroid);
				}
			}
			
			
			idCentroid.add(centroidNearest);
			
		}
		while(true)
		{
			calculateNewCentroizi();
		
			for(Task task : tasks)
			{
				distanceMax = 1000000000;
				for(Task taskCentroid : centroizi)
				{
					distance = calculateDistance(task, taskCentroid) ;
					if(distance < distanceMax)
					{
						distanceMax = distance;
						centroidNearest = centroizi.indexOf(taskCentroid);
					}
				}
			
			
				idNewCentroid.add(centroidNearest);
			
			}
		
		
		
			if(checkCentroiziNotChanged() == true)
				break;
		
		}
		
		System.out.println("AM TERMINAT DE CALCULAT CENTROIZII");
		
		for(i = 0; i < tasks.size(); i++)
		{
			System.out.println(idCentroid.get(i) +  " " + idNewCentroid.get(i) );
		}
		
	}
	
	

	@Override
	public void calculateNewCentroizi() {
		
		int nrPoints;
		int i, taskPos;
		double latitude,longitude;
		int minutes;
		int hours;
		
		System.out.println("Intra IAR AICI");
		
		
		for(i = 0 ; i < nrClusters; i++)
		{
			
		/*	LocationContext locationinainte = (LocationContext)
			centroid.getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			
			System.out.println(i + " " + centroid.getNameTask() + " " + centroid.getStartTime() + " " + locationinainte.getLatitude() + " " + locationinainte.getLongitude()); 
		*/	
			
			
			nrPoints = 0;
			latitude = 0;
			longitude = 0;
			minutes = 0;
			for(taskPos = 0; taskPos < tasks.size(); taskPos++)
			{
				if(idCentroid.get(taskPos) == i)
				{
					nrPoints++;
					LocationContext location = (LocationContext)  tasks.get(taskPos).getInternContext().
					getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
					latitude += location.getLatitude();
					longitude += location.getLongitude();
					
					
					String dataValues[] = tasks.get(taskPos).getStartTime().split("/");
					minutes += Integer.parseInt( dataValues[3]) * 60 + Integer.parseInt( dataValues[4]);
					
				}
			}
			latitude = latitude / (double) nrPoints;
			longitude = longitude / (double) nrPoints;
			minutes = minutes / nrPoints;
			
			
			hours = minutes / 60;
			minutes = minutes - hours * 60;
			
		
			Task centroid =  centroizi.get(i);
			centroid.getInternContext().getContextElementsCollection().
			put(ContextElementType.LOCATION_CONTEXT_ELEMENT, new LocationContext(new LatLng(latitude, longitude)));
			
			
			String dataValues[] = centroizi.get(i).getStartTime().split("/");
			centroid.setNameTask(centroizi.get(i).getNameTask()); 
			centroid.setStartTime(dataValues[0]+"/"+dataValues[1]+"/"+dataValues[2]+"/"+hours+"/"+minutes);
			
		/*System.out.println( i + "  " + nrPoints + " " + latitude + " " + longitude + " " + hours+"/"+minutes);
			centroid =  centroizi.get(i);
			locationinainte = (LocationContext)
			centroid.getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			
			System.out.println(i + " " + centroid.getNameTask() + " " + centroid.getStartTime() + " " + locationinainte.getLatitude() + " " + locationinainte.getLongitude()); 
		*/
		}
		
		
		
	}
	
	

	@Override
	public boolean checkCentroiziNotChanged() {
		
		int i;
		for(i = 0; i < tasks.size(); i++)
		{
		//	System.out.println(idCentroid.get(i) +  " " + idNewCentroid.get(i) );
			if(idCentroid.get(i) != idNewCentroid.get(i) )
			{
				idCentroid.clear();
				idCentroid.addAll(idNewCentroid);
				idNewCentroid.clear();
				
				return false;
			}
		}
		
		
	
		
		return true;
		
	}


	@Override
	public void chooseCentroid() {
		int iCentroid, idTask, iCentroidByNow;
		ArrayList<Integer> idCentroiziChosen = new ArrayList<Integer>();
		int distanceMinim;
		int distanceMaxim;
		
		int distanceCalculated;
		int newCentroidIndex = 0;
		Random r = new Random();
		idCentroiziChosen.add( r.nextInt(tasks.size()));
		
		
		for(iCentroid= 1; iCentroid < nrClusters; iCentroid++)
		{
			
			distanceMaxim = -1;
			
			for(idTask = 0; idTask < tasks.size(); idTask++)
			{
				distanceMinim =  2000000;
				
				
				if(idCentroiziChosen.contains(idTask)  == false )
				{				
					
					for(iCentroidByNow = 0; iCentroidByNow < idCentroiziChosen.size(); iCentroidByNow++)
					{

						
						
						distanceCalculated =
						calculateDistance(tasks.get(idTask),tasks.get(idCentroiziChosen.get(iCentroidByNow))   );
						
						
						if(distanceCalculated < distanceMinim)
							distanceMinim = distanceCalculated;
						
					}				
					if(distanceMinim  > distanceMaxim )
					{
						distanceMaxim = distanceMinim;
						newCentroidIndex = idTask;
					}
				}
				
				
				
			}
			
			
		//	System.out.println("DISTANTA MAXIMA NOUA ESTE " + distanceMaxim);
			idCentroiziChosen.add(newCentroidIndex);
		
					
		}
		
		
		System.out.println(idCentroiziChosen.toString());
		for(iCentroid = 0; iCentroid< idCentroiziChosen.size(); iCentroid++)
		{
			System.out.println(tasks.get(idCentroiziChosen.get(iCentroid)).getStartTime() + "   ");
			System.out.println(tasks.get(idCentroiziChosen.get(iCentroid)).getNameTask() + "   ");
			
			
			Task t = new Task();
			t.setNameTask(tasks.get(idCentroiziChosen.get(iCentroid)).getNameTask());
			t.setStartTime(tasks.get(idCentroiziChosen.get(iCentroid)).getStartTime());
			
			
			LocationContext location = (LocationContext)
			tasks.get(idCentroiziChosen.get(iCentroid)).getInternContext().
			getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			
			
			t.getInternContext().getContextElementsCollection().
			put(ContextElementType.LOCATION_CONTEXT_ELEMENT, location);
			
			
			centroizi.add(t);
			
			
		}
		

	}

	@Override
	public int calculateDistance(Task t1, Task t2) {
		
		
		
		int stringDistance =   KMeansDistances.calculateDistanceTitles(t1.getNameTask(), t2.getNameTask());
		int timeDistance =    KMeansDistances.calculateDistanceStartTime(t1.getStartTime(), t2.getStartTime());
		int locationDistance = KMeansDistances.calculateDistanceLocation(t1, t2); 
		//	System.out.println("distanta intre " + t1.getNameTask() + " ----  " + t2.getNameTask() + " : " +  stringDistance);
		//System.out.println("distanta intre " + t1.getStartTime() + " ----  " + t2.getStartTime() + " : " +  timeDistance);
		//System.out.println("distanta intre locatii intre " + t1.getNameTask() + " ----  " + t2.getNameTask() + " : " +  locationDistance);
		
		return timeDistance * sameNumitorStartTime + 
		stringDistance * sameNumitorTitle + locationDistance *sameNumitorLocation  ;
	}



	@Override
	public void detectCentroid() {
		// TODO Auto-generated method stub
		
	}
	



}



