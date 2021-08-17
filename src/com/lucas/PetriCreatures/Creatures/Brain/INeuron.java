package com.lucas.PetriCreatures.Creatures.Brain;

public interface INeuron {
	public void compute();
	public void addInput(float input);
	/***
	 * Retourne la sortie du neurone brute
	 * @return
	 */
	public float getOutput();
	/***
	 * Retourne la sortie d'une neurone pour une connexion descendante spécifique
	 * (Sous entendu pondéré mais bien sur cela laisse libre cours à d'autres initiatives)
	 * @param n
	 * @return
	 */
	public float getOutput(int n);
	public void flush();
}
