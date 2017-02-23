package com.zyzsoft.homebrew.ui;

import java.util.Map;

import com.google.gson.Gson;
import com.zyzsoft.homebrew.RecipeManager;
import com.zyzsoft.homebrew.recipe.Recipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class RecipeListDialogBuilder {

	public static AlertDialog create(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Load Recipe");
		
		Map<String, String> recipeNamesById = RecipeManager
					.getRecipeStorageManager().getRecipeNamesById();
		String[] buttonTags = new String[recipeNamesById.size()];
		final String[] buttonIds = new String[recipeNamesById.size()];
		int i = 0;
		for (String key : recipeNamesById.keySet()) {
			buttonTags[i] = recipeNamesById.get(key);
			buttonIds[i] = key;
			i++;
		}
		
		builder.setItems(
			buttonTags,
			new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent recipeEditorIntent = new Intent(context, TabsViewPagerFragmentActivity.class);
					RecipeManager.setCurrentRecipe(buttonIds[which]);
					context.startActivity(recipeEditorIntent);
				}
			}
		);
		
		return builder.create();
	}
}
