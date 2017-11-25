package com.umd.ece552.system;

import java.util.StringTokenizer;

import org.jfree.data.xy.XYDataset;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetParameters;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class AnfisStockPrecitionControl 
{
	/**
	 * 
	 * @param numberOfInputs
	 * @param numberOfRules
	 * @param numberOfOutputs
	 * @throws NetException
	 */
	public void createAnfis(int type, String numberOfInputs, String numberOfRules, String numberOfOutputs) throws NetException
	{
		this.evalParameters(numberOfInputs);
		this.evalParameters(numberOfRules);
		this.evalParameters(numberOfOutputs);
		int inputs = new Integer(numberOfInputs).intValue();
		int rules= new Integer(numberOfRules).intValue();
		int outputs = new Integer(numberOfOutputs).intValue();
		AnfisFacade.getInstance().buildAnfisNet(type, inputs, rules, outputs);
	}
	public void saveCommand()
	{
		return;
	}
	public void loadCommand(String suffix) throws NetException
	{
		AnfisFacade.getInstance().loadNetConfig(suffix);
	}
	
	/**
	 * 
	 * @param dirData
	 * @param strMask
	 * @param strPop
	 * @param strMaxOperations
	 * @param strThreshold
	 * @throws NetException
	 */
	public void trainigCommand(int type, String file, String strMask, String strPop, String strMaxOperations, String strThreshold)throws NetException
	{
		this.evalParameters(file);
		this.evalParameters(strMask);
		this.evalParameters(strPop);
		this.evalParameters(strMaxOperations);
		this.evalParameters(strThreshold);

		double threshold = new Double(strThreshold).doubleValue();
		int initPopulation = new Integer(strPop).intValue();
		int maxNumberOperations = new Integer(strMaxOperations).intValue();
		AnfisFacade.getInstance().trainAnfisNet(type, file, strMask, initPopulation, maxNumberOperations, threshold);
		return;
	}
	/**
	 * 
	 * @param dirData
	 * @throws NetException
	 */
	public void testingCommand(String file) throws NetException
	{
		AnfisFacade.getInstance().testAnfisNet(file);
	}
	
	/**
	 * 
	 * @param parameters
	 * @param output
	 * @return
	 * @throws NetException
	 */
	public String predictionCommand(String params) throws NetException
	{
		NetParameters outputs = AnfisFacade.getInstance().getOutputNeuralNetwork(getNetParameters(params));
		Iterator it = outputs.getIterator();
		String result = "";
		while(it.hasNext())
		{
			NetValue v = (NetValue)it.next();
			result += v.value+",";
		}
		return result.substring(0, result.length()-1);
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	private NetParameters getNetParameters(String parameters)
	{
		NetParameters params = new NetParameters();
		StringTokenizer tk = new StringTokenizer(parameters, ",");
		while(tk.hasMoreTokens())
		{
			params.add(new NetValue(tk.nextToken().trim()));
		}
		return params;
	}
	
	/**
	 * 
	 * @param dataDirText
	 * @return
	 */
	public XYDataset createSquareErrorDataSet() throws NetException
	{
		return AnfisFacade.getInstance().createSquareErrorDataSet();
	}
	
	/**
	 * 
	 * @param dataDirText
	 * @return
	 */
	public XYDataset createPredictedTargetDataSet() throws NetException
	{
		return AnfisFacade.getInstance().createPredictedTargetDataSet();
	}
	
	/**
	 * 
	 * @param dataDirText
	 * @return
	 */

	public XYDataset createSumWeightsDataSet() throws NetException
	{
		return AnfisFacade.getInstance().createSumWeightsDataSet();
	}

	/**
	 * 
	 * @param dataDirText
	 * @return
	 */
	public XYDataset createSumWeightsIncrementDataSet() throws NetException
	{
		return AnfisFacade.getInstance().createSumWeightsIncrementDataSet();
	}
	/**
	 * 
	 * @param param
	 * @throws NetException
	 */
	private  void evalParameters(String param) throws NetException
	{
		if (param == null || param.length() <=0)
			throw new NetException("invalid Parameters");
	}

	/**
	 * 
	 * @return
	 * @throws NetException
	 */
    public String getRootMeanSquareError() throws NetException
    {
    	return ""+AnfisFacade.getInstance().getRootMeanSquareError().value;
    }
    
    /**
     * 
     * @return
     * @throws NetException
     */
    public String getConfidenceInterval() throws NetException
    {
    	NetValue[] errors = AnfisFacade.getInstance().getConfidenceInterval();
    	
    	return formatDouble(""+errors[0].value, 8)+"  ,  "+formatDouble(""+errors[1].value, 8);
    }
    
    private String formatDouble(String number, int length)
    {
    	String format="";
    	for (int i =0; i<length; i++)
    	{
    		format += number.charAt(i);
    	}
    	return format;
    }
}
