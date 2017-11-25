package com.umd.ece552.nn.anfis.functions;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

public class SigmoidalFunction extends Function 
{

	public NetValue a;
	public NetValue c;
	

	public int parametersSize() throws NetException
	{
		return 2;
	}


	public void setFunctionParameters(NetParameters param)throws NetException
	{
		super.setFunctionParameters(param);
		Iterator it = param.getIterator();

		if (it.hasNext())
			a = (NetValue)it.next();
		
		if (it.hasNext())
			c = (NetValue)it.next();
		
	}

	@Override
	public NetValue operation(int nodeIndex, NetParameters inputValues) throws NetException 
	{
		Iterator it = inputValues.getIterator();

		NetValue x = null;
		if (it.hasNext())
			x = (NetValue)it.next();
		
		if (a == null || c == null || x == null)
			throw new NetException("Input or parameter values are Null");
		
		double potency = a.value * (x.value - c.value);
		double result = ( 1 / (1 + Math.exp(-potency)) );
		return new NetValue(result);
		
	}
}
