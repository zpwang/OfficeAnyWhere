package com.office.anywher.global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.office.anywher.IConst;
import com.office.anywher.email.EmailInfo;
import com.office.anywher.sehedule.ScheduleInfo;

/**
 * unique variable single model
 * 
 * @author Administrator
 * 
 */
public class GlobalVar {
	private static Map<String, Object> mVar = new HashMap<String, Object>();
	private static GlobalVar mGlobalVar = new GlobalVar();

	private GlobalVar() {
		initEmailDatas();
		try {
			initSchedule();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public synchronized static GlobalVar getInstance() {
		return mGlobalVar;
	}

	public synchronized void put(String key, Object obj) {
		mVar.put(key, obj);
	}

	public synchronized Object get(String key) {
		if (mVar.containsKey(key)) {
			return mVar.get(key);
		}
		return null;
	}

	private static void initEmailDatas() {
		List<EmailInfo> aEmails = new ArrayList<EmailInfo>();
		int key = 1;
		for (int i = 0; i < 10; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailId = key;
			aEmail.mEmailTitle = "我的收件箱测试数据"+i;
			aEmail.mEmailContent = "\t我的收件箱测试数据,我的收件箱测试数据\n我的收件箱测试数据,我的收件箱测试数据.";
			aEmail.mType = 1;
			aEmail.mFrom = "A";
			aEmail.mTo = "B;C;D";
			key ++;
			aEmails.add(aEmail);
		}
		for (int i = 0; i < 6; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailId = key;
			aEmail.mEmailTitle = "我的发件箱测试数据"+i;
			aEmail.mEmailContent = "\t我的发件箱测试数据,我的发件箱测试数据\n我的发件箱测试数据,我的发件箱测试数据.";
			aEmail.mType = 1;
			aEmail.mFrom = "A";
			aEmail.mTo = "B;C;D";
			key ++;
			aEmails.add(aEmail);
		}
		for (int i = 0; i < 8; i++) {
			EmailInfo aEmail = new EmailInfo();
			aEmail.mDoneDate = new Date();
			aEmail.mEmailId = key;
			aEmail.mEmailTitle = "我的草稿箱测试数据"+i;
			aEmail.mEmailContent = "\t我的草稿箱测试数据,我的草稿箱测试数据\n我的草稿箱测试数据,我的草稿箱测试数据.";
			aEmail.mType = 1;
			aEmail.mFrom = "A";
			aEmail.mTo = "B;C;D";
			key ++;
			aEmails.add(aEmail);
		}
		mVar.put(IConst.EMAIL_DEMO_DATA,aEmails);
	}
	
	private void initSchedule() throws ParseException{
		List<ScheduleInfo> mScheduleInfoList = new ArrayList<ScheduleInfo>();
		SimpleDateFormat sdfMore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int day = 1;
		String from = "2012-10-10 17:01:01";
		String to = "2012-10-20 22:01:01";
		for(int i=0;i<13;i++){
			from = "2012-10-"+(day>10?day:"0"+day)+" 17:01:01";
			to = "2012-10-"+(day>10?day:"0"+day)+" 22:01:01";
			ScheduleInfo aSchedule = new ScheduleInfo();
			aSchedule.mScheduleTitle = ""+i+"我的日程测试数据"+i;
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
									: "0" + c.get(Calendar.DATE))+" "+"23:59:59");
			day+=2;
			mScheduleInfoList.add(aSchedule);
		}
		mVar.put(IConst.SCHEDUL_DEMO_DATA, mScheduleInfoList);
	}
}
