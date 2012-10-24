package com.office.anywher.datas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatasUtil {

	private List<DataInfo> list = new ArrayList<DataInfo>();
	public DatasUtil(){
		for(int i=0;i<10;i++){
			DataInfo di = new DataInfo();
			di.mTitle = "个人资料测试数据"+i;
			di.mContent = i+"个人资料测试数据,个人资料测试数据,个人资料测试数据,个人资料测试数据;";
			di.mCreatePersoner = "A"+i;
			di.mDepartment = "Dep"+i;
			di.mPubDate = new Date();
			di.mType = 1;
			if(i%2==0)
				di.mValid = 1;
			else
				di.mValid = 2;
			list.add(di);
		}
		for(int i=0;i<10;i++){
			DataInfo di = new DataInfo();
			di.mTitle = "科室资料测试数据"+i;
			di.mContent = i+"科室资料测试数据,科室资料测试数据,科室资料测试数据,科室资料测试数据;";
			di.mCreatePersoner = "A"+i;
			di.mDepartment = "Dep"+i;
			di.mPubDate = new Date();
			di.mType = 2;
			if(i%2==0)
				di.mValid = 1;
			else
				di.mValid = 2;
			list.add(di);
		}
	}
	public List<DataInfo> getPubInfoByUser(String title, String createPerson,
			Date from, Date to, int valid,int type) {
		boolean pass = true;
		List<DataInfo> aDataList = new ArrayList<DataInfo>();
		for (DataInfo pi : list) {
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
			} else if (createPerson != null && !createPerson.equals("")) {
				if (pi.mCreatePersoner.contains(createPerson)) {
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
				if(pi.mPubDate.after(from) && pi.mPubDate.before(to)){
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
