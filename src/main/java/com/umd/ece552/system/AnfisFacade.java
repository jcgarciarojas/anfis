package com.umd.ece552.system;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.nn.NetManager;
import com.umd.ece552.training.NetError;
import com.umd.ece552.training.TrainingManager;
import com.umd.ece552.utils.Config;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;
import com.umd.ece552.utils.TrainingData;

/**
 * 
 * @author ingegarcia
 *
 */
public class AnfisFacade {

	private NetManager netManager;
	private NetComposite net;
	private NetError error;
	private static AnfisFacade instance = new AnfisFacade();

	private AnfisFacade()
	{
		error = new NetError();
		netManager = new NetManager();
	}
	
	public static AnfisFacade getInstance()
	{
		return instance;
	}
	
	/**
	 * 
	 * @param numberOfInputs
	 * @param numberOfRules
	 * @param numberOfOutputs
	 * @throws NetException
	 */
	public void buildAnfisNet(int type, int numberOfInputs, int numberOfRules, int numberOfOutputs) throws NetException
	{
		this.setNet(netManager.buildAnfisNN(type, numberOfInputs, numberOfRules, numberOfOutputs));
	}

	/**
	 * 
	 * @param file
	 * @param initPopulation
	 * @param maxNumberOperations
	 * @param threshold
	 * @throws NetException
	 * @throws TrainingException
	 * @throws NetException
	 */
	public void trainAnfisNet(int type, String file, String mask, int initPopulation, int maxNumberOperations, double threshold) 
		throws NetException
	{
		error.init();
		TrainingManager training = new TrainingManager(type, mask, this.getNet());
		training.train(new TrainingData(file), error, initPopulation, maxNumberOperations, threshold);
		this.saveNetConfig(file.substring(file.lastIndexOf("/")+1, file.indexOf(".")));
	}

	/**
	 * 
	 * @param file
	 * @throws NetException
	 * @throws TrainingException
	 * @throws NetException
	 */
	public void testAnfisNet(String file) 
	throws NetException
	{
		error.init();
		TrainingManager training = new TrainingManager(this.getNet());
		training.test(new TrainingData(file), error);
	}
	
	/**
	 * 
	 * @param inputs
	 * @return
	 * @throws NetException
	 * @throws TrainingException
	 * @throws NetException
	 */
	public NetParameters getOutputNeuralNetwork(NetParameters inputs) 
	throws NetException
	{
		return this.getNet().getOutputComposite(inputs);
	}
	
	/**
	 * 
	 * @return
	 */
	private NetComposite getNet()
	{
		return this.net;
	}

	/**
	 * 
	 * @param net
	 */
	private void setNet(NetComposite net)
	{
		this.net = net;
	}
	
	/**
	 * 
	 * @throws NetException
	 */
	public void saveNetConfig(String suffix) throws NetException
	{
		Config.instance().loadConfig();
		Config.instance().saveNetConfig(suffix, this.getNet());
	}

	/**
	 * 
	 * @throws NetException
	 */
	public void loadNetConfig(String suffix) throws NetException
	{
		Config.instance().loadConfig();
		this.setNet(Config.instance().loadNetConfig(suffix));
	}
	
	/**
	 * 
	 * @param dataDirText
	 * @return
	 */
    public XYDataset createSquareErrorDataSet() throws NetException
    {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        Iterator it = error.getSquareErrors(); 
        XYSeries xyseries = new XYSeries("Square Errors");
        
        int j=0;
        while(it.hasNext())
        {
        	NetValue value = (NetValue)it.next();
        	xyseries.add(j, value.value);
        	j++;
        }
        xyseriescollection.addSeries(xyseries);
        return xyseriescollection;
    }
	
    /**
     * 
     * @param dataDirText
     * @return
     */
    public XYDataset createSumWeightsDataSet() throws NetException
    {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        Iterator it = error.getSumWeights();
        XYSeries xyseries = new XYSeries("Sum Weigths");
        
        int i=0;
        while(it.hasNext())
        {
        	NetValue value = (NetValue)it.next();
        	xyseries.add(i, value.value);
        	i++;
        	
        }
        xyseriescollection.addSeries(xyseries);

        return xyseriescollection;
    }

    /**
     * 
     * @param dataDirText
     * @return
     */
    public XYDataset createSumWeightsIncrementDataSet() throws NetException
    {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        Iterator it = error.getSumWeightsIncrement();
        XYSeries xyseries = new XYSeries("Sum Weigths");
        
        int i=0;
        while(it.hasNext())
        {
        	NetValue value = (NetValue)it.next();
        	xyseries.add(i, value.value);
        	i++;
        	
        }
        xyseriescollection.addSeries(xyseries);

        return xyseriescollection;
    }

    /**
     * 
     * @param dataDirText
     * @return
     */
    public XYDataset createPredictedTargetDataSet() throws NetException
    {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        xyseriescollection.addSeries(getSeries("Output", error.getOutputs()));
        xyseriescollection.addSeries(getSeries("Expected", error.getExpectedOutputs()));

        return xyseriescollection;
    }
    
    /**
     * 
     * @param it
     * @param title
     * @return
     * @throws NetException
     */
    private XYSeries getSeries(String title, Iterator it) throws NetException
    {
    	XYSeries xyseries = new XYSeries(title);
    	
    	int i=0;
    	while(it.hasNext())
    	{
    		NetValue v = (NetValue)it.next();
    		xyseries.add(i, Math.abs(v.value));
    		i++;
    	}
    	return xyseries;
    }
    
    /**
     * 
     * @return
     * @throws NetException
     */
    public NetValue getRootMeanSquareError() throws NetException
    {
    	if(error == null)
    		throw new NetException("Error retrieving RootMeanSquareError from error!");
    	
    	return error.getRootMeanSquareError();
    }
    
    /**
     * 
     * @return
     * @throws NetException
     */
    public NetValue[] getConfidenceInterval() throws NetException
    {
    	if(error == null)
    		throw new NetException("Error retrieving ConfidenceInterval from error!");
    	
    	return error.getConfidenceInterval();
    }
}
