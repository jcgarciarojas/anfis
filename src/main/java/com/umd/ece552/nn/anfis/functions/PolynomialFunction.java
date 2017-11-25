package com.umd.ece552.nn.anfis.functions;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;
import com.umd.ece552.nn.NetContext;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class PolynomialFunction extends Function 
{
	/**
	 * 
	 */
	public int parametersSize() throws NetException
	{
		return NetContext.getInstance().getNumberOfInputs() + 1;
	}


	@Override
	public NetValue operation(int nodeIndex, NetParameters inputValues) throws NetException 
	{
		Iterator it = inputValues.getIterator();

		NetValue input = null;
		if (it.hasNext())
			input = (NetValue)it.next();
		
		if (input == null)
			throw new NetException("invalid input");
		
		Iterator itNNInputs = NetContext.getInstance().getInputParams().getIterator();
		Iterator itFunctionParams = super.getFunctionParameters().getIterator();
		
		double result = 0.0;
		while(itFunctionParams.hasNext())
		{
			double param = ((NetValue)itFunctionParams.next()).value;
			
			double inputNN = 1.0;
			if (!itNNInputs.isLast())
				inputNN = ((NetValue)itNNInputs.next()).value;
			
			result += param * inputNN;
		}

		double functionResult = input.value * result;
		return new NetValue (functionResult);
	}

}
