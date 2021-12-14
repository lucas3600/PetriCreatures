package com.lucas.PetriCreatures;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewerOf extends JFrame{
	private JPanel pan;
	public ViewerOf(JPanel pan) {
		this.pan = pan;
		
		this.setTitle("Game Viewer");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(pan	);
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
		new Thread(()->{
			while(true) {
				pan.repaint();
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
