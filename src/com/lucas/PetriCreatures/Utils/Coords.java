package com.lucas.PetriCreatures.Utils;

public class Coords {
	private float x;
	private float y;
	public Coords(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Coords sum(Coords c) {
		return new Coords(x+c.getX(),y+c.getY());
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return x+";"+y;
	}
	
	
}
