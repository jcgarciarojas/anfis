package com.umd.ece552.training.ga;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.training.NetError;
import com.umd.ece552.training.TrainingAlgorithm;
import com.umd.ece552.utils.Config;
import com.umd.ece552.utils.DataReader;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 *    @author ingegarcia
 *
 */
public class GeneticAlgorithm extends TrainingAlgorithm 
{
	private String SINGLE_NUMBER_MASK;

	private Crossover crossOver;
	private Mutation mutation;
	private NetComposite nn;
	private int numberParameters;
	private int numberOperations;

	/**
	 * 
	 * @param nn
	 * @throws NetException
	 */
	public GeneticAlgorithm(String mask, NetComposite nn) throws NetException
	{
		this.nn = nn;
		this.setNumberNetParameters(this.nn);
		this.SINGLE_NUMBER_MASK = mask;
		this.crossOver = new Crossover(getRuleMask(SINGLE_NUMBER_MASK));
		mutation = new Mutation();
	}
	
	/**
	 * create a mask for the neural network rule
	 * @param nn
	 * @param MASK
	 * @return
	 */
	private String getRuleMask(String MASK)
	{
		StringBuffer strRuleMask = new StringBuffer(); 
		for(int i=0; i< this.getNumberNetParameters();i++)
		{
			strRuleMask.append(MASK);
		}
		
		return strRuleMask.toString();
	}

	/**
	 * 
	 */
	public void trainNN(DataReader data, NetError error, int initPopulation, int maxNumberOperations, double threshold) throws NetException
	{
		this.initNumberOperations();
		Population randomPop = HypothesisFactory.instance().generateRandomPop(initPopulation, this.getNumberNetParameters(), SINGLE_NUMBER_MASK.length());
		this.evalPopulation(data, error, randomPop);
		randomPop.sort();
		while(!isThresholdReached(randomPop, threshold, maxNumberOperations))
		{
			Population parents = this.getBestParents(randomPop);
			Population offSprings = this.breedOffSprings(parents);
			this.evalPopulation(data, error, offSprings);
			this.replaceOffSpringsToPop(randomPop, offSprings);
		}
		randomPop.sort();
		Hypothesis hyp = (Hypothesis)randomPop.get(0);
		this.setNetHypothesis(error, hyp);			
		this.evalHypothesys(data, hyp, error);		
	}
	
	/**
	 * 
	 * @param randomPop
	 * @return
	 */
	protected Population getBestParents(Population randomPop)
	{
		//Sort the list and get the first one because they have the best fitness 
		randomPop.sort();
		Population newPop = new Population();
		newPop.add((Hypothesis)randomPop.get(0));
		newPop.add((Hypothesis)randomPop.get(1));
		return newPop;
	}
	
	/**
	 * 
	 * @param randomPop
	 * @param offSprings
	 */
	protected void replaceOffSpringsToPop(Population randomPop, Population offSprings)
	{
		//remove the worst parents and add the new offSprings
		randomPop.remove(randomPop.size()-1);
		randomPop.remove(randomPop.size()-1);
		randomPop.add(offSprings);
	}
	
	/**
	 * 
	 * @param parents
	 * @return
	 * @throws TrainingException
	 */
	protected Population breedOffSprings(Population parents) throws NetException
	{
		Population pop = new Population();
		Hypothesis parent0 = (Hypothesis)parents.get(0);
		Hypothesis parent1 = (Hypothesis)parents.get(1);

		String[] offSprings = this.crossOver.getOffSprings(parent0.getHypothesis().toString(), parent1.getHypothesis().toString());
		offSprings[0] = this.mutation.operation(offSprings[0]);
		offSprings[1] = this.mutation.operation(offSprings[1]);
		
		pop.add(new Hypothesis(offSprings[0]));
		pop.add(new Hypothesis(offSprings[1]));

		return pop; 
	}
	

	/**
	 * 
	 * @return
	 */
	protected boolean isThresholdReached(Population randomPop, double threshold, int maxNumberOperations)
	{
		if (addNumberOperations() >= maxNumberOperations)
			return true;
		Hypothesis h = (Hypothesis)randomPop.get(0);

		if (h.getFitness() <= threshold)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @throws NetException
	 */
	protected void evalPopulation(DataReader data, NetError error, Population randomPop) throws NetException, NetException
	{
		Iterator it = randomPop.getIterator();
		
		while(it.hasNext())
		{
			Hypothesis hyp = (Hypothesis)it.next();
			this.setNetHypothesis(error, hyp);
			this.evalHypothesys(data, hyp, null);
/*			
			double dataSetSize = 0.0;
			double fitness = 0.0;
			data.restart();
			while(data.hasNextValues())
			{
				NetParameters inputs = data.getNextInputValues();
				NetParameters outputs = nn.getOutputComposite(inputs);
				NetParameters expectedOutputs = data.getNextOutputValues();
				
				fitness += Math.pow(this.getAbsoluteFitness(outputs, expectedOutputs), 2);
				dataSetSize++;
			}
			double h = Math.sqrt(fitness/dataSetSize);
			hyp.setFitness(h);
			*/
		}
	}

	/**
	 * 
	 * @param data
	 * @param hyp
	 * @param error
	 * @throws NetException
	 */
	private void evalHypothesys(DataReader data, Hypothesis hyp, NetError error) throws NetException
	{
		double dataSetSize = 0.0;
		double fitness = 0.0;
		data.restart();
		while(data.hasNextValues())
		{
			NetParameters inputs = data.getNextInputValues();
			NetParameters outputs = nn.getOutputComposite(inputs);
			NetParameters expectedOutputs = data.getNextOutputValues();
			if (error != null)
				error.addOutputs(outputs, expectedOutputs);

			fitness += Math.pow(this.getAbsoluteFitness(outputs, expectedOutputs), 2);
			dataSetSize++;
		}
		double h = Math.sqrt(fitness/dataSetSize);
		hyp.setFitness(h);
		
		
	}
	
	/**
	 * 
	 * @param sumParams
	 * @param params
	 * @return
	 * @throws NetException
	 */
	private NetParameters addValues(NetParameters sumParams, NetParameters params) throws NetException
	{
		if (sumParams == null)
		{
			sumParams = params;
		}
		else 
		{
			Iterator itSum = sumParams.getIterator();
			Iterator itParam = params.getIterator(); 
			while(itSum.hasNext())
			{
				NetValue v = (NetValue)itSum.next();
				v.value += ((NetValue)itParam.next()).value;
			}
		}
		return sumParams;
	}
	
	/**
	 * 
	 * @param sumParams
	 * @param count
	 * @return
	 * @throws NetException
	 */
	private NetParameters setErrorOutputs(NetParameters sumParams, double count) throws NetException
	{
		Iterator itSum = sumParams.getIterator();
		while(itSum.hasNext())
		{
			NetValue v = (NetValue)itSum.next();
			v.value = v.value/count; 
		}
		
		return sumParams;
	}
	
	/**
	 * 
	 * @return
	 */
	protected int getNumberNetParameters()
	{
		return this.numberParameters;
	}

	/**
	 * 
	 * @return
	 * @throws NetException
	 */
	protected void setNumberNetParameters(NetComposite nn) throws NetException
	{
		int numberParameters = 0;
		Iterator it = nn.getChildren();
		
		while(it.hasNext())
		{
			NetComposite layer = (NetComposite)it.next();
			Iterator nodeIt = layer.getChildren();

			while(nodeIt.hasNext())
			{
				NetComposite node = (NetComposite)nodeIt.next();
				numberParameters += node.getFunction().parametersSize();
			}
			
		}
		
		this.numberParameters = numberParameters;
	}
	
	/**
	 * 
	 */
	private void initNumberOperations()
	{
		this.numberOperations = 0;
	}

	/**
	 * 
	 * @return
	 */
	private int addNumberOperations()
	{
		return this.numberOperations++;
	}
	
	public void setNetHypothesis(NetError error, Hypothesis hyp) throws NetException, NetException
	{
		String bitStrRule = hyp.getHypothesis();
		
		Iterator itDecimals = HypothesisFactory.instance().getNetValuesFromBitString(bitStrRule, SINGLE_NUMBER_MASK.length());
		this.processWeights(error, itDecimals);
		itDecimals.restart();
		Config.instance().setNetParams(nn, itDecimals);
	}

	/**
	 * 
	 * @param it
	 * @throws NetException
	 */
	private void processWeights(NetError error, Iterator it) throws NetException
	{
		double result = 0.0;
		while(it.hasNext())
		{
			NetValue value = (NetValue)it.next();
			result += Math.abs(value.value);
		}
		error.addSumWeigths(new NetValue(result));
	}
	
	/**
	 * 
	 * @param outputs
	 * @param expectedOutputs
	 * @return
	 * @throws NetException
	 */
	private double getAbsoluteFitness(NetParameters outputs, NetParameters expectedOutputs) throws NetException
	{
		Iterator itOutput = outputs.getIterator();
		Iterator itExpected = expectedOutputs.getIterator();
		
		double expected = 0.0;
		double output = 0.0;
		while(itOutput.hasNext())
		{
			NetValue outputVal = (NetValue)itOutput.next();
			NetValue expectedVal = (NetValue)itExpected.next();
			expected += expectedVal.value; 
			output += outputVal.value; 
		}
		return Math.abs(expected - output);
	}

}
