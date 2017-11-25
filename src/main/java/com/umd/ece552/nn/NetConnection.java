package com.umd.ece552.nn;

import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class NetConnection
{
	public static int INPUT_CONNECTION = 0; 
	public static int OUTPUT_CONNECTION = 1;
	private NetValue value;

	/**
	 * 
	 * @return
	 */
	public NetValue getValue()
	{
		return value;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setValue(NetValue value)
	{
		this.value = value;
	}

}
