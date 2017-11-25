package com.umd.ece552.nn.anfis;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.nn.NetConnection;
import com.umd.ece552.nn.anfis.functions.Function;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class AnfisLayer extends NetComposite 
{

	public AnfisLayer(int index)
	{
		super(index);
	}

	public void processInputComposite(NetParameters inputs) throws NetException
	{
		Iterator itInputNodes = this.getChildren();
		Iterator itInputValues = inputs.getIterator();
		
		while(itInputNodes.hasNext())
		{
			NetComposite node = (NetComposite)itInputNodes.next();
			NetValue input = (NetValue)itInputValues.next();
			node.processInputLeaf(input);
		}

	}

	public NetParameters getResultsOutputComposite() throws NetException
	{
		NetParameters value = new NetParameters();
		Iterator it = this.getChildren();
		
		while(it.hasNext())
		{
			NetComposite node = (NetComposite)it.next();
			value.add(node.getOutputLeaf());
		}
		
		return value;
	}


	public void processInnerComposite() throws NetException
	{
		Iterator it = this.getChildren();
		
		while(it.hasNext())
		{
			NetComposite node = (NetComposite)it.next();
			node.processInnerComposite();
		}
	}
	

	public NetParameters getOutputComposite(NetParameters inputs) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public void processInputLeaf(NetValue input) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public NetValue getOutputLeaf() throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public void setFunction(Function function) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public Function getFunction() throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public void setConnection(NetConnection connection, int type) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
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
	public void setNumberOfInputs(int numberOfInputs) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}

}
