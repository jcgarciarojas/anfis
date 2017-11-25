package com.umd.ece552.utils;


public abstract class DataReader {
	
	public abstract void restart();
	public abstract boolean hasNextValues();
	public abstract NetParameters getNextInputValues();
	public abstract NetParameters getNextOutputValues();
}
