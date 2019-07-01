package fa.training.models;

import java.io.Serializable;

import fa.training.utils.InvalidFixedWingTypeException;
import fa.training.utils.InvalidIdException;
import fa.training.utils.Validator;

public class FixedWing extends Airplane implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String planeType;
	private double minNeededRunwaySize;
	private String flyMethod;

	public FixedWing() {

	}

	public FixedWing(String id, String model, double cruiseSpeed, double emptyWeight, double maxTakeoffWeight,
			String planeType, double minNeededRunwaySize, String flyMethod) {
		super(id, model, cruiseSpeed, emptyWeight, maxTakeoffWeight);
		this.planeType = planeType;
		this.minNeededRunwaySize = minNeededRunwaySize;
		this.flyMethod = flyMethod;
	}
	
	

	public String getId() {
		return super.getId();
	}

	public void setId(String id) throws InvalidIdException {
		if(Validator.isFixedWingId(id) && Validator.isAirplaneExisted(id))
			super.setId(id);
		else
			throw new InvalidIdException("Id is not valid!");
	}

	public String getPlaneType() {
		return planeType;
	}

	public void setPlaneType(String planeType) throws InvalidFixedWingTypeException{
		if(Validator.isFixedWingType(planeType))
			this.planeType = planeType;
		else
			throw new InvalidFixedWingTypeException();
	}

	public double getMinNeededRunwaySize() {
		return minNeededRunwaySize;
	}

	public void setMinNeededRunwaySize(double minNeededRunwaySize) {
		this.minNeededRunwaySize = minNeededRunwaySize;
	}

	public String getFlyMethod() {
		return flyMethod;
	}

	public void setFlyMethod(String flyMethod) {
		this.flyMethod = flyMethod;
	}
	
}
