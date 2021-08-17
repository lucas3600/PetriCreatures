package com.lucas.PetriCreatures.Creatures.Brain;


public class Network{
	private Layer[] layers;
	
	
	public Network(boolean biais,int...layers){
		this.layers= new Layer[layers.length];
			this.layers[0] = new Layer(layers[0]);
			for(int i = 1; i < layers.length;i++){
				this.layers[i] = new Layer(layers[i],biais);
			}
			for(int i = 0;i < layers.length-1;i++) {
				this.layers[i].addFollowingLayer(this.layers[i+1]);
			}
	}
	public Network(Network model) {
		layers= new Layer[model.layersNumber()];
		this.layers[0] = new Layer(model.getLayer(0).getLengthWB());
		for(int i = 1; i < layers.length;i++){
			this.layers[i] = new Layer(model.getLayer(i).getLengthWB(),model.getLayer(i).hasBiais());
		}
		for(int i = 0;i < layers.length-1;i++) {
			Layer layer = layers[i];
			layer.addFollowingLayer(this.layers[i+1]);
			for (int j = 0 ;j < layer.getLength();j++	) {
				Neuron neuron = layer.getNeuron(j);
				neuron.setWeights(model.getLayer(i).getNeuron(j).getWeights());
			}
			
		}
	}
	public void flush() {
		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getLength(); j++) {
				layers[i].getNeuron(j).flush();
			}
		}
	}
	public void setInputs(float...inputs) {
		for(int i = 0; i < inputs.length;i++) {
			layers[0].getNeuron(i).setInput(inputs[i]);
		}
	}
	public void compute() {
		for(int i = 0;i < layers.length;i++	) {
			Layer layerUp = layers[i];
			layerUp.compute();
			
			if(i != (layers.length-1)) {
				for(int j = 0;j < layerUp.getLength();j++	) {
					Neuron sender = layerUp.getNeuron(j);
					Layer layerDown = layers[i+1];
					Neuron[] neuronsReceivers = layerDown.getNeurons(false);
					for(int h = 0;h < neuronsReceivers.length;h++) {
						neuronsReceivers[h].addInput(sender.getOutput(h));
					}
				}
			}
		}
	}
	public int layersNumber() {
		return layers.length;
	}
	public float[] getOutputs() {
		return layers[layers.length-1].getOutputs();
	}
	public Layer getLayer(int index) {
		if(index >= layers.length)
			return null;
		else
			return layers[index];
	}
}