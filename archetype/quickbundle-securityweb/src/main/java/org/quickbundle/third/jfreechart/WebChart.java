package org.quickbundle.third.jfreechart;
/*
 * Created on 2003-9-9
 * http://www.sentom.net
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */


import java.awt.Color;
import java.awt.Font;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;
import org.quickbundle.tools.support.font.RmFont;

/**
 * @author sentom
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WebChart {
    private DefaultPieDataset data = new DefaultPieDataset();
    private DefaultCategoryDataset dataCategory = new DefaultCategoryDataset();

    public void setValue(String key, double value) {
    	if(key == null) {
    		key = "";
    	}
        data.setValue(key, value);
        dataCategory.setValue(value, key, key);
    }
    
    static Font fontLarge = new Font(RmFont.getDefaultFontName(), Font.PLAIN, 20);
    static Font fontSmall = new Font(RmFont.getDefaultFontName(), Font.PLAIN, 12);

    /**
     * 简单饼图，无链接+2D
     * 
     * @param title
     * @param session
     * @param pw
     * @return
     */
    public String generatePieChart(String title, HttpSession session, PrintWriter pw) {
    	return generatePieChart(title, session, pw, null, false);
    }
    
    /**
     * 生成饼图
     * 
     * @param title 标题
     * @param session session
     * @param pw
     * @param pug
     * @param is3d
     * @return
     */
    public String generatePieChart(String title, HttpSession session, PrintWriter pw, PieURLGenerator pug, boolean is3d) {
        String filename = null;
        try {
            // 创建chart对象
            PiePlot plot = null;
            if(is3d) {
            	plot = new PiePlot3D(data);
            } else {
            	plot = new PiePlot(data);
            }
            plot.setInsets(new RectangleInsets(0, 5, 5, 5));

            // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位  
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));   
            // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例                  
            plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));  
                    
            // 在统计图片上建链接
            plot.setURLGenerator(pug);
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());

            JFreeChart chart = new JFreeChart("", fontLarge, plot, true);
            chart.setBackgroundPaint(Color.white);// 设置图片的背景色
  
            TextTitle _title = new TextTitle(title);
            _title.setFont(fontLarge); // 设置图片标题的字体
            chart.setTitle(_title);
            chart.getTitle().setFont(fontLarge);
            plot.setLabelFont(fontSmall);

            chart.getLegend().setItemFont(fontSmall);
            
            // 把生成的图片放到临时目录
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            // 500是图片长度，300是图片高度
            filename = ServletUtilities.saveChartAsPNG(chart, 600, 400, info, session);

            ChartUtilities.writeImageMap(pw, filename, info, false);
            pw.flush();

        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filename = "public_error_500x300.png";
        }
        return filename;
    }

    /**
     * 简单饼图，无链接+3D
     * 
     * @param title
     * @param session
     * @param pw
     * @return
     */
    public String generateBarChart(String title, HttpSession session, PrintWriter pw) {
    	return generateBarChart(title, session, pw, null, true);
    }
    
    /**
     * 生成柱图
     * @param title
     * @param session
     * @param pw
     * @param cug
     * @param is3d
     * @return
     */
    public String generateBarChart(String title, HttpSession session, PrintWriter pw, CategoryURLGenerator cug, boolean is3d) {
        String filename = null;
        try {
            JFreeChart chart = ChartFactory.createBarChart3D(title, "数值", "数据量", dataCategory, PlotOrientation.VERTICAL, false, false, false);
            chart.setBackgroundPaint(Color.white);
            chart.getTitle().setFont(fontLarge);
            
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.white);
            plot.setRangeGridlinePaint(Color.blue);
            
            CategoryAxis domainAxis=plot.getDomainAxis();
            /*------设置X轴坐标上的文字-----------*/
            domainAxis.setTickLabelFont(fontSmall);
            /*------设置X轴的标题文字------------*/
            domainAxis.setLabelFont(fontSmall);
            //设置距离图片左端距离
            domainAxis.setLowerMargin(0.05);
            
            // 设置Y轴显示整数
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            ValueAxis rAxis = plot.getRangeAxis();
            /*------设置Y轴坐标上的文字-----------*/
            rAxis.setTickLabelFont(fontSmall);
            /*------设置Y轴的标题文字------------*/
            rAxis.setLabelFont(fontSmall); 

            BarRenderer renderer = null;
            if(is3d) {
            	renderer = new BarRenderer3D();
            } else {
            	renderer = new BarRenderer();
            }
            
            //设置柱的颜色
            renderer.setSeriesPaint(0, new Color(0xff00));
            //显示数值
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
            //在统计图片上建链接
            renderer.setBaseItemURLGenerator(cug);
            plot.setRenderer(renderer);
            // 把生成的图片放到临时目录
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(chart, 600, 400, info, session);
            ChartUtilities.writeImageMap(pw, filename, info, false);
        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filename = "public_error_500x300.png";
        }
        return filename;
    }
}