package com.office.anywher.offcial;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.office.anywher.IConst;
import com.office.anywher.ListActivity;
import com.office.anywher.R;
/**
 * 协同办公
 * @author Administrator
 *
 */
public class ActionTankenActivity extends ListActivity {
	private static final String tag = "ActionTankenActivity";
	private boolean aIsTongji = false;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aBottomNav = IConst.NavigetText.XIETONGBANGGONG;
        mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> paramAdapterView,
						View paramView, int paramInt, long paramLong) {
					Toast.makeText(ActionTankenActivity.this, "ActionTankenActivity-->" + paramInt, Toast.LENGTH_SHORT)
							.show();
					if(aIsTongji){
						switch(paramInt){
						case 0:
							Intent intent = new Intent();
							intent.setClass(ActionTankenActivity.this, LineChartActivity.class);
							ActionTankenActivity.this.startActivity(intent);
							break;
						case 1:
							intent = new Intent();
							intent.setClass(ActionTankenActivity.this, PieChartActivity.class);
							ActionTankenActivity.this.startActivity(intent);
							break;
						case 2:
							intent = new Intent();
							intent.setClass(ActionTankenActivity.this, BarChartActivity.class);
							ActionTankenActivity.this.startActivity(intent);
							break;
						}
					}
				}
			});
		
		clickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				mCurClickView = v;
				mDefaultProgress.show();
				v.setEnabled(false);
				aIsTongji = false;
				switch(v.getId()){
				case R.id.bottom_naviget_1:
					setNavigetStyle(0,false);
					mDataType.setType("DaiBan");
					break;
				case R.id.bottom_naviget_2:
					setNavigetStyle(1,false);
					mDataType.setType("DaiYue");
					break;
				case  R.id.bottom_naviget_3:
					setNavigetStyle(2,false);
					mDataType.setType("HuanBan");
					break;
				case  R.id.bottom_naviget_4:
					setNavigetStyle(3,false);
					mDataType.setType( "YiBan");
					break;
				case  R.id.bottom_naviget_5:
					setNavigetStyle(4,false);
					mDataType.setType("TongJi");
					aIsTongji = true;
					break;
				}
				new Thread(mPullDatasThread).start();
			}
		};
        setNavigetStyle(0,true);
	}
	/*private void setNavigetStyle(int index,boolean init){
		for(int i=0;i<aNavs.length;i++){
			aNavs[i].setVisibility(View.VISIBLE);
			aNavs[i].setText(IConst.NavigetText.XIETONGBANGGONG[i]);
			if(init)aNavs[i].setOnClickListener(clickListener);
			if(i!=index){
				aNavs[i].setBackgroundResource(R.drawable.bottom_button_bg_out);
				aNavs[i].setTextColor(Color.parseColor(IConst.NavigetText.CLICKTEXTCOLOR[0]));
				aNavs[i].setTextSize(IConst.NavigetText.CLICKTEXTSIZE[0]);
			}else{
				aNavs[i].setBackgroundResource(R.drawable.bottom_button_bg_in);
				aNavs[i].setTextColor(Color.parseColor(IConst.NavigetText.CLICKTEXTCOLOR[1]));
				aNavs[i].setTextSize(IConst.NavigetText.CLICKTEXTSIZE[1]);
			}
		}
		if(init){
			new Thread(mPullDatasThread).start();
			mDefaultProgress.show();
		}
	}*/
	
	
	@Override
	protected synchronized void getDatas() {
		mDatasList.clear();
		if(aIsTongji){
			for(int i=0;i<3;i++){
				HashMap<String,Object> map = new HashMap<String,Object>();
				if(i==0)map.put("ItemText","Line Chart");
				if(i==1)map.put("ItemText","Pie Chart");
				if(i==2)map.put("ItemText","Column Chart");
				mDatasList.add(map);
			}
			mPullDatasSuc = true;
			return ;
		}
		for(int i=0;i<9;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("ItemText","dataType "+mDataType.getType()+" my test datas , the ActionTankenActivity "+i+" th");
			mDatasList.add(map);
		}
		mPullDatasSuc = true;
	}
}
