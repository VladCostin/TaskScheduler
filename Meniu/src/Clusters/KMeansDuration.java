package Clusters;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import ContextElements.ContextElementType;
import ContextElements.DeviceContext;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import Task.Task;
import Task.TaskState;

import com.example.meniu.Core;
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
	 * the final centers as Tasks, when K-means detects the global minimum
	 */
	ArrayList<Task> finalCenters;
	
	
	/**
	 * the id's of the tasks calculated as centers
	 */
	ArrayList<Integer> finalIdCentroid;
	
	
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
		
		
		
/*		sameNumitorTitle = 400; // in case max LocationDistance = 21000 and title is 50
		sameNumitorLocation = 1;
		sameNumitorStartTime = 20; // in case max LocationDistance = 20000 and start time maxim is 720
*/
		
		sameNumitorTitle = 500; // in case max LocationDistance = 21000 and title is 50
		sameNumitorLocation = 1;
		sameNumitorStartTime = 28;
		
		
/*		sameNumitorTitle = 500; // in case max LocationDistance = 21000 and title is 30
		sameNumitorLocation = 1;
		sameNumitorStartTime = 5; // in case max LocationDistance = 20000 and start time maxim is 720
*/	
		idCentroid = new ArrayList<Integer>();
		centroizi = new ArrayList<Task>();
		idNewCentroid = new ArrayList<Integer>();
		idCentroiziChosen = new ArrayList<Integer>();
		finalCenters = new ArrayList<Task>();
		finalIdCentroid = new ArrayList<Integer>();
		
		
		errorInit = 100000000;
		fractionError = 4;
		distanceErrorMaxim = 5000; 
		
	}
	

	@Override
	public void calculateKlusters() {
		
		int distanceMax; 
		int distance;
		int centroidNearest = 0;
		float newError;
		int negativeResult = 0;
		int i=0;
		int k;
		
		
		ArrayList<TaskState> states = new ArrayList<TaskState>();
		states.add(TaskState.EXECUTED);
		
		tasks =  (ArrayList<Task>)  MainActivity.getDatabase().getFilteredTasks(states);

		
		if(tasks.size() == 0)
			return;
		
		k = (int) Math.ceil( Math.sqrt(tasks.size()  ) );
	//	k = (int) Math.ceil( Math.sqrt(tasks.size() / 2 ) );

		
		
		nrClusters = k;
		chooseCentroid();
		
			
		// calculating the center associated to each point, except if the point is a center
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

		finalCenters.addAll(centroizi);
		finalIdCentroid.addAll(idCentroid);
		
		calculateDurationMedium();
		Core.setCentersDuration(finalCenters);
		
		
/*		
		Random r = new Random();
		idCentroiziChosen.add( r.nextInt(tasks.size()));
		centroizi.add(tasks.get(idCentroiziChosen.get( idCentroiziChosen.size() - 1 )));
		
		
		while(true)
		{
			nrClusters++;
			idCentroid.clear();
			idNewCentroid.clear();
			i++;
			
			
			// calculating the center associated to each point, except if the point is a center
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
		//	System.out.println( " DIFERENTA este" + (errorInit - newError ) + "  " + (errorInit/fractionError)  + " " + newError + " " + errorInit  );
			
			if(i == tasks.size())
				break;
			
			
			if( (errorInit - newError) < errorInit/fractionError)
			{
					
				
				if(negativeResult == 2){
					
					
					System.out.println("A IESTE PENTR CAU AVEA negativeResult = 2 " + finalCenters.size() );
					break;
				}
				
				negativeResult++;
				
				
			}			
			
			else{
				negativeResult = 0;
				finalCenters.clear();
				finalIdCentroid.clear();
				finalCenters.addAll(centroizi);
				finalIdCentroid.addAll(idCentroid);
				
				
				System.out.println("ESTE REACTUALIZAT FINAL CENTERS");
				
			}

			
			errorInit = newError;
			chooseCentroid();
			
		
		}
		
		
		
		
		System.out.println("NUMARUL DE CLUSTERE DETEMINAT ESTE " + finalCenters.size() );
		
		calculateDurationMedium();
		
		Core.setCentersDuration(finalCenters);*/
		
	}
	
	
	

	
	


	/**
	 * calculates the average duration for the determined clusters
	 */
	public void calculateDurationMedium() {

		
		int iCentroid, iTask, pointsSameCenter;
		int durationAverage;
		TreeMap<String, Integer> frequency;
		nrClusters = finalCenters.size();

		
		
		for(iCentroid = 0; iCentroid <  nrClusters ; iCentroid++)
		{

			durationAverage = 0;
			pointsSameCenter = 0;
			frequency = new TreeMap<String,Integer>();
			
			for(iTask = 0; iTask < finalIdCentroid.size(); iTask++)
			{
				if(finalIdCentroid.get(iTask) == iCentroid)
				{

					
					frequency.putAll(calculateFrequencyWords(iTask, frequency.keySet()));
					
					
					DurationContext duration = (DurationContext)tasks.get(iTask).
					getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
					durationAverage += duration.getDuration();
					
					LocationContext location = (LocationContext) tasks.get(iTask).
					getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
					
					pointsSameCenter++;
					
										
				}
			}
			

			durationAverage = durationAverage / pointsSameCenter;			
			
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
			String dataValues[] = centroid.getStartTime().split("/");
			LocationContext location = (LocationContext)
			centroid.getInternContext().
			getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			
			
			Task centroidNou = new Task();
			
			centroidNou.setNameTask(centroid.getNameTask());
			centroidNou.setStartTime(dataValues[0]+"/"+dataValues[1]+"/"+dataValues[2]+"/"+hours+"/"+minutes);
			
			
			
			
			
			centroidNou.getInternContext().getContextElementsCollection().
			put(ContextElementType.LOCATION_CONTEXT_ELEMENT, location);
			
			centroizi.set(i, centroidNou);

		}
		
		
		
	}
	
	

	@Override
	public boolean checkCentroiziNotChanged() {
		
		int i;
		for(i = 0; i < tasks.size(); i++)
		{
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


/*	@Override
	public void chooseCentroid() {
		
		
		int iCentroid, idTask, iCentroidByNow;
		
		int distanceMinim;
		int distanceMaxim;
		
		int distanceCalculated;
		int newCentroidIndex = 0;

	
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

		

	}*/
	
	
	public void chooseCentroid()
	{
		int iCentroid, idTask, iCentroidByNow;
		int distanceMinim;
		int distanceMaxim;
		int distanceCalculated;
		int newCentroidIndex;
		
		
		Random r = new Random();
		idCentroiziChosen.add( r.nextInt(tasks.size()));
		centroizi.add(tasks.get(idCentroiziChosen.get( idCentroiziChosen.size() - 1 )));
		
		for(iCentroid = 0; iCentroid < nrClusters - 1; iCentroid++  )
		{
			distanceMaxim = -1;
			newCentroidIndex = 0;
			
			
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
			
		}
		
		
		
	}

	
	
	@Override
	public int calculateDistance(Task t1, Task t2) {
		
		
		int stringDistance =   KMeansDistances.calculateDistanceTitles(t1.getNameTask(), t2.getNameTask());
		int timeDistance =    KMeansDistances.calculateDistanceStartTime(t1.getStartTime(), t2.getStartTime());
		int locationDistance = KMeansDistances.calculateDistanceLocation(t1, t2); 
		
		
		return (int) Math.sqrt( timeDistance * timeDistance * sameNumitorStartTime * sameNumitorStartTime +
			    stringDistance * stringDistance * sameNumitorTitle * sameNumitorTitle +
			    locationDistance * locationDistance * sameNumitorLocation * sameNumitorLocation);

	}
	
	
	
/*	@Override
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
							int distanceC = calculateDistance(tasks.get(taskj), tasks.get(taski) );
							
							 
					//		int stringDistance =   KMeansDistances.calculateDistanceTitles(tasks.get(taskj).getNameTask(), tasks.get(taski).getNameTask());
					//		int timeDistance =    KMeansDistances.calculateDistanceStartTime(tasks.get(taskj).getStartTime(), tasks.get(taski).getStartTime());
					//		int locationDistance = KMeansDistances.calculateDistanceLocation(tasks.get(taskj), tasks.get(taski)); 
						
							
					//		int distanceC = stringDistance * this.sameNumitorTitle  +   timeDistance * sameNumitorStartTime  + locationDistance ;
						
					//		System.out.println("Distantele intre " + taski + " " + taskj + " sunt  " + " " + stringDistance + " " + timeDistance * sameNumitorStartTime + " " + locationDistance  + " " + distanceC + " " + sumCluster );
						
							sumCluster +=  distanceC;
						
							
						}
						
						
					}
					nrPoints++;
				}
			}
			
	//		System.out.println("SUMA ESTE " + sumCluster + " " + nrPoints);
			
			medie += (int) sumCluster / (nrPoints * 2);			
			
			

			
		//	sumCluster = sumCluster / nrPoints;
		//	sumAll +=sumCluster;
		//	sumAll += (sumCluster / (nrPoints * 2))  ;
	
		}
		
	//	sumAll += sumCluster/ (2 * nrClusters);
		
		
		
	//	System.out.println("MEDIA ESTE " +  medie/ nrClusters);
		
	//	System.out.println( "Suma distantelor finale este " + sumAll);
		
	//	System.out.println(" CEA MAI MARE MEDIE ESTE " + maxim);
		
		
	//	return sumAll;
		return medie / nrClusters;			
	//	return medie;
		
		
		
		
		
		
	}*/
	
	
	
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
							
						int distanceC = calculateDistance(centroizi.get(iCluster), tasks.get(taski) );
					

								
						sumCluster +=  distanceC;

				}
			}
			

			sumAll += sumCluster;
	
		}
		
		
		return sumAll/nrClusters ;
		
	}
	

	



	@Override
	public Task detectCentroid(Task currentTask) {
		
		
		if(Core.getCentersDuration().size() == 0)
			return currentTask;
			
		Task chosenTask =  Core.getCentersDuration().get(0);
		
		float distanceMaxim = calculateDistance(currentTask, Core.getCentersDuration().get(0));
		float distance;
		
		
		
		
		for(Task center : Core.getCentersDuration())
		{
			
			DurationContext duration = (DurationContext) center.getInternContext().getContextElementsCollection().get(ContextElementType.DURATION_ELEMENT);
			
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



