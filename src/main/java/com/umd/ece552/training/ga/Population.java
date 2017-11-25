package com.umd.ece552.training.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetIterator;

/**
 * 
 * @author ingegarcia
 *
 */
public class Population {
	
	private List<Hypothesis> pop;
	
	/**
	 * 
	 */
	public Population()
	{
		this.pop = new ArrayList<Hypothesis>();
	}
	
	/**
	 * 
	 * @param obj
	 */
	public void add(Hypothesis obj)
	{
		this.pop.add(obj);
	}

	/**
	 * 
	 * @param obj
	 */
	public void add(Population pop)
	{
		Iterator it = pop.getIterator();
		while(it.hasNext())
			this.add((Hypothesis)it.next());
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Object get(int index)
	{
		return this.pop.get(index);
	}

	/**
	 * 
	 * @param pop
	 */
	public Population(List<Hypothesis> pop)
	{
		this.pop = pop;
	}
	
	/**
	 * 
	 * @return
	 */
	public Iterator getIterator()
	{
		return new NetIterator(pop);
	}
	
	/**
	 * 
	 * @return
	 */
	public int size()
	{
		return pop.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public void sort()
	{
		Collections.sort(pop);
	}

	/**
	 * 
	 * @return
	 */
	public void remove(Object obj)
	{
		this.pop.remove(obj);
	}

	/**
	 * 
	 * @return
	 */
	public void remove(int index)
	{
		this.pop.remove(index);
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		String str = "";
		
		Iterator it = this.getIterator();
		while(it.hasNext())
		{
			Hypothesis h = (Hypothesis)it.next();
			str += "Hypothesis "+h.getHypothesis() +", fitness "+ h.getFitness() + "\n";
		}
		
		return str;
	}

}

