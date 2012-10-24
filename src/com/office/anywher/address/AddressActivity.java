package com.office.anywher.address;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.office.anywher.ListActivity;
import com.office.anywher.R;
import com.office.anywher.adapters.DialAdapter;
/**
 * 协同办公
 * @author Administrator
 *
 */
public class AddressActivity extends ListActivity {
	private static final String tag = "AddressActivity";
	private RelativeLayout mHeader;
	private Button mSearch;
	private EditText mSearchKey;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater aInflater = LayoutInflater.from(this);
		mHeader = (RelativeLayout) aInflater.inflate(R.layout.list_header, null);
		mListView.addHeaderView(mHeader);
		mSearch = (Button)mHeader.findViewById(R.id.search_button);
		mSearchKey = (EditText)mHeader.findViewById(R.id.search_key);
		mSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View paramView) {
				String key = mSearchKey.getText().toString().trim();
				if(key.equals("")){
					getDatas();
				}else{
					getDatas(key);
				}
				seteAdapter();
			}
		});
	}
	@Override
	public void onResume(){
		super.onResume();
		seteAdapter();
	}
	private void getDatas(String key){
		mDatasList.clear();
		AddressUtil au = new AddressUtil();
		List<AddressInfo> ail = au.getAddressInfoByUser(key);
		for(AddressInfo ai:ail){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("userName",ai.mUserName);
			map.put("userPhone",ai.mUserPhone);
			map.put("userQQ",ai.mUserQQ);
			map.put("userEmail",ai.mUserEmail);
			mDatasList.add(map);
		}
		synchronized(this){
			mPullDatasSuc = true;
		}
	}
	protected void getDatas(){
		mDatasList.clear();
		AddressUtil au = new AddressUtil();
		List<AddressInfo> ail = au.getAddressList();
		for(AddressInfo ai:ail){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("userName",ai.mUserName);
			map.put("userPhone",ai.mUserPhone);
			map.put("userQQ",ai.mUserQQ);
			map.put("userEmail",ai.mUserEmail);
			mDatasList.add(map);
		}
		synchronized(this){
			mPullDatasSuc = true;
		}
	}
	
	private void seteAdapter(){
		mAdapter = new DialAdapter(this, mDatasList,
				R.layout.address_list_item, new String[] { "userName",
						"userPhone", "userQQ", "userEmail" }, new int[] {
						R.id.user_name, R.id.user_phone_txt, R.id.user_qq_txt,
						R.id.user_email_txt });
		mListView.setAdapter(mAdapter);
	}
}
