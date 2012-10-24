package com.office.anywher.offcial.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 公文DB操作类
 * 
 * @author wangzp
 * 
 */
public class OfficialDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "hecheng_oa.db";
	private static final int DATABASE_VERSION = 2;

	public OfficialDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(OfficialDBConst.CreateGWTableSQL.CREATE_H_GONGWEN_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int paramInt1, int paramInt2) {
		db.execSQL(OfficialDBConst.DropGWTableSQL.DROP_H_REMEBER_LOGIN_SQL);
		onCreate(db);
	}

}
