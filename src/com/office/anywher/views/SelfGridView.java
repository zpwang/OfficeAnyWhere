package com.office.anywher.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SelfGridView extends GridView {
	
	public SelfGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
     * ÉèÖÃ²»¹ö¶¯
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
