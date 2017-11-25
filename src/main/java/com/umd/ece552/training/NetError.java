package com.umd.ece552.training;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author ingegarcia
 *
 */
public class NetError {
	
	private NetParameters outputs = new NetParameters(); 
	private NetParameters target = new NetParameters(); 
	private NetParameters weigths = new NetParameters(); 

	public void init()
	{
		outputs.clear();
		target.clear();
		weigths.clear();
	}
	
	/**
	 * 
	 * @param output
	 * @param target
	 * @throws NetException
	 */
	public void addOutputs(NetParameters output, NetParameters target) throws NetException
	{
		outputs.add(sumAbsOutput(output));
		this.target.add(sumAbsOutput(target));
	}
	
	/**
	 * 
	 * @param output
	 * @return
	 * @throws NetException
	 */
	private NetValue sumAbsOutput(NetParameters output) throws NetException
	{
		Iterator it = output.getIterator();
		double result = 0.0; 
		while(it.hasNext())
		{
			NetValue v = (NetValue)it.next();
			result += Math.abs(v.value);
		}
		return new NetValue(result);
	}
	
	/**
	 * 
	 * @param output
	 */
	public void addSumWeigths(NetValue output)
	{
		weigths.add(output);
	}
	/**
	 * 
	 * @return
	 * @throws NetException
	 */
	public Iterator getSumWeights() throws NetException
	{
		return weigths.getIterator();
	}
	/**
	 * 
	 * @return
	 * @throws NetException
	 */
	public Iterator getSumWeightsIncrement() throws NetException
	{
		NetParameters params = new NetParameters();
		Iterator it = weigths.getIterator();
		
		double previous = 0.0;
		while(it.hasNext())
		{
			NetValue nValue = (NetValue)it.next();
			double current = Math.abs(nValue.value - previous);
			params.add(new NetValue(current));
			previous = current;
		}
		
		return params.getIterator();
	}
	/**
	 * 
	 * @return
	 * @throws NetException
	 */
	public Iterator getOutputs() throws NetException
	{
		return outputs.getIterator();
	}
	/**
	 * 
	 * @return
	 * @throws NetException
	 */
	public Iterator getExpectedOutputs()throws NetException
	{
		return target.getIterator();
	}

	/**
	 * 
	 * @return
	 */
	public Iterator getSquareErrors() throws NetException
	{
		NetParameters param = new NetParameters(); 
		Iterator itOutput = this.getOutputs();
		Iterator itExpected = this.getExpectedOutputs();
		
		while(itOutput.hasNext())
		{
			NetValue valueO = (NetValue)itOutput.next();
			NetValue valueE = (NetValue)itExpected.next();
			NetValue result = new NetValue(Math.pow((valueO.value - valueE.value), 2));
			param.add(result);
		}
		
		return param.getIterator();
	}

	/**
	 * 
	 * @return
	 */
	public NetValue getRootMeanSquareError() throws NetException
	{
		int count = 0;
		double sum = 0.0;
		Iterator it = this.getSquareErrors();
		while(it.hasNext())
		{
			NetValue value = (NetValue)it.next();
			sum += value.value;
			count++;
		}
		return new NetValue(Math.sqrt(sum/count));
	}
	
	/**
	 * 
	 * @param percentage
	 * @return
	 */
	public NetValue[] getConfidenceInterval() throws NetException
	{
		int count = 0;
		double sum = 0.0;
		Iterator it = this.getSquareErrors();
		while(it.hasNext())
		{
			NetValue value = (NetValue)it.next();
			sum += value.value;
			count++;
		}
		double error = Math.sqrt(sum/count);
		
		double confidence =  1.96 * Math.sqrt( (error * (1-error)  / count) ); 
		NetValue error0 = new NetValue( -confidence );
		
		NetValue error1 = new NetValue( +confidence );
		
		//95% confidence interval
		//error +- Z * sqrt (error*(1-error)/n)
		NetValue[] errors = {error0, error1};
		return errors;
	}
}
