package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
private static String DB_NAME = "sample_db";
private static int DB_VERSION = 18;

public static String ITEMS_TABLE = "items";
public static String VENDORS_TABLE = "vendors";
public static String ITEMS_VIEW = "items_view";

//	private static String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS items "
//			+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
//			+ "name VARCHAR(80) NOT NULL,"
//			+ "quantity INTEGER NOT NULL,"
//			+ "vendor_name VARCHAR(80) NOT NULL,"
//			+ "vendor_phone_num VARCHAR(15) NOT NULL);" ;
	
	private static String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS items " +
			"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"name VARCHAR(80) NOT NULL," +
			"quantity INTEGER NOT NULL," +
			"vendor_id INTEGER NOT NULL," +
			"FOREIGN KEY(vendor_id) REFERENCES vendors(id) ON DELETE CASCADE)";
	
	private static String CREATE_VENDORS_TABLE = "CREATE TABLE IF NOT EXISTS vendors " +
			"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"vendor_name VARCHAR(80) NOT NULL," +
			"vendor_phone_num VARCHAR(15) NOT NULL)";
	
	private static String CREATE_ITEMS_VIEW = "CREATE VIEW IF NOT EXISTS items_view AS SELECT " +
			"items.name, items.quantity, vendors.vendor_name, vendors.vendor_phone_num " +
			"FROM vendors JOIN items " +
			"ON vendors.id = items.vendor_id";
		
	private static String DROP_ITEMS_TABLE = "DROP TABLE IF EXISTS items";
	private static String DROP_VENDORS_TABLE = "DROP TABLE IF EXISTS vendors";
	private static String DROP_ITEMS_VIEW = "DROP TABLE IF EXISTS items_view";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION );
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_VENDORS_TABLE);
		db.execSQL(CREATE_ITEMS_TABLE);
		db.execSQL(CREATE_ITEMS_VIEW); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_VENDORS_TABLE);
		db.execSQL(DROP_ITEMS_TABLE);
		db.execSQL(DROP_ITEMS_VIEW);
		
		createTables(db);
	}
	
	private void createTables(SQLiteDatabase db){
		onCreate(db);
	}
}
