package com.umd.ece552.nn.anfis;

import java.util.StringTokenizer;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetBuilder;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.nn.NetConnection;
import com.umd.ece552.nn.anfis.functions.FunctionFactory;
import com.umd.ece552.utils.*;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class AnfisBuilder extends NetBuilder 
{
	private NetComposite neuralNetwork;


	@Override
	public void buildNN(int numberOfInputs) throws NetException {
		neuralNetwork = new AnfisNN();
		neuralNetwork.setNumberOfInputs(numberOfInputs);
	}


	@Override
	public NetComposite getNN() 
	{
		return neuralNetwork;
	}

	/**
	 * it should include the input layer
	 */
	@Override
	public void buildLayersNN(int numberOfLayers) throws NetException
	{
		for (int i = 0; i < numberOfLayers; i++)
		{
			NetComposite layer = new AnfisLayer(i);
			this.getNN().add(layer);
		}
	}


	@Override
	public void buildNodesNN(int indexLayer, int numberOfNodes, int functionType) throws NetException
	{
		NetComposite layer = this.getNN().getComposite(indexLayer);
		for (int i = 0; i < numberOfNodes; i++)
		{
			NetComposite node = new AnfisNode(i);
			node.setFunction(FunctionFactory.instance().getFunction(functionType));
			layer.add(node);
		}
	}

	/**
	 * Pass an iterator each element should have the following form: 
	 * fromLayer,fromNode;toLayer,toNode fromLayer,fromNode;toLayer,toNode
	 * (1,1)(2,1) (1,2)(2,2) 
	 */
	@Override
	public void buildConnectionsNN(String connections)  throws NetException
	{
		//this divides connections only per node (1,2)(2,2) 
		StringTokenizer stNodeConnections =  new StringTokenizer(connections, NetConstants.NODE_CONNECTIONS_SEPARATOR);
		while (stNodeConnections.hasMoreTokens())
		{
			//this divides the token 1,2
			String strNodeConn = stNodeConnections.nextToken();
			StringTokenizer tkConnections = new StringTokenizer(strNodeConn, NetConstants.CONNECTION_SEPARATOR);
			NetConnection conn = new NetConnection();
			
			if (tkConnections.hasMoreTokens())
			{
				String strConnection = tkConnections.nextToken();
				int[] nodeLocation = this.getNNLocation(strConnection);
				setConnectionToLeaf(conn, NetConnection.OUTPUT_CONNECTION, nodeLocation);
			}
			if (tkConnections.hasMoreTokens())
				tkConnections.nextToken();
			if (tkConnections.hasMoreTokens())
			{
				String strConnection = tkConnections.nextToken();
				int[] nodeLocation = this.getNNLocation(strConnection);
				setConnectionToLeaf(conn, NetConnection.INPUT_CONNECTION, nodeLocation);
			}

		}
	}
	
	/**
	 * set connection to the node
	 * @param conn
	 * @param type
	 */
	private void setConnectionToLeaf(NetConnection conn, int type, int[] connections) throws NetException
	{
		NetComposite node = this.getNode(connections[0], connections[1]);
		if (node != null)
		{
			if (type == NetConnection.OUTPUT_CONNECTION )
				node.setConnection(conn, NetConnection.OUTPUT_CONNECTION);
			else 
				node.setConnection(conn, NetConnection.INPUT_CONNECTION);
		}
	}
	
	/**
	 * get the node from an index layer and an index leaf
	 * @param indexLayer
	 * @param indexLeaf
	 * @return
	 */
	private NetComposite getNode(int indexLayer, int indexLeaf)
	{
		NetComposite leaf = null;
		NetComposite layer = null;
		
		if (indexLayer > -1)
			layer = this.getNN().getComposite(indexLayer);
		
		if (layer != null && indexLeaf > -1)
			leaf = layer.getComposite(indexLeaf);
		
		return leaf;
	}
	/**
	 * return an array int[2] from a String 1,1
	 * @param strLocation
	 * @return
	 */
	private int[] getNNLocation(String strLocation)
	{
		int[] value = {-1, -1};
		
		if (strLocation != null)
		{
			StringTokenizer tk = new StringTokenizer(strLocation, ",");
	
			if(tk.hasMoreTokens())
			{
				String to = tk.nextToken();
				if (to != null && !to.trim().equals("") && !to.trim().equals("-1"))
					value[0] = new Integer(to);
			}
			if(tk.hasMoreTokens())
			{
				String from = tk.nextToken();
				if (from != null && !from.trim().equals("") && !from.trim().equals("-1"))
					value[1] = new Integer(from);
			}
		}
		
		return value;
	}
	

	public String getAutomaticConnections(int numberOfInputs, int numberOfFunctions, int numberOfOutputs) throws NetException
	{
		StringBuffer strConnections = new StringBuffer();
		
		strConnections.append(getFirstLayerConnections(numberOfInputs, numberOfFunctions));
		strConnections.append(getSecondLayerConnections(numberOfInputs, numberOfFunctions));
		strConnections.append(getThirdLayerConnections(numberOfFunctions));
		strConnections.append(getForthLayerConnections(numberOfFunctions));
		strConnections.append(getFifthLayerConnections(numberOfFunctions, numberOfOutputs));
		return strConnections.toString();
	}


	private StringBuffer getFirstLayerConnections(int numberOfInputs, int numberOfFunctions)
	{
		StringBuffer strConnections = new StringBuffer();
		int infLimit = 0;
		int upLimit = numberOfFunctions;
		for(int i = 0; i < numberOfInputs; i++)
		{
			for(int j = infLimit; j < upLimit; j++)
				strConnections.append("(0," + i + "),(1," + j + ") ");
			upLimit += numberOfFunctions;
			infLimit = numberOfFunctions * (i+1);
		}
		return strConnections;		
	}

	private StringBuffer getSecondLayerConnections(int numberOfInputs, int numberOfFunctions)
	{
		StringBuffer strConnections = new StringBuffer();
		int totalNodes = (numberOfInputs*numberOfFunctions);

		int j=0;
		for(int i = 0; i < totalNodes; i++)
		{
			if (j == numberOfFunctions)
				j =0;
			strConnections.append("(1," + i + "),(2," + j + ") ");
			j++;
		}
		return strConnections;		
	}

	private StringBuffer getThirdLayerConnections(int numberOfFunctions)
	{
		StringBuffer strConnections = new StringBuffer();
		for(int i = 0; i < numberOfFunctions; i++)
		{
			for(int j = 0; j < numberOfFunctions; j++)
				strConnections.append("(2," + i + "),(3," + j + ") ");
		}
		return strConnections;		
	}

	private StringBuffer getForthLayerConnections(int numberOfFunctions)
	{
		StringBuffer strConnections = new StringBuffer();
		for(int i = 0; i < numberOfFunctions; i++)
		{
			strConnections.append("(3," + i + "),(4," + i + ") ");
		}
		return strConnections;		
	}

	private StringBuffer getFifthLayerConnections(int numberOfFunctions, int numberOfOutputs)
	{
		StringBuffer strConnections = new StringBuffer();
		for(int i = 0; i < numberOfFunctions; i++)
		{
			for(int j = 0; j < numberOfOutputs; j++)
			strConnections.append("(4," + i + "),(5," + j + ") ");
		}
		return strConnections;		
	}

}

