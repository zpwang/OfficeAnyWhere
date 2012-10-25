package com.office.anywher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.office.anywher.https.DataType;
import com.office.anywher.utils.ActivityStackUtil;
import com.office.anywher.utils.DefaultProgress;

public abstract class ListActivity extends MainActivity {
	private static final String tag = "ListActivity";
	protected ListView mListView;
	private LinearLayout aCenterContainer;
	protected List<HashMap<String, Object>> mDatasList = new ArrayList<HashMap<String, Object>>();
	protected SimpleAdapter mAdapter;
	protected DataType mDataType = new DataType("test url", "default");
	protected boolean mPullDatasSuc;
	protected PullDatasHandler aPullDataHandler;
	protected DefaultProgress mDefaultProgress;
	protected PullDatasThread mPullDatasThread;
	protected View mCurClickView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityStackUtil.add(this);
		initBottomNaviget();
		aPullDataHandler = new PullDatasHandler();
		mPullDatasThread = new PullDatasThread();
		mDefaultProgress = new DefaultProgress(this);
		getDatas();
		LayoutInflater aInflater = LayoutInflater.from(this);
		mListView = (ListView) aInflater.inflate(R.layout.list_view, null);
		aCenterContainer = (LinearLayout) findViewById(R.id.center_content);
		aCenterContainer.removeAllViews();	
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		aCenterContainer.addView(mListView, lp);		
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter = new SimpleAdapter(this, mDatasList, R.layout.list_item,
				new String[] { "ItemText" }, new int[] { R.id.item_text });
		mListView.setAdapter(mAdapter);
	}

	private void initBottomNaviget(){
		aNavs[0] = (TextView)findViewById(R.id.bottom_naviget_1);
		aNavs[1] = (TextView)findViewById(R.id.bottom_naviget_2);
		aNavs[2] = (TextView)findViewById(R.id.bottom_naviget_3);
		aNavs[3] = (TextView)findViewById(R.id.bottom_naviget_4);
		aNavs[4] = (TextView)findViewById(R.id.bottom_naviget_5);
	}
	
	protected synchronized void setDataApater() {
		mAdapter = null;
		mAdapter = new SimpleAdapter(this, mDatasList, R.layout.list_item,
				new String[] {"ItemSrc", "ItemText" }, new int[] { R.id.item_image,R.id.item_text });
		mListView.setAdapter(mAdapter);
	}

	protected abstract void getDatas();

	class PullDatasHandler extends Handler {
		public PullDatasHandler() {
		}

		public PullDatasHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			switch (msg.what) {
			case 0:
				mDefaultProgress.hidden();
				Toast.makeText(ListActivity.this, " ˝æ›º”‘ÿ ß∞‹!", Toast.LENGTH_SHORT)
						.show();
				if (mCurClickView != null)
					mCurClickView.setEnabled(true);
				break;
			case 1:
				mDefaultProgress.hidden();
				setDataApater();
				if (mCurClickView != null)
					mCurClickView.setEnabled(true);
				break;
			}
		}
	}

	class PullDatasThread implements Runnable {
		private DataType mDataType;

		public PullDatasThread(DataType dataType) {
			mDataType = dataType;
		}

		public PullDatasThread() {
		}

		@Override
		public void run() {
			getDatas();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			Log.d(tag, "mPullDatasSuc" + mPullDatasSuc);
			if (mPullDatasSuc)
				msg.what = 1;
			else
				msg.what = 0;
			mPullDatasSuc = false;
			aPullDataHandler.sendMessage(msg);
		}
	}

	protected TextView[] aNavs = new TextView[5];
	protected OnClickListener clickListener;
	protected String[] aBottomNav ;
	protected void setNavigetStyle(int index, boolean init) {
		for (int i = 0; i < aNavs.length; i++) {
			if(aNavs[i]==null) continue;
			aNavs[i].setVisibility(View.VISIBLE);
			aNavs[i].setText(aBottomNav[i]);
			if (init && !aBottomNav[i].equals(""))
				aNavs[i].setOnClickListener(clickListener);
			if (i != index) {
				aNavs[i].setBackgroundResource(R.drawable.bottom_button_bg_out);
				aNavs[i].setTextColor(Color
						.parseColor(IConst.NavigetText.CLICKTEXTCOLOR[0]));
				aNavs[i].setTextSize(IConst.NavigetText.CLICKTEXTSIZE[0]);
			} else {
				aNavs[i].setBackgroundResource(R.drawable.bottom_button_bg_in);
				aNavs[i].setTextColor(Color
						.parseColor(IConst.NavigetText.CLICKTEXTCOLOR[1]));
				aNavs[i].setTextSize(IConst.NavigetText.CLICKTEXTSIZE[1]);
			}
		}
		if (init) {
			new Thread(mPullDatasThread).start();
			mDefaultProgress.show();
		}
	}
}
