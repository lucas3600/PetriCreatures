package com.lucas.PetriCreatures.Creatures;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.lucas.PetriCreatures.Creatures.Brain.Brain;
import com.lucas.PetriCreatures.Creatures.Entity.Entity;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.ABlock;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.Block;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.BlockType;
import com.lucas.PetriCreatures.HMI.PetriBoxViewer;
import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.Utils.Force;
import com.lucas.PetriCreatures.Utils.MassPoint;
import com.lucas.PetriCreatures.Utils.Vector;
import com.lucas.PetriCreatures.World.Chunk;
import com.lucas.PetriCreatures.World.PetriBox;

public class Creature extends Entity {
	private Brain brain;
	private Coords centerCoords;
	private MassPoint centerOfMass;

	private float health;
	private float hunger;
	private float maxHealth;
	private float maxHunger;

	private float weight;

	private Vector speed;
	private float radialSpeed;

	private Block[] blocks;
	private String genome;

	private ArrayList<Force> forces;

	private Vector acceleration;

	private float radialAcceleration;

	private boolean computed;

	public Creature(Brain brain, Coords centerCoords, float orientation, float health, float hunger, Block[] blocks,
			String genome) {
		super(centerCoords, orientation);
		this.brain = brain;
		this.centerCoords = centerCoords;
		this.health = health;
		this.hunger = hunger;
		this.blocks = blocks;
		for (Block block : this.blocks) {
			block.setOwner(this);
		}
		this.genome = genome;
		forces = new ArrayList<Force>();
		speed = new Vector(0, 0);
		radialSpeed = 0;
		centerOfMass = computeCenterOfMass();
		defineStats();
	}

	public Creature(Brain brain, Coords centerCoords, float health, float hunger, Block[] blocks, String genome) {
		super(centerCoords, 0f);
		this.brain = brain;
		this.centerCoords = centerCoords;
		this.health = health;
		this.hunger = hunger;
		this.blocks = blocks;
		for (Block block : this.blocks) {
			block.setOwner(this);
		}
		this.genome = genome;
		forces = new ArrayList<Force>();
		speed = new Vector(0, 0);
		radialSpeed = 0;
		centerOfMass = computeCenterOfMass();
		defineStats();
	}

	private void defineStats() {

		for (Block block : blocks) {
			maxHealth += block.getLifeContribution();
			maxHunger += block.getHungerContribution();
		}
	}

	/**
	 * Description du comportement d'une créature :
	 * 1-Récupération & vérification de mort
	 * 		Si la vie est à 0 -> mort
	 * 		Si la fin est à 0 -> dégats de 2 
	 * 		Si la fin est supérieur à 40% du maximum alors on rajoute de 2 PV et on consomme 2 points de faim
	 * 1-Activation des différents blocs
	 * 2-Application des thérorèmes de la dynamique et du moment 
	 * 
	 */
	@Override
	public void tick() {
		if(health <= 0) {setDead(true);return;}	
		if(hunger < 0 || hunger == 0) {takeDamage(2);return;}
		if(hunger > maxHunger*0.4) {health+=2;hunger-=2;}
		
		forces.clear();
		for (Block block : blocks) {
			block.activate();
		}
		if (this.getSpeed().getNorm() != 0)// ON ajoute une force de Coulomb de coefficient de frottement 0,5
			forces.add(new Force(centerCoords, this.getSpeed().mult(-1 / getSpeed().getNorm()).mult(0.5f)));
		compute();
		Coords curCoords = this.getAbsoluteCoords();
		Coords newCoords = new Coords(curCoords.getX() + speed.getX() * PetriBox.timeConstant,
				curCoords.getY() - speed.getY() * PetriBox.timeConstant);// Alors ici c'est moins parce que je pense
																			// qu'il y a un soucis
		// de compatibilité entre les coordonnées trigonométriques et les coordonnées de
		// l'écran
		// Pour résoudre ça c'est un moins et ça fonctionne très bien
		this.setAbsoluteCoords(newCoords);
		this.setOrientation(this.getOrientation() + radialSpeed * PetriBox.timeConstant);
	}

	@Override
	public void draw(Graphics2D g2d) {
		Coords rotCoords = getAbsoluteCoords().sum(centerOfMass);
		g2d.rotate(-getOrientation(), rotCoords.getX(), rotCoords.getY());
		for (ABlock block : blocks) {

			if (Block.class.isAssignableFrom(block.getClass())) {
				Block cBlock = (Block) block;
				Coords c = cBlock.getRelativeCoords().sum(this.getAbsoluteCoords());
				Image image = PetriBoxViewer.images.get(cBlock.getType().name());
				g2d.drawImage(rotateImage(cBlock.getDirection(), image), (int) ((c.getX() - Block.blockLengthSide / 2)),
						(int) ((c.getY() - Block.blockLengthSide / 2)), (int) (Block.blockLengthSide),
						(int) (Block.blockLengthSide), null);
			}
		}
		g2d.rotate(getOrientation(), rotCoords.getX(), rotCoords.getY());
	}

	/*
	 * Je voulais utiliser la même méthode pour donner la direction des blocs que
	 * celle plus haut pour l'affichage des créatures Mais visiblement cela induit
	 * un décalage dans l'affichage voilà l'ancien code essayé : Coords blockCoords
	 * = cBlock.getRelativeCoords().sum(this.getAbsoluteCoords());
	 * g2d.rotate(-cBlock.getDirection() * Math.PI, blockCoords.getX(),
	 * blockCoords.getY()); g2d.drawImage(image, (int) ((c.getX() -
	 * Block.blockLengthSide / 2)), (int) ((c.getY() - Block.blockLengthSide / 2)),
	 * (int) (Block.blockLengthSide), (int) (Block.blockLengthSide), null);
	 * g2d.rotate(cBlock.getDirection() * Math.PI, blockCoords.getX(),
	 * blockCoords.getY()); Pour pallier ça j'ai créé une petite méthode qui
	 * retourne juste l'image de Pi/2,Pi,3Pi/2 qui semble plus efficace et qui
	 * permet de palier ce "bug"
	 */
	private Image rotateImage(int direction, Image image) {
		BufferedImage imageBis = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) imageBis.getGraphics();
		g.rotate(-direction * Math.PI / 2, imageBis.getWidth() / 2, imageBis.getHeight() / 2);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return imageBis;
	}

	public void addForce(Force force) {
		forces.add(force);
		computed = false;
	}

	public void flush() {
		forces.clear();
		computed = false;
	}

	private void compute() {
		Vector resulting = new Vector(0, 0);
		float torque = 0;

		// MassPoint centerOfMass = new MassPoint(centerCoords.getX(),
		// centerCoords.getY(), 1);
		float momentInertia = getMomentOfInertia(centerOfMass);
		for (Force force : forces) {
			resulting = force.sum(resulting);
			Vector distance = new Vector(force.getApplicationPoint(), centerOfMass);
			torque += distance.crossProduct(force);
		}
		acceleration = new Force(centerOfMass, resulting.getX() / centerOfMass.getWeight(),
				resulting.getY() / centerOfMass.getWeight());
		radialAcceleration = torque / momentInertia;
		speed = speed.sum(acceleration.mult(PetriBox.timeConstant));
		radialSpeed += radialAcceleration * PetriBox.timeConstant;
		setOrientation(getOrientation() + radialAcceleration * PetriBox.timeConstant);
		computed = true;
	}

	public float getRadialAcceleration() {
		if (!computed)
			compute();
		return radialAcceleration;
	}

	public Vector getAcceleration() {
		if (!computed)
			compute();
		return acceleration;
	}

	private MassPoint computeCenterOfMass() {
		float x = 0;
		float y = 0;
		float weight = 0;
		for (Block block : blocks) {
			x += block.getWeight() * block.getRelativeCoords().getX();
			y += block.getWeight() * block.getRelativeCoords().getY();
			weight += block.getWeight();
		}
		x = x / weight;
		y = y / weight;
		return new MassPoint(x, y, weight);
	}

	private float getMomentOfInertia(Coords gravityCenter) {
		float inertia = 0;
		for (Block block : blocks) {
			float distance = new Vector(block.getRelativeCoords(), gravityCenter).getNorm();
			inertia += (distance * distance) * block.getWeight();
		}
		if (inertia == 0)// Ce n'est pas possible ça fait un bug dans la matrice
			inertia = 1;
		return inertia;
	}

	@Override
	public void checkCollision(Entity e) {
		for (Block b : blocks) {
			// C'est pas du tout opti
			// En effet, pour une créature l'algo va d'abord regarder si il y a contacte en
			// testant la collision avec tous
			// Les blocs de la créature avant de retester tout ça .. Bof hein
			if (b.checkCollision(e))
				b.contact(e);
		}
	}

	public void takeDamage(float f) {
		health -= f;
		if (health < 0)
			setDead(true);
	}

	public void setHunger(float hunger) {
		this.hunger = hunger;
		if (hunger > maxHunger)
			hunger = maxHunger;
	}

	public Brain getBrain() {
		return brain;
	}

	public Coords getCenterCoords() {
		return centerCoords;
	}

	public float getHealth() {
		return health;
	}

	public float getHunger() {
		return hunger;
	}

	public ABlock[] getBlocks() {
		return blocks;
	}

	public void setBrain(Brain brain) {
		this.brain = brain;
	}

	public void setCenterCoords(Coords centerCoords) {
		this.centerCoords = centerCoords;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setBlocks(Block[] blocks) {
		this.blocks = blocks;
	}

	public String getGenome() {
		return genome;
	}

	public void setGenome(String genome) {
		this.genome = genome;
	}

	public float getWeight() {
		return weight;
	}

	public Vector getSpeed() {
		return speed;
	}

	public void setSpeed(Vector speed) {
		this.speed = speed;
	}

	@Override
	public void resolvingContainment(PetriBox box) {
		Coords c = PetriBox.CoordsToChunkCoords(this.getAbsoluteCoords());
		Chunk chunk = box.getChunk((int) (c.getX()), (int) (c.getY()));
		chunk.add(this);
	}

	public float getRadialSpeed() {
		return radialSpeed;
	}

	public void setRadialSpeed(float radialSpeed) {
		this.radialSpeed = radialSpeed;
	}

	public MassPoint getCenterOfMass() {
		return centerOfMass;
	}

}
