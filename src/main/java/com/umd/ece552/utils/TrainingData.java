package com.umd.ece552.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.umd.ece552.exception.NetException;

public class TrainingData extends DataReader 
{
	protected Iterator itParams;
	protected Iterator itOutput;
	
	/**
	 * 
	 * @param fileName
	 * @throws TrainingException
	 */
	public TrainingData(String fileName) throws NetException
	{
		this.initDataIterator(fileName);
	}

	/**
	 * 
	 * @param fileName
	 * @throws TrainingException
	 */
	private void initDataIterator(String fileName) throws NetException
	{
		BufferedReader reader = null; 
		String nextLine = null;
		ArrayList<NetParameters> input = new ArrayList<NetParameters>();
		ArrayList<NetParameters> output = new ArrayList<NetParameters>();
		
		try
		{
			reader = new BufferedReader(new FileReader(new File(fileName)));
			while ((nextLine = reader.readLine()) != null)
			{
				NetParameters[] data = this.getNetData(nextLine);
				input.add(data[0]);
				output.add(data[1]);
				
			}			
			itParams = new NetIterator(input);
			itOutput = new NetIterator(output);
		} catch(FileNotFoundException fnfe)
		{
			throw new NetException(fnfe);
		} catch(IOException ioe)
		{
			throw new NetException(ioe);
		}
	}
	
	/**
	 * 
	 * @param line
	 * @return
	 */
	private NetParameters[] getNetData(String line)
	{
		StringTokenizer tk = new StringTokenizer(line, ",");
		NetParameters input = new NetParameters();
		NetParameters output = new NetParameters();
		
		for (int i=0;i<5;i++)
		{
			if(tk.hasMoreTokens())
				input.add(getNetValue(tk.nextToken()));
		}
		if(tk.hasMoreTokens())
			output.add(getNetValue(tk.nextToken()));
		
		NetParameters[] params = {input, output};
		return params;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	private NetValue getNetValue(String number)
	{
		return new NetValue(new Double(number).doubleValue());
	}
			
	/**
	 * 
	 */
	public void restart()
	{
		itParams.restart();
		itOutput.restart();
		
	}
	
	/**
	 * 
	 */
	public boolean hasNextValues()
	{
		return itParams.hasNext();
	}
	
	/**
	 * 
	 */
	public NetParameters getNextInputValues()
	{
		return (NetParameters)itParams.next();
	}
	
	/**
	 * 
	 */
	public NetParameters getNextOutputValues()
	{
		return (NetParameters)itOutput.next();
	}
}
