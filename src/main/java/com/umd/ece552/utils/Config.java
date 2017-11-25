package com.umd.ece552.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;


import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetComposite;
import com.umd.ece552.nn.NetFactory;

/**
 * 
 * @author ingegarcia
 *
 */
public class Config 
{
	private Properties props;
	private static String propertiesName = "anfis.properties";
	public static final String NUMBER_OF_RULES = "NUMBER_OF_RULES";
	public static final String NUMBER_OF_INPUTS = "NUMBER_OF_INPUTS";
	public static final String NUMBER_OF_OUTPUTS = "NUMBER_OF_OUTPUTS";
	public static final String NET_TYPE = "NET_TYPE";
	public static final String NUMBER_OF_PARAMS = "NUMBER_OF_PARAMS";
	public static final String PARAMETER = "PARAMETER";
	private static final Config instance = new Config(); 
	
	private Config()
	{
		props = new Properties();
	}
	
	public static Config instance()
	{
		return instance;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key)
	{
		return props.getProperty(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key)
	{
		return new Double(props.getProperty(key)).doubleValue();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key)
	{
		return new Integer(props.getProperty(key)).intValue();
	}

	/**
	 * 
	 * @param nn
	 * @param parameters
	 */
	public void setNetParams(NetComposite net, Iterator parameters) throws NetException
	{
		Iterator itLayers = net.getChildren();
		while (itLayers.hasNext())
		{
			NetComposite layer = (NetComposite)itLayers.next();

			Iterator itNodes = layer.getChildren();
			while(itNodes.hasNext())
			{
				NetParameters functionParams = new NetParameters(); 
				NetComposite node = (NetComposite)itNodes.next();
				int paramSize = node.getFunction().parametersSize();
				
				for (int i=0; i<paramSize; i++ )
				{
					functionParams.add((NetValue)parameters.next());
				}
				
				node.getFunction().setFunctionParameters(functionParams);
			}
		}		
	}
	
	/**
	 * 
	 * @param nn
	 * @param parameters
	 */
	public Iterator getNetParams(NetComposite net) throws NetException
	{
		ArrayList<NetValue> l = new ArrayList<NetValue>();
		Iterator itLayers = net.getChildren();
		while (itLayers.hasNext())
		{
			NetComposite layer = (NetComposite)itLayers.next();

			Iterator itNodes = layer.getChildren();
			while(itNodes.hasNext())
			{
				NetComposite node = (NetComposite)itNodes.next();
				NetParameters params = node.getFunction().getFunctionParameters();
				Iterator itParams = params.getIterator();
				while(itParams.hasNext())
				{
					NetValue value = (NetValue)itParams.next();
					l.add(value);
				}
				
			}
		}	
		return new NetIterator(l);
	}

	/**
	 * 
	 * @param net
	 * @throws NetException
	 */
	public void saveNetConfig(String suffix, NetComposite net) throws NetException
	{
		//suffix += "_";
		Iterator it = net.getChildren();
		int inputs = 0;
		int outputs = 0;
		int rules = 0;
		while(it.hasNext())
		{
			NetComposite layer = (NetComposite)it.next();
			if (it.isFirst())
				inputs = layer.getChildren().size();
			else if (it.isLast())
				outputs = layer.getChildren().size();
			//rules layer
			else if (it.getIndex() == 4)
				rules = layer.getChildren().size();
		}
		this.props.put(Config.NET_TYPE+suffix, ""+net.netType());
		this.props.put(Config.NUMBER_OF_INPUTS+suffix, ""+inputs);
		this.props.put(Config.NUMBER_OF_OUTPUTS+suffix, ""+outputs);
		this.props.put(Config.NUMBER_OF_RULES+suffix, ""+rules);

		Iterator values = this.getNetParams(net);
		this.props.put(Config.NUMBER_OF_PARAMS+suffix, ""+values.size());
		
		int i = 0;
		while(values.hasNext())
		{
			NetValue netValues = (NetValue)values.next();
			this.props.put(Config.PARAMETER+suffix+i, ""+netValues.value); 
			i++;
		}

		this.saveConfig();
	}
	
	/**
	 * 
	 * @param net
	 * @throws NetException
	 */
	public NetComposite loadNetConfig(String suffix) throws NetException
	{
		//suffix = "_"+suffix;
		this.loadConfig();
		int inputs = this.getInt(Config.NUMBER_OF_INPUTS+suffix);
		int outputs = this.getInt(Config.NUMBER_OF_OUTPUTS+suffix);
		int rules = this.getInt(Config.NUMBER_OF_RULES+suffix);
		int type = this.getInt(Config.NET_TYPE+suffix);
		
		NetComposite net = NetFactory.instance().createNN(type, inputs, rules, outputs);
		
		int length = this.getInt(Config.NUMBER_OF_PARAMS+suffix);
		NetParameters params = new NetParameters();
		for (int i=0; i<length; i++)
		{
			NetValue value = new NetValue(this.getDouble(Config.PARAMETER+suffix+i));	
			params.add(value);
		}
		this.setNetParams(net, params.getIterator());
		return net;
	}

	/**
	 * 
	 * @param neuralNetwork
	 * @throws NNException
	 */
	public void saveConfig() throws NetException
	{
		OutputStream out = null;
		try
		{
			File file = new File(propertiesName);
			if (!file.exists())
			{
				file.createNewFile();
			}
			
			out = new FileOutputStream(propertiesName);
			props.store(out, "Anfis Configuration");
			
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println(fnfe);
			throw new NetException(fnfe.toString());
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
			throw new NetException(ioe.toString());
		} finally
		{
			if (out != null) try{out.close();} catch(IOException ioe){};
			out = null;
		}
		return;
	}

	/**
	 * 
	 * @return
	 * @throws NNException
	 */
	public void loadConfig() throws NetException
	{
		InputStream in = null;
		props.clear();
		
		try {
			File file = new File(propertiesName);
			if (!file.exists())
				this.saveConfig();
				//throw new FileNotFoundException("File "+file+" does not exists");
			
			in = new FileInputStream(file); 
			props.load(in);
			
		} 
		catch(FileNotFoundException fnfe)
		{
			throw new NetException(fnfe.toString());
		}
		catch(IOException ioe)
		{
			throw new NetException(ioe.toString());
		} finally
		{
			if (in != null) try{in.close();} catch(IOException ioe){};
			in = null;
		}
	}

}
