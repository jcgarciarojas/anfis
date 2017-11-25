package com.umd.ece552.exception;

public class NetException extends Exception {
	
	private String exception;
	
	public NetException(String exception)
	{
		super(exception);
		this.exception = exception;
	}

	public NetException(Exception ex)
	{
		super(ex);
	}
	
	public String getText()
	{
		return exception;
	}
}
