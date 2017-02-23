package com.zyzsoft.homebrew.ui;

import com.zyzsoft.homebrew.R;
import com.zyzsoft.homebrew.RecipeManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MashScheduler extends Fragment {
	
	private View _view;
	//Initial infusion fields
	private EditText _grainWeight;
	private EditText _grainTemp;
	private EditText _postMashTargetVolume;
	private EditText _targetInfusionTemp;
	
	private LinearLayout _mashStepFieldContainer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		_view = inflater.inflate(R.layout.fragment_mash_scheduler, container, false);
		_mashStepFieldContainer = (LinearLayout)_view.findViewById(R.id.mashStepFieldContainer);
		
		initInfusionFields();
		addMashStepField();
		return _view;
	}
	
	public void onClickAddMashStep(View v) {
		addMashStepField();
	}
	
	private void initInfusionFields() {
		initGrainWeightField();
		initGrainTempField();
		initPostMashTargetVolumeField();
		initTargetInfusionTempField();
	}
	
	private void initGrainWeightField() {
		final MashScheduler self = this;
		
		_grainWeight = (EditText)_view.findViewById(R.id.grainWeight);
		_grainWeight.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setGrainWeight(self.getGrainWeight());
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private float getGrainWeight() {
		return Float.parseFloat(_grainWeight.getText().toString());
	}
	
	private void initGrainTempField() {
		final MashScheduler self = this;
		
		_grainTemp = (EditText)_view.findViewById(R.id.grainTemp);
		_grainTemp.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setGrainTemp(self.getGrainTemp());
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private float getGrainTemp() {
		return Float.parseFloat(_grainTemp.getText().toString());
	}
	
	private void initPostMashTargetVolumeField() {
		final MashScheduler self = this;
		
		_postMashTargetVolume = (EditText)_view.findViewById(R.id.targetPostMashVolume);
		_postMashTargetVolume.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setPostMashTargetVolume(self.getPostMashTargetVolume());
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private float getPostMashTargetVolume() {
		return Float.parseFloat(_postMashTargetVolume.getText().toString());
	}
	
	private void initTargetInfusionTempField() {
		final MashScheduler self = this;
		
		_targetInfusionTemp = (EditText)_view.findViewById(R.id.targetInfusionTemp);
		_targetInfusionTemp.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					RecipeManager.getCurrentRecipe().setTargetInfusionTemp(self.getTargetInfusionTemp());
				} catch (NumberFormatException nfe) {
					
				}
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			
			public void afterTextChanged(Editable s) {}
		});
	}
	
	private float getTargetInfusionTemp() {
		return Float.parseFloat(_targetInfusionTemp.getText().toString());
	}
	
	public void addMashStepField() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View v = inflater.inflate(R.layout.mash_step_field, null);
		MashStepField field = (MashStepField)v;
		field.initialise(this);
		_mashStepFieldContainer.addView(field);
	}
}
