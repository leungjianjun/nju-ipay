package com.ipay.client.db;

import java.util.ArrayList;
import java.util.List;

import com.ipay.client.model.Session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDBHelper{
	
	private static final String TAG = "UserDBHelper";
	
	//用户表名
	public static final String USER_TABLE_NAME = "users";
	
	//字段名
	public static final String USER_ID = "uid";
	public static final String USER_NAME = "username";
	public static final String USER_PASSWORD = "password";
	
	//创建表sql语句
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
	USER_TABLE_NAME+"("+
	USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	USER_NAME+" VARCHAR,"+
	USER_PASSWORD+" VARCHAR"+")";
	
	//删除表sql语句
	public static final String UPGRADE = "DROP TABLE IF EXISTS "+USER_TABLE_NAME;
	
	//字段集合
	public static final String[] TABLE_COLUMNS = new String[]{
		USER_ID,USER_NAME,USER_PASSWORD
	};
	
	private SqliteHelper sqliteHelper;
	private SQLiteDatabase db;
	
	public UserDBHelper(Context context){
		sqliteHelper = SqliteHelper.getInstance(context);
		db = sqliteHelper.getWritableDatabase();
	}
	
	/**
	 * 关闭数据库和sqlitehelper
	 */
	public void Close(){
		db.close();
		sqliteHelper.close();
	}
	
	/**
	 * 插入用户
	 * 
	 * @param session	包含了用户名和密码，其他属性不用
	 * @return	如果插入成功，返回插入记录的id；如果失败，抛出DBException
	 */
	public int SaveUser(Session session) throws DBException{
		ContentValues values = new ContentValues();
		values.put(USER_NAME,session.getUsername());
		values.put(USER_PASSWORD, session.getPassword());
		
		int uid = (int) db.insert(USER_TABLE_NAME, USER_ID, values);
		Log.d(TAG, "SaveUser"+uid);
		
		if(uid == -1)
			throw new DBException("保存用户失败！");
		
		return uid;
	}
	
	/**
	 * 根据用户名删除用户
	 * 
	 * @param username	用户名
	 * @return	被影响的行数;如果删除失败抛出DBException
	 */
	public int DelUser(String username) throws DBException{
		int id = db.delete(USER_TABLE_NAME, USER_NAME + "= '" + username + "'", null);
		Log.d(TAG, "DelUser"+id);
		
		if(id == 0)
			throw new DBException("删除用户失败！");
		
		return id;
	}
	
	/**
	 * 清空用户表，慎用
	 * 
	 * @return 被影响的行数
	 */
	public int Clear(){
		int rows = db.delete(USER_TABLE_NAME, null, null);
		Log.d(TAG, "Clear " + rows + "rows");
		return rows;
	}
	
	/**
	 * 得到用户列表
	 * 
	 * @return
	 */
	public List<Session> GetUserList(){
		List<Session> userList = new ArrayList<Session>();
		Cursor cursor = db.query(USER_TABLE_NAME, 
				null, null, null, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast() && (cursor.getString(1) != null)){
			Session user = new Session(cursor.getString(1),cursor.getString(2));
			userList.add(user);
			cursor.moveToNext();
		}
		
		cursor.close();
		return userList;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}
	
}
