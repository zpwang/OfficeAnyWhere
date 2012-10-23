package com.office.anywher.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoginDao {
	private DbHelper helper;  
    private SQLiteDatabase db;  
    
    public LoginDao(Context context) {  
        helper = new DbHelper(context);  
        db = helper.getWritableDatabase();
    }
    
    public void close(){
    	db.close();
    }
    
    public int getNewId(){
    	int newId = 0;
    	Cursor c =  db.rawQuery(" select count(*) as x from "+DBConst.TableName.H_REMEBER_LOGIN, null);
    	int count = 0;
    	c.moveToFirst();
    	while(!c.isAfterLast()){
    		count = c.getInt(c.getColumnIndex("x"));
			c.moveToNext();
		}
    	c.close();
    	c=null;
    	if(count == 0)return 1;
    	StringBuffer sb = new StringBuffer();
		sb.append(" SELECT MAX("+DBConst.H_REMEBER_LOGIN.L_ID+") as x FROM ")
				.append(DBConst.TableName.H_REMEBER_LOGIN);
		c =  db.rawQuery(sb.toString(), null);
		c.moveToFirst();
		while(!c.isAfterLast()){
			newId = c.getInt(c.getColumnIndex("x"))+1;
			c.moveToNext();
		}
		return newId;
    }
    public Login getLogin(String user){
    	Login login =null;
    	StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM ")
				.append(DBConst.TableName.H_REMEBER_LOGIN);
				/*.append(" WHERE ")
				.append(DBConst.H_REMEBER_LOGIN.L_USER)
				.append("=").append("'").append(user).append("'");*/
    	Cursor c = db.rawQuery(sb.toString(),null);
    	c.moveToFirst();
    	while(!c.isAfterLast()){
    		login = new Login();
    		login.mLoginId = c.getInt(c.getColumnIndex(DBConst.H_REMEBER_LOGIN.L_ID));
    		login.mLoginState = (c.getString(c.getColumnIndex(DBConst.H_REMEBER_LOGIN.L_STATE)));
    		login.mUser = (c.getString(c.getColumnIndex(DBConst.H_REMEBER_LOGIN.L_USER)));
    		login.mPwd = (c.getString(c.getColumnIndex(DBConst.H_REMEBER_LOGIN.L_PWD)));
    		c.moveToNext();
    	}
    	return login;
    }
    
    public void delete(){
    	StringBuffer sb = new StringBuffer();
		sb.append(" DELETE  FROM ")
				.append(DBConst.TableName.H_REMEBER_LOGIN);
		db.execSQL(sb.toString());
    }
    
    public void save(Login login){
    	delete();
    	if(login.mOldState){
    		update(login);
    	}else{
    		insert(login);
    	}
    }
    public void insert(Login login){
    	db.beginTransaction();
    	try{
    		db.execSQL("INSERT INTO "+DBConst.TableName.H_REMEBER_LOGIN+
    				" VALUES(?,?,?,?)",
    				new Object[]{
    				getNewId(),
    				login.mLoginState,
    				login.mUser,
    				login.mPwd
    		});
    		db.setTransactionSuccessful();
    	}finally{
    		db.endTransaction();
    	}
    }    
    public void update(Login login){
    	if(null == login)return ;
    	ContentValues cv = new ContentValues();
    	cv.put(DBConst.H_REMEBER_LOGIN.L_STATE, login.mLoginState);
    	db.update(DBConst.TableName.H_REMEBER_LOGIN, cv, DBConst.H_REMEBER_LOGIN.L_ID+"=?",
				new String[] { login.mLoginId+""});
    }
}
