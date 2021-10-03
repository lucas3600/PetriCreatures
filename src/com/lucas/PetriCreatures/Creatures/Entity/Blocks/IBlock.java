package com.lucas.PetriCreatures.Creatures.Entity.Blocks;

import com.lucas.PetriCreatures.Creatures.Entity.Entity;

public interface IBlock {
	/**
	 * L'action de ce bloc sur l'entité avec qui il a contact
	 * Méthode appelé lorsqu'il y a collision
	 * L'autre bloc reçoit le même appel 
	 * @param b
	 */
	public void contact(Entity e);
	public void activate();
	public void setInput(float input);
	public float getOutput();
}
