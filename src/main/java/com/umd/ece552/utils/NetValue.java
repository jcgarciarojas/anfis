package com.umd.ece552.utils;


/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class NetValue {
	
	public double value;
	
	/**
	 * 
	 * @param value
	 */
	public NetValue(double value)
	{
		this.value = value;
	}
	
	/**
	 * 
	 * @param value
	 */
	public NetValue(String value)
	{
		this.value = new Double(value).doubleValue();
	}

	/**
	 * 
	 */
	public String toString()
	{
		return ""+value;
	}
}
