package com.lucas.PetriCreatures.Creatures.Blocks;

import com.lucas.PetriCreatures.Creatures.Creature;
import com.lucas.PetriCreatures.World.Coords;

public abstract class Block {
	private Block[] neighbors;
	private Creature owner;
	private Coords relativeCoords;
	private Coords absoluteCoords;

	public Block(Block[] neighbors, Creature owner, Coords relativeCoords, Coords absoluteCoords) {
		super();
		this.neighbors = neighbors;
		this.owner = owner;
		this.relativeCoords = relativeCoords;
		this.absoluteCoords = absoluteCoords;
	}

	public Block[] getNeighbors() {
		return neighbors;
	}

	public Creature getOwner() {
		return owner;
	}

	public Coords getRelativeCoords() {
		return relativeCoords;
	}

	public Coords getAbsoluteCoords() {
		return absoluteCoords;
	}

	public void setNeighbors(Block[] neighbors) {
		this.neighbors = neighbors;
	}

	public void setOwner(Creature owner) {
		this.owner = owner;
	}

	public void setRelativeCoords(Coords relativeCoords) {
		this.relativeCoords = relativeCoords;
	}

	public void setAbsoluteCoords(Coords absoluteCoords) {
		this.absoluteCoords = absoluteCoords;
	}

}
