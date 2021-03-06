package com.lucas.PetriCreatures.HMI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.lucas.PetriCreatures.Creatures.Creature;
import com.lucas.PetriCreatures.Creatures.Entity.Entity;
import com.lucas.PetriCreatures.Creatures.Entity.Food;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.ABlock;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.Block;
import com.lucas.PetriCreatures.Utils.Coords;
import com.lucas.PetriCreatures.World.Chunk;
import com.lucas.PetriCreatures.World.PetriBox;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.BlockType;

public class PetriBoxViewer extends JPanel implements MouseWheelListener, KeyListener {
	private float zoom;
	private Coords viewCenter;
	private PetriBox petriBox;
	public static HashMap<String, Image> images;

	public PetriBoxViewer(PetriBox petriBox) {
		this.petriBox = petriBox;
		zoom = 1;
		viewCenter = new Coords(petriBox.getWidth() / 2, petriBox.getHeight() / 2);
		images = new HashMap<String, Image>();
		initImages();

	}

	public PetriBoxViewer(PetriBox petriBox, float zoom, Coords viewCenter) {
		this.petriBox = petriBox;
		this.zoom = zoom;
		this.viewCenter = viewCenter;
		images = new HashMap<String, Image>();
		initImages();
	}

	private static void initImages() {
		images.put("Bone", loadImage("Ressources/Creatures/Bone.bmp"));
		images.put("Thruster", loadImage("Ressources/Creatures/Thruster.bmp"));
		images.put("Tissue", loadImage("Ressources/Creatures/Tissue.bmp"));
		images.put("Spike", loadImage("Ressources/Creatures/Spike.bmp"));
		images.put("Eye", loadImage("Ressources/Creatures/Eye.bmp"));
		images.put("Fat", loadImage("Ressources/Creatures/Fat.bmp"));
		images.put("Mouth", loadImage("Ressources/Creatures/Mouth.bmp"));
		images.put("Center", loadImage("Ressources/Creatures/Center.bmp"));
		images.put("Contact", loadImage("Ressources/Creatures/Contact.bmp"));
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		// Donc on affiche tout les chunks contenus dans un cercle dont on calcule
		// d'abord le diam?tre ? partir de la largeur de la boite
		float radius = petriBox.getWidth() / (zoom);
		// Ici on triche pour simplifier les calculs on affiche un carr? de cot? radius
		/**
		 * En fait un chunk peut ?tre un rectangle donc on calcule les demi cot?s pour
		 * parcourir la matrice
		 */
		int radiusXChunk = (int) (radius / Chunk.width) + 1;
		int radiusYChunk = (int) (radius / Chunk.height) + 1;
		Coords chunkCoords = PetriBox.CoordsToChunkCoords(viewCenter);
		// Ici il faut d?terminer quelle partie du graphics va ?tre utilis? pour le
		// dessin et qu'il faut nettoyer

		g2d.translate((getWidth() / 2), (getHeight() / 2));
		g2d.scale(zoom, zoom);
		g2d.translate(-viewCenter.getX(), -viewCenter.getY());
		
		petriBox.tickTheBox();
		
		for (int x = -radiusXChunk; x <= radiusXChunk; x++) {
			for (int y = -radiusYChunk; y <= radiusYChunk; y++) {

				Chunk chunk = petriBox.getChunk((int) (chunkCoords.getX()) + x, (int) (chunkCoords.getY()) + y);
				if (chunk == null)
					continue;
				ArrayList<Entity> entities = chunk.getEntities();
				for (Entity entity : entities) {
					entity.draw(g2d);
				}
			}
		}

	}

	private static Image loadImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			viewCenter.setY(viewCenter.getY() - 2 / zoom);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			viewCenter.setY(viewCenter.getY() + 2 / zoom);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			viewCenter.setX(viewCenter.getX() + 2 / zoom);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			viewCenter.setX(viewCenter.getX() - 2 / zoom);
		} else if (e.getKeyCode() == 107) {// Numpad Plus
			zoom = zoom * 2;

		} else if (e.getKeyCode() == 109) {// NUmpad minus
			zoom = zoom / 2;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom += e.getPreciseWheelRotation();
	}
}
