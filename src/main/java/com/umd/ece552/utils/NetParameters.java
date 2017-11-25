package com.umd.ece552.utils;

import java.util.*;

import com.umd.ece552.exception.NetException;

public class NetParameters {
	private List<NetValue> values = new Vector<NetValue>(); 
	
	public NetParameters()
	{
		return;
	}

	public NetParameters(NetValue value)
	{
		this.add(value);
	}

	public void add(NetValue value)
	{
		values.add(value);
	}
	
	public Iterator getIterator() throws NetException
	{
		return new NetIterator(values);
	}
	
	public void clear()
	{
		values.clear();
	}

}
