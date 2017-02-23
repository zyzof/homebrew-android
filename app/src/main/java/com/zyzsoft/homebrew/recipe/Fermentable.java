package com.zyzsoft.homebrew.recipe;

public class Fermentable {
	private String _name;
	private float _gravity;	//Fermentability. 100grams per litre?
	private float _lovibond; //Color contribution
	private String _description;
	
	public Fermentable(String name, float gravity, float lovibond, String description) {
		_name = name;
		_gravity = gravity;
		_lovibond = lovibond;
		_description = description;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public float getGravity() {
		return _gravity;
	}
	public void setGravity(float gravity) {
		_gravity = gravity;
	}
	public float getDegreesLovibond() {
		return _lovibond;
	}
	public void setDegreesLovibond(float degreesLovibond) {
		_lovibond = degreesLovibond;
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		_description = description;
	}
	
	@Override
	public String toString() {
		return _name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermentable other = (Fermentable) obj;
		if (_description == null) {
			if (other._description != null)
				return false;
		} else if (!_description.equals(other._description))
			return false;
		if (Float.floatToIntBits(_gravity) != Float
				.floatToIntBits(other._gravity))
			return false;
		if (Float.floatToIntBits(_lovibond) != Float
				.floatToIntBits(other._lovibond))
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}
	
	@Override

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_description == null) ? 0 : _description.hashCode());
		result = prime * result + Float.floatToIntBits(_gravity);
		result = prime * result + Float.floatToIntBits(_lovibond);
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}
}
