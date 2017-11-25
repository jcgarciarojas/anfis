package com.umd.ece552.nn;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.anfis.AnfisBuilder;
import com.umd.ece552.nn.anfis.functions.FunctionFactory;

public class NetFactory {
	
	public static int FEED_FORWARD_NN = 0;
	public static int ANFIS_NN = 1;
	private NetBuilder anfisBuilder= new AnfisBuilder();

	private static NetFactory instance = new NetFactory();
	private NetFactory()
	{
	}
	
	public static NetFactory instance()
	{
		return instance;
	}
	
	public NetComposite createNN(int type, int numberOfInputs, int numberOfRules, int numberOfOutputs) throws NetException
	{
		NetComposite net = null;
		if (type == ANFIS_NN)
		{
			int[] anfisFunctions = {FunctionFactory.NO_FUNCTION,
			FunctionFactory.SIGMOIDAL_FUNCTION,
			FunctionFactory.MIN_FUNCTION,
			FunctionFactory.RATIO_FUNCTION,
			FunctionFactory.POLYNOMIAL_FUNCTION,
			FunctionFactory.SUMMATION_FUNCTION};
			String connections = anfisBuilder.getAutomaticConnections(numberOfInputs, numberOfRules, numberOfOutputs);
			net = this.createNN(type, numberOfInputs, numberOfRules, numberOfOutputs, anfisFunctions, connections);
		}
		return net;
	}

	/**
	 * 
	 * 
	 * @param type
	 * @param numberOfInputs
	 * @param numberOfRules
	 * @param numberOfOutputs
	 * @param connections
	 * @return
	 * @throws NetException
	 */
	public NetComposite createNN(int type, int numberOfInputs, int numberOfRules, int numberOfOutputs, String connections) throws NetException
	{
		NetComposite net = null;
		if (type == ANFIS_NN)
		{
			int[] anfisFunctions = {FunctionFactory.NO_FUNCTION,
			FunctionFactory.SIGMOIDAL_FUNCTION,
			FunctionFactory.MIN_FUNCTION,
			FunctionFactory.RATIO_FUNCTION,
			FunctionFactory.POLYNOMIAL_FUNCTION,
			FunctionFactory.SUMMATION_FUNCTION};
			net = this.createNN(type, numberOfInputs, numberOfRules, numberOfOutputs, anfisFunctions, connections);
		}
		return net;
	}

	/**
	 * 
	 * @param type
	 * @param numberOfInputs
	 * @param numberOfRules
	 * @param numberOfOutputs
	 * @return
	 * @throws NetException
	 */
	public NetComposite createNN(int type, int numberOfInputs, int numberOfRules, int numberOfOutputs, int[] functions, String connections) throws NetException
	{
		NetComposite net = null;
		if (type == ANFIS_NN)
		{
			net = createAnfis(numberOfInputs, numberOfRules, numberOfOutputs, functions, connections);
		}
		return net;
	}

	/**
	 * 
	 * @param numberOfInputs
	 * @param numberOfRules
	 * @param numberOfOutputs
	 * @param functions
	 * @param connections
	 * @return
	 * @throws NetException
	 */
	private NetComposite createAnfis(int numberOfInputs, int numberOfRules, int numberOfOutputs, int[] functions, String connections) throws NetException
	{
		anfisBuilder.buildNN(numberOfInputs);
		anfisBuilder.buildLayersNN(6);
		
		anfisBuilder.buildNodesNN(0, numberOfInputs, functions[0]);
		anfisBuilder.buildNodesNN(1, (numberOfRules*numberOfInputs), functions[1]);
		anfisBuilder.buildNodesNN(2, numberOfRules, functions[2]);
		anfisBuilder.buildNodesNN(3, numberOfRules, functions[3]);
		anfisBuilder.buildNodesNN(4, numberOfRules, functions[4]);
		anfisBuilder.buildNodesNN(5, numberOfOutputs, functions[5]);
		
		anfisBuilder.buildConnectionsNN(connections);
		
		return anfisBuilder.getNN();
		
	}
	
	public NetComposite clone(NetComposite net)
	{
		return null;
	}
}
