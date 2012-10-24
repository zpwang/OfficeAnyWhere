package com.office.anywher;

import com.office.anywher.utils.ActivityStackUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;

public class WelcomeActivity extends Activity{
	
	private WelComeHandler welcomeHahdler ; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		welcomeHahdler = new WelComeHandler();
		setContentView(R.layout.welcome_activity);
		new Thread(timer).start();
	}
	
	Runnable timer = new Runnable(){
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			while(true){
				if((System.currentTimeMillis() - start)/1000 >=4){
					Message msg = new Message();
					msg.what = 1;
					welcomeHahdler.sendMessage(msg);
					break;
				}
			}
		}
	};
	
	class WelComeHandler extends Handler {
		public WelComeHandler() {
		}

		public WelComeHandler(Looper l) {
			super(l);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			switch (msg.what) {
			case 1:
				Intent intent = new Intent();
				intent.setClass(WelcomeActivity.this, LoginActivity.class);
				WelcomeActivity.this.startActivity(intent);
				break;
			}
		}
	}
}
