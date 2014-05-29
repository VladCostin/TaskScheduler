package Clusters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Task.Task;
import Task.TaskState;

import com.example.meniu.MainActivity;

public class KMeansDuration implements KMeans{
	
	int nrClusters;
	ArrayList<Integer> idCentroid;
	ArrayList<Task> taskuri;
	
	public KMeansDuration() {
		nrClusters = 3;
	
	}
	

	@Override
	public void receiveData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculateKlusters() {
		
		taskuri =  (ArrayList<Task>)  MainActivity.getDatabase().getFilteredTasks(TaskState.EXECUTED);
		System.out.println( "DIMENSIUNEA ESTE   " + taskuri.size());
		idCentroid = new ArrayList<Integer>();
		
		chooseCentroid();
		
		
		
	}

	@Override
	public void chooseCentroid() {
		int iCentroid, idTask;
		float distanceMinim= -1000;
		int newCentroidIndex;
		Random r = new Random();
		idCentroid.add( r.nextInt(taskuri.size()));
		
		
	//	for(iCentroid= 1; iCentroid < nrClusters; iCentroid++)
	//	{
			for(idTask = 0; idTask < taskuri.size(); idTask++)
			{
				if(idCentroid.contains(idTask)  == false )
					calcualteDistance(taskuri.get(idTask),taskuri.get(idCentroid.get(0))   )  ;
				
				
			}
			
			
	//	}
		

	}

	@Override
	public void calcualteDistance(Task t1, Task t2) {
		
		
	//	float StringDistance = calcualteStringDistance(t1.getNameTask(), t2.getNameTask());
	//	System.out.println("distanta intre " + t1.getNameTask() + " ----  " + t2.getNameTask() + " : " +  StringDistance);
		
		System.out.println("distanta intre " + t1.getNameTask() + " ----  " + t2.getNameTask());
	}


	@Override
	public void detectCentroid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int calcualteStringDistance(String title1, String title2){
		
		  int cost;
		
		  if (title1.length() == 0) return title2.length();
		  if (title2.length() == 0) return title1.length();
		
		  
		  
		  if(title1.charAt(title1.length() -1 ) == title2.charAt(title2.length() -1 )   )
			  cost = 0;
		  else
			  cost = 1;
		 
		  /* return minimum of delete char from s, delete char from t, and delete char from both */
		//  return Math.min( Math.min( calcualteStringDistance(s, len_s - 1, t, len_t    ) + 1,
		//		  	calcualteStringDistance(s, len_s    , t, len_t - 1) + 1 ),
		//		  	calcualteStringDistance(s, len_s - 1, t, len_t - 1) + cost);
		  
		  
		  return Math.min( Math.min( 
		  calcualteStringDistance( title1.substring(0, title1.length() -1), title2) + 1,
          calcualteStringDistance(title2 ,title2.substring(0, title2.length() -1)) + 1 ), 
          calcualteStringDistance(title1.substring(0, title1.length() -1), title2.substring(0, title2.length() -1) ) + cost);
		
		
		
	}


	


}
