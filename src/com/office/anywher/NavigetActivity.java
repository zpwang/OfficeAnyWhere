package com.office.anywher;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.office.anywher.global.GlobalVar;
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
		aGridView.setOnItemClickListener(ItemOnClick);
		aCenterLayout.addView(aGridView);
	}
	@Override
	public void onResume(){
		super.onResume();
		((List)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA)).remove(0);
		Log.d(tag, "size:"+((List)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA)).size());
		initMenuDatas();
		adapter = new SimpleAdapter(this, aMenuDatas,
				R.layout.nine_dial_gv_item, new String[] {aGridViewItemImage,
						aGridViewItemText,aGridViewItemNumber }, new int[] { R.id.gv_item_image,
						R.id.gv_item_text,R.id.gv_item_new_number });
        aGridView.setAdapter(adapter);
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
