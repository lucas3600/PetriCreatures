package com.lucas.PetriCreatures.Creatures.Entity.Blocks;

import com.lucas.PetriCreatures.Creatures.Creature;
import com.lucas.PetriCreatures.Creatures.Entity.Entity;
import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.Utils.Force;

public class Block extends ABlock {
	private BlockType type;
	private boolean attacker;
	private float harshness;
	private float damage;
	private float hungerContribution;
	private float lifeContribution;
	
	public Block(String id,BlockType type, int direction) {
		super(id,null,null,null,direction);
		this.type = type;
		if (type == BlockType.Bone) {
			harshness = 20;
			attacker = false;
			hungerContribution = 0;
			lifeContribution = 20;
			damage = 1;
		} else if (type == BlockType.Center) {
			attacker = false;
			harshness = 5;
			hungerContribution = 2;
			lifeContribution = 2;
			damage = 0;
		} else if (type == BlockType.Contact || type == BlockType.Eye) {
			harshness = 3;
			attacker = false;
			hungerContribution = 1;
			lifeContribution = 1;
			damage = 0;
		} else if (type == BlockType.Fat) {
			harshness = 6;
			hungerContribution = 20;
			lifeContribution = 5;
			damage = 0;
		} else if (type == BlockType.Mouth) {
			harshness = 3;
			attacker = true;
			hungerContribution = 2;
			lifeContribution = 3;
			damage = 15;
		} else if (type == BlockType.Spike) {
			harshness = 15;
			hungerContribution = 0;
			lifeContribution = 0;
			damage = 15;
			attacker = true;
		} else if (type == BlockType.Thruster) {
			harshness = 5;
			hungerContribution = 1;
			lifeContribution = 1;
			damage = 0;
			attacker = false;
		} else if (type == BlockType.Tissue) {
			harshness = 2;
			attacker = false;
			hungerContribution = 5;
			lifeContribution = 5;
			damage = 0;
			attacker = false;
		}
	}
	public Block(String id,BlockType type, ABlock[] neighbors, Creature owner, Coords relativeCoords, int direction) {
		super(id,neighbors, owner, relativeCoords, direction);
		this.type = type;
		if (type == BlockType.Bone) {
			harshness = 20;
			attacker = false;
			hungerContribution = 0;
			lifeContribution = 20;
			damage = 1;
		} else if (type == BlockType.Center) {
			attacker = false;
			harshness = 5;
			hungerContribution = 2;
			lifeContribution = 2;
			damage = 0;
		} else if (type == BlockType.Contact || type == BlockType.Eye) {
			harshness = 3;
			attacker = false;
			hungerContribution = 1;
			lifeContribution = 1;
			damage = 0;
		} else if (type == BlockType.Fat) {
			harshness = 6;
			hungerContribution = 20;
			lifeContribution = 5;
			damage = 0;
		} else if (type == BlockType.Mouth) {
			harshness = 3;
			attacker = true;
			hungerContribution = 2;
			lifeContribution = 3;
			damage = 15;
		} else if (type == BlockType.Spike) {
			harshness = 15;
			hungerContribution = 0;
			lifeContribution = 0;
			damage = 15;
			attacker = true;
		} else if (type == BlockType.Thruster) {
			harshness = 5;
			hungerContribution = 1;
			lifeContribution = 1;
			damage = 0;
			attacker = false;
		} else if (type == BlockType.Tissue) {
			harshness = 2;
			attacker = false;
			hungerContribution = 5;
			lifeContribution = 5;
			damage = 0;
			attacker = false;
		}
	}
	@Override
	public void contact(Entity e) {
		if (e.getClass().isAssignableFrom(Block.class)) {

		}
	}

	@Override
	public void activate() {
		if (type == BlockType.Thruster) {
			float orientation = (float) (this.getOwner().getOrientation() - this.getDirection() * Math.PI / 2);
			this.getOwner().addForce(new Force(this.getRelativeCoords(), (float) (Math.cos(orientation) * input),
					(float) (Math.sin(orientation) * input)));
		}
	}

	public BlockType getType() {
		return type;
	}

	public boolean isAttacker() {
		return attacker;
	}

	public float getHarshness() {
		return harshness;
	}

	public float getDamage() {
		return damage;
	}

	public float getHungerContribution() {
		return hungerContribution;
	}

	public float getLifeContribution() {
		return lifeContribution;
	}

}
