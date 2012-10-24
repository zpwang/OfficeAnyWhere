package com.office.anywher.offcial;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.office.anywher.utils.ActivityStackUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class PieChartActivity extends Activity{
	private double[] value = {1,1,2,2};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(execute(this));
	}
	private View execute(Context context) { 
         int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE,Color.GREEN }; 
         DefaultRenderer renderer = buildCategoryRenderer(colors); 
         CategorySeries categorySeries = new CategorySeries("Vehicles Chart"); 
         categorySeries.add("待办文件", value[0]); 
         categorySeries.add("待阅文件", value[1]); 
         categorySeries.add("已办文件 ", value[2]); 
         categorySeries.add("缓办文件 ", value[3]);
         return ChartFactory.getPieChartView(context, categorySeries, renderer); 
     } 
       
	 private DefaultRenderer buildCategoryRenderer(int[] colors) { 
         DefaultRenderer renderer = new DefaultRenderer(); 
         renderer.setBackgroundColor(Color.BLACK);
         renderer.setApplyBackgroundColor(true);
         renderer.setLabelsTextSize(15);
         renderer.setLabelsColor(Color.WHITE);
         renderer.setChartTitle("文件处理饼图");
         renderer.setChartTitleTextSize(20);
         renderer.setLegendTextSize(15);
         renderer.setLegendHeight(60);
         for (int color : colors) { 
             SimpleSeriesRenderer r = new SimpleSeriesRenderer(); 
             r.setColor(color); 
             renderer.addSeriesRenderer(r); 
         } 
         return renderer; 
     } 
}
