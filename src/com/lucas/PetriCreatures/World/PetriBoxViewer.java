package com.lucas.PetriCreatures.World;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.JPanel;

public class PetriBoxViewer extends JPanel{
	private float zoom;
	private Coords viewCenter;
	private PetriBox petriBox;
	private HashMap<String,Image> images;
	
	public PetriBoxViewer(PetriBox petriBox) {
		this.petriBox= petriBox;
		zoom = 1;
		viewCenter = new Coords(0,0);
		images = new HashMap<String,Image>();
		
	}
	public PetriBoxViewer(PetriBox petriBox,float zoom,Coords viewCenter) {
		this.petriBox= petriBox;
		this.zoom = zoom;
		this.viewCenter = viewCenter;
		images = new HashMap<String,Image>();
	}
	private static void initImages() {
		
	}
	@Override
	public void paintComponent(Graphics g) {
		
	}
}
