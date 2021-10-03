package com.lucas.PetriCreatures.Creatures;

import java.util.ArrayList;

import com.lucas.PetriCreatures.Creatures.Entity.Blocks.Block;

/**
 * En cours Note : un codon code pour 1024 valeurs (4^5) donc en float on fait
 * int(codon)-512/512.0f et en int ben voil� Il y a cinq marqueurs A,B,C,D,F F
 * est utilis� pour rep�rer les codons marqueurs de fin ou des choses comme �a
 * 
 * @author lucas
 *
 */
public class CreatureFactory {
	/**
	 * Le genome est organis� en codons Les codons sont compos�s de 5 lettres allant
	 * de Le marqueur de fin de ligne est FFFFF
	 * 
	 * @param genome
	 * @return
	 */
	public static Creature interpretGenome(String genome) {
		String[] splitedGenome = genome.split("FFFFF");
		String brainGenome = splitedGenome[0];
		String bodyGenome = splitedGenome[1];
		ArrayList<Block> body = new ArrayList<Block>();
		// Le bloc central a toujours l'id AAAAB
		String[] blockSequences = bodyGenome.split("FFFFA");
		for(String blockSequence : blockSequences) {
			//Une s�quence d'un bloc doit contenir 20 ou 25 bases. Si ce nombre n'est pas respect�, on saute juste
			//cette s�quence. Cel� a pour but de laisser une chance aux organismes de d�velopper "en secret" de nouvelles choses
			if(blockSequence.length() != 25 && blockSequence.length() != 20)
				continue;
			
		}
		return null;
	}

	public float codonToFloat(String codon) {
		return (codonToInt(codon) - 512) / 512.0f;
	}

	public int codonToInt(String codon) {
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
			number+=value*Math.pow(4, 4-i);
		}
		return number;
	}
}
