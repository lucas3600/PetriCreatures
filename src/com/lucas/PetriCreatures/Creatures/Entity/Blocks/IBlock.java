package com.lucas.PetriCreatures.Creatures.Entity.Blocks;

import com.lucas.PetriCreatures.Creatures.Entity.Entity;

public interface IBlock {
	/**
	 * L'action de ce bloc sur l'entit� avec qui il a contact
	 * M�thode appel� lorsqu'il y a collision
	 * L'autre bloc re�oit le m�me appel 
	 * @param b
	 */
	public void contact(Entity e);
	public void activate();
	public void setInput(float input);
	public float getOutput();
}
