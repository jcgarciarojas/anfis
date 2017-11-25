package com.umd.ece552.training.ga;

/**
 * 
 * @author ingegarcia
 *
 */
public class Hypothesis implements Comparable<Hypothesis>
{
	private String b = new String();
	private double fitness;

	/**
	 * 
	 * @param b
	 */
	public Hypothesis(String b)
	{
		this.b = b;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getHypothesis()
	{
		return b;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getFitness()
	{
		return this.fitness;
	}

	/**
	 * 
	 * @param fitness
	 */
	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}
	
	/**
	 * 
	 */
	public int compareTo(Hypothesis obj)
	{
		int compare = 0;
		if (this.getFitness() > obj.getFitness())
			compare = 1;
		else if (this.getFitness() < obj.getFitness())
			compare = -1;
		
		return compare;
	}
}
