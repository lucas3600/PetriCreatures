package com.lucas.PetriCreatures.Creatures.Entity.Blocks;

import com.lucas.PetriCreatures.Creatures.Creature;
import com.lucas.PetriCreatures.Creatures.Entity.Entity;
import com.lucas.PetriCreatures.Creatures.Entity.Food;
import com.lucas.PetriCreatures.Creatures.Entity.Vegetable;
import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.Utils.Force;
import com.lucas.PetriCreatures.Utils.Vector;

public class Block extends ABlock {
	private BlockType type;
	private boolean attacker;
	private float harshness;
	private float damage;
	private float hungerContribution;
	private float lifeContribution;
	private float weight;

	public Block(String id, BlockType type, int direction) {
		super(id, new ABlock[4], null, null, direction);
		this.type = type;
		if (type == BlockType.Bone) {
			harshness = 20;
			attacker = false;
			hungerContribution = 0;
			lifeContribution = 20;
			damage = 1;
			weight = 5;
		} else if (type == BlockType.Center) {
			attacker = false;
			harshness = 5;
			hungerContribution = 2;
			lifeContribution = 2;
			damage = 0;
			weight = 1;
		} else if (type == BlockType.Contact || type == BlockType.Eye) {
			harshness = 3;
			attacker = false;
			hungerContribution = 1;
			lifeContribution = 1;
			damage = 0;
			weight = 1;
		} else if (type == BlockType.Fat) {
			harshness = 6;
			hungerContribution = 20;
			lifeContribution = 5;
			damage = 0;
			weight = 3;
		} else if (type == BlockType.Mouth) {
			harshness = 3;
			attacker = true;
			hungerContribution = 2;
			lifeContribution = 3;
			damage = 15;
			weight = 1;
		} else if (type == BlockType.Spike) {
			harshness = 15;
			hungerContribution = 0;
			lifeContribution = 0;
			damage = 15;
			attacker = true;
			weight = 1.5f;
		} else if (type == BlockType.Thruster) {
			harshness = 5;
			hungerContribution = 1;
			lifeContribution = 1;
			damage = 0;
			attacker = false;
			weight = 1;
		} else if (type == BlockType.Tissue) {
			harshness = 2;
			attacker = false;
			hungerContribution = 5;
			lifeContribution = 5;
			damage = 0;
			attacker = false;
			weight = 1;
		}
	}

	public Block(String id, BlockType type, ABlock[] neighbors, Creature owner, Coords relativeCoords, int direction) {
		super(id, neighbors, owner, relativeCoords, direction);
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
		if (e.getClass().isAssignableFrom(Creature.class)) {
			Block contactBlock = null;
			Creature c = (Creature)e;
			for(ABlock b : c.getBlocks()) {
				Vector distance = new Vector(this.getAbsoluteCoords(),b.getAbsoluteCoords());
				if(distance.getNorm() < 2 * Block.blockLengthSide) {
					contactBlock = (Block) b;
					break;
				}
				
			}
			c.takeDamage(damage/contactBlock.getHarshness());
		} else if (e.getClass().isAssignableFrom(Food.class)) {
			if (type == BlockType.Mouth) {
				Food f = (Food) (e);
				f.setAmount(f.getAmount() - 2);
				this.getOwner().setHunger(this.getOwner().getHunger() + 2);
			}
		} else if (e.getClass().isAssignableFrom(Vegetable.class)) {
			
		}
	}

	@Override
	public void activate() {
		if (type == BlockType.Thruster) {
			input = 1;
			float orientation = (float) (this.getOwner().getOrientation() - this.getDirection() * Math.PI / 2);
			this.getOwner().addForce(new Force(this.getRelativeCoords(), (float) (Math.cos(orientation) * input),
					(float) (Math.sin(orientation) * input)));
		}
	}

	@Override
	public boolean checkCollision(Entity e) {
		// On utilise la collision par cercle 
		if (e.getClass().isAssignableFrom(Creature.class)) {
			Creature c = (Creature)e;
			for(ABlock b : c.getBlocks()) {
				Vector distance = new Vector(this.getAbsoluteCoords(),b.getAbsoluteCoords());
				if(distance.getNorm() < 2 * Block.blockLengthSide)
					return true;
			}
		} else if (e.getClass().isAssignableFrom(Food.class)) {
			//Le rayon d'un cercle de nourriture est de Food.amount
			//On va utiliser cette petite classe #Recup pour calculer facilement la distance entre les deux centres et on la comparerra à la distance minimum pour collision
			Vector distance = new Vector(e.getAbsoluteCoords(),this.getAbsoluteCoords());
			if(distance.getNorm() < (ABlock.blockLengthSide+((Food)e).getAmount())) 
				return true;
			
		}
		return false;
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

	public float getWeight() {
		return weight;
	}

}
