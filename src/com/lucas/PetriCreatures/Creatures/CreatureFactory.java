package com.lucas.PetriCreatures.Creatures;

import java.util.ArrayList;

import com.lucas.PetriCreatures.Creatures.Entity.Blocks.ABlock;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.Block;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.BlockType;
import com.lucas.PetriCreatures.Utils.Coords;

/**
 * En cours Note : un codon code pour 1024 valeurs (4^5) donc en float on fait
 * int(codon)-512/512.0f et en int ben voilà Il y a cinq marqueurs A,B,C,D,F F
 * est utilisé pour repérer les codons marqueurs de fin ou des choses comme ça
 * 
 * @author lucas
 *
 */
public class CreatureFactory {
	private static class ScalableMatrix {
		private ArrayList<ArrayList<Block>> rightMatrix;
		private ArrayList<ArrayList<Block>> leftMatrix;

		public ScalableMatrix() {
			rightMatrix = new ArrayList<ArrayList<Block>>();
			leftMatrix = new ArrayList<ArrayList<Block>>();
		}

		public ArrayList<Block> getColumn(int column) {
			if (column >= 0) {
				if (rightMatrix.size() <= column)
					return null;
				else
					return rightMatrix.get(column);
			} else {
				if (leftMatrix.size() >= -column)
					return leftMatrix.get(-column - 1);
				else
					return null;
			}
		}

		private Block getInColumnRow(ArrayList<Block> column, int row) {
			if(row < 0)
				return null;
			else if (row >= column.size())
				return null;
			else
				return column.get(row);
		}

		public Block getBlock(int column, int row) {
			ArrayList<Block> arrayColumn = getColumn(column);
			if (arrayColumn != null) {
				Block block = getInColumnRow(arrayColumn, row);
				return block;
			}
			return null;
		}

		public boolean addBlock(int column, int row, Block block) {
			if (column >= 0) {
				if (column >= rightMatrix.size()) {
					int difference = column - rightMatrix.size() + 1;
					for (int i = 0; i < difference; i++) {
						rightMatrix.add(new ArrayList<Block>());
					}
				}
			} else {
				if (-column > leftMatrix.size()) {
					int difference = -column - leftMatrix.size();
					for (int i = 0; i < difference; i++) {
						leftMatrix.add(new ArrayList<Block>());
					}
				}

			}
			ArrayList<Block> arrayColumn = getColumn(column);
			if (arrayColumn.size() <= row) {

				int difference = arrayColumn.size() - row + 1;
				for (int i = 0; i < difference - 1; i++) {
					arrayColumn.add(null);
				}
				// La dernière case a ajouté pour agrandir la colonne est la case contenant le
				// block spécifié alors on l'ajoute ici. OPTIMISATION NOUS VOILA
				arrayColumn.add(block);
				return true;
			}
			if (arrayColumn.get(row) == null) {
				arrayColumn.set(row, block);
				return true;
			}
			return false;
		}

		public boolean setBlock(int column, int row, Block block) {
			ArrayList<Block> arrayColumn = getColumn(column);
			if (arrayColumn == null)
				return false;
			if (row >= arrayColumn.size())
				return false;
			else
				arrayColumn.set(row, block);
			return true;
		}

		public void addBlockOnTopOf(int column, Block block) {
			ArrayList<Block> arrayColumn = getColumn(column);
			if (arrayColumn == null) {
				addBlock(column, 0, block);
			} else {
				addBlock(column, arrayColumn.size(), block);
			}
		}

		public int leftSize() {
			return leftMatrix.size();
		}

		public int rightSize() {
			return -rightMatrix.size();
		}
	}

	/**
	 * Le genome est organisé en codons Les codons sont composés de 5 lettres allant
	 * de Le marqueur de fin de ligne est FFFFF
	 * 
	 * @param genome
	 * @return
	 */
	public static Creature interpretGenome(String genome) {
		String[] splitedGenome = genome.split("FFFFF");
		String brainGenome = splitedGenome[0];
		// On traite les neurones après car on a besoin des id des blocks
		
		
	
		// Le portocole pour le corps est le suivant :
		/*
		 * On créé une matrice agrandissable On ajoute les blocs en mode Tetris en les
		 * faisant descendre pour qu'il prenne la place au sommet dans chaque colonne Le
		 * curseur à chaque fois est déplacé en fonction de l'information génétique
		 * Ensuite on calcule les connexions entre les blocs et on supprime ce qui ne
		 * sont pas liés à l'ensemble Enfin une fois que tout les blocs "savent" qui
		 * sont leur voisin on créé un tableau avec les blocs traités et restants Et
		 * enfin une fois les autres protocoles finis on créé la créature
		 */
		String bodyGenome = splitedGenome[1];
		ArrayList<Block> body = new ArrayList<Block>();
		// Le bloc central a toujours l'id AAAAB
		// Le bloc central n'est pas contenu dans le code génétique. Il est ajouté
		// automatiquement

		Block blocCentral = new Block("AAAAB", BlockType.Center, 0);

		String[] blockSequences = bodyGenome.split("FFFFA");
		
		int cursor = 0;
		
		ScalableMatrix matrix = new ScalableMatrix();
		matrix.addBlockOnTopOf(0, blocCentral);
		
		for (String blockSequence : blockSequences) {
			// Une séquence d'un bloc doit contenir 20 ou 15 bases. Si ce nombre n'est pas
			// respecté, on saute juste
			// cette séquence. Celà a pour but de laisser une chance aux organismes de
			// développer "en secret" de nouvelles choses
			if (blockSequence.length() != 15 && blockSequence.length() != 20)
				continue;
			String id = blockSequence.substring(0, 5);
			BlockType type = codonToBlockType(blockSequence.substring(5, 10));
			int deplacement = codonToInt(blockSequence.substring(10, 15))-512;// On se ramène sur une échelle de -512 à 512
			// On plafonne pour l'instant le déplacement à 12 pour ne pas que ça fasse des trucs trop chiants
			if(deplacement > 12)
				deplacement = 12;
			if(deplacement < -12)
				deplacement = -12;
			int direction = 0;
			if(blockSequence.length() == 20) {
				direction = codonToInt(blockSequence.substring(15,20));
				if(direction > 4)
					direction = 4;
			}
			cursor+=deplacement;
			matrix.addBlockOnTopOf(cursor, new Block(id,type,direction));
		}
		//Maintenant le boulot de vérification de la cohérence : 
		ArrayList<Coords> toIndex = new ArrayList<Coords>();
		toIndex.add(new Coords(0,0));
		ArrayList<Block> good = new ArrayList<Block>();
		
		while(!toIndex.isEmpty()) {
			Coords c = toIndex.get(0);
			Block block = matrix.getBlock((int)c.getY(), (int)c.getX());
			if(block != null) {
				good.add(block);
				for(int i = 0;i < 4;i++) {
					Coords c2 = null;
					if(i == 0)
						c2 = new Coords(c.getX()-1,c.getY());
					else if(i==1)
						c2 = new Coords(c.getX(),c.getY()+1	);
					else if(i == 2) 
						c2 = new Coords(c.getX()+1,c.getY());
					else 
						c2 = new Coords(c.getX(),c.getY()-1);
					
					Block buffer = matrix.getBlock((int)c2.getY(), (int)c2.getX());
					if(buffer != null) {
						block.getNeighbors()[i] = buffer;
						// En fait on calcule la direction du point de vue de buffer de block 
						//Donc dans quel case de neighbords il faut mettre block pour buffer
						int i2 = i+2;// Normalement ça prend l'opposé
						if(i2 >= 4)
							i2-=4;// Comme ça si c'est on est l'ouest : 3+2 = 5 => 1 Est (East) on est bon
						buffer.getNeighbors()[i2]= block;
						toIndex.add(c2);
					}
					
				}
				//On calcule les coordonnées relatives par rapport au centre qui est le bloc central
				block.setRelativeCoords(new Coords(c.getX()*ABlock.blockLengthSide,c.getY()*ABlock.blockLengthSide));

				matrix.setBlock((int)c.getY(),(int)c.getX(), null);
			}
			toIndex.remove(0);
		}
		Block[] blocks = good.toArray(new Block[0]);
		
		//Ici on traite le code pour le cerveau
				/*
				 * 
				 * 
				 * 
				 */
		// Et on obtient alors :
		return new Creature(null,new Coords(0,0),0,0,0,blocks,genome);
	}

	/**
	 * 
	 * @param codon La chaine de 5 bases : A,B,C ou D
	 * @return La valeur entre -1 et 1 correspond. Possède 1024 valeurs possibles
	 */
	public float codonToFloat(String codon) {
		return (codonToInt(codon) - 512) / 512.0f;
	}
	/*
	 * Bone(), // 0 Tissue(),// 1 Fat(),// 2 Spike(),// 3 Mouth(),// 4
	 * Thruster(),//5 Center(), Eye(),// 6 Contact();//7
	 */

	public static BlockType codonToBlockType(String codon) {
		int value = codonToInt(codon);
		switch (value) {
		case 0:
			return BlockType.Bone;
		case 1:
			return BlockType.Tissue;
		case 2:
			return BlockType.Fat;
		case 3:
			return BlockType.Spike;
		case 4:
			return BlockType.Mouth;
		case 5:
			return BlockType.Thruster;
		case 6:
			return BlockType.Eye;
		case 7:
			return BlockType.Contact;
		default:
			return BlockType.Tissue;
		}
	}

	/**
	 * Retourne la valeur codée sur 5 bases.
	 * 
	 * @param codon La chaine e 5 bases : A,B,C ou D
	 * @return Un entier entre 0 et 1024
	 */
	public static int codonToInt(String codon) {
		if (codon.length() != 5)
			throw new RuntimeException();
		int number = 0;
		for (int i = 4; i >= 0; i--) {
			int value = 0;
			switch (codon.charAt(i)) {
			case 'A':
				value = 0;
				break;
			case 'B':
				value = 1;
				break;
			case 'C':
				value = 2;
				break;
			case 'D':
				value = 3;
				break;
			}
			number += value * Math.pow(4, 4 - i);
		}
		return number;
	}
}
