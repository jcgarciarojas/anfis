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
public class MinFunction extends Function 
{

	@Override
	public NetValue operation(int nodeIndex, NetParameters inputValues) throws NetException
	{
		double val1 = 0.0;
		
		Iterator it = inputValues.getIterator();
		while (it.hasNext())
		{
			NetValue value = (NetValue)it.next();
			if (it.isFirst())
				val1 = value.value;
			else
			{
				val1 = Math.min(val1, value.value);
			}
		}
		
		return new NetValue(val1);
	}

}
