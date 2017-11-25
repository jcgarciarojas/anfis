package com.umd.ece552.nn.anfis.functions;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class GaussianFunction extends Function 
{
	public NetValue a;
	public NetValue b;
	public NetValue c;

	public int parametersSize() throws NetException
	{
		return 3;
	}

	public void setFunctionParameters(NetParameters param)throws NetException
	{
		super.setFunctionParameters(param);
		Iterator it = param.getIterator();

		if (it.hasNext())
			a = (NetValue)it.next();
		
		if (it.hasNext())
			b = (NetValue)it.next();
		
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
		
		if (a == null || b == null || c == null || x == null)
			throw new NetException("Input or parameter values are Null");
		
		double divisor = (x.value - c.value) / a.value;
		return new NetValue(1.0 / ( 1.0 + Math.pow(divisor, (2.0 * b.value) )) );
	}

}
