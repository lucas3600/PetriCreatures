package com.lucas.PetriCreatures.Creatures.Entity;

import com.lucas.PetriCreatures.Utils.Coords;

public abstract class Entity implements IEntity{
	private Coords absoluteCoords;
	private float orientation;
	private boolean dead;
	
	public Entity(Coords absoluteCoords,float orientation) {
		super();
		this.absoluteCoords = absoluteCoords;
		this.orientation = orientation;
	}

	public Coords getAbsoluteCoords() {
		return absoluteCoords;
	}

	public void setAbsoluteCoords(Coords absoluteCoords) {
		this.absoluteCoords = absoluteCoords;
	}

	public float getOrientation() {
		return orientation;
	}

	public boolean isDead() {
		return dead;
	}

	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	
}
