package com.zyzsoft.homebrew.ui;

import com.zyzsoft.homebrew.R;
import com.zyzsoft.homebrew.R.layout;
import com.zyzsoft.homebrew.R.menu;
import com.zyzsoft.homebrew.RecipeManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private String m_newRecipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipeManager.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onClickOpenRecipes(View v) {
    	//Bring up recipe selector (modal dialog?)
    	AlertDialog recipeList = RecipeListDialogBuilder.create(this);
    	recipeList.show();
    }
    
    public void onClickOpenMiscCalculations(View v) {
    	//TODO: Go to misc calculations selector (same layout as recipe selector- AlertDialog)
    }
    
    public void onClickNewRecipe(View v) {
        final Activity self = this;
        final EditText recipeNameInput = new EditText(this);

        DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newRecipeName = recipeNameInput.getText().toString();
                RecipeManager.setNewRecipe();
                RecipeManager.getCurrentRecipe().setName(newRecipeName);
                startActivity(new Intent(self, TabsViewPagerFragmentActivity.class));
            }
        };

        DialogInterface.OnClickListener cancelListener =  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };

       new AlertDialog.Builder(this)
                .setTitle("Recipe Name")
                .setView(recipeNameInput)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", cancelListener)
                .show();
    }
}
