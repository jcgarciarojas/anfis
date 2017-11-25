package com.umd.ece552.training;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.training.ga.GeneticAlgorithm;
import com.umd.ece552.utils.DataReader;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author ingegarcia
 *
 */
public class TrainingManager {
	
	private TrainingAlgorithm training;
	private NetComposite nn;
	
	/**
	 * 
	 * @param nn
	 * @throws NetException
	 */
	public TrainingManager(int type, String mask, NetComposite nn) throws NetException
	{
		this.setNeuralNet(nn);
		this.setTrainingAlgoritm(TrainingAlgorithmFactory.instance().getAlgorithm(type, mask, nn));
	}
	
	/**
	 * 
	 * @param nn
	 * @throws NetException
	 */
	public TrainingManager(NetComposite nn) throws NetException
	{
		this.setNeuralNet(nn);
	}

	public void setNeuralNet(NetComposite nn)
	{
		this.nn = nn;
	}
	
	public void setTrainingAlgoritm(TrainingAlgorithm training)
	{
		this.training = training;
	}

	/**
	 * 
	 * @param data
	 * @param initPopulation
	 * @param maxNumberOperations
	 * @param threshold
	 * @throws TrainingException
	 */
	public void train(DataReader data, NetError error, int initPopulation, int maxNumberOperations, double threshold)
	throws NetException
	{
		training.trainNN(data, error, initPopulation, maxNumberOperations, threshold);
	}

	/**
	 * 
	 * @param data
	 * @throws NetException
	 */
	public void test(DataReader data, NetError error) throws NetException, NetException
	{
		while(data.hasNextValues())
		{
			NetParameters outputs = this.feedForward(data.getNextInputValues());
			error.addOutputs(outputs, data.getNextOutputValues());
		}
	}
	
	/**
	 * 
	 * @param inputs
	 * @return
	 * @throws NetException
	 */
	public NetParameters feedForward(NetParameters inputs) throws NetException
	{
		return nn.getOutputComposite(inputs);
	}

    
    public NetValue getRootMeanSquareError()
    {
    	return null;
    }
    
    public NetValue getConfidenceInterval()
    {
    	return null;
    }
}
