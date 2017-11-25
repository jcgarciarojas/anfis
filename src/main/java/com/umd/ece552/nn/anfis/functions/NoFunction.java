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
public class NoFunction extends Function 
{

	@Override
	public NetValue operation(int nodeIndex, NetParameters inputValues) throws NetException
	{
		NetValue value = null;
		
		Iterator it = inputValues.getIterator();
		if (it.hasNext())
			value = (NetValue)it.next();
		
		return value;
	}

}
