package com.lucas.PetriCreatures.Utils;

public class MassPoint extends Coords{
	private float weight;
	
	public MassPoint(float x, float y,float weight) {
		super(x, y);
		this.weight =weight;
		// TODO Auto-generated constructor stub
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
