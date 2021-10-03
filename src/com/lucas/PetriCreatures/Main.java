package com.lucas.PetriCreatures;

import com.lucas.PetriCreatures.Creatures.Entity.Food;
import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.World.PetriBox;
import com.lucas.PetriCreatures.World.PetriBoxViewer;

public class Main {

	public static void main(String[] args) {
		PetriBox petriBox = new PetriBox(2,2);
		PetriBoxViewer petriViewer = new PetriBoxViewer(petriBox);
		
		for(int i = 0;i < 10;i++) {
			petriBox.addEntity(new Food(new Coords((float)(Math.random()*100),(float)(Math.random()*100)),(int) (Math.random()*10)));
		}
		
		
		ViewerOf frame = new ViewerOf(petriViewer);
		frame.addKeyListener(petriViewer);
	}

}
