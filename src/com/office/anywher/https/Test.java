package com.office.anywher.https;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public class Test {

	private static final String tag = "Test";
	/**
	 * @param args
	 */
	public static String  test() {
		String url = "http://59.33.252.254:7001/oa/login.jsp"; 
		JsonBean[] jsonBeans = new JsonBean[]{new JsonBean("userName","a"),new JsonBean("password","123")};
		try {
			return HttpClient.request(jsonBeans, url).toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		test();
	}

}
