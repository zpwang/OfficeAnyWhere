package com.office.anywher.address;

import java.util.ArrayList;
import java.util.List;

public class AddressUtil {
	
	private List<AddressInfo> mAddressList = new ArrayList<AddressInfo>();
	
	public AddressUtil(){
		for(int i=0;i<12;i++){
			AddressInfo ai = new AddressInfo();
			ai.mUserName = "²âÊÔÓÃ»§"+i;
			ai.mUserPhone = "1372675984"+i;
			ai.mUserQQ = "154783982";
			ai.mUserEmail = "Test@hecheng.com";
			mAddressList.add(ai);
		}
	}
	
	public List<AddressInfo> getAddressInfoByUser(String name){
		List<AddressInfo> aAddressList = new ArrayList<AddressInfo>();
		for(AddressInfo ai:mAddressList){
			if(ai.mUserName.contains(name)){
				aAddressList.add(ai);
			}
		}
		return aAddressList;
	}
	
	public List<AddressInfo> getAddressList(){
		return mAddressList;
	}
}
