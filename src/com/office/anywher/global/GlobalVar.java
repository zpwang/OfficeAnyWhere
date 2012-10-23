package com.office.anywher.global;

import java.util.HashMap;
import java.util.Map;

/**
 * unique variable
 * single model
 * @author Administrator
 *
 */
public class GlobalVar {
	private static Map<String,Object> mVar = new HashMap<String,Object>();
	private static GlobalVar mGlobalVar = new GlobalVar();
	private GlobalVar(){}
	public synchronized static GlobalVar getInstance(){
		return mGlobalVar;
	}
	public synchronized void put(String key,Object obj){
		mVar.put(key, obj);
	}
	public synchronized Object get(String key){
		if(mVar.containsKey(key)){
			return mVar.get(key);
		}
		return null;
	}
}
