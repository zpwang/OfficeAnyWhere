package com.office.anywher.infopub;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.office.anywher.ListActivity;
/**
 * 协同办公
 * @author Administrator
 *
 */
public class InfoPubActivity extends ListActivity {
	private static final String tag = "InfoPubActivity";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				Toast.makeText(InfoPubActivity.this, "InfoPubActivity-->" + paramInt, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
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
