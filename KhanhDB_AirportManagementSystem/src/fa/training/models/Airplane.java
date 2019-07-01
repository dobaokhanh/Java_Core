package fa.training.models;

import java.io.Serializable;

import fa.training.utils.InvalidAirplaneModelException;
import fa.training.utils.InvalidIdException;
import fa.training.utils.Validator;


public class Airplane implements Serializable{
	/**
	 * 
	 */
	private String id;
	private static final long serialVersionUID = 1L;
	private String model;
	private double cruiseSpeed;
	private double emptyWeight;
	private double maxTakeoffWeight;
	
	public Airplane() {
		
	}
	
	public Airplane(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeoffWeight) {
		super();
		this.id = id;
		this.model = model;
		this.cruiseSpeed = cruiseSpeed;
		this.emptyWeight = emptyWeight;
		this.maxTakeoffWeight = maxTakeoffWeight;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) throws InvalidIdException {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) throws InvalidAirplaneModelException{
		if(Validator.isModelSize(model))
			this.model = model;
		else
			throw new InvalidAirplaneModelException();
	}

	public double getCruiseSpeed() {
		return cruiseSpeed;
	}

	public void setCruiseSpeed(double cruiseSpeed) {
		this.cruiseSpeed = cruiseSpeed;
	}

	public double getEmptyWeight() {
		return emptyWeight;
	}

	public void setEmptyWeight(double emptyWeight) {
		this.emptyWeight = emptyWeight;
	}

	public double getMaxTakeoffWeight() {
		return maxTakeoffWeight;
	}

	public void setMaxTakeoffWeight(double maxTakeoffWeight) {
		this.maxTakeoffWeight = maxTakeoffWeight;
	}

	
	/*public void display() {
		System.out.println(id +"\t" + model +  );
	}*/
}
