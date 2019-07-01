package fa.training.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import fa.training.utils.InvalidIdException;
import fa.training.utils.Validator;

public class Airports implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private double runwaySize;
	private int maxFixedWingParkingPlace;
	private Set<String> listOfFixedWingAirplaneID = new TreeSet<>();
	private int maxRotatedWingParkingPlace;
	private Set<String> listOfHelicopterID = new TreeSet<>();
	private Set<Airplane> airplanes = new HashSet<>();
	public Airports() {
		
	}
	
	public Airports(String id, String name, double runwaySize, int maxFixedWingParkingPlace,
			Set<String> listOfFixedWingAirplaneID, int maxRotatedWingParkingPlace, Set<String> listOfHelicopterID,
			Set<Airplane> airplanes) {
		super();
		this.id = id;
		this.name = name;
		this.runwaySize = runwaySize;
		this.maxFixedWingParkingPlace = maxFixedWingParkingPlace;
		this.listOfFixedWingAirplaneID = listOfFixedWingAirplaneID;
		this.maxRotatedWingParkingPlace = maxRotatedWingParkingPlace;
		this.listOfHelicopterID = listOfHelicopterID;
		this.airplanes = airplanes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) throws InvalidIdException {
		if(Validator.isAiportsId(id) && !Validator.isAirportExisted(id))
			this.id = id;
		else
			throw new InvalidIdException();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRunwaySize() {
		return runwaySize;
	}

	public void setRunwaySize(double runwaySize) {
		this.runwaySize = runwaySize;
	}

	public int getMaxFixedWingParkingPlace() {
		return maxFixedWingParkingPlace;
	}

	public void setMaxFixedWingParkingPlace(int maxFixedWingParkingPlace) {
		this.maxFixedWingParkingPlace = maxFixedWingParkingPlace;
	}

	public Set<String> getListOfFixedWingAirplaneID() {
		return listOfFixedWingAirplaneID;
	}

	public void setListOfFixedWingAirplaneID(Set<String> listOfFixedWingAirplaneID) {
		this.listOfFixedWingAirplaneID = listOfFixedWingAirplaneID;
	}

	public int getMaxRotatedWingParkingPlace() {
		return maxRotatedWingParkingPlace;
	}

	public void setMaxRotatedWingParkingPlace(int maxRotatedWingParkingPlace) {
		this.maxRotatedWingParkingPlace = maxRotatedWingParkingPlace;
	}

	public Set<String> getListOfHelicopterID() {
		return listOfHelicopterID;
	}

	public void setListOfHelicopterID(Set<String> listOfHelicopterID) {
		this.listOfHelicopterID = listOfHelicopterID;
	}

	public Set<Airplane> getAirplanes() {
		return airplanes;
	}

	public void setAirplanes(Set<Airplane> airplanes) {
		this.airplanes = airplanes;
	}
	
	public void display() {
		System.out.println(id + "\t" + name + "\t" + runwaySize + "\t" + maxFixedWingParkingPlace + "\t" + maxRotatedWingParkingPlace);
	}
}
