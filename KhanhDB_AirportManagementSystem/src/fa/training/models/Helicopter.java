package fa.training.models;

import java.io.Serializable;

import fa.training.utils.InvalidIdException;
import fa.training.utils.Validator;

public class Helicopter extends Airplane implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double range;
	private String flyMethod;
	public Helicopter() {
		
	}
	
	public Helicopter(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeoffWeight,
			 double range, String flyMethod) {
		super(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight);
		this.range = range;
		this.flyMethod = flyMethod;
	}
	
	

	public String getId() {
		return super.getId();
	}

	public void setId(String id) throws InvalidIdException{
		if(Validator.isHelicopterId(id) && !Validator.isAirplaneExisted(id))
			super.setId(id);
		else
			throw new InvalidIdException("Id is not valid !");
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public String getFlyMethod() {
		return flyMethod;
	}

	public void setFlyMethod(String flyMethod) {
		this.flyMethod = flyMethod;
	}
	
}
