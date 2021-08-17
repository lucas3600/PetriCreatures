package com.lucas.PetriCreatures.Creatures.Brain.Functions;

public class Sigmoid implements Function{

	@Override
	public float compute(float value) {
		return (float) (1.0f/(1.0f+Math.pow(Math.E,-5*value)));
	}
	
}
