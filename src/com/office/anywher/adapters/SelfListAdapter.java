package com.office.anywher.adapters;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.office.anywher.R;

public class SelfListAdapter implements android.widget.ListAdapter {
	private Context context;
	private int size = 10;
	private List datas;
	public SelfListAdapter(Context _context,int _size,List _datas ){
		context = _context;
		datas = _datas;
		size = (datas!=null && datas.size()<size)?datas.size():_size;
	}
	public void unregisterDataSetObserver(DataSetObserver arg0) {
	}
	public void registerDataSetObserver(DataSetObserver arg0) {
	}
	public boolean isEmpty() {
		return false;
	}

	public boolean hasStableIds() {
		return false;
	}

	public int getViewTypeCount() {
		return 1;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater aInflater = LayoutInflater.from(context);
		RelativeLayout aRelativeLayoutItem = (RelativeLayout)aInflater.inflate(R.layout.list_item, null);
		TextView aTextView = (TextView)aRelativeLayoutItem.findViewById(R.id.item_text);
		aTextView.setText(datas.get(arg0)+"");
		return aRelativeLayoutItem;
	}
	public int getItemViewType(int arg0) {
		return arg0;
	}
	public long getItemId(int arg0) {
		return 0;
	}
	public Object getItem(int arg0) {
		return null;
	}
	public int getCount() {
		return size;
	}
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
}