package com.umd.ece552.system;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.ui.RefineryUtilities;

import com.umd.ece552.exception.NetException;
import com.umd.ece552.nn.NetFactory;
import com.umd.ece552.training.TrainingAlgorithmFactory;

/*
Train
for each case(set of weigths) in the population
test in anfis and calculate fitness


Charts
1. Square Error for output unit on training data
(output-expected)^2 per case

2. Sum weights increment on training data per case

3. Predicted vs Target output in training data

Global Anfis Error
RMSE = sqrt(1/n*sum(o-t)^2)
95% confidence interval
error +- Z * sqrt (error*(1-error)/n)
*/
public class AnfisStockPrediction extends JFrame implements ActionListener
{
	//private static Properties systemProp;
	private static final String CREATE_COMMAND = "create";
	private static final String LOAD_COMMAND = "load";
	private static final String TRAINING_COMMAND = "train";
	private static final String TESTING_COMMAND = "test";
	
	private static final String PREDICT_COMMAND = "predict";
	
	private static final String NO_COMMAND = "N/A";
	private static final String RMSE_COMMAND = "Square Error";
	private static final String SUM_WEIGHTS_COMMAND = "Sum Weights Increment";
	private static final String PRD_VS_TARGET_COMMAND = "Predicted vs Target Output";
	private static final String ALL_CHARTS_COMMAND = "All Charts";
	
	//private JCheckBox createStatisticFilesCheck;
	private JTextField inputsText;
	private JTextField outputsText;
	private JTextField rulesText;
	private JTextField maskText;
	private JTextField thresholdText;
	private JTextField dataDirText;
	private JTextField gaPopText;
    private JTextField gaMaxOperationsText;
	private JTextField rooSquareErrorText;
    private JTextField confidenceIntervalText;

	private JTextField parametersText;
	private JTextField outputText;
	private JTextField errorText;
	private JTextField configText;

	//private boolean generateStatisticFile;
    private JPanel parent; 
    private AnfisStockPrecitionControl control;

	/**
	 * 
	 * @param title
	 * @throws NNException
	 */
	private AnfisStockPrediction(String title) throws NetException
	{
		super(title);
		setSize(480,400);
		addComponentstoWindow();
		control = new AnfisStockPrecitionControl();
	}
	
	/**
	 * 
	 * @return
	 */
	
	private JPanel createLeftPanel()
	{
		inputsText = new JTextField(5);
		inputsText.setEditable(true);
        JLabel inputTextLabel = new JLabel("Inputs");
        inputTextLabel.setLabelFor(inputsText);

        outputsText = new JTextField(5);
        outputsText.setEditable(true);
        JLabel layersTextLabel = new JLabel("Outputs");
        layersTextLabel.setLabelFor(outputsText);

		rulesText = new JTextField(5);
		rulesText.setEditable(true);
        JLabel rulesLabel = new JLabel("Number of Rules");
        rulesLabel.setLabelFor(rulesText);

		JButton createButton = new JButton("Create");
        createButton.setToolTipText("Create Anfis");
        createButton.setActionCommand(CREATE_COMMAND);
        createButton.setEnabled(true);
        createButton.addActionListener(this);
        
        JPanel entryControlsPane = new JPanel();
        GridLayout grid = new GridLayout(3,2); 
        entryControlsPane.setLayout(grid);
        entryControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(), BorderFactory.createEmptyBorder(5,5,5,5)));
		
        entryControlsPane.add(inputTextLabel);
        entryControlsPane.add(inputsText);
        entryControlsPane.add(layersTextLabel);
        entryControlsPane.add(outputsText);
        entryControlsPane.add(rulesLabel);
        entryControlsPane.add(rulesText);


        JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Creation"), BorderFactory.createEmptyBorder(5,5,5,5)));		
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(entryControlsPane);
        panel.add(createButton);

		return panel;
	}

	private JPanel createLowerPanel()
	{
		maskText = new JTextField(5);
		maskText.setEditable(true);
        JLabel maskLabel = new JLabel("BitString Mask");
        maskLabel.setLabelFor(maskText);

		gaPopText = new JTextField(5);
		gaPopText.setEditable(true);
        JLabel gaPopLabel = new JLabel("Population");
        gaPopLabel.setLabelFor(gaPopText);

		gaMaxOperationsText = new JTextField(5);
		gaMaxOperationsText.setEditable(true);
        JLabel gaMaxOperationLabel = new JLabel("Max Operations");
        gaMaxOperationLabel.setLabelFor(gaMaxOperationsText);

        thresholdText = new JTextField(5);
		thresholdText.setEditable(true);
        JLabel thresholdLabel = new JLabel("Threshold");
        thresholdLabel.setLabelFor(thresholdText);

        dataDirText = new JTextField(5);
        dataDirText.setEditable(true);
        JLabel dataLabel = new JLabel("Data Dir");
        dataLabel.setLabelFor(dataDirText);

        rooSquareErrorText = new JTextField(5);
        rooSquareErrorText.setEditable(false);
        JLabel rooSquareLabel = new JLabel("Root-Mean Square Error");
        rooSquareLabel.setLabelFor(rooSquareErrorText);

        confidenceIntervalText = new JTextField(5);
        confidenceIntervalText.setEditable(false);
        JLabel confidenceLabel = new JLabel("Confidence Interval");
        confidenceLabel.setLabelFor(confidenceIntervalText);

                
		String[] charts = { NO_COMMAND, RMSE_COMMAND, SUM_WEIGHTS_COMMAND, PRD_VS_TARGET_COMMAND, ALL_CHARTS_COMMAND};
		JComboBox chartList = new JComboBox(charts);
		chartList.setSelectedIndex(0);
		chartList.addActionListener(this);
        JLabel chartsLabel = new JLabel("Charts");
        chartsLabel.setLabelFor(chartList);
        
        JButton trainButton = new JButton("Train");
		trainButton.setToolTipText("Train Anfis");
		trainButton.setActionCommand(TRAINING_COMMAND);
		trainButton.setEnabled(true);
		trainButton.addActionListener(this);

        JButton testButton = new JButton("Test");
        testButton.setToolTipText("Test Anfis");
        testButton.setActionCommand(TESTING_COMMAND);
        testButton.setEnabled(true);
        testButton.addActionListener(this);

		JPanel entryControlsPane = new JPanel();
        GridLayout grid = new GridLayout(8,2); 
        entryControlsPane.setLayout(grid);
        entryControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(), BorderFactory.createEmptyBorder(5,5,5,5)));
		
        entryControlsPane.add(maskLabel);
        entryControlsPane.add(maskText);
        entryControlsPane.add(gaPopLabel);
        entryControlsPane.add(gaPopText);
        entryControlsPane.add(gaMaxOperationLabel);
        entryControlsPane.add(gaMaxOperationsText);

        entryControlsPane.add(thresholdLabel);
        entryControlsPane.add(thresholdText);
        entryControlsPane.add(dataLabel);
        entryControlsPane.add(dataDirText);

        entryControlsPane.add(rooSquareLabel);
        entryControlsPane.add(rooSquareErrorText);
        entryControlsPane.add(confidenceLabel);
        entryControlsPane.add(confidenceIntervalText);
        
        entryControlsPane.add(chartsLabel);
        entryControlsPane.add(chartList);

        JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("GA Training"), BorderFactory.createEmptyBorder(5,5,5,5)));		
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(trainButton);
        buttonPanel.add(testButton);

        panel.add(entryControlsPane);
        panel.add(buttonPanel);

		return panel;
	}	

	private JPanel createRightPanel()
	{
		parametersText = new JTextField(5);
		parametersText.setEditable(true);
        JLabel maskLabel = new JLabel("Parameters");
        maskLabel.setLabelFor(parametersText);

		outputText = new JTextField(5);
		outputText.setEditable(true);
        JLabel thresholdLabel = new JLabel("Outputs");
        thresholdLabel.setLabelFor(outputText);

        errorText = new JTextField(5);
        errorText.setEditable(true);
        JLabel dataLabel = new JLabel("Error");
        dataLabel.setLabelFor(errorText);

		JButton predictionButton = new JButton("Prediction");
		predictionButton.setToolTipText("Anfis Prediction");
		predictionButton.setActionCommand(TRAINING_COMMAND);
		predictionButton.setEnabled(true);
		predictionButton.addActionListener(this);
        
        JPanel entryControlsPane = new JPanel();
        GridLayout grid = new GridLayout(3,2); 
        entryControlsPane.setLayout(grid);
        entryControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(), BorderFactory.createEmptyBorder(5,5,5,5)));
		
        entryControlsPane.add(maskLabel);
        entryControlsPane.add(parametersText);
        entryControlsPane.add(thresholdLabel);
        entryControlsPane.add(outputText);
        entryControlsPane.add(dataLabel);
        entryControlsPane.add(errorText);
        
        JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Prediction"), BorderFactory.createEmptyBorder(5,5,5,5)));		
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(entryControlsPane);
        panel.add(predictionButton);

		return panel;
	}		

	private JPanel createConfigPanel()
	{
		configText = new JTextField(3);
		configText.setEditable(true);
        JLabel maskLabel = new JLabel("Configuration");
        maskLabel.setLabelFor(configText);

		JButton loadButton = new JButton("Load");
		loadButton.setToolTipText("Load Configuration");
		loadButton.setActionCommand(LOAD_COMMAND);
		loadButton.setEnabled(true);
		loadButton.addActionListener(this);
        
		JPanel entryControlsPane = new JPanel();
        GridLayout grid = new GridLayout(3,2); 
        entryControlsPane.setLayout(grid);
        entryControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(), BorderFactory.createEmptyBorder(5,5,5,5)));
		
        entryControlsPane.add(maskLabel);
        entryControlsPane.add(configText);
        
        JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Configuration"), BorderFactory.createEmptyBorder(5,5,5,5)));		
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(entryControlsPane);
        panel.add(loadButton);

		return panel;
	}		

	/**
	 * 
	 */
	private void addComponentstoWindow()
	{
		parent = new JPanel(new BorderLayout());
		parent.add(this.createLeftPanel(), BorderLayout.LINE_START);
		parent.add(createRightPanel(), BorderLayout.CENTER);
		parent.add(createConfigPanel(), BorderLayout.LINE_END);
		parent.add(createLowerPanel(), BorderLayout.PAGE_END);

		parent.setVisible(true);
        super.add(parent);

        inputsText.setText("5");
        outputsText.setText("1");
        rulesText.setText("5");   
        maskText.setText("0000111100001111");
        gaPopText.setText("200");
        gaMaxOperationsText.setText("2000");
        thresholdText.setText("0.005");
        dataDirText.setText("C:/Documents and Settings/ingegarcia/My Documents/University/michigan/MS-SWE/ECE552/ANFIS/training_data/0.csv");
	}

	/**
	 * 
	 * @param message
	 */
    private void showMessage(String message) {
    	JOptionPane.showMessageDialog(this,message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
	
    /**
     * 
     * @param message
     * @return
     */
    private boolean questionMessage(String message) 
    {
    	boolean overwritte = false;
    	if (JOptionPane.showConfirmDialog(this,message, "Message", JOptionPane.YES_NO_OPTION, 2) == 1)
    		overwritte = true;
    		
    	return overwritte;
    }

    /**
     * 
     */
    public void actionPerformed(ActionEvent e)
    {
    	
    	try {
	    	
    		if (CREATE_COMMAND.equals(e.getActionCommand())) 
	    		control.createAnfis(NetFactory.ANFIS_NN, inputsText.getText(), rulesText.getText(), outputsText.getText());
	    	else if (LOAD_COMMAND.equals(e.getActionCommand())) 
	    		control.loadCommand(configText.getText());
	    	else if (TESTING_COMMAND.equals(e.getActionCommand())) 
	    	{
	    		control.testingCommand(dataDirText.getText());
	    		confidenceIntervalText.setText(control.getConfidenceInterval());
	    	    rooSquareErrorText.setText(control.getRootMeanSquareError());
	    	}
	    	else if (TRAINING_COMMAND.equals(e.getActionCommand())) 
	    	{
	    		control.trainigCommand(TrainingAlgorithmFactory.GENETIC_ALGORITHM, dataDirText.getText(), maskText.getText(), gaPopText.getText(), gaMaxOperationsText.getText(), thresholdText.getText());
	    		confidenceIntervalText.setText(control.getConfidenceInterval());
	    	    rooSquareErrorText.setText(control.getRootMeanSquareError());
	    	}
	    	else if (PREDICT_COMMAND.equals(e.getActionCommand())) 
	    	{
	    		outputText.setText(control.predictionCommand(parametersText.getText()));
	    		
	    	}
	    	else if ("comboBoxChanged".equals(e.getActionCommand())) 
	    	{
	    		JComboBox cb = (JComboBox)e.getSource();
	            String option = (String)cb.getSelectedItem();

		    	if (RMSE_COMMAND.equals(option))
		    		this.getChart(option);
		    	else if (SUM_WEIGHTS_COMMAND.equals(option)) 
		    		this.getChart(option);
		    	else if (PRD_VS_TARGET_COMMAND.equals(option)) 
		    		this.getChart(option);
		    	else if (ALL_CHARTS_COMMAND.equals(option))
		    	{
		    		this.getChart(RMSE_COMMAND);
		    		this.getChart(SUM_WEIGHTS_COMMAND);
		    		this.getChart(PRD_VS_TARGET_COMMAND);
		    	}
		    	cb.setSelectedIndex(0);
		    	return;

	    	}
    		showMessage("Process Completed!");

    	} catch(NetException ex)
    	{
    		showMessage(ex.getText());
    		ex.printStackTrace();
    	} catch(Exception ex)
    	{
    		showMessage("System Exception!");
    		ex.printStackTrace();
    	} finally 
    	{
    	}

    }

    /**
     * 
     * @param option
     * @throws NetException
     */
    private void getChart(String option) throws NetException
    {
    	AnfisStockPredictionChart chartWindow = null;
    	if (RMSE_COMMAND.equals(option)) 
    		chartWindow = new AnfisStockPredictionChart(option+" Chart", option, "Epoch", "Values", control.createSquareErrorDataSet());
    	else if (SUM_WEIGHTS_COMMAND.equals(option)) 
    		chartWindow = new AnfisStockPredictionChart(option+" Chart", option, "Epoch", "Values", control.createSumWeightsIncrementDataSet());
    	else if (PRD_VS_TARGET_COMMAND.equals(option)) 
    		chartWindow = new AnfisStockPredictionChart(option+" Chart", option, "Epoch", "Values", control.createPredictedTargetDataSet());
    	else if (ALL_CHARTS_COMMAND.equals(option)) 
    		chartWindow = new AnfisStockPredictionChart(option+" Chart", option, "Epoch", "Values", control.createPredictedTargetDataSet());
    	else
    		return;

        chartWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	chartWindow.pack();
        RefineryUtilities.centerFrameOnScreen(chartWindow);
        chartWindow.setVisible(true);
    	
    }

    /**
     * This method creates the main window of the voice dictionary
     */
    private static void createAndShowGUI() throws NetException {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        AnfisStockPrediction frame = new AnfisStockPrediction("Anfis Stock Prediction using GA learning algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    /**
	 * 
	 * @param args
	 * @throws NetException
	 */
	public static void main(String[] args)
	{
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run()  {
            	try{
                    createAndShowGUI();
                } catch(NetException nne)
                {
                	nne.printStackTrace();
                	System.out.println(nne);
                	System.exit(1);
                }
            }
        });
	}
}
