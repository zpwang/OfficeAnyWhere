package com.office.anywher.https;

public class JsonBean {

	private String key;
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
	
	public JsonBean(String _key,String _value){
		this.key = _key;
		this.value = _value;
	}
	
	
}
