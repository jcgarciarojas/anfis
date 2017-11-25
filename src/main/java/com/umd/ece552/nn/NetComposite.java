package com.umd.ece552.nn;

import java.util.List;
import java.util.ArrayList;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.anfis.functions.Function;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetIterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public abstract class NetComposite {
	
	private int index;
	private List<NetComposite> objects; 
	
	public NetComposite()
	{
		objects = new ArrayList<NetComposite>();
	}

	public NetComposite(int index)
	{
		this();
		this.index = index;
	}
	
	public int getIndex()
	{
		return index;
	}

	//these methods manage the children
	/**
	 * 
	 * @param component
	 */
	public void add(NetComposite component) throws NetException
	{
		this.objects.add(component);
	}
	/**
	 * 
	 * @param component
	 */
	public void remove(NetComposite component) throws NetException
	{
		this.objects.remove(component);
	}
	/**
	 * 
	 * @return
	 */
	public Iterator getChildren() throws NetException
	{
		return new NetIterator(objects);
	}	
	/**
	 * 
	 * @return
	 */
	public int getNumberChildren() throws NetException
	{
		return objects.size();
	}	
	/**
	 * 
	 * @param compositeIndex
	 * @return
	 */
	public NetComposite getComposite(int compositeIndex)
	{
		return (NetComposite)objects.get(compositeIndex);
	}
	/**
	 * 
	 * @return
	 */
	public boolean isLeaf()
	{
		return false;
	}

	public int netType()
	{
		return -1;
	}

	//these method is for the NN 
	public abstract void setNumberOfInputs(int numberOfInputs) throws NetException;
	public abstract NetParameters getOutputComposite(NetParameters inputs) throws NetException;
	
	//these methods are for the layers
	public abstract void processInputComposite(NetParameters inputs) throws NetException;
	public abstract void processInnerComposite() throws NetException;
	public abstract NetParameters getResultsOutputComposite() throws NetException;
	
	//these methods specific for nodes
	public abstract void processInputLeaf(NetValue input) throws NetException;
	public abstract NetValue getOutputLeaf() throws NetException;
	public abstract NetValue getPreviousOutputLeaf() throws NetException;
	public abstract void setFunction(Function function) throws NetException;
	public abstract Function getFunction() throws NetException;
	public abstract void setConnection(NetConnection connection, int type) throws NetException;
	public abstract Iterator getConnections(int type) throws NetException;
	public abstract NetParameters getWeights() throws NetException;
	public abstract void setWeights(NetParameters inputs) throws NetException;
	
}
