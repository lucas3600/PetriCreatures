package com.lucas.PetriCreatures.Creatures.Entity;

import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.World.Chunk;
import com.lucas.PetriCreatures.World.PetriBox;

public abstract class SimpliestEntity extends Entity{

	public SimpliestEntity(Coords absoluteCoords, float orientation) {
		super(absoluteCoords, orientation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void resolvingContainment(PetriBox box) {

		Coords c = PetriBox.CoordsToChunkCoords(this.getAbsoluteCoords());
		Chunk chunk = box.getChunk((int)(c.getX()), (int)(c.getY()));
		chunk.add(this);
	}

	@Override
	public void checkCollision(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
