package com.example.sqlite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private static String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DatabaseHelper dbHelper = new DatabaseHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 // Enable foreign key constraints
	    if (!db.isReadOnly()) {
	        db.execSQL("PRAGMA foreign_keys = ON;");
	    }
	
		Log.e(TAG, "Database created and opened!");

		insertItems(db);		
//		logAllItems(db);
		
		logItemsWithView(db);
		
		updateItems("Oranges", "20", db);
//		logAllItems(db);
		
		deleteItem("Oranges", db);
//		logAllItems(db);

	}
	
	private void insertItems(SQLiteDatabase db){
		ContentValues values = new ContentValues();
		values.put("vendor_name", "METRO");
		values.put("vendor_phone_num", "+49 003 1275");
		long vendor_id = db.insert(DatabaseHelper.VENDORS_TABLE, null, values);	
		Log.e(TAG, "Vendor item added");
		
		values = new ContentValues();
		values.put("name", "Oranges");
		values.put("quantity", 10);
		values.put("vendor_id", vendor_id);
		
		db.insert(DatabaseHelper.ITEMS_TABLE, null, values);
		Log.e(TAG, "Item item added");
	}
	
	private void logAllItems(SQLiteDatabase db){
		Cursor c = db.query(DatabaseHelper.ITEMS_TABLE, null, null, null, null, null, null);
		Log.e(TAG,String.format("number of items %d", c.getCount()));
		
		while (c.moveToNext()) {
			Log.e(TAG, String.format("id %s, name %s, quantity %s, vendor name %s", c.getString(0),c.getString(1), c.getString(2),
					c.getString(3)));
		}
	}

	
	private void logItemsWithView(SQLiteDatabase db){
		Cursor c = db.query(DatabaseHelper.ITEMS_VIEW, null, null, null, null, null, null);
		Log.e(TAG,String.format("number of items %d", c.getCount()));
		
		while (c.moveToNext()) {
			Log.e(TAG, String.format("item %s, quantity %s, vendor name %s, vendor_phone_number %s", c.getString(0), c.getString(1),
					c.getString(2),c.getString(3)));
		}
	}
	
	private void updateItems(String itemName, String itemValue,SQLiteDatabase db){
		ContentValues values = new ContentValues();
		values.put("quantity", itemValue);
		
		db.update(DatabaseHelper.ITEMS_TABLE, values, "name=?", new String[]{itemName});
	}
	
	private void deleteItem(String itemName, SQLiteDatabase db){
		db.delete(DatabaseHelper.ITEMS_TABLE, "name=?", new String[]{itemName});
	}
	
	
	protected void insertBitmap(SQLiteDatabase database, Bitmap bmp) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		bmp.compress(CompressFormat.JPEG, 85, baos);
		baos.close();
		byte[] blob = baos.toByteArray();
	    ContentValues cv = new ContentValues();
	    cv.put("bitmap", blob);
	    database.insert("mytable", null, cv);
	}
}
