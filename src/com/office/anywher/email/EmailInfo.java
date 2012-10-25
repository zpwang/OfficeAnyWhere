package com.office.anywher.email;

import java.util.Date;

public class EmailInfo {

	public int mEmailId;
	public String mEmailTitle;
	public String mEmailContent;
	public String mFrom;
	public String mTo;
	public int mState;/*1:已读,2:未读,3:等待，4：已发送，5：保存*/
	public int mType;/*1:收件箱2:发件箱3:草稿箱*/
	public Date mDoneDate;
	
}
