package com.umd.ece552.training.ga;

import java.util.Random;

/**
 * 
 * @author ingegarcia
 *
 */
public class Mutation {
	
	private Random rnd;
	
	/**
	 * 
	 */
	public Mutation()
	{
		this.rnd = new Random();
	}
	
	/**
	 * 
	 * @param parent
	 * @return
	 */
	public String operation(String parent)
	{
		StringBuffer child = new StringBuffer();
		int position = this.rnd.nextInt(parent.length());
		if (position >= parent.length())
			position = parent.length()-1;

		char mutateChar = '0';
		if (parent.charAt(position) == '0')
			mutateChar = '1';
		
		child.append(parent.substring(0, (position) ) + mutateChar);
		if(position+1 < parent.length()) 
			child.append(parent.substring(position+1));
		
		return child.toString();
	}
	
	
}
