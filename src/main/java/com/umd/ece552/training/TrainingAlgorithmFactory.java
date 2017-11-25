package com.umd.ece552.training;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.training.ga.GeneticAlgorithm;

public class TrainingAlgorithmFactory {

	public static int GENETIC_ALGORITHM = 0;
	public static int BACK_PROPAGATION = 1;
	private static TrainingAlgorithmFactory instance = new TrainingAlgorithmFactory();
	private TrainingAlgorithmFactory()
	{
		
	}
	
	public static TrainingAlgorithmFactory instance()
	{
		return instance;
	}
	
	/**
	 * 
	 * @param type
	 * @param mask
	 * @param nn
	 * @return
	 * @throws NetException
	 */
	public TrainingAlgorithm getAlgorithm(int type, String mask, NetComposite nn) throws NetException
	{
		TrainingAlgorithm a = null;
		if(type == GENETIC_ALGORITHM)
			a = new GeneticAlgorithm(mask, nn);
		return a;
	}
}
