package com.lucas.PetriCreatures.Creatures.Brain;

import com.lucas.PetriCreatures.Creatures.Brain.Functions.Sigmoid;

public class Layer {
	private Neuron[] neurons;
	private Neuron[] neuronsWithoutBiais;
	private boolean biais;

	public Layer(Neuron[] neurons) {
		this.neurons = neurons;
	}

	public Layer(int length, boolean biais) {
		this.biais = biais;
		if (biais) {
			neurons = new Neuron[length + 1];
			neuronsWithoutBiais = new Neuron[length];
			neurons[0] = new Biais();
			for (int i = 1; i < neurons.length; i++) {
				neurons[i] = new Neuron(new Sigmoid());
				neuronsWithoutBiais[i - 1] = neurons[i];
			}
		} else {
			neurons = new Neuron[length];
			neuronsWithoutBiais = new Neuron[length];
			for (int i = 0; i < neurons.length; i++) {
				neurons[i] = new Neuron(new Sigmoid());
				neuronsWithoutBiais[i] = neurons[i];
			}
		}
	}

	public Layer(int length) {
		biais = false;
		neurons = new Neuron[length];
		neuronsWithoutBiais = new Neuron[length];
		for (int i = 0; i < neurons.length; i++) {
			neurons[i] = new Neuron(new Sigmoid());
			neuronsWithoutBiais[i] = neurons[i];
		}

	}

	/****
	 * Attention si la rangée possède un neurone de biais alors le neurone en indice
	 * 0 sera le neurone de biais
	 * 
	 * @param index la position dans le tableau du neurone
	 * @return Neuron le neurone correspondant. Retourne null si l'index est en
	 *         dehors du tableau de neurones
	 */
	public void compute() {
		for (Neuron n : neurons) {
			n.compute();
		}
	}

	public Neuron getNeuron(int index) {
		if (index >= neurons.length)
			return null;
		else
			return neurons[index];
	}

	public float[] getOutputs() {
		float[] outputs = new float[neurons.length];
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = neurons[i].getOutput();
		}
		return outputs;
	}

	public Neuron[] getNeurons() {
		return neurons;
	}

	/***
	 * 
	 * @param biaisIncluded -> permet de préciser si le neurone de biais est inclu
	 *                      dans le retour où non
	 * @return
	 */
	public Neuron[] getNeurons(boolean biaisIncluded) {
		return biaisIncluded ? neurons : neuronsWithoutBiais;
	}

	public void addFollowingLayer(Layer layer) {
		for (Neuron neuron : neurons) {
			neuron.addFollowingNeurons(layer);
		}
	}

	public boolean hasBiais() {
		return biais;
	}

	public int getLength() {
		return neurons.length;
	}

	/**
	 * geLengthWithoutBiaisNeuron
	 * 
	 * @return
	 */
	public int getLengthWB() {
		return neuronsWithoutBiais.length;
	}
}