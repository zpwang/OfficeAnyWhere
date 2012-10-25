package com.office.anywher.email;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.office.anywher.IConst;
import com.office.anywher.ListActivity;
import com.office.anywher.R;
import com.office.anywher.utils.ActivityStackUtil;
/**
 * 协同办公
 * @author Administrator
 *
 */
public class EmailActivity extends ListActivity {
	private static final String tag = "EmailActivity";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);
        aBottomNav = IConst.NavigetText.EMAIL;
        
        mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				Toast.makeText(EmailActivity.this, "EmailActivity-->" + paramInt, Toast.LENGTH_SHORT)
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
    				mDataType.setType("ShouJianXiang");
    				break;
    			case  R.id.bottom_naviget_2:
    				setNavigetStyle(1,false);
    				mDataType.setType("FaJianXiang");
    				break;
    			case  R.id.bottom_naviget_3:
    				setNavigetStyle(2,false);
    				mDataType.setType("CaoGaoXiang");
    				break;
    			}
    			new Thread(mPullDatasThread).start();
    		}
    	};
        setNavigetStyle(0,true);

	}
	
	protected void getDatas(){
		mDatasList.clear();
		for(int i=0;i<18;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("ItemSrc",R.drawable.list_title_log);
			map.put("ItemText","dataType "+mDataType.getType()+" my test datas , the ActionTankenActivity "+i+" th");
			mDatasList.add(map);
		}
		synchronized(this){
			mPullDatasSuc = true;
		}
	}
}
