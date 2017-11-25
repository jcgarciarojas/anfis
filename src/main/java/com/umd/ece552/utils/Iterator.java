package com.umd.ece552.utils;

/**
 * 
 * @author ingegarcia
 *
 */
public interface Iterator {
	
	public boolean hasNext();
	public Object next();
	public boolean isLast();
	public boolean isFirst();
	public int size();
	public int getIndex();
	public void restart();
	
}
