package com.office.anywher.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.office.anywher.R;

public class DialAdapter extends SimpleAdapter {

	private Context mContext;
	public DialAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource,
			String[] from, int[] to) {
		super(context, data, resource, from, to);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int mPosition = position;
		convertView = super.getView(position, convertView, parent);
		Button dial = (Button) convertView
				.findViewById(R.id.dial);// id为你自定义布局中按钮的id
		final TextView phone = (TextView)convertView.findViewById(R.id.user_phone);
		dial.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone.getText().toString().trim()));
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}
}