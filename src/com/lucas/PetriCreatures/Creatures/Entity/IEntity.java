package com.lucas.PetriCreatures.Creatures.Entity;

import java.awt.Graphics2D;

import com.lucas.PetriCreatures.World.PetriBox;

public interface IEntity {
	public void tick();
	public void resolvingContainment(PetriBox box);
	public void draw(Graphics2D g2d);
	public void checkCollision(Entity e);
}
