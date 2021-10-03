package com.lucas.PetriCreatures.World;

import com.lucas.PetriCreatures.Creatures.Entity.Entity;
import com.lucas.PetriCreatures.Utils.Coords;

public class PetriBox {
	private Chunk[][] chunks;
	private int widthC;
	private int heightC;
	private int width;
	private int height;
	public static final float timeConstant = 0.1f;
	
	/**
	 * 
	 * @param widthC Le nombre de chunks horizontal
	 * @param heightC Le nombre de chunks vertical
	 */
	public PetriBox(int widthC, int heightC) {
		this.widthC = widthC;
		this.heightC = heightC;
		this.width = widthC*Chunk.width;
		this.height = heightC*Chunk.height;
		chunks = new Chunk[width][];
		for (int x = 0; x < width; x++) {
			chunks[x] = new Chunk[height];
			for (int y = 0; y < height; y++) {
				chunks[x][y] = new Chunk();
			}
		}
	}
	/**
	 * Ajoute une entit� aux coordonn�es en param�tres 
	 * @param entity L'enitt� 
	 * @param c les coordonn�es o� sera ajouter l'entit�. Modifies les coordonn�es assign�s � l'enitt�
	 */
	public void addEntity(Entity entity	,Coords c) {
		entity.setAbsoluteCoords(c);
		entity.resolvingContainment(this);
	}
	/**
	 * Ajoute une entit� aux coordonn�es de l'entit�
	 * @param entity L'entit� � ajouter
	 */
	public void addEntity(Entity entity) {
		entity.resolvingContainment(this);
	}
	/**
	 * Retourne le chunk contenu � la position sp�cifi�e dans la matrice des chunks
	 * @param x
	 * @param y
	 * @return
	 */
	public Chunk getChunk(int x, int y) {
		if (x < 0 || x >= widthC || y < 0 || y >= heightC)
			return null;
		return chunks[x][y];
	}
	/**
	 * Retourne le chunk dans lequel se trouve les coordonn�es sp�cifi�es
	 * @param c
	 * @return
	 */
	public static Coords CoordsToChunkCoords(Coords c) {
		int x = 0;
		int y = 0;
		if(c.getX() < 0)
			x = (int)(c.getX()/Chunk.width)-1;
		else
			x = (int)(c.getX()/Chunk.width);
		if(c.getY() < 0)
			y = (int)(c.getY()/Chunk.height)-1;
		else
			y = (int)(c.getY()/Chunk.height);
		
		return new Coords(x,y);
	}
	public Chunk[][] getChunks() {
		return chunks;
	}
	/**
	 * 
	 * @return La largeur en nombre de chunks
	 */
	public int getWidthC() {
		return widthC;
	}
	/**
	 * 
	 * @return La hauteur en nombre de chunks
	 */
	public int getHeightC() {
		return heightC;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
}
