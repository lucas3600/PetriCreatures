package com.lucas.PetriCreatures.Utils;

public class Force extends Vector{
	private Coords applicationPoint;

	public Force(Coords applicationPoint,float x, float y) {
		super(x, y);
		this.applicationPoint = applicationPoint;
	}
	public Force(float aX,float aY,float nX,float nY) {
		super(nX,nY);
		applicationPoint = new Coords(aX,aY);
	}
	public Coords getApplicationPoint() {
		return applicationPoint;
	}
	public void setApplicationPoint(Coords applicationPoint) {
		this.applicationPoint = applicationPoint;
	}
	
}
