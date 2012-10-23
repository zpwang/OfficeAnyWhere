package com.office.anywher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "hecheng_oa.db";  
	private static final int DATABASE_VERSION = 2;
	public DbHelper(Context context) {  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBConst.CreateTableSQL.CREATE_H_REMEBER_LOGIN_SQL);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int paramInt1,
			int paramInt2) {
		db.execSQL(DBConst.DropTableSQL.DROP_H_REMEBER_LOGIN_SQL);
		onCreate(db);
	} 
}
/*
NULL 值为NULL
INTEGER 值为带符号的整型，根据类别用1，2，3，4，6，8字节存储
REAL 值为浮点型，8字节存储 
TEXT 值为text字符串，使用数据库编码(UTF-8, UTF-16BE or UTF-16-LE)存储
BLOB 值为二进制数据，具体看实际输入
但实际上，sqlite3也接受如下的数据类型：
smallint  16 位元的整数
interger  32 位元的整数
decimal(p,s)  p 精确值和 s 大小的十进位整数，精确值p是指全部有几个数(digits)大小值    ，s是指小数点後有几位数。如果没有特别指定，则系统会设为 p=5; s=0 。
float   32位元的实数。
double   64位元的实数。
char(n)   n 长度的字串，n不能超过 254。
varchar(n)  长度不固定且其最大长度为 n 的字串，n不能超过 4000。
graphic(n)  和 char(n) 一样，不过其单位是两个字元 double-bytes， n不能超过127。   这个形态是为了支援两个字元长度的字体，例如中文字。
vargraphic(n)  可变长度且其最大长度为 n 的双字元字串，n不能超过 2000。
date   包含了 年份、月份、日期。
time   包含了 小时、分钟、秒。
timestamp  包含了 年、月、日、时、分、秒、千分之一秒。
 * */
