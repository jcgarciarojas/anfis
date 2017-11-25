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
public class SummationFunction extends Function 
{

	@Override
	public NetValue operation(int nodeIndex, NetParameters inputValues) throws NetException 
	{
		double result = 0.0;
		
		Iterator it = inputValues.getIterator();
		while (it.hasNext())
		{
			NetValue value = (NetValue)it.next();
			result += value.value;
		}
		
		return new NetValue(result);
	}

}
