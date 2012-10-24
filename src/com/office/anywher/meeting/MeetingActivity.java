package com.office.anywher.meeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.office.anywher.IConst;
import com.office.anywher.ListActivity;
import com.office.anywher.R;
import com.office.anywher.email.EmailActivity;
import com.office.anywher.utils.ActivityStackUtil;
/**
 * 协同办公
 * @author Administrator
 *
 */
public class MeetingActivity extends ListActivity {
	private static final String tag = "MeetingActivity";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);

        aBottomNav = IConst.NavigetText.MEETING;
        mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				Toast.makeText(MeetingActivity.this, "MeetingActivity-->" + paramInt, Toast.LENGTH_SHORT)
						.show();
			}
		});
        clickListener = new OnClickListener(){
    		@Override
    		public void onClick(View v) {
    			mCurClickView = v;
    			mDefaultProgress.show();
    			v.setEnabled(false);
    			switch(v.getId()){
    			case  R.id.bottom_naviget_1:
    				setNavigetStyle(0,false);
    				mDataType.setType("HuiYi 1");
    				break;
    			case  R.id.bottom_naviget_2:
    				setNavigetStyle(1,false);
    				mDataType.setType("HuiYi 2");
    				break;
    			case  R.id.bottom_naviget_3:
    				setNavigetStyle(2,false);
    				mDataType.setType("HuiYi3");
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
			aNavs[i].setText(IConst.NavigetText.MEETING[i]);
			if(init){
				aNavs[i].setOnClickListener(clickListener);
			}
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
		if(init)new Thread(mPullDatasThread).start();

	}*/
	protected void getDatas(){
		mDatasList.clear();
		for(int i=0;i<18;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("ItemText","dataType "+mDataType.getType()+" my test datas , the ActionTankenActivity "+i+" th");
			mDatasList.add(map);
		}
		synchronized(this){
			mPullDatasSuc = true;
		}
	}
}
