package com.umd.ece552.nn;

import com.umd.ece552.exception.NetException;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public abstract class NetBuilder {
	
	public abstract NetComposite getNN();
	public abstract void buildNN(int numberOfInputs) throws NetException;
	public abstract void buildLayersNN(int numberOfLayers) throws NetException;
	public abstract void buildNodesNN(int layer, int numberOfNodes, int functionType) throws NetException;
	public abstract void buildConnectionsNN(String connections) throws NetException;
	public abstract String getAutomaticConnections(int numberOfInputs, int numberOfFunctions, int numberOfOutputs) throws NetException;

}
