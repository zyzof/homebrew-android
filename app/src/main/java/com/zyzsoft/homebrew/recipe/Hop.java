package com.zyzsoft.homebrew.recipe;

public class Hop {
	
	private float _alphaAcidContent;
	private float _quantityGrams;
	private int _boilMinutes;
	
	public Hop(float alphaAcidContent, float quantityGrams, int boilMinutes) {
		_alphaAcidContent = alphaAcidContent;
		_quantityGrams = quantityGrams;
		_boilMinutes = boilMinutes;
	}
	
	public float getAlphaAcidContent() {
		return _alphaAcidContent;
	}
	
	public float getQuantityGrams() {
		return _quantityGrams;
	}
	
	public int getBoilMinutes() {
		return _boilMinutes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(_alphaAcidContent);
		result = prime * result + _boilMinutes;
		result = prime * result + Float.floatToIntBits(_quantityGrams);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hop other = (Hop) obj;
		if (Float.floatToIntBits(_alphaAcidContent) != Float
				.floatToIntBits(other._alphaAcidContent))
			return false;
		if (_boilMinutes != other._boilMinutes)
			return false;
		if (Float.floatToIntBits(_quantityGrams) != Float
				.floatToIntBits(other._quantityGrams))
			return false;
		return true;
	}

}
