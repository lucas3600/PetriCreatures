package com.lucas.PetriCreatures.Creatures;

import com.lucas.PetriCreatures.Creatures.Blocks.Block;
import com.lucas.PetriCreatures.Creatures.Brain.Brain;
import com.lucas.PetriCreatures.World.Coords;

public class Creature {
	private Brain brain;
	private Coords centerCoords;
	private float health;
	private float hunger;
	private Block[] blocks;
	private String genome;
	
	public Creature(Brain brain, Coords centerCoords, float health, float hunger, Block[] blocks,String genome) {
		super();
		this.brain = brain;
		this.centerCoords = centerCoords;
		this.health = health;
		this.hunger = hunger;
		this.blocks = blocks;
		this.genome = genome;
	}

	public Brain getBrain() {
		return brain;
	}

	public Coords getCenterCoords() {
		return centerCoords;
	}

	public float getHealth() {
		return health;
	}

	public float getHunger() {
		return hunger;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public void setBrain(Brain brain) {
		this.brain = brain;
	}

	public void setCenterCoords(Coords centerCoords) {
		this.centerCoords = centerCoords;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setHunger(float hunger) {
		this.hunger = hunger;
	}

	public void setBlocks(Block[] blocks) {
		this.blocks = blocks;
	}

	public String getGenome() {
		return genome;
	}

	public void setGenome(String genome) {
		this.genome = genome;
	}
	
	
}
