package com.office.anywher;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.office.anywher.utils.ActivityStackUtil;
import com.office.anywher.views.SelfGridView;

public class NavigetActivity extends MainActivity{
	private static final String tag = "NavigetActivity";
	private LinearLayout aCenterLayout;
	private LayoutInflater aInflater;
	private SelfGridView aGridView;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aCenterLayout = (LinearLayout)findViewById(R.id.center_content);
        aCenterLayout.removeAllViews();
        aInflater=LayoutInflater.from(this);
        aGridView = (SelfGridView)aInflater.inflate(R.layout.grid_view, null);        
        aGridView.setAdapter(adapter);
		aGridView.setOnItemClickListener(ItemOnClick);
		aCenterLayout.addView(aGridView);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			dialog() ;
	       
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	private void dialog() {
		AlertDialog.Builder builder = new Builder(NavigetActivity.this);
		builder.setMessage("确认退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 ActivityStackUtil.finishProgram();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.create().show();
	}
	
}
