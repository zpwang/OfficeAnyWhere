package com.office.anywher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.office.anywher.address.AddressActivity;
import com.office.anywher.datas.DatasActivity;
import com.office.anywher.email.EmailActivity;
import com.office.anywher.email.EmailUtil;
import com.office.anywher.global.GlobalVar;
import com.office.anywher.infopub.InfoPubActivity;
import com.office.anywher.meeting.MeetingActivity;
import com.office.anywher.offcial.ActionTankenActivity;
import com.office.anywher.sehedule.ScheduleActivity;
import com.office.anywher.sehedule.ScheduleInfo;
import com.office.anywher.utils.ActivityStackUtil;
import com.office.anywher.views.SelfGridView;

public class MainActivity extends Activity {

	private static final String tag = "MainActivity";
	protected static final String aGridViewItemImage = "ItemImage";
	protected static final String aGridViewItemText = "ItemText";
	protected static final String aGridViewItemNumber = "ItemNewNumber";
	protected SimpleAdapter adapter;
    private ImageView aMenuButton;
    private ScrollView aLeftNaviget;
    private SelfGridView aSelfGridView;
    private Button aSingOut;
    private TextView aWellcome;
	protected List<HashMap<String,Object>> aMenuDatas;
	protected List<HashMap<String,Object>> aLeftMenuDatas;
    private static AnimationSet aLeftNavShowActionset = new  AnimationSet(true);   
    private static AnimationSet aLeftNavHiddenActionset = new  AnimationSet(true);
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackUtil.add(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        aMenuButton = (ImageView)findViewById(R.id.menu_button);
        aLeftNaviget = (ScrollView)findViewById(R.id.navigate_left);
        aSingOut = (Button)findViewById(R.id.sing_out);
        aWellcome = (TextView)findViewById(R.id.welcome_text);
        aWellcome.setText(IConst.WELL_COME+","+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME));
        setLeftNavAction();
        initMenuDatas();
        initLeftMenuDatas();
		adapter = new SimpleAdapter(this, aMenuDatas,
				R.layout.nine_dial_gv_item, new String[] {aGridViewItemImage,
						aGridViewItemText,aGridViewItemNumber }, new int[] { R.id.gv_item_image,
						R.id.gv_item_text,R.id.gv_item_new_number });
		SimpleAdapter leftAdapter = new SimpleAdapter(this, aLeftMenuDatas,
				R.layout.nine_dial_gv_item, new String[] {aGridViewItemImage,
						aGridViewItemText }, new int[] { R.id.gv_item_image,
						R.id.gv_item_text });
		aSelfGridView = (SelfGridView)findViewById(R.id.grid_view_left_nv);        
		aSelfGridView.setAdapter(leftAdapter);
		aSelfGridView.setOnItemClickListener(LeftItemOnClick);		
        aMenuButton.setOnClickListener(new OnClickListener(){
			boolean leftNavShow  = false;
        	@Override
			public void onClick(View v) {
        		leftNavShow = !leftNavShow;	
        		if(leftNavShow){
        			aLeftNaviget.startAnimation(aLeftNavShowActionset);
        			aLeftNaviget.setVisibility(View.VISIBLE);
        			aMenuButton.setImageResource(R.drawable.hidden);
        		}else{
					aLeftNaviget.startAnimation(aLeftNavHiddenActionset);
					aLeftNaviget.setVisibility(View.GONE);
					aMenuButton.setImageResource(R.drawable.expand);
				}							
			}        	
        });
        aSingOut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new Builder(MainActivity.this);
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
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { 
        case R.id.change_user:
        	Intent intent = new Intent();
        	Bundle bundle = new Bundle();
        	bundle.putString(IConst.USER_CHANGE, "Y");
        	intent.putExtras(bundle);
        	intent.setClass(this, LoginActivity.class);
        	this.startActivity(intent);
        	break;
        }
        return true;
    }
   
    
    protected void initMenuDatas(){
		aMenuDatas = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_gongwenchuli);
		map.put(aGridViewItemText,"");
		map.put(aGridViewItemNumber, "2");
		aMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_youxiangguanli);
		map.put(aGridViewItemText,"");
		map.put(aGridViewItemNumber,EmailUtil.getEmailNumber(""+GlobalVar.getInstance().get(IConst.LOGIN_USER_NAME),1));
		aMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_richenganpai);
		map.put(aGridViewItemNumber,((List<ScheduleInfo>)GlobalVar.getInstance().get(IConst.SCHEDUL_DEMO_DATA)).size());
		aMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_huiyiguanli);
		map.put(aGridViewItemText,"");
		map.put(aGridViewItemNumber,"20");
		aMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_xinxifabu);
		map.put(aGridViewItemText,"");
		map.put(aGridViewItemNumber,"9");
		aMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_ziliaoku);
		map.put(aGridViewItemText,"");
		map.put(aGridViewItemNumber,"20");
		aMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_tongxunlu);
		map.put(aGridViewItemText,"");
		map.put(aGridViewItemNumber,"12");
		aMenuDatas.add(map);
		
	}
    
    protected void initLeftMenuDatas(){
		aLeftMenuDatas = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.home_log);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_gongwenchuli);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_youxiangguanli);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_richenganpai);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_huiyiguanli);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_xinxifabu);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_ziliaoku);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
		map = new HashMap<String,Object>();
		map.put(aGridViewItemImage, R.drawable.n_tongxunlu);
		map.put(aGridViewItemText,"");
		aLeftMenuDatas.add(map);
		
	}
	
	public OnItemClickListener ItemOnClick = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch(arg2){
			case 0:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ActionTankenActivity.class);
				startActivity(intent);
				break;
			case 1:
				intent = new Intent();
				intent.setClass(MainActivity.this, EmailActivity.class);
				startActivity(intent);
				break;
			case 2:
				intent = new Intent();
				intent.setClass(MainActivity.this, ScheduleActivity.class);
				startActivity(intent);
				break;
			case 3:
				intent = new Intent();
				intent.setClass(MainActivity.this, MeetingActivity.class);
				startActivity(intent);
				break;
			case 4:
				intent = new Intent();
				intent.setClass(MainActivity.this, InfoPubActivity.class);
				startActivity(intent);
				break;
			case 5:
				intent = new Intent();
				intent.setClass(MainActivity.this, DatasActivity.class);
				startActivity(intent);
				break;
			case 6:
				intent = new Intent();
				intent.setClass(MainActivity.this, AddressActivity.class);
				startActivity(intent);
				break;
			}
		}};
    
		
		public OnItemClickListener LeftItemOnClick = new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				case 0:
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, NavigetActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent();
					intent.setClass(MainActivity.this, ActionTankenActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent();
					intent.setClass(MainActivity.this, EmailActivity.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent();
					intent.setClass(MainActivity.this, ScheduleActivity.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent();
					intent.setClass(MainActivity.this, MeetingActivity.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent();
					intent.setClass(MainActivity.this, InfoPubActivity.class);
					startActivity(intent);
					break;
				case 6:
					intent = new Intent();
					intent.setClass(MainActivity.this, DatasActivity.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent();
					intent.setClass(MainActivity.this, AddressActivity.class);
					startActivity(intent);
					break;
				}
			}};
	private void setLeftNavAction() {
		Animation alphaAnimShwo = new AlphaAnimation(0.6f,1.0f);
		//Animation translateAnimShow = new TranslateAnimation(0f,100f,0f,0f);
		//Animation scaleAnimationShwo = new ScaleAnimation(0.1f, 1.0f,0.1f,1.0f);  
		aLeftNavShowActionset.addAnimation(alphaAnimShwo);
		//aLeftNavShowActionset.addAnimation(translateAnimShow);
		//aLeftNavShowActionset.addAnimation(scaleAnimationShwo);
		aLeftNavShowActionset.setDuration(200);
		Animation alphaAnimHidden = new AlphaAnimation(1.0f,0.6f);
		//Animation translateAnimHidden = new TranslateAnimation(100f,0f,0f,0f);
		//Animation scaleAnimationHidden = new ScaleAnimation(1.0f, 0.0f,1.0f,0.0f);   
		aLeftNavHiddenActionset.addAnimation(alphaAnimHidden);
		//aLeftNavHiddenActionset.addAnimation(translateAnimHidden);
		//aLeftNavHiddenActionset.addAnimation(scaleAnimationHidden);
		aLeftNavHiddenActionset.setDuration(100);
	}
}
