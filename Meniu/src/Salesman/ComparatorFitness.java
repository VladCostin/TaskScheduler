package Salesman;

import java.util.Comparator;

public class ComparatorFitness implements Comparator{ 

	@Override
	public int compare(Object lhs, Object rhs) {
		
		Individual ind1 = (Individual) lhs;
		
		Individual ind2 = (Individual) rhs;
		
		if(ind1.getFitnessValue() < ind2.getFitnessValue())
			return -1;
		
		if(ind1.getFitnessValue() > ind2.getFitnessValue())
			return 1;
		
		return 0;
		
		
	}
	
	

}
