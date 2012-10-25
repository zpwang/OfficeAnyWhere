package com.office.anywher.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.office.anywher.IConst;
import com.office.anywher.global.GlobalVar;

public class EmailUtil {

	public static int getEmailNumber(String user,int type){
		int count = 0;	
		Map<String,List<EmailInfo>> emails = (Map<String,List<EmailInfo>>)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA);
		List<EmailInfo> byUser = emails.get(user);
		if(byUser==null)return 0;
		for(EmailInfo ei:byUser){
			if(ei.mState == type){
				count ++;
			}
		}
		return count;
	}
	public static void updateEmailState(EmailInfo ei,String user,int state){
		Map<String,List<EmailInfo>> emails = (Map<String,List<EmailInfo>>)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA);
		List<EmailInfo> userList = emails.get(user);
		for(EmailInfo email:userList){
			if(email.mEmailId == ei.mEmailId){
				email.mState = state;
			}
		}
		emails.put(user, userList);
	}
	public static List<EmailInfo> getEmailsByUserAndType(String user,int type,String key){
		List<EmailInfo> rtn = new ArrayList<EmailInfo>(); 
		Map<String,List<EmailInfo>> emails = (Map<String,List<EmailInfo>>)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA);
		List<EmailInfo> byUser = emails.get(user);
		for(EmailInfo ei:byUser){
			if(ei.mType == type){
				if(key==null || key.trim().length()<=0)rtn.add(ei);
				if(key!=null && key.trim().length()>0 && (ei.mEmailTitle.contains(key)||ei.mEmailContent.contains(key))){
					rtn.add(ei);
				}
			}
		}
		return rtn;
	}
	
	public static void addOneEmailInfo(EmailInfo ei,String user,int type,int state){
		Map<String,List<EmailInfo>> emails = (Map<String,List<EmailInfo>>)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA);
		List<EmailInfo> userList = emails.get(user);
		ei.mState = state;
		ei.mType = type;
		userList.add(ei);
		emails.put(user, userList);
	}
	
	public static void delOneEmailInfo(EmailInfo ei,String user){
		Map<String,List<EmailInfo>> emails = (Map<String,List<EmailInfo>>)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA);
		List<EmailInfo> userList = emails.get(user);
		int index = 0;
		for(EmailInfo email:userList){
			if(email.mType == 3 && email.mEmailId == ei.mEmailId){
				break;
			}
			index++;
		}
		userList.remove(index);
		emails.put(user, userList);
	}
	
	
}
