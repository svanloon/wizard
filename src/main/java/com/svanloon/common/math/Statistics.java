package com.svanloon.common.math;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Document the  class 
 *
 * @author spierce
 * @version $Rev$, $LastChangedDate$
 */
public class Statistics {
	private static final Map<Double, Double> factorialCache = new LinkedHashMap<Double, Double>();

	/**
	 * Constructs a new <code>Statistics</code> object. 
	 */
	private Statistics() {
		super();
	}

	/**
	 *  As described by MS Excel
	 *  
	 *  Returns the individual term binomial distribution probability. 
	 *  Use in problems with a fixed number of tests or trials, 
	 *  when the outcomes of any trial are only success or failure, 
	 *  when trials are independent, 
	 *  and when the probability of success is constant throughout the experiment. 
	 *  For example, BINOMDIST can calculate the probability that two of the next three babies born are male.
	 *  
	 * @param totalTrials
	 * @param successes
	 * @param chanceOfSuccess
	 * @return double
	 */
	public static double binomialProbability(long totalTrials, long successes, double chanceOfSuccess) {
		if (totalTrials < successes) {
			throw new IllegalArgumentException("#trials cannot be less than #successes");
		}
		if (chanceOfSuccess < 0) {
			throw new IllegalArgumentException("chance of success cannot be less than 0");
		}
		if (chanceOfSuccess > 1) {
			throw new IllegalArgumentException("chance of success cannot greater than 1");
		}
		double q = 1 - chanceOfSuccess;
		double result;
		double pow_p = Math.pow(chanceOfSuccess, successes);
		double pow_q = Math.pow(q,(totalTrials-successes));
		result = combinations(totalTrials, successes) * pow_p * pow_q;
		return result;
	}

	/**
	 * 
	 * Document the factorial method 
	 *
	 * @param n
	 * @return double
	 */
	public static double factorial(double n) {
		Double result = factorialCache.get(Double.valueOf(n));
		if (result == null) {
			result = Double.valueOf(factorialRecurs(n));
			factorialCache.put(Double.valueOf(n), result);
		}
		return result.doubleValue();
	}

	/**
	 * 
	 * Document the factorialRecurs method 
	 *
	 * @param n
	 * @return double
	 */
	public static double factorialRecurs(double n) {
		// check cache
	    if( n <= 1 ) {     // base case
	        return 1;
	    }
		return n * factorialRecurs( n - 1 );
	}

	/**
	 * (N-choose-k) is defined as a number of ways in which one can select k objects from N objects, 
	 * and is an alternative term for binomial coefficient. 
	 * Also defined as combination without repetition in combinations and permutations.
	 * 
	 * @param population
	 * @param selections
	 * @return double
	 */
	public static double combinations(double population, double selections) {
		if (population < selections) {
			throw new IllegalArgumentException("population (" + population + " ) cannot be less than selections (" + selections + ")");
		}
		double n1 = factorial(population);
		double d1 = (factorial(selections) * factorial(population - selections));
		return n1 / d1;
	}

	/**
	 * Returns the hypergeometric distribution. 
	 * HYPGEOMDIST returns the probability of a given number of sample successes, 
	 * given the sample size, population successes, and population size. 
	 * Use HYPGEOMDIST for problems with a finite population, 
	 * where each observation is either a success or a failure, 
	 * and where each subset of a given size is chosen with equal likelihood.
	 * 
	 * @param favorableSelections sample_s
	 * @param totalSelections number_sample
	 * @param favorablePopulation population_s
	 * @param totalPopulation number_population
	 * @return double
	 */
	public static double hyperGeoDist(long favorableSelections, long totalSelections, long favorablePopulation, long totalPopulation) {
		if (favorablePopulation > totalPopulation) {
			throw new IllegalArgumentException("favorablePopulation (" + favorablePopulation + " ) cannot be more than totalPopulation (" + totalPopulation + ")");
		}
		
		double a = combinations(favorablePopulation, favorableSelections);
		double b = combinations(totalPopulation - favorablePopulation, totalSelections - favorableSelections);
		double c = combinations(totalPopulation, totalSelections);
		
		return a * b / c; 
	}

	/**
	 * 
	 * This will give a list of the prime factors of the number num. 
	 *
	 * @param num
	 * @return List
	 */
	public static List<Integer> factors(int num) {
		List<Integer> factors = new ArrayList<Integer>();
		int temp = num;
		while(true) {
			double sqrt = num /2.0;
			boolean isPrime = true;
			for(double i = 2; i <= sqrt; i++) {
				if( temp/(int)i == temp / i) {
					factors.add(Integer.valueOf((int)i));
					temp = (int)(temp / i);
					isPrime = false;
					break;
				}
				isPrime = true;
			}
			if(!isPrime) {
				continue;
			}
			break;
		}
		return factors;
	}

}