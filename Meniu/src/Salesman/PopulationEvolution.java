package Salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
	ArrayList<Individual> population;
	
	
	/**
	 * contains the new population after selection, crossover, mutation
	 */
	ArrayList<Individual> newPopulation;
	
	
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
	
	

	/**
	 * @param fixedTasks : the tasks whose start time and end time cannot be changed for today from database
	 * @param shiftingTasks : the tasks whose start time and end time can be changed from database
	 */
	public PopulationEvolution(ArrayList<FixedTaskInformation> fixedTasks, List<Task> shiftingTasks) 
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
		
		
		
		initMembers();
		
		
		selection();
		crossOver();
		

		
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
		
		
		System.out.println("DIMENSIUNEA ESTE " + sizeSelection);
		
		for(indexInd = 0; indexInd < sizeSelection; indexInd++){
			System.out.println(population.get(indexInd).getFitnessValue() + "--- " + population.get(indexInd).getStartTime());
			newPopulation.add(population.get(indexInd));
		}
	}
	
	
	/**
	 * selects random two individuals and calculates two children
	 */
	public void crossOver() {
		
		int indexChildren, iterations, taskId, duration1, duration2;
		int position1, position2, position1Next, position2Next;
		Random r = new Random();
		LocationContext location, location1Next, location2Next;
		
		
		iterations = sizeCrossover / 2;
		
		
		
		
		for(indexChildren = 0; indexChildren < iterations; indexChildren++)
		{
			Individual ind1 = population.get(r.nextInt(population.size()));
			Individual ind2 = population.get(r.nextInt(population.size()));
			Individual child1, child2;
			
			child1 = new Individual();
			child2 = new Individual();
			
			while(true)
			{
			
				taskId = r.nextInt(shiftingTasks.size());
				if(ind1.getOrderTasks().contains(taskId) &&
				   ind2.getOrderTasks().contains(taskId) )
					break;
				
				
				
			}
			
			child1.getOrderTasks().add(taskId);
			
			System.out.println("INDIVIDUL 1 ESTE " + ind1.getOrderTasks().toString());
			System.out.println("INDIVIDUL 2 ESTE " + ind2.getOrderTasks().toString());
		//	System.out.println("PRIMUL TASK ESTE" + taskId);
			
			while(child1.getOrderTasks().size() != shiftingTasks.size() )
			{
				position1 = ind1.getOrderTasks().indexOf(taskId);  // determine the index of the task in individual 1
				position2 = ind2.getOrderTasks().indexOf(taskId);  // determine the index of the task in individual 1
				
				position1Next =  (position1 + 1) % shiftingTasks.size();
				position2Next =  (position2 + 1) % shiftingTasks.size();
				
				
			//	System.out.println("Pozitiile viitoare sunt" + position1Next + " " + position2Next);
				
				location = (LocationContext) shiftingTasks.get(position1).
				getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
				
				location1Next = (LocationContext) shiftingTasks.get(position1Next).
				getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
				
				location2Next = (LocationContext) shiftingTasks.get(position2Next).
				getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
				
				
				duration1 = ComputationalMethods.calculateDurationTravel
				(location.getLatitude(), location.getLongitude(), location1Next.getLatitude(), location1Next.getLongitude());
				
				
				duration2 = ComputationalMethods.calculateDurationTravel
				(location.getLatitude(), location.getLongitude(), location2Next.getLatitude(), location2Next.getLongitude());
				
				
				
				
				if(duration1 < duration2){
					child1.getOrderTasks().add(ind1.getOrderTasks().get(position1Next));
					taskId = ind1.getOrderTasks().get(position1Next);
				}
				else{
					child1.getOrderTasks().add(ind2.getOrderTasks().get(position2Next));
					taskId = ind2.getOrderTasks().get(position2Next);
				}
				
			//	System.out.println("TASKUL ALES ESTE" + taskId);
				
				
			}
			
			System.out.println(child1.getOrderTasks().toString());
			
			
		}
		
		System.out.println("A IESIT");

		
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
			factorial *= indexTask;
			taskId.add(indexTask -1 );
		}
		
		
		sizePopulation = Math.max(2,  factorial / ConstantsPopulation.nrIndRatio);
		sizeTasks = shiftingTasks.size();
		
		for(indexIndivid = 0; indexIndivid < sizePopulation; indexIndivid++  )
		{
			tasksRemained.addAll(taskId);
			Individual ind = new Individual();
			
			for(indexTask = 0; indexTask < sizeTasks; indexTask++)
				ind.getOrderTasks().add( tasksRemained.remove(r.nextInt(tasksRemained.size())) ); 
			
			ind.setStartTime(ConstantsPopulation.minimalStartTime +
			r.nextInt(ConstantsPopulation.maximalEndTime - ConstantsPopulation.minimalStartTime));
			
			population.add(ind);
			
		}
		
		
		
		sizeSelection = (int) (population.size() * ConstantsPopulation.selectionRatio);
		sizeCrossover = population.size() - sizeSelection;
		
		
		ComputationalMethods.initDurations();

		
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
		ArrayList<Integer> tasks = ind.getOrderTasks();
		
		LocationContext location1, location2;
		
		
		for(indexTask = 0; indexTask < tasks.size() - 1 ; indexTask++)
		{
			sumMinutes +=  ComputationalMethods.durations.get(tasks.get(indexTask));
			

			location1 = (LocationContext) PopulationEvolution.shiftingTasks.get(tasks.get(indexTask)).
					getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
					
			location2 = (LocationContext) PopulationEvolution.shiftingTasks.get(tasks.get(indexTask + 1)).
			getInternContext().getContextElementsCollection().get(ContextElementType.LOCATION_CONTEXT_ELEMENT);
			
			
			durationTravel =	ComputationalMethods.calculateDurationTravel
			(location1.getLatitude(), location1.getLongitude(), location2.getLatitude(), location2.getLongitude());
			
			sumMinutes +=durationTravel;
			
			
		}
		

		sumMinutes +=  ComputationalMethods.durations.get(tasks.get(indexTask));
		
		
		
		
		if(ind.getStartTime() < ConstantsPopulation.minimalStartTime)
			return sumMinutes + ConstantsPopulation.penalty *
			(ConstantsPopulation.minimalStartTime - ind.getStartTime());
		
		if( (sumMinutes + ind.getStartTime() ) > ConstantsPopulation.maximalEndTime)
			return sumMinutes + 
			(sumMinutes + ind.getStartTime() - ConstantsPopulation.maximalEndTime  ) * ConstantsPopulation.penalty;
		
		
		return sumMinutes;
		
		
	}
	
}
