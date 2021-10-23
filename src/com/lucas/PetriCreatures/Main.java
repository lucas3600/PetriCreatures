package com.lucas.PetriCreatures;

import com.lucas.PetriCreatures.Creatures.Creature;
import com.lucas.PetriCreatures.Creatures.CreatureFactory;
import com.lucas.PetriCreatures.Creatures.Entity.Food;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.ABlock;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.Block;
import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.World.PetriBox;
import com.lucas.PetriCreatures.World.PetriBoxViewer;

public class Main {

	public static void main(String[] args) {
		PetriBox petriBox = new PetriBox(5, 5);
		PetriBoxViewer petriViewer = new PetriBoxViewer(petriBox);

		for (int i = 0; i < 10; i++) {
			petriBox.addEntity(new Food(new Coords((float) (Math.random() * 100), (float) (Math.random() * 100)),
					(int) (Math.random() * 10)));
		}
		int counter = 0;
		int globalCounter = 0;
		Creature creature = CreatureFactory
				.interpretGenome("AFFFFFAAAAAAAAAACAAAAFFFFAAAAAAAAAAACAAAAFFFFAAAAAAAAAAACAAAAFFFFA");
		petriBox.addEntity(creature, new Coords((float) (Math.random() * 5 * 50), (float) (Math.random() * 5 * 50)));
		/*
		 * Creature creature = CreatureFactory.interpretGenome(
		 * "AFFFFFAAAAAAAAAACAAAAFFFFAAAAAAAAAAACAAAAFFFFAAAAAAAAAAACAAAAFFFFA");
		 * petriBox.addEntity(creature,new
		 * Coords((float)(Math.random()*5*50),(float)(Math.random()*5*50)));
		 */
		/*
		 * while(true) { Creature creature =
		 * CreatureFactory.interpretGenome(generateRandomGenome());
		 * if(creature.getBlocks().length > 1) { petriBox.addEntity(creature,new
		 * Coords((float)(Math.random()*5*50),(float)(Math.random()*5*50))); counter++;
		 * }
		 * 
		 * if(counter > 1) break; globalCounter++; }
		 */
		System.out.println(globalCounter);
		ViewerOf frame = new ViewerOf(petriViewer);
		frame.addKeyListener(petriViewer);

	}

	public static String generateRandomGenome() {
		String genome = "AFFFFF";
		int n = 100;
		for (int i = 0; i < n; i++) {
			int l = (int) (Math.random() * 41);
			if (l < 10)
				genome += "A";
			else if (l < 20)
				genome += "B";
			else if (l < 30)
				genome += "C";
			else if (l < 40)
				genome += "D";
			else
				genome += "F";
		}

		return genome;
	}
}
