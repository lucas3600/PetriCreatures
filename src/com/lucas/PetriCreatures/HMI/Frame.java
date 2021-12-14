package com.lucas.PetriCreatures.HMI;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.lucas.PetriCreatures.World.PetriBox;

public class Frame extends JFrame {
	private PetriBoxViewer petriViewer;
	private InfoViewer infoViewer;
	private SpeciesTree tree;
	private Tools tools;
	private boolean showInfo;

	public Frame(PetriBox box) {
		showInfo = false;

		infoViewer = new InfoViewer();
		petriViewer = new PetriBoxViewer(box);
		tools = new Tools();
		tree = new SpeciesTree();

		this.setTitle("Game Viewer");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(petriViewer, BorderLayout.CENTER);
		this.add(tools, BorderLayout.SOUTH);
		this.setLocationRelativeTo(null);

		this.setVisible(true);
		
		addKeyListener(petriViewer);
		addMouseWheelListener(petriViewer);
		new Thread(() -> {
			while (true) {
				petriViewer.repaint();
				try {
					Thread.sleep(900);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
