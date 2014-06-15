package Salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.meniu.Core;

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
		int decade= 0, nrDecades = 100;
		
		
		initMembers();
		
		
	//	for(Task task :shiftingTasks)
	//		System.out.println(task.getNameTask() + " " + task.getPriority());
		
		
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
			
		System.out.println("INTRA AICI 2");

		
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

		System.out.println("%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%");
		
		for(indexInd = 0; indexInd < sizeSelection; indexInd++){
			
		/*	if(indexInd == 0)
			{
				System.out.println(population.get(indexInd).getFitnessValue() + "--- " + population.get(indexInd).getStartTime() + "---" + population.get(indexInd).getOrderTasks().size() + " " + population.get(indexInd).getDuration());
				System.out.println(population.get(indexInd).getOrderTasks().toString() );
				System.out.println("##################");
			}*/
			
			newPopulation.add(population.get(indexInd));
		}
		
	/*	for(indexInd = 0; indexInd < newPopulation.size(); indexInd++)
		{
			System.out.print(newPopulation.get(indexInd).getFitnessValue() + " ");
		}
		
		System.out.println(population.get(0).getOrderTasks().toString() );
		*/
		
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
			
			
	/*		System.out.println("--------------");
			System.out.println("Parintele 1 " + extras1.getOrderTasks());
			System.out.println("Parintele 2 " + extras2.getOrderTasks());
			System.out.println("--------------");
	*/		
			
			prepareParentsForCrossOver(ind1, ind2, extras1, extras2);
			
			tasksRemained.clear();
			nrTasks = ind1.getOrderTasks().size();
			tasksRemained.addAll(ind1.getOrderTasks());

		
			
			taskId = ind1.getOrderTasks().get( r.nextInt(nrTasks));			
			child1.getOrderTasks().add(taskId);
			tasksRemained.remove(new Integer( taskId));
			
	/*		System.out.println("--------------");
			System.out.println("Parintele 1 " + extras1.getOrderTasks());
			System.out.println("Parintele 2 " + extras2.getOrderTasks());
			System.out.println("--------------");
			
	*/
			
			
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
				
			//	System.out.println("TASKUL ALES ESTE" + taskId);
				
				tasksRemained.remove(new Integer( taskId));
			}
			
	//		System.out.println("Ce au in comun este" + child1.getOrderTasks().toString());
			
			child2.getOrderTasks().addAll(child1.getOrderTasks());
			
			if(child1.getOrderTasks().size() < extras1.getOrderTasks().size() )
			{
		/*		System.out.println("Radacina nu are dimensiunea parintelui 1");
				System.out.println(child1.getOrderTasks().toString());
				System.out.println(extras1.getOrderTasks().toString());
				System.out.println("----------");
		*/		
				
				for(Integer task : extras1.getOrderTasks())
					if(!child1.getOrderTasks().contains(task))
						child1.getOrderTasks().add(task);
				
		//		System.out.println(child1.getOrderTasks().toString());
			}
			
			if(child2.getOrderTasks().size() < extras2.getOrderTasks().size() )
			{
				
		/*		System.out.println("Radacina nu are dimensiunea parintelui 2");
				System.out.println(child2.getOrderTasks().toString());
				System.out.println(extras2.getOrderTasks().toString());
				System.out.println("----------");
		*/		
				for(Integer task : extras2.getOrderTasks())
					if(!child2.getOrderTasks().contains(task))
						child2.getOrderTasks().add(task);
				
		//		System.out.println(child2.getOrderTasks().toString());
			}
			
		//	System.out.println("Copil1 este " + child1.getOrderTasks().toString());
		//	System.out.println("Copil2 este " + child1.getOrderTasks().toString());
			
			
			child1.setStartTime(ind1.getStartTime() + (int) 
			( ConstantsPopulation.sample1Alpha * (ind2.getStartTime() - ind1.getStartTime())  ) );
			
			child2.setStartTime(ind1.getStartTime() + (int) 
			( ConstantsPopulation.sample2Alpha * (ind2.getStartTime() - ind1.getStartTime())  ) );
			
		//	System.out.println(child1.getStartTime() + " " + child2.getStartTime() + " " + ind1.getStartTime() + " " + ind2.getStartTime());
			
			
			newPopulation.add(child1);
			newPopulation.add(child2);
			
			
		}
		
		//System.out.println("A IESIT " +  newPopulation.size());

		
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
		int mutationType;
		int indexTask;
		
		
		
		for(Individual ind : population)
		{
			if(r.nextFloat() < ConstantsPopulation.mutationThreshold )
			{
				if(ind.getOrderTasks().size() == 0)
					continue;
				
				
				mutationType = r.nextInt(6);
				
		//		System.out.println("S-a produs o mutatie");
				
				switch(mutationType)
				{
					case 0 : 
						
					ind.setStartTime(ind.getStartTime() + ConstantsPopulation.minMutationMinutes 
					+ r.nextInt(ConstantsPopulation.maxMutationMinutes - ConstantsPopulation.minMutationMinutes));
					
					break;
					
					case 1 : 
						
					ind.setStartTime(ind.getStartTime() - ConstantsPopulation.minMutationMinutes 
					+ r.nextInt(ConstantsPopulation.maxMutationMinutes - ConstantsPopulation.minMutationMinutes));
						
					break; 
					
					
					case 2 :
						
					
					
					for(indexTask = 0; indexTask < shiftingTasks.size(); indexTask++)
						if(! ind.getOrderTasks().contains(indexTask))
						{
							ind.getOrderTasks().add(indexTask);
							break;
						}
					
					
					break;
					
					
					case 3 :
						
					
						
					for(indexTask = 0; indexTask < shiftingTasks.size(); indexTask++)
						if(ind.getOrderTasks().contains(indexTask))
						{
							ind.getOrderTasks().remove(new Integer(indexTask));
							break;
						}
						
					break; 
					
					
					case 4 :
						
						
					Integer aux;
					int position1, position2;
					position1 = r.nextInt(ind.getOrderTasks().size());
					position2 = r.nextInt(ind.getOrderTasks().size());
					
					aux = ind.getOrderTasks().get(position1);
					ind.getOrderTasks().set(position1, ind.getOrderTasks().get(position2));
					ind.getOrderTasks().set(position2, aux);
					
			//		System.out.println("Mutatie " + ind.getOrderTasks().toString());
					
					break;
					
					case 5 :
						
					for(indexTask = 0; indexTask < shiftingTasks.size(); indexTask++)
							if(!ind.getOrderTasks().contains(indexTask))
							{
								ind.getOrderTasks().remove(r.nextInt(ind.getOrderTasks().size()));
								ind.getOrderTasks().add(new Integer(indexTask));
								break;
							}
							
					break; 
					
				
				}
				
		//		System.out.println("new Dim " + ind.getOrderTasks().size());
				
			}
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
		float penaltyMorning = 0 , penaltyEvening = 0;
		ArrayList<Integer> tasks = ind.getOrderTasks();
		
		LocationContext location1, location2;
		
	//	System.out.println("------------");
		
		
		if(tasks.size() == 0)
			return 0;
		
		
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
		
		ind.setDuration(sumMinutes);
		
	//	System.out.println("Inainte " + sumMinutes + " " + ind.getStartTime());
		
		penaltyMorning = penaltyMorningCompute(ind);
		penaltyEvening = penaltyEvening(ind,sumMinutes);
		
	
	//	System.out.println("PEdeapsa " +  penaltyEvening);
		
		
		sumMinutes += penaltyEvening + penaltyMorning;
		
	//	System.out.println("Dupa " + sumMinutes);
		
		for(Integer idTask : ind.getOrderTasks())
		{
			priorityNumber = Core.getPrioritiesValues().get( shiftingTasks.get(idTask).getPriority());
						
			sumMinutes = sumMinutes - ComputationalMethods.durations.get(idTask) - ComputationalMethods.prioritiesValues.get(priorityNumber);  
		}
		
		
	//	System.out.println("DUpa 2 " + sumMinutes);
		
		
		return sumMinutes;
		
		
	}
	
	public float penaltyMorningCompute(Individual ind)
	{
		float penaltyMorning = 0;
		
		if(ind.getStartTime() < ConstantsPopulation.minimalStartTime){
			
			if( (ConstantsPopulation.minimalStartTime - ind.getStartTime()  )< 30)
			{
				penaltyMorning = ConstantsPopulation.penalty_one *
							(ConstantsPopulation.minimalStartTime - ind.getStartTime());
			}
			else{
				if( (ConstantsPopulation.minimalStartTime - ind.getStartTime() ) < 60)
				{
					penaltyMorning = ConstantsPopulation.penalty_two *
								(ConstantsPopulation.minimalStartTime - ind.getStartTime());
				}
				else{
						penaltyMorning = ConstantsPopulation.penalty_three *
									(ConstantsPopulation.minimalStartTime - ind.getStartTime());
				}
			}
			
		}
		
		return penaltyMorning;
	}
	
	public float penaltyEvening(Individual ind, float sumMinutes)
	{
		float penaltyEvening= 0;
		
		if( (sumMinutes + ind.getStartTime() ) > ConstantsPopulation.maximalEndTime)
		{
		//	System.out.println("intra aici" +  ind.getStartTime() + " " + sumMinutes + " " + penaltyEvening);
			
			if((sumMinutes + ind.getStartTime()  - ConstantsPopulation.maximalEndTime ) < 30 )
			{
				penaltyEvening =(sumMinutes + ind.getStartTime() - ConstantsPopulation.maximalEndTime  ) 
				* ConstantsPopulation.penalty_one;
			} 
			else{
				if((sumMinutes + ind.getStartTime()  - ConstantsPopulation.maximalEndTime ) < 60 )
					penaltyEvening =(sumMinutes + ind.getStartTime() - ConstantsPopulation.maximalEndTime  ) 
					* ConstantsPopulation.penalty_two;
				else{
						penaltyEvening =(sumMinutes + ind.getStartTime() - ConstantsPopulation.maximalEndTime  ) 
						* ConstantsPopulation.penalty_three;
				}
			}
		}
	//	System.out.println("Datele sunt " + ind.getStartTime() + " " + sumMinutes + " " + penaltyEvening);
		
		return penaltyEvening;
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
	
	
}
