package com.office.anywher.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.office.anywher.R;

public class DefaultProgress {
	
	private Builder mBuilder;
	private Dialog mDialog ;
	private Context mContext;
	public DefaultProgress(Context context){
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.default_progress_dialog,null);
		mBuilder = new AlertDialog.Builder(context);
		mDialog = mBuilder.setTitle("«Î…‘∫Ú...").setView(layout).create();
	}

	public void show(){
		if(mBuilder==null)return;
		mDialog.show();
	}
	public void hidden(){
		if(mDialog==null)return;
		mDialog.dismiss();
	}
}
