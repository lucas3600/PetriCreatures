package com.lucas.PetriCreatures.HMI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Un petit menu de bas de fenetres pour contenir quelques outils afin
 * d'intéragir avec la boite de petri : -Des boutons pour naviguer entre les
 * créatures -Des boutons pour gérer le temps -Un bouton pour ouvrir l'arbre
 * évolutif
 * 
 * @author lucas
 * 
 * 
 */
public class Tools extends JPanel {
	private JButton previous;
	private JButton next;
	private JButton pause;
	private JButton play;
	
	public Tools() {
		previous = new JButton("Previous");
		next = new JButton("Next");
		pause = new JButton("Pause");
		play = new JButton("Play");
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		this.add(previous,c);
		c.gridy=1;
		this.add(next,c);
		c.gridx = 3;
		c.gridy = 0;
		this.add(pause,c);
		c.gridy = 1;
		this.add(play,c);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		this.add(new JPanel(),c);
		c.gridy = 1;
		this.add(new JPanel(),c);
	}
}
