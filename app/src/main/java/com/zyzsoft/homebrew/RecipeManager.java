package com.zyzsoft.homebrew;

import android.content.Context;

import com.zyzsoft.homebrew.data.RecipeStorageManager;
import com.zyzsoft.homebrew.recipe.Recipe;

public class RecipeManager {
	private static Recipe _currentRecipe;
	private static RecipeStorageManager _storageMgr;
	
	public static void init(Context context) {
		_currentRecipe = new Recipe();
		_storageMgr = new RecipeStorageManager(context);
	}
	
	public static Recipe getCurrentRecipe() {
		assert(_currentRecipe != null);
		assert(_storageMgr != null);
		
		_storageMgr.saveRecipe(_currentRecipe);
		
		return _currentRecipe;
	}
	
	public static void setCurrentRecipe(String recipeId) {
		Recipe recipe = _storageMgr.loadRecipe(recipeId);
		_currentRecipe = recipe;
	}

    public static void setNewRecipe() {
        _storageMgr.saveRecipe(_currentRecipe);
        _currentRecipe = new Recipe();
    }
	
	//TODO: standalone storage manager that saves things on a timer 
	//as well as handling explicit saves
	public static RecipeStorageManager getRecipeStorageManager() {
		return _storageMgr;
	}

    public static void deleteCurrentRecipe() {
        _storageMgr.deleteRecipe(_currentRecipe.getId());
        _currentRecipe = null;
    }
}
