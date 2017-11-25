package com.umd.ece552.training;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.utils.DataReader;

/**
 * 
 * @author ingegarcia
 *
 */
public abstract class TrainingAlgorithm 
{
	public abstract void trainNN(DataReader data, NetError error, int initPopulation, int maxNumberOperations, double threshold) throws NetException;
}
