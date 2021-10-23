package com.lucas.PetriCreatures.Creatures.Entity;

import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.World.PetriBox;

public class Vegetable extends SimpliestEntity{
	private float amount;
	private static final float max_food = 100;
	public Vegetable(Coords absoluteCoords,float amount) {
		super(absoluteCoords,0f);
		this.amount = amount;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public void tick() {
		if(amount <= max_food) {
			amount+=0.4;
		}
	}
	
}
