package com.office.anywher.https;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils {
	
	public static boolean hasNetWorking(Context context){
		if(null == context)return false;
		ConnectivityManager mConnectivityManager = (ConnectivityManager)context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
		if(mNetworkInfo == null){
			 return mNetworkInfo.isAvailable();  
		}else{
			return false;
		}
	}
	
	public static int whichConnectedType(Context context){
		if (null == context)
			return -1;
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
			return mNetworkInfo.getType();
		}
		return -1;
	}
	
}
