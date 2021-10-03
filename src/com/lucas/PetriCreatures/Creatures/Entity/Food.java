package com.lucas.PetriCreatures.Creatures.Entity;

import com.lucas.PetriCreatures.Utils.Coords;

public class Food extends SimpliestEntity{
	private int amount;
	public Food(Coords absoluteCoords,int amount) {
		super(absoluteCoords,0f);
		this.amount = amount;
		
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
