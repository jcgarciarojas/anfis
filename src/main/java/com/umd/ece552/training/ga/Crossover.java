package com.umd.ece552.training.ga;

import com.umd.ece552.exception.NetException;

public class Crossover {

	private int len;
	private String mask;
	private static final char byte1 = '0'; 
	private static final char byte2 = '1'; 
	
	/**
	 * 
	 */
	public Crossover(String mask)
	{
		this.mask = mask;
		this.len = mask.length();
	}

	/**
	 * 
	 * @param parents
	 * @return
	 */
	public String[] getOffSprings(String parent1, String parent2) throws NetException
	{
		String[] children = {getOffSpring(parent1, parent2), getOffSpring(parent2, parent1)};
		return children;
	}

	/**
	 * 
	 * @param parent1
	 * @param parent2
	 * @return
	 */
	private String getOffSpring(String parent1, String parent2)
	{
		StringBuffer offSpring = new StringBuffer();

		for(int index=0; index < len; index++)
		{
			if(this.mask.charAt(index) == byte1)
				offSpring.append(parent1.charAt(index));

			else if(this.mask.charAt(index) == byte2)
				offSpring.append(parent2.charAt(index));
		}
		
		return offSpring.toString();
	}

}
