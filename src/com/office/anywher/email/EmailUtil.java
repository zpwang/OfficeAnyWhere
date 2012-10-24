package com.office.anywher.email;

import java.util.List;

import com.office.anywher.IConst;
import com.office.anywher.global.GlobalVar;

public class EmailUtil {

	public static int getEmailNumber(int type){
		int count = 0;
		List<EmailInfo> emails = ((List<EmailInfo>)GlobalVar.getInstance().get(IConst.EMAIL_DEMO_DATA));
		for(EmailInfo ei:emails){
			if(ei.mState == type){
				count ++;
			}
		}
		return count;
	}
	
	public static void updateEmailState(int id,int type){
		
	}
	
}
