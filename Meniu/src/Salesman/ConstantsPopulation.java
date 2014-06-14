package Salesman;

public interface ConstantsPopulation
{
	int nrIndRatio				= 20;
	
	
	int minimalStartTime		= 420;
	
	
	int maximalEndTime			= 1260;
	
	
	float speedPase				= (float) 1.5; // the user needs 1.5 seconds to walk a meter
	
	
	/**
	 *  for each minute the duration of the individual exceeds the limits of the interval when the tasks
	 *  can be executed, the fitness is incresed with 50
	 */
	int penalty					= 1;
	
	
	/**
	 * 1/5 representing the best individuals will be selected for the next population
	 */
	float selectionRatio		= (float)  0.80; // the ratio from population which will be selected based on the fitness
	
	float sample1Alpha			= (float)  -0.25;
	
	float sample2Alpha			= (float)  0.25;
	
	
	float mutationThreshold		= (float) 0.1;
	
	
	int maxMutationMinutes		= 120;
	
	int minMutationMinutes		= 30;
	
	
	
	
}
