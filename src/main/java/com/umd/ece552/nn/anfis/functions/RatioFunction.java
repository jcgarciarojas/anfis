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
public class RatioFunction extends Function 
{

	@Override
	public NetValue operation(int nodeIndex, NetParameters inputValues) throws NetException 
	{
		
		double dividend = 0.0;
		double divisor = 0.0;
		
		Iterator it = inputValues.getIterator();
		while (it.hasNext())
		{
			NetValue value = (NetValue)it.next();
			divisor += value.value;
			
			if (it.getIndex() == nodeIndex)
				dividend = value.value;
		}
		
		return new NetValue(dividend / divisor);
	}

}
