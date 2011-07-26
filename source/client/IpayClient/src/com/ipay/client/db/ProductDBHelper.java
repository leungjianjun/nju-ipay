package com.ipay.client.db;

import java.util.ArrayList;

import com.ipay.client.model.Product;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProductDBHelper {
	
	private static final String TAG = "ProductDBHelper";
	
	//字段名
	public static final String PRODUCT_ID = "pid";
	public static final String PRODUCT_NAME = "pname";
	public static final String PRODUCT_BARCODE = "barcode";
	public static final String PRODUCT_BANNER = "banner";
	public static final String PRODUCT_PRICE = "price";
	public static final String PRODUCT_DESCRIPTION = "description";
	
	//表名
	public static final String PRODUCT_TABLE_NAME = "products";
	
	//创建表的sql语句
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+
	PRODUCT_TABLE_NAME+"("+
	PRODUCT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
	PRODUCT_NAME+" VARCHAR,"+
	PRODUCT_BARCODE+" VARCHAR,"+
	PRODUCT_BANNER+" VARCHAR,"+
	PRODUCT_PRICE+" DOUBLE,"+
	PRODUCT_DESCRIPTION+" VARCHAR"+")";
	
	//删除表sql语句
	public static final String UPGRADE = "DROP TABLE IF EXISTS "+PRODUCT_TABLE_NAME;
	
	//字段集合
	public static final String[] TABLE_COLUMNS = new String[]{
		PRODUCT_ID,
		PRODUCT_NAME,
		PRODUCT_BARCODE,
		PRODUCT_BANNER,
		PRODUCT_PRICE,
		PRODUCT_DESCRIPTION
	};
	
	private SqliteHelper sqliteHelper;
	private SQLiteDatabase db;
	
	public ProductDBHelper(Context context){
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
	 * 保存单个商品信息
	 * 
	 * @param product
	 * @return
	 */
	public int SaveProduct(Product product) throws DBException{
		ContentValues values = new ContentValues();
		values.put(PRODUCT_NAME, product.getName());
		values.put(PRODUCT_BARCODE, product.getBarcode());
		values.put(PRODUCT_BANNER, product.getBanner());
		values.put(PRODUCT_PRICE, product.getPrice());
		values.put(PRODUCT_DESCRIPTION, product.getDescription());
		
		int uid = (int) db.insert(PRODUCT_TABLE_NAME, PRODUCT_ID, values);
		Log.d(TAG, "SaveProduct"+uid);
		
		if(uid == -1)
			throw new DBException("保存商品信息失败！");
		
		return uid;
	}
	
	/**
	 * 保存多个商品信息
	 * 
	 * @param productList
	 * @throws DBException 如果有一个商品保存失败就回滚事务，并抛出异常
	 */
	public void SaveProductList(ArrayList<Product> productList) throws DBException{
		db.beginTransaction();
		
		try{
			
			for(Product product : productList){
				ContentValues values = new ContentValues();
				values.put(PRODUCT_NAME, product.getName());
				values.put(PRODUCT_BARCODE, product.getBarcode());
				values.put(PRODUCT_BANNER, product.getBanner());
				values.put(PRODUCT_PRICE, product.getPrice());
				values.put(PRODUCT_DESCRIPTION, product.getDescription());
				
				int uid = (int) db.insert(PRODUCT_TABLE_NAME, PRODUCT_ID, values);
				Log.d(TAG, "SaveProduct"+uid);
				
				if(uid == -1)
					throw new DBException("保存多个商品信息失败！");
			}
			
			db.setTransactionSuccessful();
		}catch(DBException dbe){
			db.endTransaction();
			throw dbe;
		}
		
		db.endTransaction();
	}
	
	/**
	 * 删除单个商品信息
	 * 
	 * @param barcode
	 * @return
	 * @throws DBException
	 */
	public int DelProduct(String barcode) throws DBException{
		int id = db.delete(PRODUCT_TABLE_NAME, PRODUCT_BARCODE+ "=" + barcode, null);
		Log.d(TAG, "DelProduct"+id);
		
		if(id == 0)
			throw new DBException("删除商品信息失败！");
		
		return id;
	}
	
	/**
	 * 清空商品信息表
	 * 
	 * @return 被删除的行数
	 */
	public int Clear(){
		int rows = db.delete(PRODUCT_TABLE_NAME, null, null);
		Log.d(TAG, "Clear " + rows + "rows");
		return rows;
	}
}
