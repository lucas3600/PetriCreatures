package com.lucas.PetriCreatures.Creatures.Brain;

public class Biais extends Neuron{

	public Biais(float[] weights) {
		super(null, weights);
		// TODO Auto-generated constructor stub
	}
	public Biais() {
		super(null);
	}
	@Override
	public void compute() {
		setOutput(1);
	}
	@Override
	public void addInput(float input) {
		
	}
	
}
