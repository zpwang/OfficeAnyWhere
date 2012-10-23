package com.office.anywher.offcial;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Window;

public class BarChartActivity extends Activity {
	private static String mChartTitle="公文处理柱形报表";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(ChartFactory.getBarChartView(this, getBarDataset(), getBarRenderer(), Type.DEFAULT));
	}

	// 柱状图渲染器
	public XYMultipleSeriesRenderer getBarRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.BLUE);// 颜色红色
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(15);
		renderer.addSeriesRenderer(r);
		renderer.setChartTitle("答题正确率");// 设置标题
		renderer.setXTitle("知识点");// x轴标题
		renderer.setYTitle("正确率");// y轴标题
		renderer.setAxisTitleTextSize(18);
		renderer.setAxesColor(Color.YELLOW);
		renderer.setXAxisMin(0.5);// x轴最小值
		renderer.setXAxisMax(5.5);
		renderer.setYAxisMin(0);// y轴最小值
		renderer.setYAxisMax(100);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setMarginsColor(Color.BLACK);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setApplyBackgroundColor(true);
		renderer.setGridColor(Color.BLACK);
		renderer.setShowGrid(true);// 显示网格
		renderer.setFitLegend(true);// 调整合适的位置
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setXLabels(0);
		
		renderer.addXTextLabel(1, "知识点一");
		renderer.addXTextLabel(2, "知识点二");
		renderer.addXTextLabel(3, "知识点三");
		renderer.addXTextLabel(4, "知识点四");
		renderer.setBarSpacing(2);
		return renderer;
	}

	// 柱图数据
	private XYMultipleSeriesDataset getBarDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		CategorySeries series = new CategorySeries("本次练习 %"); // 声明一个柱形图
		// 为柱形图添加值
		series.add(50);
		series.add(80);
		series.add(44);
		series.add(32);
		dataset.addSeries(series.toXYSeries());// 添加该柱形图到数据设置列表
		return dataset;
	}

}
