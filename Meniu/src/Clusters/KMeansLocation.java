package Clusters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import com.example.meniu.MainActivity;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import Task.Task;
import Task.TaskState;

/**
 * calculates the centers for the location, taking into consideration the title and the people
 * @author ${Vlad Herescu}
 *
 */
public class KMeansLocation implements KMeans {
	
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
	 * the id-s of the tasks selected priori to be centers
	 * used in order to keep the clusters without points
	 */
	ArrayList<Integer> idCentroiziChosen;
	
	
	/**
	 * if the fraction between the last distance calculated and the new distance calcualted
	 * is bigger than the last distance calculated / fraction Error, I increase the number of clusters
	 */
	int fractionError;
	

	

	
	
	public KMeansLocation() {
		
		
		idCentroid = new ArrayList<Integer>();
		centroizi = new ArrayList<Task>();
		idNewCentroid = new ArrayList<Integer>();
		idCentroiziChosen = new ArrayList<Integer>();
		
		nrClusters = 0;
		
		
		fractionError = 3;
		
		
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
		float errorInit = 10000000;
		ArrayList<TaskState> states = new ArrayList<TaskState>();
		states.add(TaskState.EXECUTED);
	
		tasks =  (ArrayList<Task>)  MainActivity.getDatabase().getFilteredTasks(states);
		
		
		while(true)
		{
			idCentroid.clear();
			centroizi.clear();
			idCentroiziChosen.clear();
			nrClusters++;
			
			chooseCentroid();
		
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
			
			
			newError =	calculateError();
		//	System.out.println("DIFERENTA este" + (errorInit - newError ) + "  " + (errorInit/fractionError)  + " " + newError + " " + errorInit  );
			
			if( (errorInit - newError) <= errorInit/fractionError  || nrClusters == tasks.size()){
				
		//		System.out.println( "DIFERENTA este " + (errorInit - newError));
		//		System.out.println( "Impartit la 3 este " + (errorInit/ fractionError));
				
				break;
			}
			
			errorInit = newError;
			
			
			calculatesTitlesCenters();
		}
		
		System.out.println("DIMENSIUNEA FINALA ESTE " + nrClusters);
		
		

	}

	/**
	 * calculates the average location and title
	 */
	private void calculatesTitlesCenters() {
		
		TreeMap<String, Integer> frequency;
		int iCentroid, iTask, pointsSameCenter;
		double latitude, longitude;
		
		for(iCentroid = 0; iCentroid <  nrClusters ; iCentroid++)
		{
		//	System.out.println("AFISEZ duratele pentru un centroid");
			frequency = new TreeMap<String,Integer>();
			pointsSameCenter = 0;
			latitude = 0;
			longitude = 0;
			
			for(iTask = 0; iTask < idCentroid.size(); iTask++)
			{
				if(idCentroid.get(iTask) == iCentroid)
				{
					
				//	System.out.println("NUMELE TASKULUI ESTE " + tasks.get(iTask).getNameTask());
					
					frequency.putAll(calculateFrequencyWords(iTask, frequency.keySet()));

					
					LocationContext location = (LocationContext) tasks.get(iTask).
					getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);

					
					latitude += location.getLatitude();
					longitude += location.getLongitude();
					pointsSameCenter++;
				}
			}
			
			latitude = latitude / (double) pointsSameCenter;
			longitude = longitude / (double) pointsSameCenter;
		//	System.out.println("MEDIA ESTE " + latitude + " " + longitude);

			
		//	System.out.println("----------------------");
		//	System.out.println(frequency.keySet().toString());
		//	System.out.println(frequency.values().toString());
		//	System.out.println("-----------------------");
			
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


	@Override
	public Task detectCentroid(Task currentTask) {
		
		
		Task chosenTask = centroizi.get(0);
		float distanceMaxim = calculateDistance(currentTask, centroizi.get(0));
		float distance;
		
		for(Task center : centroizi)
		{
		//	System.out.println(" Un centroid are numele" + center.getNameTask());
			distance = calculateDistance(center, currentTask);
			
			if( distance < distanceMaxim  )
			{
				distanceMaxim = distance;
				chosenTask = center;
			}
			
		}
		
		
		if(distanceMaxim == 0)
			return chosenTask;
		return null;

	}

	@Override
	public void chooseCentroid() {
		
		int iCentroid, idTask, iCentroidByNow;
		
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
	public void calculateNewCentroizi() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkCentroiziNotChanged() {
		// TODO Auto-generated method stub
		return false;
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
						if(idCentroid.get(taskj) == iCluster )
							sumCluster +=  calculateDistance( tasks.get( taski ), tasks.get( taskj ));	
						
					nrPoints++;
				}
			}
			
			
			medie += (int) sumCluster / nrPoints;
		}
		
		return medie / nrClusters;
	
	}

	@Override
	public int calculateDistance(Task t1, Task t2) {
		int stringDistance =   KMeansDistances.calculateDistanceTitles(t1.getNameTask(), t2.getNameTask());
		return stringDistance;
	}

}
