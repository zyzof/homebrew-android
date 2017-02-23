package com.zyzsoft.homebrew.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.util.Log;

public class Recipe {
	
	private UUID _id;
	private String _name;
	
	//MASH INGREDIENTS
	//Mappings of ingredients to quantities (in grams)
	private Map<Fermentable, Float> _fermentables;
	private float _mashVolume;
	private float _brewhouseEfficiency;
	
	//MASH SCHEDULE
	private float _grainWeight; 	//User specified only
	private float _grainTemp;	//User specified
	private float _postMashTargetVolume; //User specified
	private float _targetInfusionTemp;	//User specified
	private Map<Integer, MashStep> _mashStepsById;
	
	
	//BOIL
	private List<Hop> _hops;
	private float _originalGravity;	//User specified in boil editor. For actual, get from BrewCalculator.
	private float _boilVolume; //User specified, see above.
	
	public Recipe() {
		_id = UUID.randomUUID();
		_fermentables = new HashMap<Fermentable, Float>();
		_hops = new ArrayList<Hop>();
		_mashStepsById = new HashMap<Integer, MashStep>();
	}
	
	public void addFermentable(Fermentable fermentable, float quantity) {
		Float previousQuantity = _fermentables.get(fermentable);
		if (previousQuantity != null) {
			_fermentables.put(fermentable, previousQuantity + quantity);
		} else {
			_fermentables.put(fermentable, quantity);
		}
	}
	
	public void removeFermentable(Fermentable fermentable, float quantity) {
		Float previousQuantity = _fermentables.get(fermentable);
		if (previousQuantity != null && previousQuantity - quantity > 0) {
			_fermentables.put(fermentable, previousQuantity - quantity);
		} else {
            _fermentables.remove(fermentable);
        }
	}
	
	public Map<Fermentable, Float> getFermentables() {
		return Collections.unmodifiableMap(_fermentables);
	}
	
	public void addHop(float alphaAcidContent, float quantityGrams, int minutes) {
		_hops.add(new Hop(alphaAcidContent, quantityGrams, minutes));
	}
	
	public void removeHop(float alphaAcidContent, float quantityGrams, int minutes) {
		_hops.remove(new Hop(alphaAcidContent, quantityGrams, minutes));
	}
	
	public List<Hop> getHops() {
		return Collections.unmodifiableList(_hops);
	}
	
	public void setMashVolume(float mashVolume) {
		_mashVolume = mashVolume;
	}
	
	public float getMashVolume() {
		return _mashVolume;
	}

	public void setBrewhouseEfficiency(float brewhouseEfficiency) {
		_brewhouseEfficiency = brewhouseEfficiency;
	}
	
	public float getBrewhouseEfficiency() {
		return _brewhouseEfficiency;
	}
	
	public float getOriginalGravity() {
		return _originalGravity;
	}
	
	public void setOriginalGravity(float originalGravity) {
		_originalGravity = originalGravity;
	}

	public void setBoilVolume(float boilVolume) {
		_boilVolume = boilVolume;
	}
	
	public float getBoilVolume() {
		return _boilVolume;
	}
	
	public void setGrainWeight(float weight) {
		_grainWeight = weight;
	}
	
	public float getGrainWeight() {
		return _grainWeight;
	}
	
	public void setGrainTemp(float temp) {
		_grainTemp = temp;
	}
	
	public float getGrainTemp() {
		return _grainTemp;
	}
	
	public void setPostMashTargetVolume(float volume) {
		_postMashTargetVolume = volume;
	}
	
	public float getPostMashTargetVolume() {
		return _postMashTargetVolume;
	}
	
	public void setTargetInfusionTemp(float temp) {
		_targetInfusionTemp = temp;
	}
	
	public float getTargetInfusionTemp() {
		return _targetInfusionTemp;
	}
	
	public void addMashStep(int id, MashStep mashStep) {
		_mashStepsById.put(id, mashStep);
	}
	
	public void removeMashStep(int mashStepId) {
		_mashStepsById.remove(mashStepId);
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public UUID getId() {
		return _id;
	}
}
