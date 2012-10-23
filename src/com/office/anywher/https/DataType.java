package com.office.anywher.https;

public class DataType {

	private String url ;
	private String type;
	
	public DataType(String _url,String _type){
		url = _url;
		type = _type;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
