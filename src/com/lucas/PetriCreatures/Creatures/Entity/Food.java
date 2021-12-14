package com.lucas.PetriCreatures.Creatures.Entity;

import java.awt.Color;
import java.awt.Graphics2D;

import com.lucas.PetriCreatures.Utils.Coords;

public class Food extends SimpliestEntity {
	private int amount;

	public Food(Coords absoluteCoords, int amount) {
		super(absoluteCoords, 0f);
		this.amount = amount;

	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public void tick() {
		if(amount < 0)
			this.setDead(true);
	}

	@Override
	public void draw(Graphics2D g2d) {
		Coords c = this.getAbsoluteCoords();
		g2d.setColor(Color.RED);
		float foodRadius = 2 * this.getAmount();
		// System.out.println((int)(this.getWidth()/2.0+(-foodRadius+c.getX()-viewCenter.getX())*zoom)+"
		// "
		// +(int)(this.getHeight()/2.0+(-foodRadius+c.getY()-viewCenter.getY())*zoom));
		g2d.fillOval((int) ((-foodRadius + c.getX())), (int) ((-foodRadius + c.getY())), (int) ((2 * foodRadius)),
				(int) ((2 * foodRadius)));
	}

}
