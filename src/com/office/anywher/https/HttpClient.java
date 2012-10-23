package com.office.anywher.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpClient {
	public static JSONObject request(JsonBean[] jsonBeans,String url) throws JSONException, ClientProtocolException, IOException{
		HttpPost request = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		if(jsonBeans!=null && jsonBeans.length>0 ){
			for(int i=0;i<jsonBeans.length;i++){
				params.add(new BasicNameValuePair(jsonBeans[i].getKey(),jsonBeans[i].getValue()));
			}
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "gb2312"); 
			request.setEntity(httpentity);
		}
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = httpResponse.getEntity();
			BufferedReader reader  = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while((line = reader.readLine())!=null){
				sb.append(line+"\n");
			}
			JSONObject jo1 = new JSONObject(sb.toString());
			Log.d("HttpClient", jo1.getString("loginId"));
			Log.d("HttpClient", jo1.getString("address"));
			Log.d("HttpClient", jo1.getString("name"));
			Log.d("HttpClient", jo1.getString("password"));
			Log.d("HttpClient", sb.toString());
			JSONArray ja = new JSONArray(sb.toString());
			for(int i=0;i<ja.length();i++){
				JSONObject jo = (JSONObject)ja.get(i);
				Log.d("HttpClient", jo.getString("loginId"));
				Log.d("HttpClient", jo.getString("address"));
				Log.d("HttpClient", jo.getString("name"));
				Log.d("HttpClient", jo.getString("password"));
			}
        }
		return null;
	}	
}
