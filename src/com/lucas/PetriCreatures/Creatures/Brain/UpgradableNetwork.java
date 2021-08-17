package com.lucas.PetriCreatures.Creatures.Brain;

public class UpgradableNetwork extends Network{
	private int fitness;
	
	public UpgradableNetwork(Network model) {
		super(model);
		fitness = 0;
	}

	public UpgradableNetwork(boolean biais, int[] layers) {
		super(biais, layers);
		fitness = 0;
	}
	public void randomize() {
		for(int i = 0;i < this.layersNumber();i++) {
			Layer l = this.getLayer(i);
			for(int n = 0;n < l.getLength();n++) {
				Neuron neuron = l.getNeuron(n);
				for(int w = 0; w < neuron.getWeights().length;w++) {
					neuron.setWeight(w, (float)(Math.random()*2-1));
				}
			}
		}
	}
	public void mutate() { 
		int selectedLayer = (int)(Math.random() * (this.layersNumber()-1));
		Layer l = this.getLayer(selectedLayer);
		
		int selectedNeuron = (int)(Math.random()*l.getLength());
		Neuron n = l.getNeuron(selectedNeuron);
		
		int selectedWeight = (int)(Math.random()*n.getWeights().length);
		n.setWeight(selectedWeight,(float)( n.getWeight(selectedWeight)+Math.random()*0.05-0.0025));
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	public void changeFitnessBy(int fitnessChanging) {
		this.fitness = fitness + fitnessChanging;
	}
	
}
