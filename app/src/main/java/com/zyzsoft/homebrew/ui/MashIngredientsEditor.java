package com.zyzsoft.homebrew.ui;

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

import com.zyzsoft.homebrew.R;
import com.zyzsoft.homebrew.RecipeManager;
import com.zyzsoft.homebrew.recipe.BrewCalculator;
import com.zyzsoft.homebrew.recipe.Fermentable;
import com.zyzsoft.homebrew.recipe.Recipe;

import java.util.Map;

public class MashIngredientsEditor extends Fragment {
	
	private static final int INITIAL_NUM_FIELDS = 3;

	private View _view;
	
	private TextView _mashStatsLabelOg;
    private TextView _mashStatsLabelAbv;
    private TextView _mashStatsLabelSrm;
    private View _mashStatsSrmColor;

	private EditText _mashVolumeField;
	private EditText _brewhouseEfficiencyField;	
	private LinearLayout _fieldContainer;
    private ImageButton _addMashIngredientBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		_view = inflater.inflate(R.layout.fragment_mash_ingredients_editor, container, false);
		
		_fieldContainer = (LinearLayout)_view.findViewById(R.id.mashIngredientContainer);
		initMashVolumeField();
		initBrewhouseEfficiencyField();

		addMashIngredientFields();

        _addMashIngredientBtn = (ImageButton)_view.findViewById(R.id.addMashIngredient);
        _addMashIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMashIngredientField();
            }
        });

        Utils.initKeyboardDismissEvents(_view);
		
		return _view;
	}

    private void addMashIngredientFields() {
        Map<Fermentable, Float> fermentables = RecipeManager.getCurrentRecipe().getFermentables();
        if (fermentables.size() > 0) {
            for (Fermentable fermentable : fermentables.keySet()) {
                addMashIngredientField(fermentable, fermentables.get(fermentable));
            }
        } else {
            addMashIngredientField();
        }
    }

    private void addMashIngredientField() {
        MashIngredientField field = createMashIngredientField();
        field.initialiseListeners();
        _fieldContainer.addView(field);
	}

    private void addMashIngredientField(Fermentable fermentable, Float quantity) {
        MashIngredientField field = createMashIngredientField();
        field.setSelectedFermentable(fermentable);
        field.setQuantity(quantity);
        field.initialiseListeners();
        _fieldContainer.addView(field);
    }

    private MashIngredientField createMashIngredientField() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.mash_ingredient_field, null);
        MashIngredientField field = (MashIngredientField)v;
        field.initialiseFields(this);
        return field;
    }
	
	private void initMashVolumeField() {
		final MashIngredientsEditor self = this;
		_mashVolumeField = (EditText)_view.findViewById(R.id.mashVolume);
		_mashVolumeField.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setMashVolume(self.getMashVolumeFieldValue());
					updateMashStats();
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
		
		float mashVolume = RecipeManager.getCurrentRecipe().getMashVolume();
		if (mashVolume > 0) {
			_mashVolumeField.setText(Float.toString(mashVolume));
		}
	}
	
	public float getMashVolumeFieldValue() {
		return Float.parseFloat(_mashVolumeField.getText().toString());
	}
	
	private void initBrewhouseEfficiencyField() {
		final MashIngredientsEditor self = this;
		_brewhouseEfficiencyField = (EditText)_view.findViewById(R.id.brewhouseEfficiency);
		_brewhouseEfficiencyField.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setBrewhouseEfficiency(self.getBrewhouseEfficiencyFieldValue());
					updateMashStats();
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
		
		float brewhouseEfficiency = RecipeManager.getCurrentRecipe().getBrewhouseEfficiency();
		if (brewhouseEfficiency > 0) {
			_brewhouseEfficiencyField.setText(Float.toString(brewhouseEfficiency));
		}
	}
	
	public float getBrewhouseEfficiencyFieldValue() {
		return Float.parseFloat(_brewhouseEfficiencyField.getText().toString());
	}
	
	public void updateMashStats() {
		if (_mashStatsLabelOg == null) {
			_mashStatsLabelOg = (TextView)_view.findViewById(R.id.mashStatsOG);
		}
        if (_mashStatsLabelAbv == null) {
            _mashStatsLabelAbv = (TextView)_view.findViewById(R.id.mashStatsAbv);
        }
        if (_mashStatsLabelSrm == null) {
            _mashStatsLabelSrm = (TextView)_view.findViewById(R.id.mashStatsSRMValue);
        }
        if (_mashStatsSrmColor == null) {
            _mashStatsSrmColor = _view.findViewById(R.id.mashStatsSRMColor);
        }
		
		Recipe currentRecipe = RecipeManager.getCurrentRecipe();
		float og = BrewCalculator.calculateOriginalGravity(currentRecipe);
		float srm = BrewCalculator.calculateSRM(currentRecipe);
        float estimatedAbv = BrewCalculator.calculateABV(og, 1.005f);   //TODO: ability to edit est f.g.
		String newOgLabel = String.format("OG: %s", String.format("%.2f", og));
        String newAbvLabel = String.format("Est. %ABV: %s", String.format("%.2f", estimatedAbv));
        String newSrmLabel = String.format("SRM: %s", String.format("%.2f", srm));
        int srmColor = BrewCalculator.getSrmColour(srm);
		
		_mashStatsLabelOg.setText(newOgLabel);
        _mashStatsLabelAbv.setText(newAbvLabel);
        _mashStatsLabelSrm.setText(newSrmLabel);
        _mashStatsSrmColor.setBackgroundColor(srmColor);
	}
}
