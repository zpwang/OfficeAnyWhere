package com.office.anywher.sehedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.office.anywher.MainActivity;
import com.office.anywher.R;
import com.office.anywher.https.DataType;
import com.office.anywher.utils.DefaultProgress;
/**
 * 协同办公
 * @author Administrator
 *
 */
public class ScheduleActivity extends MainActivity {
	private static final String tag = "ScheduleActivity";
	private LinearLayout aCenterContainer;
	private RelativeLayout aCalenaderlayout;
	private TextView aCalenaderItem[][] = new TextView [6][7];
	private TextView aTitleItem[][] = new TextView [6][7];
	private TextViewState aTextViewState[][] = new TextViewState [6][7];
	private static int[][] aCalendar = new int[6][7];
	private static int[][] aHashSchedul = new int[6][7];
	private static int[] monthDay = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdfMore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<ScheduleInfo> mScheduleInfoList = new ArrayList<ScheduleInfo>();
	private Button aBeforeMonth ;
	private Button aAfterMonth	;
	private TextView aSelectDate;
	private int mCurYear;
	private int mCurMonth;
	private int mToday;
	private int mMonth;
	private int mYear;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aCenterContainer = (LinearLayout) findViewById(R.id.center_content);
        aCenterContainer.removeAllViews();
    	LayoutInflater aInflater = LayoutInflater.from(this);
    	aCalenaderlayout = (RelativeLayout) aInflater.inflate(R.layout.calendar_activity, null);
    	aCenterContainer.addView(aCalenaderlayout);
    	aBeforeMonth = (Button)findViewById(R.id.before_month);
    	aAfterMonth = (Button)findViewById(R.id.after_month);
    	aBeforeMonth.setOnClickListener(new MonthClick());
    	aAfterMonth.setOnClickListener(new MonthClick());
    	aSelectDate = (TextView)findViewById(R.id.shwo_select_date);
    	aPullDataHandler = new PullDatasHandler();
        mDefaultProgress = new DefaultProgress(this);
        new Thread(new PullDatasThread()).start();
        mDefaultProgress.show();
	}
	@Override
	public void onResume(){
		super.onResume();
		initCalendarItem();
		initTitleItem();
    	for(int i=0;i<aCalenaderItem.length;i++){
    		for(int j=0;j<aCalenaderItem[i].length;j++){
    			aCalenaderItem[i][j].setOnClickListener(new DayClick());
    			aTextViewState[i][j] = new TextViewState();
    		}
    	}
    	try {
			initSchedule();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	aSelectDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				 try {
					initSchedule();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
    	});
	}
	private void initSchedule() throws ParseException{
		Calendar c = Calendar.getInstance();
    	mCurYear = c.get(Calendar.YEAR);
		mCurMonth = c.get(Calendar.MONTH)+1;
		mToday = c.get(Calendar.DATE);
		mYear =  c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH)+1;
		aSelectDate.setText(mCurYear+"年"+mCurMonth+"月");
    	initCalendar(mCurYear,mCurMonth);
	}
	class DayClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			int select_i=0,select_j=0;
			int aClickId = v.getId();
			for(int i=0;i<aCalenaderItem.length;i++){
	    		for(int j=0;j<aCalenaderItem[i].length;j++){
	    			if(aCalenaderItem[i][j].getId() == aClickId){
	    				select_i = i;
	    				select_j = j;
	    				aCalenaderItem[i][j].setBackgroundColor(Color.parseColor("#8888FF"));
	    				aTitleItem[i][j].setBackgroundColor(Color.parseColor("#8888FF"));
	    				aTextViewState[i][j].mClickCount++;
	    			}else{
	    				aCalenaderItem[i][j].setBackgroundColor(Color.parseColor(aTextViewState[i][j].mOldColor));
	    				aTitleItem[i][j].setBackgroundColor(Color.parseColor(aTextViewState[i][j].mOldColor));
	    				aTextViewState[i][j].mClickCount = 0;
	    			}
	    		}
	    	}
			if(aTextViewState[select_i][select_j].mClickCount >= 2){
				Toast.makeText(ScheduleActivity.this,"创建新日程:"+mCurYear+"年"+mCurMonth+"月"+((TextView)v).getText(), Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	
	class MonthClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			Calendar c = null;
			switch(v.getId()){
			case R.id.before_month:
				c = Calendar.getInstance();
				Date date = null;
				try {
					date = sdf.parse(mCurYear+"-"+(mCurMonth>=10?mCurMonth:"0"+mCurMonth)+"-01");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.setTime(date);
				c.add(Calendar.MONTH,-1);
				mCurYear = c.get(Calendar.YEAR);
				mCurMonth = c.get(Calendar.MONTH)+1;
				aSelectDate.setText(mCurYear+"年"+mCurMonth+"月");
				try {
					initCalendar(mCurYear,mCurMonth);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				break;
			case R.id.after_month:
				c = Calendar.getInstance();
				date = null;
				try {
					date = sdf.parse(mCurYear+"-"+(mCurMonth>=10?mCurMonth:"0"+mCurMonth)+"-01");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				c.setTime(date);
				c.add(Calendar.MONTH,1);
				mCurYear = c.get(Calendar.YEAR);
				mCurMonth = c.get(Calendar.MONTH)+1;
				aSelectDate.setText(mCurYear+"年"+mCurMonth+"月");
				try {
					initCalendar(mCurYear,mCurMonth);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	private void initCalendar(int year,int month) throws ParseException{
		setEnable();
		createSchedule(year,month);
		int first = 0;
		boolean isStart = false;
		boolean isEnd = false;
		for (int i = 0; i < aCalendar.length; i++) {
			for (int j = 0; j < aCalendar[i].length; j++) {
				aCalenaderItem[i][j].setText(aCalendar[i][j]+"");
				
				if(aCalendar[i][j]==1){
					first++;
				}
				if(aCalendar[i][j]==1 && first==1){isStart=true;}
				if(aCalendar[i][j]==1 && first==2)isEnd=true;
				if(isStart && !isEnd){
					if(aCalendar[i][j] == mToday && mCurYear == mYear && mCurMonth == mMonth){
						String color = "#FFC266";
						aCalenaderItem[i][j].setBackgroundColor(Color.parseColor(color));
						aTitleItem[i][j].setBackgroundColor(Color.parseColor(color));
						aTextViewState[i][j].mOldColor = color;
					}
					else{
						String color = "#FFF5CC";
						aCalenaderItem[i][j].setBackgroundColor(Color.parseColor(color));
						aTitleItem[i][j].setBackgroundColor(Color.parseColor(color));
						aTextViewState[i][j].mOldColor = color;
					}
					String curDateStr = mCurYear+"-"+mCurMonth+"-"+aCalendar[i][j]+" 10:10:10";
					Date curDate = sdfMore.parse(curDateStr);
					if(hasSchedule(curDate,i,j)){
						String color = "#FF3333";
						aCalenaderItem[i][j].setBackgroundColor(Color.parseColor(color));
						aTitleItem[i][j].setBackgroundColor(Color.parseColor(color));
						aTextViewState[i][j].mOldColor = color;
					}
				}else{
					String color = "#FFF8DD";
					aCalenaderItem[i][j].setBackgroundColor(Color.parseColor(color));
					aTitleItem[i][j].setBackgroundColor(Color.parseColor(color));
					aTextViewState[i][j].mOldColor = color;
					aCalenaderItem[i][j].setEnabled(false);
				}
				insertScheduleTitle();
			}
		}
	}
	
	private boolean hasSchedule(Date curDate,int i,int j){
		boolean has = false;
		for(ScheduleInfo si : mScheduleInfoList){
			if(curDate.after(si.mFrom) && curDate.before(si.mTo)){
				has = true;
				aTextViewState[i][j].mScheduleInfo.add(si);
			}
		}
		return has;
	}
	
	private void insertScheduleTitle(){
		for (int i = 0; i < aTextViewState.length; i++) {
			for (int j = 0; j < aTextViewState[i].length; j++) {
				if(aTextViewState[i][j]!=null && aTextViewState[i][j].mScheduleInfo.size()>0){
					int size = aTextViewState[i][j].mScheduleInfo.size();
					String title = aTextViewState[i][j].mScheduleInfo.get(0).mScheduleTitle;
					if(title!=null)title = title.substring(0,2);
					aTitleItem[i][j].setText(title+".."+"(+"+size+")");
				}else{
					aTitleItem[i][j].setText("");
				}
			}
		}
	}
	
	private  void createSchedule(int year,int month){
		for(int ii=0;ii<aTextViewState.length;ii++){
    		for(int jj=0;jj<aTextViewState[ii].length;jj++){
    			aTextViewState[ii][jj].mScheduleInfo.clear();
    		}
    	}
		Calendar calender = Calendar.getInstance();
		try {
			calender.setTime(sdf.parse(year+"-"+(month>=10?month:"0"+month)+"-01"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
		int weekOfMonth =  calender.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		int dayMonth = getMonthDay(year,month);
		int count = 1;
		int start_i = weekOfMonth-1;
		int start_j = dayOfWeek-1;
		/*for(;start_i< aCalendar.length;start_i++){
			for (; start_j < aCalendar[start_i].length; start_j++) {
				if(count<=dayMonth){
					aCalendar[start_i][start_j] = count;
					count++;
				}
			}
			start_j=0;
		}*/
		calender.add(Calendar.MONTH,-1);
		int beforMonth = calender.get(Calendar.MONTH)+1;
		int daybeforeMonth = getMonthDay(year,beforMonth);
		aBeforeMonth.setText(beforMonth+"月");
		calender.add(Calendar.MONTH,2);
		int afterMonth = calender.get(Calendar.MONTH)+1;
		aAfterMonth.setText(afterMonth+"月");
		int dayafter = 1;
		boolean isStart = false;
		class Record {
			public int i;
			public int j;
		}
		List<Record> recordList = new ArrayList<Record>();
		for(int i=0;i< aCalendar.length;i++){
			for (int j=0; j < aCalendar[i].length; j++) {
				if(i==start_i && j==start_j) isStart = true;
				if(isStart){
					if(count<=dayMonth){
						aCalendar[i][j] = count;
						count++;
					}else{
						aCalendar[i][j] = dayafter;
						dayafter++;
					}
				}else{
					Record record = new Record();
					record.i = i;
					record.j = j;
					recordList.add(record);
				}
			}
		}
		int recordCount = recordList.size();
		for(int index=(recordCount-1);index>=0;index--){
			Record record = recordList.get(index);
			aCalendar[record.i][record.j] = daybeforeMonth;
			daybeforeMonth--;
		}
	}
	
	private static int getMonthDay(int year,int month){
		if(year%400==0||(year%4==0&&year%100!=0))
		{ 
			monthDay[1]= 29;
		}else{
			monthDay[1]= 28;
		}
		return monthDay[month-1];
	}

	private void setEnable(){
		for(int i=0;i<aCalenaderItem.length;i++){
    		for(int j=0;j<aCalenaderItem[i].length;j++){
    			aCalenaderItem[i][j].setEnabled(true);
    		}
    	}
	}
	private void initCalendarItem(){
		aCalenaderItem[0][0] =  (TextView)this.findViewById(R.id.first_month_1);
		aCalenaderItem[0][1] =  (TextView)this.findViewById(R.id.first_month_2);
		aCalenaderItem[0][2] =  (TextView)this.findViewById(R.id.first_month_3);
		aCalenaderItem[0][3] =  (TextView)this.findViewById(R.id.first_month_4);
		aCalenaderItem[0][4] =  (TextView)this.findViewById(R.id.first_month_5);
		aCalenaderItem[0][5] =  (TextView)this.findViewById(R.id.first_month_6);
		aCalenaderItem[0][6] =  (TextView)this.findViewById(R.id.first_month_7);
		aCalenaderItem[1][0] =  (TextView)this.findViewById(R.id.second_month_1);
		aCalenaderItem[1][1] =  (TextView)this.findViewById(R.id.second_month_2);
		aCalenaderItem[1][2] =  (TextView)this.findViewById(R.id.second_month_3);
		aCalenaderItem[1][3] =  (TextView)this.findViewById(R.id.second_month_4);
		aCalenaderItem[1][4] =  (TextView)this.findViewById(R.id.second_month_5);
		aCalenaderItem[1][5] =  (TextView)this.findViewById(R.id.second_month_6);
		aCalenaderItem[1][6] =  (TextView)this.findViewById(R.id.second_month_7);
		aCalenaderItem[2][0] =  (TextView)this.findViewById(R.id.third_month_1);
		aCalenaderItem[2][1] =  (TextView)this.findViewById(R.id.third_month_2);
		aCalenaderItem[2][2] =  (TextView)this.findViewById(R.id.third_month_3);
		aCalenaderItem[2][3] =  (TextView)this.findViewById(R.id.third_month_4);
		aCalenaderItem[2][4] =  (TextView)this.findViewById(R.id.third_month_5);
		aCalenaderItem[2][5] =  (TextView)this.findViewById(R.id.third_month_6);
		aCalenaderItem[2][6] =  (TextView)this.findViewById(R.id.third_month_7);
		aCalenaderItem[3][0] =  (TextView)this.findViewById(R.id.four_month_1);
		aCalenaderItem[3][1] =  (TextView)this.findViewById(R.id.four_month_2);
		aCalenaderItem[3][2] =  (TextView)this.findViewById(R.id.four_month_3);
		aCalenaderItem[3][3] =  (TextView)this.findViewById(R.id.four_month_4);
		aCalenaderItem[3][4] =  (TextView)this.findViewById(R.id.four_month_5);
		aCalenaderItem[3][5] =  (TextView)this.findViewById(R.id.four_month_6);
		aCalenaderItem[3][6] =  (TextView)this.findViewById(R.id.four_month_7);
		aCalenaderItem[4][0] =  (TextView)this.findViewById(R.id.five_month_1);
		aCalenaderItem[4][1] =  (TextView)this.findViewById(R.id.five_month_2);
		aCalenaderItem[4][2] =  (TextView)this.findViewById(R.id.five_month_3);
		aCalenaderItem[4][3] =  (TextView)this.findViewById(R.id.five_month_4);
		aCalenaderItem[4][4] =  (TextView)this.findViewById(R.id.five_month_5);
		aCalenaderItem[4][5] =  (TextView)this.findViewById(R.id.five_month_6);
		aCalenaderItem[4][6] =  (TextView)this.findViewById(R.id.five_month_7);
		aCalenaderItem[5][0] =  (TextView)this.findViewById(R.id.six_month_1);
		aCalenaderItem[5][1] =  (TextView)this.findViewById(R.id.six_month_2);
		aCalenaderItem[5][2] =  (TextView)this.findViewById(R.id.six_month_3);
		aCalenaderItem[5][3] =  (TextView)this.findViewById(R.id.six_month_4);
		aCalenaderItem[5][4] =  (TextView)this.findViewById(R.id.six_month_5);
		aCalenaderItem[5][5] =  (TextView)this.findViewById(R.id.six_month_6);
		aCalenaderItem[5][6] =  (TextView)this.findViewById(R.id.six_month_7);
	}
	private void initTitleItem(){
		aTitleItem[0][0] =  (TextView)this.findViewById(R.id.first_title_1);
		aTitleItem[0][1] =  (TextView)this.findViewById(R.id.first_title_2);
		aTitleItem[0][2] =  (TextView)this.findViewById(R.id.first_title_3);
		aTitleItem[0][3] =  (TextView)this.findViewById(R.id.first_title_4);
		aTitleItem[0][4] =  (TextView)this.findViewById(R.id.first_title_5);
		aTitleItem[0][5] =  (TextView)this.findViewById(R.id.first_title_6);
		aTitleItem[0][6] =  (TextView)this.findViewById(R.id.first_title_7);
		aTitleItem[1][0] =  (TextView)this.findViewById(R.id.sec_title_1);
		aTitleItem[1][1] =  (TextView)this.findViewById(R.id.sec_title_2);
		aTitleItem[1][2] =  (TextView)this.findViewById(R.id.sec_title_3);
		aTitleItem[1][3] =  (TextView)this.findViewById(R.id.sec_title_4);
		aTitleItem[1][4] =  (TextView)this.findViewById(R.id.sec_title_5);
		aTitleItem[1][5] =  (TextView)this.findViewById(R.id.sec_title_6);
		aTitleItem[1][6] =  (TextView)this.findViewById(R.id.sec_title_7);
		aTitleItem[2][0] =  (TextView)this.findViewById(R.id.third_title_1);
		aTitleItem[2][1] =  (TextView)this.findViewById(R.id.third_title_2);
		aTitleItem[2][2] =  (TextView)this.findViewById(R.id.third_title_3);
		aTitleItem[2][3] =  (TextView)this.findViewById(R.id.third_title_4);
		aTitleItem[2][4] =  (TextView)this.findViewById(R.id.third_title_5);
		aTitleItem[2][5] =  (TextView)this.findViewById(R.id.third_title_6);
		aTitleItem[2][6] =  (TextView)this.findViewById(R.id.third_title_7);
		aTitleItem[3][0] =  (TextView)this.findViewById(R.id.four_title_1);
		aTitleItem[3][1] =  (TextView)this.findViewById(R.id.four_title_2);
		aTitleItem[3][2] =  (TextView)this.findViewById(R.id.four_title_3);
		aTitleItem[3][3] =  (TextView)this.findViewById(R.id.four_title_4);
		aTitleItem[3][4] =  (TextView)this.findViewById(R.id.four_title_5);
		aTitleItem[3][5] =  (TextView)this.findViewById(R.id.four_title_6);
		aTitleItem[3][6] =  (TextView)this.findViewById(R.id.four_title_7);
		aTitleItem[4][0] =  (TextView)this.findViewById(R.id.five_title_1);
		aTitleItem[4][1] =  (TextView)this.findViewById(R.id.five_title_2);
		aTitleItem[4][2] =  (TextView)this.findViewById(R.id.five_title_3);
		aTitleItem[4][3] =  (TextView)this.findViewById(R.id.five_title_4);
		aTitleItem[4][4] =  (TextView)this.findViewById(R.id.five_title_5);
		aTitleItem[4][5] =  (TextView)this.findViewById(R.id.five_title_6);
		aTitleItem[4][6] =  (TextView)this.findViewById(R.id.five_title_7);
		aTitleItem[5][0] =  (TextView)this.findViewById(R.id.six_title_1);
		aTitleItem[5][1] =  (TextView)this.findViewById(R.id.six_title_2);
		aTitleItem[5][2] =  (TextView)this.findViewById(R.id.six_title_3);
		aTitleItem[5][3] =  (TextView)this.findViewById(R.id.six_title_4);
		aTitleItem[5][4] =  (TextView)this.findViewById(R.id.six_title_5);
		aTitleItem[5][5] =  (TextView)this.findViewById(R.id.six_title_6);
		aTitleItem[5][6] =  (TextView)this.findViewById(R.id.six_title_7);
	}
	/**
	 * get schedule
	 * @throws ParseException 
	 */
	private void getDatas() throws ParseException{
		String from = "2012-10-10 17:01:01";
		String to = "2012-10-20 22:01:01";
		ScheduleInfo aSchedule = new ScheduleInfo();
		aSchedule.mScheduleTitle = "我的日程测试数据";
		aSchedule.mScheduleDetails = "我的日程测试数据我的日程测试数据我的日程测试数据我的日程测试数据我的日程测试数据我的日程测试数据";
		Calendar c = Calendar.getInstance();
		c.setTime(sdfMore.parse(from));
		aSchedule.mFrom = sdfMore
				.parse(c.get(Calendar.YEAR)
						+ "-"
						+ ((c.get(Calendar.MONTH) + 1) >= 10 ? c
								.get(Calendar.MONTH) + 1 : "0"
								+ (c.get(Calendar.MONTH) + 1))
						+ "-"
						+ (c.get(Calendar.DATE) >= 10 ? c.get(Calendar.DATE)
								: "0" + c.get(Calendar.DATE))+" "+"00:01:01");
		c.setTime(sdfMore.parse(to));
		aSchedule.mTo = sdfMore
				.parse(c.get(Calendar.YEAR)
						+ "-"
						+ ((c.get(Calendar.MONTH) + 1) >= 10 ? c
								.get(Calendar.MONTH) + 1 : "0"
								+ (c.get(Calendar.MONTH) + 1))
						+ "-"
						+ (c.get(Calendar.DATE) >= 10 ? c.get(Calendar.DATE)
								: "0" + c.get(Calendar.DATE))+" "+"23:59:59");;
				
	  mScheduleInfoList.add(aSchedule);

	}

	class TextViewState{
		public String mOldColor;
		public int mClickCount;
		public boolean mHasSchedule;		
		public List<ScheduleInfo>  mScheduleInfo = new ArrayList<ScheduleInfo>();;
	}
	protected PullDatasHandler aPullDataHandler;
	protected DefaultProgress mDefaultProgress;
	class PullDatasHandler extends Handler{
		public PullDatasHandler(){}
		public PullDatasHandler(Looper l){
			super(l);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			switch(msg.what){
			case 0:
				mDefaultProgress.hidden();
				Toast.makeText(ScheduleActivity.this, "数据加载失败!", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				mDefaultProgress.hidden();
				break;
			}
		}
	}
	class PullDatasThread implements Runnable {
		private DataType mDataType;
		public PullDatasThread(DataType dataType){
			mDataType = dataType;
		}
		public PullDatasThread(){}
		@Override
		public void run() {
			boolean mPullDatasSuc = true;
			try {
				getDatas();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				mPullDatasSuc = false;
				e.printStackTrace();
			} catch (ParseException e) {
				mPullDatasSuc = false;
				e.printStackTrace();
			}
			Message msg = new Message();
			if(mPullDatasSuc)msg.what =1;
			else msg.what = 0;
			aPullDataHandler.sendMessage(msg);
		}
	}
	
}