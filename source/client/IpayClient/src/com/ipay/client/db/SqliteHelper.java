package com.ipay.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "SqliteHelper";
	
	public static final String DB_NAME = "ipay.db";
	public static int DB_VERSION = 1;
	
	private static SqliteHelper sqliteHelper;

	/**
	 * 把构造函数申明为私有，为了使用Singleton模式
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	private SqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public static SqliteHelper getInstance(Context context){
		if(sqliteHelper == null)
			sqliteHelper = new SqliteHelper(context, DB_NAME, null, DB_VERSION);
		return sqliteHelper;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(UserDBHelper.CREATE_TABLE);
		Log.d(TAG, "created table "+UserDBHelper.USER_TABLE_NAME);
		
		db.execSQL(ProductDBHelper.CREATE_TABLE);
		Log.d(TAG, "create table "+ProductDBHelper.PRODUCT_TABLE_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(UserDBHelper.UPGRADE);
		db.execSQL(ProductDBHelper.UPGRADE);
		onCreate(db);
	}
}
