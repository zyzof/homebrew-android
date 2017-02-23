package com.zyzsoft.homebrew.ui;

import com.zyzsoft.homebrew.R;
import com.zyzsoft.homebrew.RecipeManager;
import com.zyzsoft.homebrew.recipe.MashStep;
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

public class MashStepField extends LinearLayout {
	
	private static int currentId = 0;
	
	private MashScheduler _container;
	
	private int _id;
	private EditText _nameField;
	private String _nameMostRecentlyPresent;
	private EditText _temperature;
	private float _temperatureMostRecentlyPresent;
	private EditText _duration;
	private int _durationMostRecentlyPresent;	//Minutes

	private ImageButton _removeMashStepButton;
	
	public MashStepField(Context context) {
	    super(context);
	}
	
	public MashStepField(Context context, AttributeSet attribs) {
		super(context, attribs);
	}

	public void initialise(MashScheduler containingEditor) {
		_container = containingEditor;
		
		_id = currentId++;
		
		initNameField();
		initTempField();
		initDurationField();
		
		initRemoveButton();
	}
	
	private void initNameField() {
		_nameField = (EditText)findViewById(R.id.mashStepName);
		
		_nameField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					String newMashStepName = getMashStepName();
					Recipe currentRecipe = RecipeManager.getCurrentRecipe();
					
					currentRecipe.removeMashStep(_id);
					
					MashStep newMashStep = new MashStep(
							newMashStepName,
							_temperatureMostRecentlyPresent,
							_durationMostRecentlyPresent);
					
					currentRecipe.addMashStep(_id, newMashStep);
					
					_nameMostRecentlyPresent = newMashStepName;
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private String getMashStepName() {
		return _nameField.getText().toString();
	}
	
	private void initTempField() {
		_temperature = (EditText)findViewById(R.id.mashStepTemperature);
		
		_temperature.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					float newTemp = getMashStepTemperature();
					Recipe currentRecipe = RecipeManager.getCurrentRecipe();
					
					currentRecipe.removeMashStep(_id);
					
					MashStep newMashStep = new MashStep(
							_nameMostRecentlyPresent,
							newTemp,
							_durationMostRecentlyPresent);
					
					currentRecipe.addMashStep(_id, newMashStep);
					
					_temperatureMostRecentlyPresent = newTemp;
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private float getMashStepTemperature() {
		return Float.parseFloat(_temperature.getText().toString());
	}
	
	private void initDurationField() {
		_duration = (EditText)findViewById(R.id.mashStepDuration);
		
		_duration.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					int newDuration = getMashStepDuration();
					Recipe currentRecipe = RecipeManager.getCurrentRecipe();
					
					currentRecipe.removeMashStep(_id);
					
					MashStep newMashStep = new MashStep(
							_nameMostRecentlyPresent,
							_temperatureMostRecentlyPresent,
							newDuration);
					
					currentRecipe.addMashStep(_id, newMashStep);
					
					_durationMostRecentlyPresent = newDuration;
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private int getMashStepDuration() {
		return Integer.parseInt(_duration.getText().toString());
	}
	
	private void initRemoveButton() {
		final MashStepField self = this;
		_removeMashStepButton = (ImageButton)findViewById(R.id.removeMashStep);
		_removeMashStepButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				((ViewGroup)getParent()).removeView(self);
				RecipeManager.getCurrentRecipe().removeMashStep(_id);
			}
		});
	}
}