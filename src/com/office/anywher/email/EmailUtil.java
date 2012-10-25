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
	public static void updateEmailState(int id,int type){
		
	}
	
	public static List<EmailInfo> getEmailsByUserAndType(String user,int type){
		List<EmailInfo> rtn = new ArrayList<EmailInfo>(); 
		Map<String,List<EmailInfo>> emails = (Map<String,List<EmailInfo>>)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA);
		List<EmailInfo> byUser = emails.get(user);
		for(EmailInfo ei:byUser){
			if(ei.mType == type){
				rtn.add(ei);
			}
		}
		return rtn;
	}
	
}
