package com.office.anywher;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.office.anywher.db.Login;
import com.office.anywher.db.LoginDao;
import com.office.anywher.global.GlobalVar;
import com.office.anywher.https.HttpClient;
import com.office.anywher.https.JsonBean;
import com.office.anywher.utils.ActivityStackUtil;
import com.office.anywher.utils.DefaultProgress;

public class LoginActivity extends Activity {
	private static final String tag = "LoginActivity";
	private static final String M_STATE = "M_STATE";
	private Button aLoginButton;
	private CheckBox aRemember;
	private EditText aUser;
	private EditText aPwd;
	private boolean aLoginSuccess =  true;
	private LoginHandler aLoginHandler;
	private DefaultProgress aDefaultProgress;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        aLoginButton = (Button)findViewById(R.id.login_button);
        aRemember = (CheckBox)findViewById(R.id.remember_me);
        aUser = (EditText)findViewById(R.id.login_user_name);
        aPwd = (EditText)findViewById(R.id.login_user_pwd);
        aDefaultProgress =new DefaultProgress(this);
        aLoginHandler = new LoginHandler();
        aLoginButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View paramView) {
				if(aUser.getText().toString().trim().equals("")||aPwd.getText().toString().trim().equals("")){
					Toast.makeText(LoginActivity.this, "用户名或密码为空!", Toast.LENGTH_SHORT).show();
					//return;
				}
				dialog();
				new Thread(new LoginThread(aUser.getText().toString().trim(),aPwd.getText().toString().trim())).start();
			}
        });
        Intent intent = this.getIntent();
        String userChange = intent.getStringExtra(IConst.USER_CHANGE);
        LoginDao dao = new LoginDao(this);
		Login login = dao.getLogin("");
		dao.close();
		if((null==userChange || !userChange.equals("Y")) && null!=login && login.mLoginState.equals("Y")){
			aRemember.setChecked(true);
			aUser.setText(login.mUser);
			aPwd.setText(login.mPwd);
			dialog();
			new Thread(new LoginThread(aUser.getText().toString().trim(),aPwd.getText().toString().trim())).start();
		}
	}
	@Override
	public void onStop() {
		super.onStop();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			AlertDialog.Builder builder = new Builder(LoginActivity.this);
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
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void store(){
		LoginDao dao = new LoginDao(this);
		Login login = new Login();
		if (aRemember.isChecked()) {
			login.mUser = aUser.getText().toString().trim();
			login.mPwd = aUser.getText().toString().trim();
			login.mLoginState = "Y";
			login.mOldState = false;
			dao.save(login);
		} else {
			login.mUser = aUser.getText().toString().trim();
			login.mPwd = aUser.getText().toString().trim();
			login.mLoginState = "N";
			login.mOldState = false;
			dao.save(login);
		}
		dao.close();
	}
	
	private void dialog() {
		aDefaultProgress = new DefaultProgress(this);
		aDefaultProgress.show();
	}
	
	
	class LoginHandler extends Handler{
		public LoginHandler(){}
		public LoginHandler(Looper l){
			super(l);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			int state = b.getInt(M_STATE);
			switch(msg.what){
			case 0:
				aDefaultProgress.hidden();
				Toast.makeText(LoginActivity.this, "登录失败!", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				aDefaultProgress.hidden();
				store();
				Intent intent = new Intent();
			    intent.setClass(LoginActivity.this, NavigetActivity.class);
			    startActivity(intent);
				break;
			}
		}
	}
	class LoginThread implements Runnable {
		private String userName;
		private String password;
		public LoginThread(String user,String pwd){
			userName = user;
			password = pwd;
		}
		@Override
		public void run() {
			aLoginSuccess = login(userName,password);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = new Message();
			if(aLoginSuccess)msg.what =1;
			else msg.what = 0;
			GlobalVar.getInstance().put(IConst.LOGIN_USER_NAME, userName);
			aLoginHandler.sendMessage(msg);
		}
	}
	
	private synchronized boolean login(String userName,String password){
		/*String url = "http://10.0.2.2:8080/war/jsp/dcwork/mobile/querydocdaibanlist.jsp"; 
		JsonBean[] jsonBeans = new JsonBean[]{new JsonBean("loginId","a")};
		try {
			Log.d(tag,HttpClient.request(jsonBeans, url).toString());
			return true;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return true;
	}
}
