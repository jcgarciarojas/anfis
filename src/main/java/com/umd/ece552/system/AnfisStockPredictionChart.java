package com.umd.ece552.system;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

/**
 * 
 * @author Juan Garcia Rojas
 *
 */
public class AnfisStockPredictionChart extends JFrame
{

	/**
	 * 
	 * @param titleWindow
	 * @param titleChart
	 * @param xTitle
	 * @param yTitle
	 * @param dataSet
	 */
    public AnfisStockPredictionChart(String titleWindow, String titleChart, String xTitle, String yTitle, XYDataset dataSet)
    {
        super(titleWindow);
        JPanel jpanel = createDemoPanel(titleChart, xTitle, yTitle, dataSet);
        jpanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(jpanel);
    }

    /**
     * 
     * @param titleChart
     * @param xTitle
     * @param yTitle
     * @param dataSet
     * @return
     */
    protected JPanel createDemoPanel(String titleChart, String xTitle, String yTitle, XYDataset dataSet)
    {
        JFreeChart jfreechart = createChart(titleChart, xTitle, yTitle, dataSet);
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setMouseWheelEnabled(true);
        return chartpanel;
    }

    /**
     * 
     * @param titleChart
     * @param xTitle
     * @param yTitle
     * @param xydataset
     * @return
     */
    protected JFreeChart createChart(String titleChart, String xTitle, String yTitle, XYDataset xydataset)
    {
    	
        JFreeChart jfreechart = ChartFactory.createXYLineChart(titleChart, xTitle, yTitle, xydataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
        xylineandshaperenderer.setSeriesShapesVisible(0, false);
        xylineandshaperenderer.setSeriesShapesVisible(1, false);
        xyplot.setRenderer(xylineandshaperenderer);
        NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return jfreechart;
        
    }
}
