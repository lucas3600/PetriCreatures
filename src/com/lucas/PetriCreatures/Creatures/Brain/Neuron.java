package com.lucas.PetriCreatures.Creatures.Brain;

import com.lucas.PetriCreatures.Creatures.Brain.Functions.Function;
import com.lucas.PetriCreatures.Creatures.Entity.Blocks.Block;

public class Neuron implements INeuron {
	private Function transferFunction;
	private float[] weights;
	private float input;
	private float output;
	private Block inputer;
	private Block outputer;

	/***
	 * 
	 * @param transferFunction
	 * @param weights
	 */
	public Neuron(Function transferFunction, float... weights) {
		this.transferFunction = transferFunction;
		this.weights = weights;
		input = 0;
		output = 0;
		inputer = null;
		outputer = null;
	}

	public Neuron(Function transferFunction) {
		this.transferFunction = transferFunction;
		weights = new float[0];
		input = 0;
		output = 0;
		inputer = null;
		outputer = null;
	}

	public Neuron(Function transferFunction, Block inputer, Block outputer, float... weights) {
		this.transferFunction = transferFunction;
		this.weights = weights;
		input = 0;
		output = 0;
		this.inputer = inputer;
		this.outputer = outputer;
	}

	public Neuron(Function transferFunction, Block inputer, Block outputer) {
		this.transferFunction = transferFunction;
		weights = new float[0];
		input = 0;
		output = 0;
		this.inputer = inputer;
		this.outputer = outputer;
	}

	public void addFollowingNeurons(Layer layer) {
		if (layer.hasBiais()) {
			weights = new float[layer.getLength() - 1];
		} else
			weights = new float[layer.getLength() - 1];
	}

	public Function getTransferFunction() {
		return transferFunction;
	}

	public float[] getWeights() {
		return weights;
	}

	public float getWeight(int n) throws ArrayIndexOutOfBoundsException {
		if (n < 0 || n >= weights.length)
			throw new ArrayIndexOutOfBoundsException("" + n + " est en dehors");
		else
			return weights[n];
	}

	public void setInput(float input) {
		this.input = input;
	}

	public void setWeight(int n, float weight) throws ArrayIndexOutOfBoundsException {
		if (n < 0 || n >= weights.length)
			throw new ArrayIndexOutOfBoundsException("" + n + " est en dehors");
		else
			weights[n] = weight;
	}

	public void setTransferFunction(Function transferFunction) {
		this.transferFunction = transferFunction;
	}

	public void setWeights(float[] weights) {
		this.weights = weights;
	}

	@Override
	public void compute() {
		if(inputer != null)
			input = inputer.getOutput();
		output = transferFunction.compute(input);
		if(outputer != null)
			outputer.setInput(output);
	}

	public void setOutput(float output) {
		this.output = output;
	}

	@Override
	public void addInput(float input) {
		this.input += input;
	}

	@Override
	public float getOutput() {
		return output;
	}

	@Override
	public void flush() {
		input = 0;
		output = 0;
	}

	@Override
	public float getOutput(int n) {
		return output * weights[n];
	}
}