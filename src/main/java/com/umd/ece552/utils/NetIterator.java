package com.umd.ece552.utils;

import java.util.List;

/**
 * 
 * @author ingegarcia
 *
 */
public class NetIterator implements Iterator {

	private List objects; 
	private int index =-1;
	
	/**
	 * 
	 * @param objects
	 */
	public NetIterator(List objects)
	{
		this.objects = objects;
	}
	
	/**
	 * 
	 */
	public boolean hasNext()
	{
		boolean hasNext = index < objects.size()-1;
		return (hasNext);
	}
	
	public boolean isLast()
	{
		if (index > objects.size()-1) throw new IndexOutOfBoundsException("");
		return (index == objects.size()-1);
	}

	public boolean isFirst()
	{
		if (index > objects.size()-1) throw new IndexOutOfBoundsException("");
		return (index == 0);
	}
	
	public int size()
	{
		return objects.size();
	}

	/**
	 * 
	 */
	public Object next()
	{
		++index;
		return objects.get(index);
	}

	public int getIndex()
	{
		return index;
	}

	public void restart()
	{
		this.index = -1;
	}
}
