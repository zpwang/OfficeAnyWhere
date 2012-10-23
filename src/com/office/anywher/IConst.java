package com.office.anywher;

public interface IConst {

	public static final String USER_CHANGE = "USER_CHANGE";
	public static final String LOGIN_USER_NAME = "LOGIN_USER_NAME";
	public static final String WELL_COME = "和诚移动OA欢迎您";
	public static final String EMAIL_DEMO_DATA = "EMAIL_DEMO_DATA";
	public static final String SCHEDUL_DEMO_DATA = "SCHEDUL_DEMO_DATA";

	public interface NavigetText{
		public static final String[] CLICKTEXTCOLOR = new String[]{"#331A00","#FFFFFF"};
		public static final int[] CLICKTEXTSIZE = new int[]{15,15};
		//public static final String[] CLICKBGCOLOR = new String[]{"#80000000","#CC0000"};
		public static final String[] XIETONGBANGGONG = new String[]{"待办文件","待阅文件","缓办文件","已办文件","公文统计"};
		public static final String[] EMAIL = new String[]{"收件箱","发件箱","草稿箱","",""};
		public static final String[] MEETING = new String[]{"最新议题","历史议题","议题查询","",""};
		public static final String[] DATAS = new String[]{"个人资料","科室资料","资料查询","",""};
	}
}
