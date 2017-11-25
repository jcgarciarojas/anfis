package com.umd.ece552.nn;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.anfis.AnfisBuilder;
import com.umd.ece552.nn.anfis.functions.FunctionFactory;
import com.umd.ece552.utils.NetParameters;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class NetManager 
{

	private NetBuilder builder = null;
	
	public NetManager()
	{
		builder = new AnfisBuilder();
	}
	/**
	 * inputs, functions
	 * @return
	 */
	public NetComposite buildAnfisNN(int type, int numberOfInputs, int numberOfRules, int numberOfOutputs) throws NetException 
	{
		return NetFactory.instance().createNN(type, numberOfInputs, numberOfRules, numberOfOutputs);
	}
	/**
	 * 
	 * @param numberOfInputs
	 * @param numberOfRules
	 * @param numberOfOutputs
	 * @param strConnections
	 * @return
	 * @throws NetException
	 */
	public NetComposite buildAnfisNN(int type, int numberOfInputs, int numberOfRules, int numberOfOutputs, String strConnections) throws NetException 
	{
		return NetFactory.instance().createNN(type, numberOfInputs, numberOfRules, numberOfOutputs, strConnections);
	}
}
