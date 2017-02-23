package com.zyzsoft.homebrew.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "homebrew.db";
	private static final int DB_VERSION = 1;
	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(QueryProvider.CREATE_DB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersionNum, int newVersionNum) {
		//Do nothing.
	}
}
