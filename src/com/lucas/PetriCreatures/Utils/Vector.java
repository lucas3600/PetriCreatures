package com.lucas.PetriCreatures.Utils;

public class Vector {
	private float x;
	private float y;
	private float norm;

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector(Coords A, Coords B) {
		x = B.getX() - A.getX();
		y = B.getY() - A.getY();
	}

	private void computeNorm() {
		norm = (float) Math.sqrt(x * x + y * y);
	}

	public float getNorm() {
		return norm;
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
		computeNorm();
	}

	public void setY(float y) {
		this.y = y;
		computeNorm();
	}
}
