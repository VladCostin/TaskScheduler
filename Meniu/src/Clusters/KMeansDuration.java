package Clusters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
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
	 * contains the new centers of the clusters associated to each point
	 */
	ArrayList<Integer> idNewCentroid;
	/**
	 * contains the old centers of the clusters associated to each point
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
	 * the final centers, when K-means detects the global minimum
	 */
	ArrayList<Task> finalCenters;
	
	
	/**
	 * the id-s of the tasks selected priori to be centers
	 * used in order to keep the clusters without points
	 */
	ArrayList<Integer> idCentroiziChosen;
	
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
	 * the initial error for computing the number of clusters
	 */
	float errorInit;
	
	
	/**
	 * if the fraction between the last distance calculated and the new distance calcualted
	 * is bigger than the last distance calculated / fraction Error, I increase the number of clusters
	 */
	int fractionError;
	
	/**
	 * the algorithm stops to calculate the clusters with a K value increased
	 * if the sum of distances exceeds this value
	 */
	int distanceErrorMaxim;
	

	
	/**
	 * initialize the members
	 */
	public KMeansDuration() {
		nrClusters = 0;
		
		
		
		sameNumitorTitle = 500; // in case max LocationDistance = 21000 and title is 30
		sameNumitorLocation = 1;
		sameNumitorStartTime = 14; // in case max LocationDistance = 20000 and start time maxim is 720
	
		
		idCentroid = new ArrayList<Integer>();
		centroizi = new ArrayList<Task>();
		idNewCentroid = new ArrayList<Integer>();
		idCentroiziChosen = new ArrayList<Integer>();
		finalCenters = new ArrayList<Task>();
		
		
		errorInit = 100000000;
		fractionError = 3;
		distanceErrorMaxim = 5000; 
		
	}
	

	@Override
	public void receiveData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculateKlusters() {
		
		int distanceMax; 
		int distance;
		int centroidNearest = 0;
		float newError;
		float firstNegativeRatio = 10;
		float secondNegativeRatio;
		int negativeResult = 0;
		
		
		ArrayList<TaskState> states = new ArrayList<TaskState>();
		states.add(TaskState.EXECUTED);
		
		tasks =  (ArrayList<Task>)  MainActivity.getDatabase().getFilteredTasks(states);

		
		System.out.println( "DIMENSIUNEA ESTE   " + tasks.size());
		System.out.println("AFISEZ INDICII CENTROIZILOR");
		
		
		
		
		Random r = new Random();
		idCentroiziChosen.add( r.nextInt(tasks.size()));
		centroizi.add(tasks.get(idCentroiziChosen.get( idCentroiziChosen.size() - 1 )));
		
		
		for(int i = 0; i < 11; i++)
		{
	//	while(true)
	//	{
			nrClusters++;
			idCentroid.clear();
	//		centroizi.clear();
			idNewCentroid.clear();
	//		idCentroiziChosen.clear();
			
			
			
			// calculez idCentroid, adica pentr
			for(Task task : tasks)
			{
				
				if(idCentroiziChosen.contains(tasks.indexOf(task)))
				{
					idCentroid.add( idCentroiziChosen.indexOf((tasks.indexOf(task)) )); 
					
					continue;
				}
				
				
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
					if(idCentroiziChosen.contains(tasks.indexOf(task)))
					{
						idNewCentroid.add( idCentroiziChosen.indexOf((tasks.indexOf(task)) )); 
						
						continue;
					}
					
					
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
		
		//	System.out.println("AM TERMINAT DE CALCULAT CENTROIZII");
		
		
	//		System.out.println("AFISEZ EROAREA PENTRU K = " + nrClusters);
		
			
			newError =	calculateError();
			System.out.println((i + 1) +  " DIFERENTA este" + (errorInit - newError ) + "  " + (errorInit/fractionError)  + " " + newError + " " + errorInit  );
			
			
			if( (errorInit - newError) < errorInit/fractionError)
			{
					
				
				
				if(negativeResult == 2){
					
					
					System.out.println("A IESTE PENTR CAU AVEA negativeResult = 2 " + finalCenters.size() );
					break;
				}
				
				negativeResult++;
				
			/*	secondNegativeRatio = (errorInit/fractionError) / (errorInit - newError);
				
				if(secondNegativeRatio >  firstNegativeRatio ){
					System.out.println("A IESTE PENTR CA AL DOILEA RAPORT ERA MAI MARE DECAT PRIMUL" + " " + finalCenters.size());
					break;
				}
				
				firstNegativeRatio = secondNegativeRatio;*/
				
				
			}
			else{
				negativeResult = 0;
				firstNegativeRatio = 10;
				finalCenters.clear();
				finalCenters.addAll(centroizi);
				
				System.out.println("ESTE REACTUALIZAT FINAL CENTERS");
				
			}
			
		/*	if( (errorInit - newError) < errorInit/fractionError  || nrClusters == tasks.size()){
				
				System.out.println( "DIFERENTA este " + (errorInit - newError));
				System.out.println( "Impartit la 5 este " + (errorInit/ fractionError));
				
				break;
			}*/
			
			errorInit = newError;
			chooseCentroid();
			
		
		}
		
		
		System.out.println("NUMARUL DE CLUSTERE DETEMINAT ESTE " + finalCenters.size() );
		
	//	calculatesTitlesCenters();
		calculateDurationMedium();
		
	}
	
	
	/**
	 * creating a center based on the information from the task selected to be a center 
	 */
	public void addTaskToCenterList(Task center)
	{
		
		
		
		
	}
	
	

	/**
	 * calculates the average duration for the determined clusters
	 */
	public void calculateDurationMedium() {
		
		
		System.out.println("AFISEZ DURATELE");
		
		int iCentroid, iTask, pointsSameCenter;
		int durationAverage;
		TreeMap<String, Integer> frequency;
		nrClusters = finalCenters.size();
		
		for(iCentroid = 0; iCentroid <  nrClusters ; iCentroid++)
		{
			System.out.println("AFISEZ duratele pentru un centroid");
			durationAverage = 0;
			pointsSameCenter = 0;
			frequency = new TreeMap<String,Integer>();
			
			for(iTask = 0; iTask < idCentroid.size(); iTask++)
			{
				if(idCentroid.get(iTask) == iCentroid)
				{
					
				//	System.out.println("NUMELE TASKULUI ESTE " + tasks.get(iTask).getNameTask());
					
					frequency.putAll(calculateFrequencyWords(iTask, frequency.keySet()));
					
					
					DurationContext duration = (DurationContext)tasks.get(iTask).
					getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
					durationAverage += duration.getDuration();
					
					LocationContext location = (LocationContext) tasks.get(iTask).
					getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
					
					pointsSameCenter++;
					
					
					
					System.out.println(tasks.get(iTask).getNameTask() + " " + duration.getDuration()  + " " + location.getLatitude() + " " + location.getLongitude() + " " + tasks.get(iTask).getStartTime() );
				}
			}
			

			durationAverage = durationAverage / pointsSameCenter;

			System.out.println("MEDIA ESTE " + durationAverage);
			
			System.out.println("----------------------");
			System.out.println(frequency.keySet().toString());
			System.out.println(frequency.values().toString());
			System.out.println("-----------------------");
			
			
		//	DurationContext duration = (DurationContext) centroizi.get(iCentroid).
		//	getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
			
			
		//	duration.setDuration(durationAverage);
			
			
			DurationContext duration = new DurationContext(durationAverage);
			
			finalCenters.get(iCentroid).getInternContext().getContextElementsCollection()
			.put(ContextElementType.DURATION_ELEMENT, duration);
			
		}
		
		
	}


	
	/**
	 * @param iTask : the tasks's id for which i calculate the frequency for the words from his title 
	 * @param dictionary : the words known by now
 	 * @return : the new set of pairs : words and how many of them appear
	 */ 
	public TreeMap<String,Integer> calculateFrequencyWords(int iTask, Set<String> dictionary) {
		
		String taskTitle;
		String words[];
		TreeMap<String, Integer> frequency = new TreeMap<String,Integer>();
		int iCenter, nrAppear, center;
		boolean contained;
		
		taskTitle = tasks.get(iTask).getNameTask();
		words = taskTitle.split(" ");
		center = idCentroid.get(iTask);
		
		
		for(String word : words)
		{
			contained = false;
			
			for(String wordDictionary : dictionary)
				if(KMeansDistances.calcualteStringDistance(word, wordDictionary) == 0 )
				{
					contained = true;
					break;
				}
			if(contained == true)
				continue;
			
			nrAppear = 1;
			
			for(iCenter = 0; iCenter < tasks.size(); iCenter++)
			{
				if(idCentroid.get(iCenter) == center && iCenter != iTask)
				{
					
					String wordsTitleTwo[] = tasks.get(iCenter).getNameTask().split(" ");
					
					for(String wordTitleTwo : wordsTitleTwo)
					{
							
						if(KMeansDistances.calcualteStringDistance(word, wordTitleTwo) == 0 )
							nrAppear++;
						
						
					
					}
					
				}
				
				
			}
			frequency.put(word, nrAppear);
			
		}
		
		return frequency;
		
		
	}


	/**
	 * calculates the centers' titles
	 */
	public void calculatesTitlesCenters() {
		
		int i,j, k;
		TreeMap<String, Integer> frequency;
		
		
		for(i = 0; i < nrClusters; i++)
		{
			
			frequency = new TreeMap<String, Integer>();
			
			for( j = 0; j < tasks.size(); j++)
			{
				if(idNewCentroid.get(j) == i)
				{
					String words[] = tasks.get(j).getNameTask().split(" ");
					
					for( k = 0; k < tasks.size(); k++)
					{
						if(idNewCentroid.get(k) == i)
						{
							
							
							
						}
					}
				}
			}
			
			
		}
		
		
	}


	@Override
	public void calculateNewCentroizi() {
		
		int nrPoints;
		int i, taskPos;
		double latitude,longitude;
		int minutes;
		int hours;
		

		
		
		
		for(i = 0 ; i < nrClusters; i++)
		{	
			
			nrPoints = 0;
			latitude = 0;
			longitude = 0;
			minutes = 0;

			
			for(taskPos = 0; taskPos < tasks.size(); taskPos++)
			{
			//	System.out.println("Dimensiunea este " + idCentroid.size() + " " + idCentroid.toString() );
				
				
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
		
		int distanceMinim;
		int distanceMaxim;
		
		int distanceCalculated;
		int newCentroidIndex = 0;
	//	Random r = new Random();
	//	idCentroiziChosen.add( r.nextInt(tasks.size()));

	
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
			

		idCentroiziChosen.add(newCentroidIndex);
		centroizi.add(tasks.get(idCentroiziChosen.get( idCentroiziChosen.size() - 1 )));

		
		
	//	System.out.println(idCentroiziChosen.toString());
	//	for(iCentroid = 0; iCentroid< idCentroiziChosen.size(); iCentroid++)
	//	{
		//	System.out.println(tasks.get(idCentroiziChosen.get(iCentroid)).getStartTime() + "   ");
		//	System.out.println(tasks.get(idCentroiziChosen.get(iCentroid)).getNameTask() + "   ");
			
			
		
			
			
	//	}
		
		
		
		
		
	/*	int iCentroid, idTask, iCentroidByNow;
		
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
			

			idCentroiziChosen.add(newCentroidIndex);

					
		}
		
		
		System.out.println(idCentroiziChosen.toString());
		for(iCentroid = 0; iCentroid< idCentroiziChosen.size(); iCentroid++)
		{
		//	System.out.println(tasks.get(idCentroiziChosen.get(iCentroid)).getStartTime() + "   ");
		//	System.out.println(tasks.get(idCentroiziChosen.get(iCentroid)).getNameTask() + "   ");
			
			
			Task t = new Task();
			t.setNameTask(tasks.get(idCentroiziChosen.get(iCentroid)).getNameTask());
			t.setStartTime(tasks.get(idCentroiziChosen.get(iCentroid)).getStartTime());
			
			
			LocationContext location = (LocationContext)
			tasks.get(idCentroiziChosen.get(iCentroid)).getInternContext().
			getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			
			
			t.getInternContext().getContextElementsCollection().
			put(ContextElementType.LOCATION_CONTEXT_ELEMENT, location);
			
			
			centroizi.add(t);
			
			
		}*/
		

	}

	@Override
	public int calculateDistance(Task t1, Task t2) {
		
		
		
		int stringDistance =   KMeansDistances.calculateDistanceTitles(t1.getNameTask(), t2.getNameTask());
		int timeDistance =    KMeansDistances.calculateDistanceStartTime(t1.getStartTime(), t2.getStartTime());
		int locationDistance = KMeansDistances.calculateDistanceLocation(t1, t2); 
	//    System.out.println("DISTANTA INTRE NUME " + t1.getNameTask() + " ----  " + t2.getNameTask() + " : " +  stringDistance);
	//	System.out.println("distanta temporala intre " + t1.getStartTime() + " ----  " + t2.getStartTime() + " : " +  timeDistance * sameNumitorStartTime );
	//	System.out.println("distanta intre locatii intre " + t1.getNameTask() + " ----  " + t2.getNameTask() + " : " +  locationDistance * sameNumitorLocation);
		
		return timeDistance * sameNumitorStartTime + 
		stringDistance * sameNumitorTitle + locationDistance *sameNumitorLocation  ;
	}
	
	
	@Override
	public float calculateError() {
		
		int iCluster;
		int taski, taskj;
		float sumAll = 0, sumCluster = 0;
		int nrPoints;
		int medie= 0;


		
		for(iCluster = 0; iCluster < nrClusters ; iCluster++)
		{
			sumCluster = 0;
			nrPoints = 0;
			
			for(taski = 0; taski < tasks.size(); taski++)
			{
				
				
				
				if(idCentroid.get(taski) == iCluster )
				{
					for(taskj = 0; taskj < tasks.size();taskj++)
					{
						if(idCentroid.get(taskj) == iCluster )
						{
							//int distanceC = calculateDistance(tasks.get(taskj), tasks.get(taski) );
							
							 
							int stringDistance =   KMeansDistances.calculateDistanceTitles(tasks.get(taskj).getNameTask(), tasks.get(taski).getNameTask());
							int timeDistance =    KMeansDistances.calculateDistanceStartTime(tasks.get(taskj).getStartTime(), tasks.get(taski).getStartTime());
							int locationDistance = KMeansDistances.calculateDistanceLocation(tasks.get(taskj), tasks.get(taski)); 
							
							
							int distanceC = stringDistance * this.sameNumitorTitle  +   timeDistance * sameNumitorStartTime  + locationDistance ;
							
					//		System.out.println("Distantele intre " + taski + " " + taskj + " sunt  " + " " + stringDistance + " " + timeDistance * sameNumitorStartTime + " " + locationDistance  + " " + distanceC + " " + sumCluster );
							
							sumCluster +=  distanceC;

							
						}
						
						
					}
					nrPoints++;
				}
			}
			
	//		System.out.println("SUMA ESTE " + sumCluster + " " + nrPoints);
			
			medie += (int) sumCluster / nrPoints;
	
		}
		
	//	sumAll += sumCluster/ (2 * nrClusters);
		
		
		
	//	System.out.println("MEDIA ESTE " +  medie/ nrClusters);
		
	//	System.out.println( "Suma distantelor finale este " + sumAll);
		
	//	System.out.println(" CEA MAI MARE MEDIE ESTE " + maxim);
		
		
	//	return sumAll;
		return medie / nrClusters;
	//	return maxim;
		
		
		
		
		
		
	}
	

	



	@Override
	public Task detectCentroid(Task currentTask) {
		
		
		Task chosenTask = finalCenters.get(0);
		float distanceMaxim = calculateDistance(currentTask, finalCenters.get(0));
		float distance;
		
		for(Task center : finalCenters)
		{
		//	System.out.println(" Un centroid are numele" + center.getNameTask());
			
			DurationContext duration = (DurationContext) center.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
			
		//	if(duration == null)
		//		System.out.println("Durata este null" + center.getNameTask());
			
			distance = calculateDistance(center, currentTask);
			
			if( distance < distanceMaxim  )
			{
				distanceMaxim = distance;
				chosenTask = center;
			}
			
		}
		
		
		return chosenTask;
		
	}

}



