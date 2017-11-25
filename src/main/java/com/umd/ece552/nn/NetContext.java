package com.umd.ece552.nn;

import com.umd.ece552.utils.NetParameters;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class NetContext 
{
	private static NetContext instance = new NetContext();
	private NetParameters inputParams = new NetParameters();
	private int numberOfInputs;
	

	private NetContext()
	{
		return;
	}
	
	/**
	 * 
	 * @return
	 */
	public static NetContext getInstance()
	{
		return instance;
	}
	
	/**
	 * 
	 * @param inputParams
	 */
	public void setInputParams(NetParameters inputParams)
	{
		this.inputParams = inputParams;
	}
	
	/**
	 * 
	 * @return
	 */
	public NetParameters getInputParams()
	{
		return this.inputParams;
	}
	
	public int getNumberOfInputs()
	{
		return numberOfInputs;
	}
	
	public void setNumberOfInputs(int numberOfInputs)
	{
		this.numberOfInputs = numberOfInputs;
	}
}
