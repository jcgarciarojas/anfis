package com.umd.ece552.nn.anfis;


import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.nn.NetConnection;
import com.umd.ece552.nn.NetContext;
import com.umd.ece552.nn.NetFactory;
import com.umd.ece552.nn.anfis.functions.Function;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author ingegarcia
 *
 */
public class AnfisNN extends NetComposite
{
	public AnfisNN()
	{
		super();
	}

	public AnfisNN(int index)
	{
		super(index);
	}

	public int netType()
	{
		return NetFactory.ANFIS_NN;
	}
	

	public void setNumberOfInputs(int numberOfInputs)
	{
		NetContext.getInstance().setNumberOfInputs(numberOfInputs);
	}

	public NetParameters getOutputComposite(NetParameters inputs) throws NetException
	{
		NetContext.getInstance().setInputParams(inputs);
		NetParameters output = null;
		Iterator itChildren = this.getChildren();
		
		while(itChildren.hasNext())
		{
			NetComposite layer = (NetComposite)itChildren.next();
			
			//assigning inputs to the input layer
			if (itChildren.isFirst())
				layer.processInputComposite(inputs);

			//getting output from the output layer
			else if (!itChildren.isLast())
				layer.processInnerComposite();

			else
				output = layer.getResultsOutputComposite();

		}
		
		return output;
	}
	

	public void processInputComposite(NetParameters inputs) throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public void processInnerComposite() throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public NetParameters getResultsOutputComposite() throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public void processInputLeaf(NetValue input) throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public NetValue getOutputLeaf() throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public void setFunction(Function function) throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public Function getFunction() throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public void setConnection(NetConnection connection, int type) throws NetException
	{
		throw new NetException("Invalid Method");
	}
	public NetParameters getWeights() throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public void setWeights(NetParameters inputs) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public Iterator getConnections(int type) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public NetValue getPreviousOutputLeaf() throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
}
