package com.lucas.PetriCreatures.Creatures.Entity;

import com.lucas.PetriCreatures.World.PetriBox;

public interface IEntity {
	public void tick();
	public void resolvingContainment(PetriBox box);
}
