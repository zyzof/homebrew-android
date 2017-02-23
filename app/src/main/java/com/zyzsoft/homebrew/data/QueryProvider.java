package com.zyzsoft.homebrew.data;

public class QueryProvider {
	
	public static String CREATE_DB = "CREATE TABLE recipe"
			+ " ("
				+ "id TEXT PRIMARY KEY NOT NULL,"
				+ " name TEXT NOT NULL,"
				+ " recipe_content text NOT NULL"
			+ ")";
}
