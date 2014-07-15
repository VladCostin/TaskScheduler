package Salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.meniu.Core;
import com.google.android.gms.maps.model.LatLng;

import ContextElements.ContextElementType;
import ContextElements.DurationContext;
import ContextElements.LocationContext;
import DatabaseOperation.FixedTaskInformation;
import Task.Task;

public class PopulationEvolution {
	
	/**
	 * the tasks whose start time and end time cannot be changed
	 */
	static ArrayList<FixedTaskInformation> fixedTasks;
	/**
	 * the tasks whose start time and end time can be changed
	 */
	static List<Task> shiftingTasks;
	
	
	/**
	 * the population before selection, crossover, mutation
	 */
	private ArrayList<Individual> population;
	
	
	/**
	 * contains the new population after selection, crossover, mutation
	 */
	private ArrayList<Individual> newPopulation;
	
	
	/**
	 * computes time and distance
	 */
	ComputationalMethods calculator;
	
	
	/**
	 * how many individuals will be added to the new population
	 */
	int sizeSelection;
	
	/**
	 * how many individuals will be created using crossover
	 */
	int sizeCrossover;
	
	private LatLng currentPosition;
	
	private LatLng endPosition;	

	
	/**
	 * the start time that must not be exceeded
	 */
	private int startTimeMinutes;
	
	
	/**
	 * the end time that must not be exceeded
	 */
	private int endTimeMinutes;
	
	

	/**
	 * @param fixedTasks : the tasks whose start time and end time cannot be changed for today from database
	 * @param shiftingTasks : the tasks whose start time and end time can be changed from database
	 */
	public PopulationEvolution(ArrayList<FixedTaskInformation> fixedTasks, 
			List<Task> shiftingTasks) 
	{
		this.fixedTasks = fixedTasks;
		this.shiftingTasks = shiftingTasks;

				
				
		
		population = new ArrayList<Individual>();
		newPopulation = new ArrayList<Individual>();
		calculator = new ComputationalMethods();
		
		
		
	}
	
	/**
	 * initializing the object
	 */
	public PopulationEvolution()
	{
		population = new ArrayList<Individual>();
		newPopulation = new ArrayList<Individual>();
		calculator = new ComputationalMethods();
	}
	
	
	public void startEvolution()
	{
		int decade= 0, nrDecades = shiftingTasks.size() * 10,indexInd;
		this.population.clear();
		this.newPopulation.clear();
		
		initMembers();
		
		
		while(true)
		{
			selection();
			
			if(decade == nrDecades - 1)	
				break;
			
			
			crossOver();	
			mutation();
					
			population.clear();
			population.addAll(newPopulation);
			newPopulation.clear();
			
			decade++;

		} 


		
	}
	
	/**
	 * initializes the individuals : for each individual a start time is assigned, when the tasks start to be executed
	 * 
	 */
	public void initMembers()
	{
		int indexTask,indexIndivid, sizeTasks, sizePopulation, factorial= 1;
		Random r = new Random();
		ArrayList<Integer> taskId = new ArrayList<Integer>();			// lista de id-uri asociate taskurilor
		ArrayList<Integer> tasksRemained = new ArrayList<Integer>();	// va aduga taskId, dupa care va da aleator
																		// cate un task individului pentru executie
		
		for(indexTask = 1; indexTask <= shiftingTasks.size(); indexTask++)
		{
	//		factorial *= indexTask;
			taskId.add(indexTask -1 );
		}
		
		
	//	sizePopulation = Math.max(20,  factorial / ConstantsPopulation.nrIndRatio);
		
	   if(shiftingTasks.size() >= 5 )
		   sizePopulation = shiftingTasks.size() * 5;
	   else
		   sizePopulation = shiftingTasks.size() * 10;
		
		sizeTasks = shiftingTasks.size();
		
		for(indexIndivid = 0; indexIndivid < sizePopulation; indexIndivid++  )
		{
			tasksRemained.addAll(taskId);
			Individual ind = new Individual();
			
			for(indexTask = 0; indexTask < sizeTasks; indexTask++)
				ind.getOrderTasks().add( tasksRemained.remove(r.nextInt(tasksRemained.size())) ); 
			
			
			ind.setStartTime(startTimeMinutes +
			r.nextInt(endTimeMinutes - startTimeMinutes));
			
			population.add(ind);
			
		}
		
		
		
		for(indexIndivid = 0; indexIndivid < sizePopulation; indexIndivid++  )
		{
			tasksRemained.addAll(taskId);

			int nr = r.nextInt(tasksRemained.size());

			Individual ind = new Individual();
			for(indexTask = 0; indexTask < nr; indexTask++)
					ind.getOrderTasks().add( tasksRemained.remove(r.nextInt(tasksRemained.size())) ); 
			
			
			
			ind.setStartTime(startTimeMinutes +
			r.nextInt(endTimeMinutes - startTimeMinutes));
			
			population.add(ind);
			tasksRemained.clear();
			
		}
		
		
		
		sizeSelection = (int) (population.size() * ConstantsPopulation.selectionRatio);
		sizeCrossover = population.size() - sizeSelection;
		
		ComputationalMethods.initPrioritiesValues();
		

		
	}
	


	/**
	 * selects the best individuals in population
	 */
	public void selection()
	{
		float fitness;
		int indexInd;
		
		for(Individual ind : population){
			fitness = calculateFitnessValue(ind);
			ind.setFitnessValue( fitness );
		}
		Collections.sort(population, new ComparatorFitness());
		
		
		System.out.println("INAINTE");
		for(indexInd = 0; indexInd < population.size(); indexInd++)
			System.out.print(indexInd + "---- " + population.get(indexInd).getOrderTasks() +" --- " + population.get(indexInd).getFitnessValue() + " , ");

		
		for(indexInd = 0; indexInd < sizeSelection; indexInd++){
			
			newPopulation.add(population.get(indexInd));
		}
		
		
	}
	
	
	/**
	 * selects random two individuals and calculates two children
	 */
	public void crossOver() {
		
		int indexChildren, iterations, taskId, duration1, duration2;
		int position1, position2, position1Next, position2Next, indexTask;
		int nrTasks;
		Random r = new Random();
		boolean contain1, contain2;
		LocationContext location, location1Next, location2Next;
		
		ArrayList<Integer> tasksRemained = new ArrayList<Integer>();	// va aduga taskId, dupa care va da aleator
																		// cate un task individului pentru executie

		
		iterations = sizeCrossover / 2;
		
		
		
		for(indexChildren = 0; indexChildren < iterations; indexChildren++)
		{
			Individual ind1 = new Individual(); 
			Individual ind2 = new Individual();
			Individual child1 = new Individual();
			Individual child2 = new Individual();
			
			Individual extras1 =  population.get(r.nextInt(population.size()));
			Individual extras2 = population.get(r.nextInt(population.size()));
			
			if(extras1.getOrderTasks().size() == 0 || extras2.getOrderTasks().size() == 0)
			{	
				newPopulation.add(child1);
				newPopulation.add(child2);
				continue;
			}
				
			
			prepareParentsForCrossOver(ind1, ind2, extras1, extras2);
			
			tasksRemained.clear();
			nrTasks = ind1.getOrderTasks().size();
			tasksRemained.addAll(ind1.getOrderTasks());

		
			if(nrTasks != 0)
			{
				taskId = ind1.getOrderTasks().get( r.nextInt(nrTasks));			
				child1.getOrderTasks().add(taskId);
				tasksRemained.remove(new Integer( taskId));	
			
				while(child1.getOrderTasks().size() != nrTasks )
				{
					position1 = ind1.getOrderTasks().indexOf(taskId);  // determine the index of the task in individual 1
					position2 = ind2.getOrderTasks().indexOf(taskId);  // determine the index of the task in individual 1 
				
					position1Next =  (position1 + 1) % ind1.getOrderTasks().size();
					position2Next =  (position2 + 1) % ind2.getOrderTasks().size();
				
					contain1 = child1.getOrderTasks().contains((ind1.getOrderTasks().get(position1Next)));
					contain2 = child1.getOrderTasks().contains((ind2.getOrderTasks().get(position2Next)));
				
					if(contain1 == true && contain2 == false)
					{
						taskId = ind2.getOrderTasks().get(position2Next);
						child1.getOrderTasks().add(taskId);
						tasksRemained.remove(new Integer( taskId));
						continue;
					}
				
				
					if(contain2 == true && contain1 == false)
					{
						taskId = ind1.getOrderTasks().get(position1Next);
						child1.getOrderTasks().add(taskId);
						tasksRemained.remove(new Integer( taskId));
						continue;

					}
					if(contain2 == true && contain1 == true)
					{
						taskId = tasksRemained.remove(r.nextInt(tasksRemained.size()));
						child1.getOrderTasks().add(taskId);
						tasksRemained.remove(new Integer( taskId));
						continue;
					}
					
				
				
				
			//	System.out.println("Pozitiile viitoare sunt" + position1Next + " " + position2Next);
				
					location = (LocationContext) shiftingTasks.get(taskId).
						getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
				
					location1Next = (LocationContext) shiftingTasks.get(ind1.getOrderTasks().get(position1Next)).
							getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
				
					location2Next = (LocationContext) shiftingTasks.get(ind2.getOrderTasks().get(position2Next)).
							getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
				
				
					duration1 = ComputationalMethods.calculateDurationTravel
							(location.getLatitude(), location.getLongitude(), location1Next.getLatitude(), location1Next.getLongitude());
				
					
					duration2 = ComputationalMethods.calculateDurationTravel
							(location.getLatitude(), location.getLongitude(), location2Next.getLatitude(), location2Next.getLongitude());
				
				
				
				
					if(duration1 < duration2)
					{
						taskId = ind1.getOrderTasks().get(position1Next);
						child1.getOrderTasks().add(taskId);
					
					}
					else
					{
						taskId = ind2.getOrderTasks().get(position2Next);
						child1.getOrderTasks().add(taskId);
					
					}

				
					tasksRemained.remove(new Integer( taskId));
				}
			}

			
			child2.getOrderTasks().addAll(child1.getOrderTasks());
			
			if(child1.getOrderTasks().size() < extras1.getOrderTasks().size() )
			{	
				
				for(Integer task : extras1.getOrderTasks())
					if(!child1.getOrderTasks().contains(task))
						child1.getOrderTasks().add(task);

			}
			
			if(child2.getOrderTasks().size() < extras2.getOrderTasks().size() )
			{
						
				for(Integer task : extras2.getOrderTasks())
					if(!child2.getOrderTasks().contains(task))
						child2.getOrderTasks().add(task);
				
			}
			
			
			
			child1.setStartTime(ind1.getStartTime() + (int) 
			( ConstantsPopulation.sample1Alpha * (ind2.getStartTime() - ind1.getStartTime())  ) );
			
			child2.setStartTime(ind1.getStartTime() + (int) 
			( ConstantsPopulation.sample2Alpha * (ind2.getStartTime() - ind1.getStartTime())  ) );
			
			
			newPopulation.add(child1);
			newPopulation.add(child2);
			
			
		}

		
	}
	
	
	/**
	 * gets random parents from population, then removes the tasks they do not 
	 * have in common
	 * @param ind1 : one of the parents
	 * @param ind2 : the second parent
	 * @param extras2 
	 * @param extras1 
	 */
	public void prepareParentsForCrossOver(Individual ind1 , Individual ind2, Individual extras1, Individual extras2)
	{
		Random r = new Random();
		
		ind1.getOrderTasks().addAll(extras1.getOrderTasks());
		ind2.getOrderTasks().addAll(extras2.getOrderTasks());
		
		ind1.setStartTime(extras1.getStartTime());
		ind2.setStartTime(extras2.getStartTime());
		
		
		ArrayList<Integer> removeTasks1, removeTasks2;
		removeTasks1 = new ArrayList<Integer>();
		removeTasks2= new ArrayList<Integer>();

		
		
		for(Integer idTask : ind1.getOrderTasks())
			if( !ind2.getOrderTasks().contains(idTask) )
				removeTasks1.add(idTask);
		
		for(Integer idTask : ind2.getOrderTasks())
			if( !ind1.getOrderTasks().contains(idTask) )
				removeTasks2.add(idTask);
		
		for(Integer idTask: removeTasks1)
			ind1.getOrderTasks().remove(idTask);
		
		for(Integer idTask: removeTasks2)
			ind2.getOrderTasks().remove(idTask);
		
		
	}
	
	

	/**
	 * changes the individual's structure :
	 * 0 : it changes its startTime by adding minutes
	 * 1 : it changes its startTime by decreasing minutes
	 * 2 : it adds a task
	 * 3 : it removes a task
	 */
	private void mutation() {

		Random r = new Random();
		ArrayList<Integer> toRemoveTasks;
		ArrayList<Integer> toAddTasks;
		ArrayList<Integer> switchIndexTasks;
		int mutationType;
		int indexTask;
		int indexInd;
		float mutationThresHold;
		int firstInd = newPopulation.size() * 2 / 10;
		int secondInd = firstInd * 2;
		
		indexInd = 0;

		for(Individual ind : newPopulation)
		{

			mutationThresHold = ConstantsPopulation.mutationThresholdOne;
			if(indexInd >= firstInd && indexInd < secondInd)
				mutationThresHold = ConstantsPopulation.mutationThresHoldTwo;
			
			if(indexInd >= secondInd)
				mutationThresHold = ConstantsPopulation.mutationThresHoldThree;
		
			
			if(r.nextFloat() < mutationThresHold)
			{
				if(ind.getOrderTasks().size() == 0)
				{
					ind.getOrderTasks().add(r.nextInt( shiftingTasks.size()));
					ind.setStartTime(startTimeMinutes + r.nextInt(endTimeMinutes - startTimeMinutes));
					continue;
				}
				
				
				mutationType = r.nextInt(6);				
				switch(mutationType)
				{
					case 0 :  // se adauga minute la momentul de incepere
						
					ind.setStartTime(ind.getStartTime() + ConstantsPopulation.minMutationMinutes 
					+ r.nextInt(ConstantsPopulation.maxMutationMinutes - ConstantsPopulation.minMutationMinutes));
					
					break;
					
					case 1 :  // se elimina minute de la momentul de incepere
						
					ind.setStartTime(ind.getStartTime() - ConstantsPopulation.minMutationMinutes 
					+ r.nextInt(ConstantsPopulation.maxMutationMinutes - ConstantsPopulation.minMutationMinutes));
						
					break; 
					
					
					case 2 :  
						
				
					toAddTasks = new ArrayList<Integer>();
					int addId;
					for(indexTask = 0; indexTask < shiftingTasks.size(); indexTask++)
						if(! ind.getOrderTasks().contains(indexTask))
							toAddTasks.add(indexTask);
						
					if(toAddTasks.size() !=0)
					{
						addId = toAddTasks.get(r.nextInt(toAddTasks.size()));
						ind.getOrderTasks().add(r.nextInt(ind.getOrderTasks().size()), addId);
					}

					
					break;
					
					
					case 3 :
						
					toRemoveTasks = new ArrayList<Integer>();
					int removeId;

					
					for(indexTask = 0; indexTask < shiftingTasks.size(); indexTask++)
						if(ind.getOrderTasks().contains(indexTask))
						{
							toRemoveTasks.add(indexTask);
						}
						
					removeId = toRemoveTasks.get(r.nextInt(toRemoveTasks.size()));
					ind.getOrderTasks().remove(new Integer(removeId)); 

						
					break; 
					
					
					case 4 :

						
					Integer aux;
					switchIndexTasks = new ArrayList<Integer>();
					int position1, position2;
					position1 = r.nextInt(ind.getOrderTasks().size());
					
					for(int i = 0; i < ind.getOrderTasks().size(); i++)
						if(position1 != i)
							switchIndexTasks.add(i);
					
					
					
					if(switchIndexTasks.size() == 0)
						break;
					
					position2 = switchIndexTasks.get(r.nextInt(switchIndexTasks.size()));
					
					aux = ind.getOrderTasks().get(position1);
					ind.getOrderTasks().set(position1, ind.getOrderTasks().get(position2));
					ind.getOrderTasks().set(position2, aux);

					
					break;
					
					case 5 :
					
					
						
					toAddTasks = new ArrayList<Integer>();
					for(indexTask = 0; indexTask < shiftingTasks.size(); indexTask++)
						if(!ind.getOrderTasks().contains(indexTask))
							toAddTasks.add(new Integer(indexTask));
					
					
					if(toAddTasks.size() > 0 ){

						
						ind.getOrderTasks().set(r.nextInt(ind.getOrderTasks().size()),
						toAddTasks.get(r.nextInt(toAddTasks.size())));

					}
							
					break; 
					
				
				}

				
			}
			
			indexInd++;
		}
		
		
	}
	
	
	/**
	 * the fitness represents the sum of each duration + the durations taken to travel between the locations
	 * @param ind : the individual for which the fitness is calculated
	 * @return : the fitness calculated
	 */
	public float calculateFitnessValue(Individual ind)
	{
		
		float sumMinutes = 0;
		int durationTravel;
		int indexTask;
		int priorityNumber;
		int durationTask;
		float penalty;
		int startTime;
		int sumRecompensa = 0;
		
		ArrayList<Integer> tasks = ind.getOrderTasks();
		
		LocationContext location1, location2;

		
		
		if(tasks.size() == 0)
			return 0;
		
		
		
		
		location1 = (LocationContext) PopulationEvolution.shiftingTasks.get(tasks.get(0)).
		getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		durationTravel =	ComputationalMethods.calculateDurationTravel
		(currentPosition.latitude, currentPosition.longitude, location1.getLatitude(), location1.getLongitude());
		
		sumMinutes +=durationTravel;
		
		
	// chestia asta am pus-o ca sa calculez startTime
		startTime = ind.getStartTime();
		startTime += durationTravel;

		for(indexTask = 0; indexTask < tasks.size() - 1 ; indexTask++)
		{

			shiftingTasks.get(tasks.get(indexTask)).setStartTimeFromInterger(startTime);
			durationTask = ComputationalMethods.determineDurationTask(shiftingTasks.get(tasks.get(indexTask)));
			startTime += durationTask;

			
			sumMinutes += durationTask;
			

			location1 = (LocationContext) PopulationEvolution.shiftingTasks.get(tasks.get(indexTask)).
					getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
					
			location2 = (LocationContext) PopulationEvolution.shiftingTasks.get(tasks.get(indexTask + 1)).
			getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			
			
			durationTravel =	ComputationalMethods.calculateDurationTravel
			(location1.getLatitude(), location1.getLongitude(), location2.getLatitude(), location2.getLongitude());
			
			
			sumMinutes +=durationTravel;
			startTime += durationTravel;
			
			
		}

		shiftingTasks.get(tasks.get(indexTask)).setStartTimeFromInterger(startTime);
		durationTask = ComputationalMethods.determineDurationTask(shiftingTasks.get(tasks.get(indexTask)));
		sumMinutes += durationTask;
		
		
		location1 = (LocationContext) PopulationEvolution.shiftingTasks.get(tasks.get(indexTask)).
		getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
		
		
		durationTravel =	ComputationalMethods.calculateDurationTravel
		( location1.getLatitude(), location1.getLongitude(), endPosition.latitude, endPosition.longitude);
		sumMinutes +=durationTravel;

		
		ind.setDuration(sumMinutes);


		penalty = calculatePenalty(ind, sumMinutes);
	

		
		sumMinutes += penalty;
		
		for(Integer idTask : ind.getOrderTasks())
		{
		  	priorityNumber = Core.getPrioritiesValues().get( shiftingTasks.get(idTask).getPriority());
		  	int cucu =  ComputationalMethods.prioritiesValues.get(priorityNumber);
		  	
		  //	System.out.println("Recompensa este" + cucu);
		  	
		  	 sumMinutes -= cucu;
			//sumMinutes -= ComputationalMethods.prioritiesValues.get(priorityNumber);
			

		}
		
		return sumMinutes;
		
		
	}
	
	private float calculatePenalty(Individual ind, float sumMinutes) {
		
		int durationOver = 0;
		float penalty = 0;
		
		
		if(ind.getStartTime() < startTimeMinutes)
			durationOver += startTimeMinutes - ind.getStartTime();
		if( (sumMinutes + ind.getStartTime() ) > endTimeMinutes)
			durationOver += sumMinutes + ind.getStartTime() - endTimeMinutes;

		
		if(durationOver <0)
			return 0;
		
		if(durationOver < 15 )
		{
			penalty = ConstantsPopulation.penalty_one * durationOver;
		}
		if(durationOver >= 15 && durationOver < 30  )
		{
			
			penalty =  ConstantsPopulation.penalty_one * 15;
			penalty += ConstantsPopulation.penalty_two *  (durationOver - 15);
		}
		if(durationOver >= 30)
		{
			penalty = ConstantsPopulation.penalty_three * durationOver;
			
		/*	penalty =  ConstantsPopulation.penalty_one * 15;
			penalty += ConstantsPopulation.penalty_two *  (30 - 15);
			penalty += ConstantsPopulation.penalty_three * (durationOver - 30);
			
		*/
		}
		
		
		return penalty;
		
		
		
	}


	public ArrayList<Individual> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

	public ArrayList<Individual> getNewPopulation() {
		return newPopulation;
	}

	public void setNewPopulation(ArrayList<Individual> newPopulation) {
		this.newPopulation = newPopulation;
	}

	public LatLng getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(LatLng currentPosition) {
		this.currentPosition = currentPosition;
	}

	public LatLng getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(LatLng endPosition) {
		this.endPosition = endPosition;
	}

	public int getStartTimeMinutes() {
		return startTimeMinutes;
	}

	public void setStartTimeMinutes(int startTimeMinutes) {
		this.startTimeMinutes = startTimeMinutes;
	}

	public int getEndTimeMinutes() {
		return endTimeMinutes;
	}

	public void setEndTimeMinutes(int endTimeMinutes) {
		this.endTimeMinutes = endTimeMinutes;
	}


	
	
}
