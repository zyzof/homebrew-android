package com.zyzsoft.homebrew.ui;

import com.zyzsoft.homebrew.R;
import com.zyzsoft.homebrew.RecipeManager;
import com.zyzsoft.homebrew.recipe.BrewCalculator;
import com.zyzsoft.homebrew.recipe.Hop;
import com.zyzsoft.homebrew.recipe.Recipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BoilIngredientsEditor extends Fragment {
	
	private static final int INITIAL_NUM_FIELDS = 3;
	
	private View _view;
	
	private TextView _boilStatsLabel;
	private EditText _boilVolumeField;
	private EditText _originalGravityField;
	private LinearLayout _fieldContainer;
    private ImageButton _addNewIngredientBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		_view = inflater.inflate(R.layout.fragment_boil_ingredients_editor, container, false);
		_fieldContainer = (LinearLayout)_view.findViewById(R.id.boilIngredientContainer);
		initBoilVolumeField();
		initOriginalGravityField();

		addBoilIngredientFields();

        _addNewIngredientBtn = (ImageButton)_view.findViewById(R.id.addBoilIngredient);
        _addNewIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBoilIngredientField();
            }
        });

        Utils.initKeyboardDismissEvents(_view);

		return _view;
	}

    private void addBoilIngredientFields() {
        List<Hop> hops = RecipeManager.getCurrentRecipe().getHops();
        if (hops.size() > 0) {
            for (Hop hop : hops) {
                addBoilIngredientField(hop);
            }
        } else {
            addBoilIngredientField();
        }
    }

    private void addBoilIngredientField() {
		BoilIngredientField field = createBoilIngredientField();
        field.initialiseListeners();
		_fieldContainer.addView(field);
	}

    private void addBoilIngredientField(Hop hop) {
        BoilIngredientField field = createBoilIngredientField();
        field.setAlphaAcidValue(hop.getAlphaAcidContent());
        field.setQuantity(hop.getQuantityGrams());
        field.setBoilTimeMins(hop.getBoilMinutes());
        field.initialiseListeners();
        _fieldContainer.addView(field);
    }

    private BoilIngredientField createBoilIngredientField() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.boil_ingredient_field, null);
        BoilIngredientField field = (BoilIngredientField)v;
        field.initialiseFields(this);
        return field;
    }
	
	private void initBoilVolumeField() {
		final BoilIngredientsEditor self = this;
		_boilVolumeField = (EditText)_view.findViewById(R.id.boilVolume);
		_boilVolumeField.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setBoilVolume(self.getBoilVolumeFieldValue());
					updateBoilStats();
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
		
		float boilVolume = RecipeManager.getCurrentRecipe().getBoilVolume();
		if (boilVolume > 0) {
			_boilVolumeField.setText(Float.toString(boilVolume));
		}
	}
	
	private void initOriginalGravityField() {
		final BoilIngredientsEditor self = this;
		_originalGravityField = (EditText)_view.findViewById(R.id.originalGravity);
		_originalGravityField.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setOriginalGravity(self.getOriginalGravityFieldValue());
					updateBoilStats();
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
		
		float originalGravity = RecipeManager.getCurrentRecipe().getOriginalGravity();
		if (originalGravity > 0) {
			_originalGravityField.setText(new DecimalFormat("0.000").format(originalGravity));
		}
	}
	
	public float getBoilVolumeFieldValue() {
		return Float.parseFloat(_boilVolumeField.getText().toString());
	}
	
	public float getOriginalGravityFieldValue() {
		return Float.parseFloat(_originalGravityField.getText().toString());
	}

	public void updateBoilStats() {
		if (_boilStatsLabel == null) {
			_boilStatsLabel = (TextView)_view.findViewById(R.id.boilStats);
		}
		
		Recipe currentRecipe = RecipeManager.getCurrentRecipe();
		float ibu = BrewCalculator.calculateIBU(currentRecipe);
		String newLabel = String.format("IBU: %s", ibu);
		
		_boilStatsLabel.setText(newLabel);
	}
}
