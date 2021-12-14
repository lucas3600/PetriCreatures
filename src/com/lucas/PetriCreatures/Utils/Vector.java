package com.lucas.PetriCreatures.Utils;

public class Vector {
	private float x;
	private float y;
	private float norm;
	private boolean isNormCal;
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
		isNormCal = false;
	}

	public Vector(Coords A, Coords B) {
		x = B.getX() - A.getX();
		y = B.getY() - A.getY();
	}

	private void computeNorm() {
		norm = (float) Math.sqrt(x * x + y * y);
		isNormCal = true;
	}

	public float getNorm() {
		if(!isNormCal)
			computeNorm();
		return norm;
	}
	/**
	 * Multiplie le vecteur par un r�el
	 * @param r Un r�el
	 * @return Le vecteur multipli� par le r�el r
	 */
	public Vector mult(float r) {
		return new Vector(getX()*r,getY()*r);
	}
	public float scal(Vector v) {
		return v.getX() * x + v.getY() * y;
	}

	public Vector sum(Vector v) {
		if (v == null)
			return this;
		return new Vector(x + v.getX(), y + v.getY());
	}

	public float crossProduct(Vector vector) {
		return x * vector.getY() - y * vector.getX();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
		isNormCal = false;
	}

	public void setY(float y) {
		this.y = y;
		isNormCal = false;
	}
	public String toString() {
		return "Vecteur "+x+";"+y;
	}
}
