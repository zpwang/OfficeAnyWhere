package com.office.anywher.infopub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PubInfoUtil {

	private List<PubInfo> mPubInfo = new ArrayList<PubInfo>();

	public PubInfoUtil() {
		for (int i = 0; i < 3; i++) {
			PubInfo pi = new PubInfo();
			pi.mCreatePerson = "A";
			pi.mTitle = "A发布的测试信息";
			pi.mValid = 1;
			pi.mPubState = 1;
			pi.mContent = "\t发布的测试信息,发布的测试信息,发布的测试信息发布的测试信息发布的测试信息";
			pi.mpubDate = new Date();
			pi.mDepartment = "A归属部门";
			mPubInfo.add(pi);
		}
		for (int i = 0; i < 3; i++) {
			PubInfo pi = new PubInfo();
			pi.mCreatePerson = "B";
			pi.mTitle = "B发布的测试信息";
			pi.mValid = 2;
			pi.mPubState = 5;
			pi.mContent = "\t发布的测试信息,发布的测试信息,发布的测试信息发布的测试信息发布的测试信息";
			pi.mpubDate = new Date();
			pi.mDepartment = "B归属部门";
			mPubInfo.add(pi);
		}

		for (int i = 0; i < 3; i++) {
			PubInfo pi = new PubInfo();
			pi.mCreatePerson = "B";
			pi.mTitle = "B发布的测试信息";
			pi.mContent = "\t发布的测试信息,发布的测试信息,发布的测试信息发布的测试信息发布的测试信息";
			pi.mValid = 1;
			pi.mPubState = 3;
			pi.mpubDate = new Date();
			pi.mDepartment = "B归属部门";
			mPubInfo.add(pi);
		}
	}
	
	public List<PubInfo> getPubInfoByUser(String title, String createPerson,
			Date from, Date to, int pubState, int valid) {
		boolean pass = true;
		List<PubInfo> aPubList = new ArrayList<PubInfo>();
		for (PubInfo pi : mPubInfo) {
			if (title != null && !title.equals("")) {
				if (pi.mTitle.contains(title)) {
					pass = true;
				}else{
					pass = false;
				}
			} else if (createPerson != null && !createPerson.equals("")) {
				if (pi.mCreatePerson.contains(createPerson)) {
					pass = true;
				}else{
					pass = false;
				}
			}else if(pubState!=0){
				if(pi.mPubState == pubState){
					pass = true;
				}else{
					pass = false;
				}
			}else if(valid !=0){
				if(pi.mValid == valid){
					pass = true;
				}else{
					pass = false;
				}
			}else if(from!=null && to!=null){
				if(pi.mpubDate.after(from) && pi.mpubDate.before(to)){
					pass = true;
				}else{
					pass = false;
				}
			}
			if(pass == true)
				aPubList.add(pi);
		}
		return aPubList;
	}

	public List<PubInfo> getAddressList() {
		return mPubInfo;
	}
}
