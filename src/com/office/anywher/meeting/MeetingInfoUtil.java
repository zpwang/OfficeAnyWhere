package com.office.anywher.meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingInfoUtil {

	private List<MeetingInfo> list = new ArrayList<MeetingInfo>();
	public MeetingInfoUtil(){
		for(int i=0;i<20;i++){
			MeetingInfo mi = new MeetingInfo();
			mi.mTitle = "测试最新会议管理数据"+i;
			mi.mContent = "测试最新会议管理数据,测试会议管理数据,测试会议管理数据,测试会议管理数据,测试会议管理数据";
			mi.mFrom = new Date();
			mi.mTo = new Date();
			mi.mRoom = "会议室"+i;
			mi.mDep = "Dep"+i;
			mi.mParter = "A;B;C;D;E";
			mi.mType = 1;
			if(i%2==0)
				mi.mValid = 1;
			else
				mi.mValid = 2;
			list.add(mi);
		}
		for(int i=0;i<16;i++){
			MeetingInfo mi = new MeetingInfo();
			mi.mTitle = "测试历史会议管理数据"+i;
			mi.mContent = "测试历史会议管理数据,测试会议管理数据,测试会议管理数据,测试会议管理数据,测试会议管理数据";
			mi.mFrom = new Date();
			mi.mTo = new Date();
			mi.mDep = "Dep"+i;
			mi.mRoom = "会议室"+i;
			mi.mParter = "A;B;C;D;E";
			mi.mType = 2;
			if(i%2==0)
				mi.mValid = 1;
			else
				mi.mValid = 2;
			list.add(mi);
		}
	}
	
	public List<MeetingInfo> getMeetfoByUser(String title,String room,Date from, Date to, int valid,int type) {
		boolean pass = true;
		List<MeetingInfo> aDataList = new ArrayList<MeetingInfo>();
		for (MeetingInfo pi : list) {
			if(type != 0){
				if(pi.mType == type)
					pass = true;
				else
					pass = false;
			}else if (title != null && !title.equals("")) {
				if (pi.mTitle.contains(title)) {
					pass = true;
				}else{
					pass = false;
				}
			} else if (room != null && !room.equals("")) {
				if (pi.mRoom.contains(room)) {
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
				if(pi.mFrom.after(from) && pi.mFrom.before(to)){
					pass = true;
				}else{
					pass = false;
				}
			}
			if(pass == true)
				aDataList.add(pi);
		}
		return aDataList;
	}
}
