package Salesman;

public interface ConstantsPopulation
{
	int nrIndRatio				= 20;
	
	
	
	float speedPase				= (float) 1.5; // the user needs 1.5 seconds to walk a meter
	
	
	/**
	 *  for each minute the duration of the individual exceeds the limits of the interval when the tasks
	 *  can be executed, the fitness is increased
	 */
	int penalty_one			= 5;
	
	int penalty_two			= 10;
	
	int penalty_three		= 15;
	
	
	/**
	 * 1/5 representing the best individuals will be selected for the next population
	 */
	float selectionRatio		= (float)  0.50; // the ratio from population which will be selected based on the fitness
	
	float sample1Alpha			= (float)  -0.25;
	
	float sample2Alpha			= (float)  0.25;
	
	
	float mutationThresholdOne		= (float) 0.05;
	
	
	float mutationThresHoldTwo		= (float) 0.15;
	
	float mutationThresHoldThree	= (float) 0.3;

	
	int maxMutationMinutes		= 120;
	
	int minMutationMinutes		= 30;
	
	
	
	int pickerCurrentStartHour		= 7;
	int pickerCurrentStartMinute	= 0;
	
	
	int pickerCurrentEndHour	= 21;
	int pickerCurrentEndMinute	= 0;
	
	
	
	
	
	
}
