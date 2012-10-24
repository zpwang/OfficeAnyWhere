package com.office.anywher.infopub;

import java.util.Date;

public class PubInfo {

	public String mTitle;
	public String mContent;
	public String mCreatePerson;
	public String mDepartment;
	public Date mpubDate;
	public int mPubState;/*1-新建 2-待审批 3-审批未通过 4-待发布 5-已发布*/
	public int mValid;/*1-有效 2-无效*/
	
}
