package com.umd.ece552.nn.anfis.functions;

import com.umd.ece552.exception.NetException;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class FunctionFactory 
{
	public static final int SIGMOIDAL_FUNCTION = 0;
	public static final int GAUSSIAN_FUNCTION = 1;
	public static final int NO_FUNCTION = 2;
	public static final int MIN_FUNCTION = 3;
	public static final int POLYNOMIAL_FUNCTION = 4;
	public static final int RATIO_FUNCTION = 5;
	public static final int SUMMATION_FUNCTION = 6;
	private static FunctionFactory instance = new FunctionFactory();
	
	private FunctionFactory()
	{
	}
	
	public static FunctionFactory instance()
	{
		return instance;
	}

	public Function getFunction(int type) throws NetException
	{
		Function f = null;
		
		if (type == SIGMOIDAL_FUNCTION)
			f = new SigmoidalFunction();

		else if (type == GAUSSIAN_FUNCTION)
			f = new GaussianFunction();

		else if (type == NO_FUNCTION)
			f = new NoFunction();
		
		else if (type == MIN_FUNCTION)
			f = new MinFunction();
		
		else if (type == POLYNOMIAL_FUNCTION)
			f = new PolynomialFunction();
		
		else if (type == RATIO_FUNCTION)
			f = new RatioFunction();
		
		else if (type == SUMMATION_FUNCTION)
			f = new SummationFunction();
		
		return f;
	}
}
