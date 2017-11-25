package com.umd.ece552.nn.anfis;

import java.util.List;
import java.util.Vector;
import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.nn.NetConnection;
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
public class AnfisNode extends NetComposite 
{
	private Function function;
	private List<NetConnection> inputConnections = new Vector<NetConnection>();
	private List<NetConnection> outputConnections = new Vector<NetConnection>();
	private NetValue output;
	private NetValue previousOutput;


	public AnfisNode(int index)
	{
		super(index);
	}


	public void initConnections()
	{
		this.getInputConnections().clear();
		this.getOutputConnections().clear();
	}
	

	protected void setPreviousOutputLeaf(NetValue output)
	{
		this.previousOutput = output;
	}


	public NetValue getPreviousOutputLeaf() throws NetException
	{
		return this.previousOutput;
	}


	protected NetValue getNodeOutput()
	{
		return this.output;
	}


	protected void setNodeOutput(NetValue output)
	{
		this.output = output;
	}


	private List<NetConnection> getInputConnections()
	{
		return inputConnections;
	}
	
	private List<NetConnection> getOutputConnections()
	{
		return outputConnections;
	}

	public Function getFunction() throws NetException
	{
		return this.function;
	}
	public void setFunction(Function function) throws NetException
	{
		this.function = function;
	}

	public void setConnection(NetConnection connection, int type) throws NetException
	{
		if (type == NetConnection.INPUT_CONNECTION)
			this.getInputConnections().add(connection);
			
		if (type == NetConnection.OUTPUT_CONNECTION)
			this.getOutputConnections().add(connection);
		
	}

	public Iterator getConnections(int type) throws NetException
	{
		List<NetConnection> l = null;
		if (type == NetConnection.INPUT_CONNECTION)
			l = this.getInputConnections();
			
		else 
			l = this.getOutputConnections();
		
		return new NetIterator(l);
	}
	

	public void processInputLeaf(NetValue input) throws NetException
	{
		
		this.setPreviousOutputLeaf(this.getNodeOutput());
		this.setNodeOutput(function.operation(super.getIndex(), new NetParameters(input)));
		populateOutputConnections(this.getNodeOutput());
	}
	

	public void processInnerComposite() throws NetException
	{
		this.setPreviousOutputLeaf(this.getNodeOutput());
		NetParameters params = this.getValuesInputConnections();
		this.setNodeOutput(function.operation(super.getIndex(), params));
		populateOutputConnections(this.getNodeOutput());
	}
	

	public NetValue getOutputLeaf() throws NetException
	{
		this.processInnerComposite();
		return this.getNodeOutput();
	}
	

	private NetParameters getValuesInputConnections()
	{
		NetParameters params = new NetParameters();
		Iterator itInputConnections = createIterator(this.getInputConnections());
		
		while(itInputConnections.hasNext())
		{
			NetConnection conn = (NetConnection)itInputConnections.next();
			params.add(conn.getValue());
		}
		
		return params;
		
	}
	

	private void populateOutputConnections(NetValue output)
	{
		Iterator itConnections = createIterator(this.getOutputConnections());
		while(itConnections.hasNext())
		{
			NetConnection outputConn = (NetConnection)itConnections.next();
			outputConn.setValue(output);
		}
	}
	

	private Iterator createIterator(List<NetConnection> list)
	{
		return new NetIterator(list);
	}


	public NetParameters getOutputComposite(NetParameters inputs) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public void processInputComposite(NetParameters inputs) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public NetParameters getResultsOutputComposite() throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public void add(NetComposite component) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}
	public void remove(NetComposite component) throws NetException
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
	public void setNumberOfInputs(int numberOfInputs) throws NetException
	{
		throw new NetException("Invalid Method for this Object");
	}

	public boolean isLeaf()
	{
		return true;
	}
}
