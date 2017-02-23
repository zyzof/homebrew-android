package com.zyzsoft.homebrew.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zyzsoft.homebrew.recipe.Recipe;

public class RecipeStorageManager {

	private DatabaseHelper _dbHelper;

	public RecipeStorageManager(Context context) {
		_dbHelper = new DatabaseHelper(context);
	}

	public void saveRecipe(Recipe recipe) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		String recipeJson = gson.toJson(recipe);
		SQLiteDatabase db = _dbHelper.getWritableDatabase();
		
		String recipeName = recipe.getName();
		if (recipeName == null) {
			recipeName = "";
		}
		String id = recipe.getId().toString();

		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("name", recipeName);
		values.put("recipe_content", recipeJson);
		
		int numRowsUpdated = db.update("recipe", values, "id=?", new String[] { id });
		if (numRowsUpdated == 0) {
			db.insert("recipe", null, values);
		}
	}

	public Map<String, String> getRecipeNamesById() {
		Map<String, String> recipeNamesById = new HashMap<String, String>();

		SQLiteDatabase db = _dbHelper.getReadableDatabase();

		Cursor c = db.query("recipe", new String[] { "id", "name" }, null,
				null, null, null, "id ASC");
		while (c.moveToNext()) {
			recipeNamesById.put(c.getString(0), c.getString(1));
		}

		return recipeNamesById;
	}

	public Recipe loadRecipe(String recipeId) {
		String recipeJson = getRecipeJson(recipeId);
		return new Gson().fromJson(recipeJson, Recipe.class);
	}
	
	public String getRecipeJson(String recipeId) {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();

		Cursor c = db.query("recipe", new String[] { "recipe_content" }, "id=?",
				new String[] { recipeId }, null, null, null);
		c.moveToFirst();

		String recipeJson = c.getString(0);
		
		return recipeJson;
	}

    public void deleteRecipe(UUID id) {
        SQLiteDatabase db = _dbHelper.getWritableDatabase();
        db.delete("recipe", "id=?", new String[] { id.toString() } );
    }
}
