package com.zyzsoft.homebrew.ui;

import com.zyzsoft.homebrew.R;
import com.zyzsoft.homebrew.RecipeManager;
import com.zyzsoft.homebrew.R.id;
import com.zyzsoft.homebrew.recipe.Recipe;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.text.DecimalFormat;

public class BoilIngredientField extends LinearLayout {

    private static final DecimalFormat FLOAT_FORMAT = new DecimalFormat("#.###");
	
	private BoilIngredientsEditor _container;
	
	private EditText _alphaAcidContentField;
	private float _alphaAcidMostRecentlyPresent;
	private EditText _quantityField;
	private float _quantityMostRecentlyPresent;
	private EditText _boilTimeMinutesField;
	private int _boilTimeMostRecentlyPresent;
	
	private ImageButton _removeIngredientButton;
	
	public BoilIngredientField(Context context) {
	    super(context);
	}
	
	public BoilIngredientField(Context context, AttributeSet attribs) {
		super(context, attribs);
	}

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        Utils.initKeyboardDismissEvents(this);
    }

	public void initialiseFields(BoilIngredientsEditor containingEditor) {
		_container = containingEditor;
		
		initAlphaAcidContentField();
		initQuantityField();
		initBoilTimeMinutesField();
		initRemoveButton();
	}

    public void initialiseListeners() {
        initAlphaAcidContentFieldListener();
        initQuantityFieldListener();
        initBoilTimeMinutesFieldListener();
    }
	
	private void initAlphaAcidContentField() {
		_alphaAcidContentField = (EditText)findViewById(R.id.alphaAcidContent);
	}

    public void initAlphaAcidContentFieldListener() {
        _alphaAcidContentField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    float newAlphaAcidContent = getAlphaAcidContentValue();
                    Recipe currentRecipe = RecipeManager.getCurrentRecipe();

                    currentRecipe.removeHop(_alphaAcidMostRecentlyPresent,
                            _quantityMostRecentlyPresent, _boilTimeMostRecentlyPresent);

                    currentRecipe.addHop(newAlphaAcidContent, _quantityMostRecentlyPresent,
                            _boilTimeMostRecentlyPresent);

                    _container.updateBoilStats();

                    _alphaAcidMostRecentlyPresent = newAlphaAcidContent;
                } catch (NumberFormatException nfe) {

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            public void afterTextChanged(Editable s) {}
        });
    }
	
	private void initQuantityField() {
		_quantityField = (EditText)findViewById(R.id.boilIngredientsQuantity);
	}

    private void initQuantityFieldListener() {
        _quantityField.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    float newQuantity = getQuantityValue();
                    Recipe currentRecipe = RecipeManager.getCurrentRecipe();

                    currentRecipe.removeHop(_alphaAcidMostRecentlyPresent, _quantityMostRecentlyPresent,
                            _boilTimeMostRecentlyPresent);

                    currentRecipe.addHop(_alphaAcidMostRecentlyPresent, newQuantity,
                            _boilTimeMostRecentlyPresent);

                    _container.updateBoilStats();

                    _quantityMostRecentlyPresent = newQuantity;
                } catch (NumberFormatException nfe) {

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            public void afterTextChanged(Editable s) {}
        });
    }
	
	private void initBoilTimeMinutesField() {
		_boilTimeMinutesField = (EditText)findViewById(R.id.boilTimeMinutes);
	}

    private void initBoilTimeMinutesFieldListener() {
        _boilTimeMinutesField.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int newBoilTimeMinutes = getBoilTimeMinutesValue();
                    Recipe currentRecipe = RecipeManager.getCurrentRecipe();

                    currentRecipe.removeHop(_alphaAcidMostRecentlyPresent, _quantityMostRecentlyPresent,
                            _boilTimeMostRecentlyPresent);

                    currentRecipe.addHop(_alphaAcidMostRecentlyPresent, _quantityMostRecentlyPresent,
                            newBoilTimeMinutes);

                    _container.updateBoilStats();

                    _boilTimeMostRecentlyPresent = newBoilTimeMinutes;
                } catch (NumberFormatException nfe) {

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            public void afterTextChanged(Editable s) {}
        });
    }
	
	public float getAlphaAcidContentValue() {
        float alpha = 0.f;
        try {
            alpha = Float.parseFloat(_alphaAcidContentField.getText().toString());
        } catch (NumberFormatException nfe) {}
        return alpha;
	}
	
	public float getQuantityValue() {
        float quantity = 0.f;
		try {
            quantity = Float.parseFloat(_quantityField.getText().toString());
        } catch (NumberFormatException nfe) {}
        return quantity;
	}
	
	public int getBoilTimeMinutesValue() {
        int mins = 0;
		try {
            mins = Integer.parseInt(_boilTimeMinutesField.getText().toString());
        } catch (NumberFormatException nfe) {}
        return mins;
	}
	
	private void initRemoveButton() {
		final BoilIngredientField self = this;
		_removeIngredientButton = (ImageButton)findViewById(R.id.removeBoilIngredient);
		_removeIngredientButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				((ViewGroup)getParent()).removeView(self);
				RecipeManager.getCurrentRecipe().removeHop(
						self.getAlphaAcidContentValue(), self.getQuantityValue(),
						self.getBoilTimeMinutesValue());
				_container.updateBoilStats();
			}
		});
	}

    public void setAlphaAcidValue(float alphaAcidContent) {
        _alphaAcidContentField.setText(FLOAT_FORMAT.format(alphaAcidContent));
        _alphaAcidMostRecentlyPresent = alphaAcidContent;
    }

    public void setQuantity(float quantityGrams) {
        _quantityField.setText(FLOAT_FORMAT.format(quantityGrams));
        _quantityMostRecentlyPresent = quantityGrams;
    }

    public void setBoilTimeMins(int boilMinutes) {
        _boilTimeMinutesField.setText(FLOAT_FORMAT.format(boilMinutes));
        _boilTimeMostRecentlyPresent = boilMinutes;
    }
}
