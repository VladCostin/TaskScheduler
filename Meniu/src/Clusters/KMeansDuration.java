package Clusters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ContextElements.ContextElementType;
import ContextElements.LocationContext;
import Task.Task;
import Task.TaskState;
import android.location.Location;

import com.example.meniu.MainActivity;
import com.google.android.gms.maps.model.LatLng;

public class KMeansDuration implements KMeans{
	
	int nrClusters;
	ArrayList<Integer> idNewCentroid;
	ArrayList<Integer> idCentroid;
	ArrayList<Task> tasks; 
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
		
		
		
		int stringDistance =   calculateDistanceTitles(t1.getNameTask(), t2.getNameTask());
		int timeDistance =    calculateDistanceStartTime(t1.getStartTime(), t2.getStartTime());
		int locationDistance = calculateDistanceLocation(t1, t2); 
		//	System.out.println("distanta intre " + t1.getNameTask() + " ----  " + t2.getNameTask() + " : " +  stringDistance);
		//System.out.println("distanta intre " + t1.getStartTime() + " ----  " + t2.getStartTime() + " : " +  timeDistance);
		//System.out.println("distanta intre locatii intre " + t1.getNameTask() + " ----  " + t2.getNameTask() + " : " +  locationDistance);
		
		return timeDistance * sameNumitorStartTime + 
		stringDistance * sameNumitorTitle + locationDistance *sameNumitorLocation  ;
	}



	/**
	 * @param t1 : one of the task whose location is taken into consideration for distance	
	 * @param t2 : the second task whose location is taken into consideration for distance
	 * @return : the distance between the locations of the two tasks
	 */
	public int calculateDistanceLocation(Task t1, Task t2) {
		
		
		float results[] = new float[3];
		
		LocationContext location1 =(LocationContext) 
		t1.getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		LocationContext location2 =(LocationContext) 
		t2.getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		

		
		
		Location.distanceBetween(location1.getLatitude(), location1.getLongitude(),
				                 location2.getLatitude(), location2.getLongitude(), results);
		
		
		return  (int) results[0];
	}


	/**
	 * @param startTime : the start time of the first task
	 * @param startTime2 : the start time of the second task
	 * @return
	 */
	private int calculateDistanceStartTime(String startTime, String startTime2) {
		
		int minutes1, hours1, minutes2, hours2;
		
		String startTimeMinutesAndAhours1[] = startTime.split("/");
		String startTimeMinutesAndAhours2[] = startTime2.split("/");
		
		minutes1 = Integer.parseInt(startTimeMinutesAndAhours1[4]);
		hours1   = Integer.parseInt(startTimeMinutesAndAhours1[3]);
		
		
		minutes2 = Integer.parseInt(startTimeMinutesAndAhours2[4]);
		hours2   = Integer.parseInt(startTimeMinutesAndAhours2[3]);
		
		
		
		
//		System.out.println(hours1 + " " + minutes1 + " "  + hours2 + " " + minutes2);
		return Math.abs((hours1 * 60 +  minutes1) - (hours2 * 60 +  minutes2) );
	}


	@Override
	public void detectCentroid() {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * @param nameTask : the title of the first Task
	 * @param nameTask2 : the titles of the second Task
	 * return the Distance between the two titles
	 */
	private int calculateDistanceTitles(String nameTask, String nameTask2) {
		
		ArrayList<Integer> wordsTakenIntoConsideration = new ArrayList<Integer>();
		int minim;
		int position = 0;
		int valueDistanceWords;
		int i;
		int distanceTotal = 0;
		String wordsString1[];
		String wordsString2[]; 
		
		if(nameTask.split(" ").length < nameTask2.split(" ").length)
		{
			wordsString1  = nameTask.split(" ");
			wordsString2 = nameTask2.split(" ");
			
		}
		else
		{
			wordsString1  = nameTask2.split(" ");
			wordsString2 = nameTask.split(" ");
		}
		
		
		for(String word : wordsString1)
		{
			minim = 1000000;
			
			for(i = 0; i < wordsString2.length ; i++)
				if(wordsTakenIntoConsideration.contains(i) == false)
				{
					  valueDistanceWords = calcualteStringDistance(word, wordsString2[i]);
					
					  if( valueDistanceWords < minim)
					  {
						  minim = valueDistanceWords;
						  position = i;
						  
					  }
				}
			
			
			distanceTotal +=minim;
			wordsTakenIntoConsideration.add(position);
			
			
		}
		
		
		for(i= 0; i < wordsString2.length; i++)
		{
			if(wordsTakenIntoConsideration.contains(i) == false)
				distanceTotal += wordsString2[i].length();
		}
		
		return distanceTotal;
	}
	

	@Override
	public int calcualteStringDistance(String a, String b){
		
	 	a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
		
		
	}





	




}



