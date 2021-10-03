package com.lucas.PetriCreatures.World;

import java.util.ArrayList;

import com.lucas.PetriCreatures.Creatures.Entity.Entity;

public class Chunk {
	public static final int width = 50;
	public static final int height = 50;
	
	private ArrayList<Entity> entities;
	
	public Chunk() {
		entities = new ArrayList<Entity>();
	}
	
	public boolean contains(Entity entity) {
		return entities.contains(entity);
	}
	public void add(Entity entity) {
		entities.add(entity);
	}
	public void remove(Entity entity) {
		entities.remove(entity);
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
}
