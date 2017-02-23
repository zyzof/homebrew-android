package com.zyzsoft.homebrew.recipe;

public class MashStep {
	private String _name;
	private float _temperature;
	private int _duration;
	
	public MashStep(String name, float temp, int duration) {
		_name = name;
		_temperature = temp;
		_duration = duration;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public float getTemperature() {
		return _temperature;
	}
	
	public void setTemperature(float temp) {
		_temperature = temp;
	}
	
	public int getDuration() {
		return _duration;
	}
	
	public void setDuration(int duration) {
		_duration = duration;
	}
}
