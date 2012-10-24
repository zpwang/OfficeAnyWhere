package com.office.anywher.offcial.common;

/**
 * 公文DB操作的常量定义
 * 
 * @author wangzp
 * 
 */
public interface OfficialDBConst {

	/**
	 * 公文表名称
	 */
	public static final String TABLE_H_GONGWEN = "H_GONGWEN";
	
	/**
	 * 公文表字段定义
	 */
	public interface H_GONGWEN {
		public static final String L_ID = "L_ID";
		public static final String L_USER = "L_USER"; // 用户：暂时分为A、B两个用户
		public static final String L_STATE = "L_STATE"; // 状态：流程未完结0、流程已完结1
		public static final String L_OLD_CODE = "L_OLD_CODE"; // 公文字号
		public static final String L_OLD_TITLE = "L_OLD_TITLE"; // 标题
		public static final String L_OFFICIAL_SUMMARY = "L_OFFICIAL_SUMMARY"; // 公文摘要
		public static final String L_OFFICIAL_CONTENT = "L_OFFICIAL_CONTENT"; // 公文正文
		public static final String L_PROCESS_NAME = "L_PROCESS_NAME"; // 流程名称
		public static final String L_CUR_STEP = "L_CUR_STEP"; // 当前节点（拟稿、待阅、待办、缓办、已办）
		public static final String L_UP_STEP = "L_UP_STEP"; // 上一节点
	}
	
	/**
	 * 创建公文表H_GONGWEN的sql语句
	 */
	public interface CreateGWTableSQL {
		public static final String CREATE_H_GONGWEN_SQL = " CREATE TABLE IF NOT EXISTS "
				+ TABLE_H_GONGWEN
				+ " ( "
				+ H_GONGWEN.L_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ H_GONGWEN.L_USER + " text, "
				+ H_GONGWEN.L_STATE + " text, "
				+ H_GONGWEN.L_OLD_CODE + " text, "
				+ H_GONGWEN.L_OLD_TITLE + " text, "
				+ H_GONGWEN.L_OFFICIAL_SUMMARY + " text, "
				+ H_GONGWEN.L_OFFICIAL_CONTENT + " text, "
				+ H_GONGWEN.L_PROCESS_NAME + " text, "
				+ H_GONGWEN.L_CUR_STEP + " text, "
				+ H_GONGWEN.L_UP_STEP + " text)";
	}
	
	/**
	 * 删除公文表H_GONGWEN的sql语句
	 */
	public interface DropGWTableSQL {
		public static final String DROP_H_REMEBER_LOGIN_SQL = " DROP TABLE  IF  EXISTS "
				+ TABLE_H_GONGWEN;
	}

}
