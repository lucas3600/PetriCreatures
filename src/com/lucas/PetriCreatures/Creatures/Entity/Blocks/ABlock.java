package com.lucas.PetriCreatures.Creatures.Entity.Blocks;

import com.lucas.PetriCreatures.Creatures.Creature;
import com.lucas.PetriCreatures.Creatures.Entity.Entity;
import com.lucas.PetriCreatures.Utils.Coords;

public abstract class ABlock implements IBlock{
	private ABlock[] neighbors;
	private String id;
	private Creature owner;
	private Coords relativeCoords;
	private int direction;
	protected float input;
	protected float output;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static final float blockLengthSide =5;
	
	public ABlock(String id,ABlock[] neighbors, Creature owner, Coords relativeCoords,int direction) {
		this.neighbors = neighbors;
		this.owner = owner;
		this.id = id;
		this.direction = direction;
		this.relativeCoords = relativeCoords;
	}

	public ABlock[] getNeighbors() {
		return neighbors;
	}

	public Creature getOwner() {
		return owner;
	}

	public Coords getRelativeCoords() {
		return relativeCoords;
	}

	public void setNeighbors(ABlock[] neighbors) {
		this.neighbors = neighbors;
	}

	public void setOwner(Creature owner) {
		this.owner = owner;
	}

	public void setRelativeCoords(Coords relativeCoords) {
		this.relativeCoords = relativeCoords;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public void setInput(float input) {
	this.input = input;
	}

	@Override
	public float getOutput() {
		// TODO Auto-generated method stub
		return output;
	}

	public String getId() {
		return id;
	}

}
