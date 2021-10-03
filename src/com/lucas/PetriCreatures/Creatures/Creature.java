package com.lucas.PetriCreatures.Creatures;

import java.util.ArrayList;

import com.lucas.PetriCreatures.Creatures.Brain.Brain;
import com.lucas.PetriCreatures.Creatures.Entity.Entity;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.ABlock;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.Block;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.BlockType;
import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.Utils.Force;
import com.lucas.PetriCreatures.Utils.MassPoint;
import com.lucas.PetriCreatures.Utils.Vector;

public class Creature extends Entity {
	private Brain brain;
	private Coords centerCoords;
	private float health;
	private float hunger;

	private float weight;
	private float speed;

	private Block[] blocks;
	private String genome;

	private ArrayList<Force> forces;

	private ArrayList<MassPoint> points;

	private Vector acceleration;

	private float radialAcceleration;

	private boolean computed;

	public Creature(Brain brain, Coords centerCoords, float orientation, float health, float hunger, Block[] blocks,
			String genome) {
		super(centerCoords, orientation);
		this.brain = brain;
		this.centerCoords = centerCoords;
		this.health = health;
		this.hunger = hunger;
		this.blocks = blocks;
		this.genome = genome;
	}

	public Creature(Brain brain, Coords centerCoords, float health, float hunger, Block[] blocks, String genome) {
		super(centerCoords, 0f);
		this.brain = brain;
		this.centerCoords = centerCoords;
		this.health = health;
		this.hunger = hunger;
		this.blocks = blocks;
		this.genome = genome;
	}
	@Override
	public void tick() {
		for (Block block : blocks) {
			block.activate();
		}
		
	}

	public void addForce(Force force) {
		forces.add(force);
		computed = false;
	}

	public void flush() {
		forces.clear();
		computed = false;
	}

	private void compute() {
		Vector resulting = null;
		float torque = 0;
		MassPoint centerOfMass = getCenterOfMass();
		float momentInertia = 0;
		for (Force force : forces) {
			resulting = force.sum(resulting);
			Vector distance = new Vector(force.getApplicationPoint(), centerOfMass);
			torque += distance.crossProduct(force);
		}

		acceleration = new Force(centerOfMass, resulting.getX() / centerOfMass.getWeight(),
				resulting.getY() / centerOfMass.getWeight());
		this.radialAcceleration = torque;
		computed = true;
	}

	public float getMomentum() {
		if (!computed)
			compute();
		return radialAcceleration;
	}

	public Vector getTranslation() {
		if (!computed)
			compute();
		return acceleration;
	}

	private MassPoint getCenterOfMass() {
		float x = 0;
		float y = 0;
		float weight = 0;
		for (MassPoint point : points) {
			x += point.getWeight() * point.getX();
			y += point.getWeight() * point.getY();
			weight += point.getWeight();
		}
		x = x / weight;
		y = y / weight;
		return new MassPoint(x, y, weight);
	}

	private float getMomentOfInteria(Coords gravityCenter) {
		float inertia = 0;
		for (MassPoint point : points) {
			inertia += (new Vector(point, gravityCenter).getNorm()) * point.getWeight();
		}
		return inertia;
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

	public ABlock[] getBlocks() {
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

	public float getWeight() {
		return weight;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
