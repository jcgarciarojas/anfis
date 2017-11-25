package com.umd.ece552.nn.anfis.functions;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public abstract class Function 
{
	private NetParameters functionParams = new NetParameters();
	
	public NetParameters getFunctionParameters() throws NetException
	{
		return this.functionParams;
	}
	
	public void setFunctionParameters(NetParameters functionParams)throws NetException
	{
		this.functionParams = functionParams;
	}

	public int parametersSize() throws NetException
	{
		return 0;
	}
	
	public abstract NetValue operation(int nodeIndex, NetParameters inputValues) throws NetException;
}
