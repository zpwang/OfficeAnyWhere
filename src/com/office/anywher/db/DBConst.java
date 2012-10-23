package com.office.anywher.db;

public interface DBConst {

	public interface TableName{
		public static final String H_REMEBER_LOGIN = "H_REMEBER_LOGIN";
		public static final String H_A_GONGWEN = "H_A_GONGWEN";
		public static final String H_B_GONGWEN = "H_B_GONGWEN";
	}
	public interface H_REMEBER_LOGIN{
		public static final String  L_ID="L_ID";
		public static final String  L_STATE="L_STATE";
		public static final String  L_USER="L_USER";
		public static final String  L_PWD ="L_PWD";
	}
	public interface CreateTableSQL{
		public static final String CREATE_H_REMEBER_LOGIN_SQL = " CREATE TABLE IF NOT EXISTS " +
				TableName.H_REMEBER_LOGIN+
				" ( "+H_REMEBER_LOGIN.L_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				H_REMEBER_LOGIN.L_STATE+" text, "+
				H_REMEBER_LOGIN.L_USER+" text, "+
				H_REMEBER_LOGIN.L_PWD +" text)";
	}
	
	public interface DropTableSQL{
		public static final String DROP_H_REMEBER_LOGIN_SQL = " DROP TABLE  IF  EXISTS "+TableName.H_REMEBER_LOGIN;
	}
}
