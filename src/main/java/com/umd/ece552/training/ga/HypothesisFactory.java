package com.umd.ece552.training.ga;

import java.util.ArrayList;
import java.util.Random;

import com.umd.ece552.utils.Iterator;
import com.umd.ece552.utils.NetIterator;
import com.umd.ece552.utils.NetValue;

/**
 * 
 * @author ingegarcia
 *
 */
public class HypothesisFactory {
	
	private Random r = new Random();
	private static HypothesisFactory instance = new HypothesisFactory();
	
	private HypothesisFactory()
	{
		r.setSeed(1);
	}

	/**
	 * 
	 * @return
	 */
	public static HypothesisFactory instance() 
	{
		return instance;
	}

	/**
	 * 
	 * @param population
	 * @param numberOfParameters
	 * @param maskLen
	 * @return
	 */
	public Population generateRandomPop(int population, int numberOfParameters, double bitMaskLen)
	{
		ArrayList<Hypothesis> list = new ArrayList<Hypothesis>();
		StringBuffer buffer = null; 
		for(int i=0;i<population;i++)
		{
			buffer = new StringBuffer();
			for (int j=0; j<numberOfParameters; j++)
			{
				double decimalRandom = getGaussianRandom();
				String bitString = decimalToBitString(bitMaskLen, decimalRandom);
				buffer.append(bitString);
			}

			list.add(new Hypothesis(buffer.toString()));
		}
		
		return new Population(list);
	}
	
	/**
	 * convert a decimal number to a bynary representation, this number should be between +1 to 1 
	 * @param decimal
	 * @return
	 */
	public String decimalToBitString(double maskLen, double decimal)
	{
		StringBuffer bitStr = new StringBuffer();
		
		if (Math.signum(decimal) > 0)
			bitStr.append(0);
		else
			bitStr.append(1);
		bitStr.append(integerPartOfNumber(decimal));
		
		double result = decimal;
		double decimalLen = maskLen - 2.0;
		for(int i = 0; i < decimalLen ; i++)
		{
			result = decimalPartOfNumber(result) * 2.0;
			bitStr.append(integerPartOfNumber(result));
		}
		return bitStr.toString();
	}
	
	/**
	 * extract the integer part of the decimal number
	 * @param decimal
	 * @return
	 */
	private int integerPartOfNumber(double decimal)
	{
		String strNumber = ""+Math.abs(decimal);
		return new Integer(strNumber.substring(0, strNumber.indexOf(".")));
	}

	/**
	 * extract de decimal part of the decimal number 
	 * @param decimal
	 * @return
	 */
	private double decimalPartOfNumber(double decimal)
	{
		String strNumber = ""+decimal;
		return new Double(strNumber.substring(( strNumber.indexOf(".") )));
	}

	/**
	 * 
	 * @param bitString
	 * @return
	 */
	public double BitStringToDecimal(String bitString)
	{
		double result = 0.0;

		for(int index=2, count=1; index<bitString.length(); index++, count++)
		{
			double a = Math.pow(0.5, count);
			String strInt = ""+bitString.charAt(index);
		    a = a * new Double(strInt);
			result += a;
		}
		
		if (bitString.charAt(1) == '1')
			result += 1;
		//setting the sign of the number
		if (bitString.charAt(0) == '1')
			result *= -1;
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	private double getGaussianRandom()
	{
		double random = r.nextGaussian();
		if (random > 1.0) random -= 1;
		if (random < -1.0) random += 1;
		return random;
	}
	
	/**
	 * 
	 * @param bitStringRule
	 * @param bitMaskLen
	 * @return
	 */
	public Iterator getNetValuesFromBitString(String bitStringRule, double bitMaskLen)
	{
		ArrayList<NetValue> decimals = new ArrayList<NetValue>();
		int beginIndex = 0;
		int endIndex = (int)bitMaskLen;
		int maxLenght = bitStringRule.length();
		while(endIndex <= maxLenght)
		{
			String strBit = bitStringRule.substring(beginIndex, endIndex);
			NetValue decimal = new NetValue(BitStringToDecimal(strBit));
			decimals.add(decimal);
			beginIndex = endIndex;
			endIndex += bitMaskLen; 

		}
		return new NetIterator(decimals);
	}
	


}
