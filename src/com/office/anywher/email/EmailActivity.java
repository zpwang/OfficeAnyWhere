package com.office.anywher.email;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.office.anywher.IConst;
import com.office.anywher.ListActivity;
import com.office.anywher.R;
import com.office.anywher.global.GlobalVar;
import com.office.anywher.utils.ActivityStackUtil;
/**
 * 协同办公
 * @author Administrator
 *
 */
public class EmailActivity extends ListActivity {
	private static final String tag = "MeetingActivity";
	private RelativeLayout mHeader;
	private Button mSearch;
	private Button mNewBtn;
	private RelativeLayout mMoreCondition;
	private EditText mEmailTitleTxt;
	private String mSearchKey;
	SimpleDateFormat sdfMore = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);
        aBottomNav = IConst.NavigetText.EMAIL;
        LayoutInflater aInflater = LayoutInflater.from(this);
        mHeader = (RelativeLayout) aInflater.inflate(R.layout.list_email_header, null);
        mSearch = (Button)mHeader.findViewById(R.id.search_email_btn);
        mNewBtn = (Button)mHeader.findViewById(R.id.new_email_bt);
        mEmailTitleTxt = (EditText)mHeader.findViewById(R.id.email_key_txt);
        mListView.addHeaderView(mHeader);
        mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				alterShowPubInfoDetails(paramInt-1);
			}
		});
        mSearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mSearchKey = mEmailTitleTxt.getText().toString().trim();
				new Thread(mPullDatasThread).start();
			}
        });
        mNewBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				alertNewEmail(null);
			}
        });
        
		clickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurClickView = v;
				mDefaultProgress.show();
				v.setEnabled(false);
				switch (v.getId()) {
				case R.id.bottom_naviget_1:
					setNavigetStyle(0, false);
					mDataType.setType("ShouJianXiang");
					type = 1;
					break;
				case R.id.bottom_naviget_2:
					setNavigetStyle(1, false);
					mDataType.setType("FaJianXiang");
					type = 2;
					break;
				case R.id.bottom_naviget_3:
					setNavigetStyle(2, false);
					mDataType.setType("CaoGaoXiang");
					type = 3;
					break;
				}
				new Thread(mPullDatasThread).start();
			}
		};
        setNavigetStyle(0,true);
	}
	@Override
	public void onResume(){
		super.onResume();
		//seteAdapter();
	}
	
	private EditText mTitleTxt;
	private EditText mReceverTxt;
	private EditText mContentTxt;
	private Button mSendBtn;
	private Button mSaveBtn;
	private void alertNewEmail(final EmailInfo emailInfo){
		LayoutInflater aInflater = LayoutInflater.from(this);
		RelativeLayout view= (RelativeLayout) aInflater.inflate(R.layout.new_email_layout, null);
		mTitleTxt = (EditText)view.findViewById(R.id.emial_title_txt);
		mTitleTxt.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (mTitleTxt.getText().toString().trim().length()<=0) {
					mTitleTxt.setError(IConst.NULL_ERROR_INFO);
				}
			}
		});
		mReceverTxt = (EditText)view.findViewById(R.id.recevier_txt);
		mReceverTxt.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (mReceverTxt.getText().toString().trim().length()<=0) {
					mReceverTxt.setError(IConst.NULL_ERROR_INFO);
				}
			}
		});
		mContentTxt = (EditText)view.findViewById(R.id.email_content_txt);
		if(emailInfo!=null){
			mTitleTxt.setText(emailInfo.mEmailTitle);
			mReceverTxt.setText(emailInfo.mTo);
			mContentTxt.setText(emailInfo.mEmailContent);
		}
		mSendBtn = (Button)view.findViewById(R.id.email_send_btn);
		mSaveBtn = (Button)view.findViewById(R.id.email_save_btn);
		AlertDialog.Builder builder = new Builder(EmailActivity.this);
		if(emailInfo==null){
			builder.setTitle("新建");
			mSaveBtn.setEnabled(true);
		}else{
			builder.setTitle("编辑");
			mSaveBtn.setEnabled(false);
		}
		builder.setView(view);
		builder.setPositiveButton("关闭",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new Thread(mPullDatasThread).start();
			}
		});
		final Dialog dialog = builder.create();
		dialog.show();
		mSendBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				EmailInfo ei = new EmailInfo();
				ei.mEmailTitle = mTitleTxt.getText().toString();
				ei.mFrom = ""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME);
				ei.mTo = ""+mReceverTxt.getText().toString();
				ei.mEmailContent = mContentTxt.getText().toString();
				ei.mDoneDate = new Date();
				if(mTitleTxt.getText().toString().trim().length()<=0){
					Toast.makeText(EmailActivity.this, "标题为空.", Toast.LENGTH_SHORT).show();
					return;
				}else if(mReceverTxt.getText().toString().trim().length()<=0){
					Toast.makeText(EmailActivity.this, "收件人为空.", Toast.LENGTH_SHORT).show();
					return;
				}
				if(emailInfo==null){
					EmailUtil.addOneEmailInfo(ei,""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME),2,4);
				}else{
					EmailUtil.delOneEmailInfo(emailInfo, ""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME));
				}
				for(int i=0;i<mReceverTxt.getText().toString().split(";").length;i++){
					ei = new EmailInfo();
					ei.mEmailTitle = mTitleTxt.getText().toString();
					ei.mFrom = ""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME);
					ei.mTo = ""+mReceverTxt.getText().toString();
					ei.mEmailContent = mContentTxt.getText().toString();
					ei.mDoneDate = new Date();
					EmailUtil.addOneEmailInfo(ei,mReceverTxt.getText().toString().split(";")[i],1,2);
				}
				Toast.makeText(EmailActivity.this, "发送成功.", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				new Thread(mPullDatasThread).start();
			}
		});
		mSaveBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				EmailInfo ei = new EmailInfo();
				ei.mEmailTitle = mTitleTxt.getText().toString();
				ei.mFrom = ""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME);
				ei.mTo = ""+mReceverTxt.getText().toString();
				ei.mEmailContent = mContentTxt.getText().toString();
				ei.mDoneDate = new Date();
				if(mTitleTxt.getText().toString().trim().length()<=0){
					Toast.makeText(EmailActivity.this, "标题为空.", Toast.LENGTH_SHORT).show();
					return;
				}else if(mReceverTxt.getText().toString().trim().length()<=0){
					Toast.makeText(EmailActivity.this, "收件人为空.", Toast.LENGTH_SHORT).show();
					return;
				}
				EmailUtil.addOneEmailInfo(ei,""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME),3,5);
				Toast.makeText(EmailActivity.this, "保存成功.", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				new Thread(mPullDatasThread).start();
			}
		});
	}
	private void alterShowPubInfoDetails(int index){
		if(list==null || list.size()==0 ||index>=list.size())return ;
		EmailInfo info = list.get(index);
		if(type==3){
			alertNewEmail(info);
			return;
		}
		if(type == 1){//change state to read
			EmailUtil.updateEmailState(info, ""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME), 1);
		}
		LayoutInflater aInflater = LayoutInflater.from(this);
		RelativeLayout view= (RelativeLayout) aInflater.inflate(R.layout.read_email_info_layout, null);
		((TextView)view.findViewById(R.id.sender_txt)).setText(info.mFrom==null?"":info.mFrom);
		((TextView)view.findViewById(R.id.email_title_txt)).setText(info.mEmailTitle==null?"":info.mEmailTitle);
		((TextView)view.findViewById(R.id.email_content_txt)).setText(info.mEmailContent==null?"":info.mEmailContent);
		((TextView)view.findViewById(R.id.send_time_txt)).setText(info.mDoneDate==null?"":sdfMore.format(info.mDoneDate));
		AlertDialog.Builder builder = new Builder(EmailActivity.this);
		builder.setTitle("查看");
		builder.setView(view);
		builder.setPositiveButton("关闭",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new Thread(mPullDatasThread).start();
			}
		});
		builder.create().show();
	}
	
	
	private int type = 1;
	private List<EmailInfo> list = null;
	@Override
	protected synchronized void getDatas() {
		mDatasList.clear();
		list = null;
		try {
			EmailUtil piu = new EmailUtil();
			list = piu.getEmailsByUserAndType(GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME)+"",type,mSearchKey);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list==null || list.size()==0)return ;
		for(EmailInfo pi:list){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("ItemSrc",R.drawable.list_title_log);
			String stateStr = "";
			switch(pi.mState){//1:已读,2:未读,3:等待，4：已发送，5：保存
				case 1:stateStr = "已读";break;
				case 2:stateStr = "未读";break;
				case 3:stateStr = "等待";break;
				case 4:stateStr = "已发送";break;
				case 5:stateStr = "保存";break;
			}
			map.put("ItemText",""+pi.mEmailTitle+"["+stateStr+"]");
			mDatasList.add(map);
		}
		mPullDatasSuc = true;
	}
	
	private synchronized void seteAdapter(){
		mAdapter = new SimpleAdapter(this, mDatasList, R.layout.list_item,
				new String[] { "ItemText" }, new int[] { R.id.item_text });
		mListView.setAdapter(mAdapter);
	}
}
