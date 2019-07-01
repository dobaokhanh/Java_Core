package fa.training.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import fa.training.models.Airplane;
import fa.training.models.Airports;
import fa.training.models.FixedWing;
import fa.training.models.Helicopter;
import fa.training.utils.Constant;
import fa.training.utils.InvalidAirplaneModelException;
import fa.training.utils.InvalidFixedWingTypeException;
import fa.training.utils.Validator;

public class AirplaneService {
	
	private IOService ioService;
	private Set<Airplane> airplanes;
	
	public AirplaneService() {
		this.ioService = new IOService();
		this.airplanes = new HashSet<>();
	}
	
	
	
	public Set<Airplane> getAirplanes() {
		return airplanes;
	}

	public void setAirplanes(Set<Airplane> airplanes) {
		this.airplanes = airplanes;
	}



	/**
	 * Set common informations of airplane
	 * 
	 * @param scanner
	 * @param airplane
	 */
	public void createAirplane(Scanner scanner, Airplane airplane) {
		String model, cruiseSpeed, emptyWeight;
		do {
			System.out.println("Enter airplane model: ");
			model = scanner.nextLine();
			try {
				airplane.setModel(model);
			} catch (InvalidAirplaneModelException e) {
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("Enter cruise speed: ");
			cruiseSpeed = scanner.nextLine();
			try {
				airplane.setCruiseSpeed(Validator.isDouble(cruiseSpeed));
			} catch (NumberFormatException e) {
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("Enter empty weight: ");
			emptyWeight = scanner.nextLine();
			try {
				airplane.setEmptyWeight(Validator.isDouble(emptyWeight));
			} catch (NumberFormatException e) {
				continue;
			}
			break;
		} while (true);

	}

	/**
	 * Get airplane by id
	 * 
	 * @param airplanes
	 * @param id
	 * @return
	 */
	public Airplane getAirplane(Set<Airplane> airplanes, String id) {
		Airplane airplane = new Airplane();

		for (Airplane a : airplanes)
			if (a.getId().equalsIgnoreCase(id))
				airplane = a;
		return airplane;
	}

	/**
	 * Change plane type and needed runway of fixed wing airplane
	 * 
	 * @param airplanes
	 * @param id
	 * @param planeType
	 * @param neededRunway
	 * @throws InvalidFixedWingTypeException
	 */
	public String changeInfoOfFixedWing(Set<Airplane> airplanes,Set<Airports> airports, String id, String planeType, double neededRunway)
			throws InvalidFixedWingTypeException {
		for (Airplane a : airplanes) {
			if (a.getId().equalsIgnoreCase(id)) {
				((FixedWing) a).setPlaneType(planeType);
				((FixedWing) a).setMinNeededRunwaySize(neededRunway);
				return Constant.SUCCESS;
			}
		}
		for(Airports a: airports) {
			for(Airplane ap: a.getAirplanes()) {
				if(ap.getId().equalsIgnoreCase(id)) {
					((FixedWing) ap).setPlaneType(planeType);
					((FixedWing) ap).setMinNeededRunwaySize(neededRunway);
					return Constant.SUCCESS;
				}
			}
		}
		
		try {
			ioService.save(airplanes, Constant.FILE_PATH_AIRPLANE);
			ioService.save(airports, Constant.FILE_PATH_AIRPORT);
		} catch (Exception e) {
			System.out.println("Save fail!");
		}
		
		return Constant.FAIL;
	}

	/**
	 * Remove airplane from list of unlocated airplanes
	 * 
	 * @param airplanes
	 * @param airplane
	 * @throws Exception
	 */
	public void removeAirplane(Set<Airplane> airplanes, Airplane airplane) throws Exception {
		boolean removed = false;
		Iterator<Airplane> iterator = airplanes.iterator();
		while (iterator.hasNext()) {
			Airplane ap = iterator.next();
			if (ap.getId().equalsIgnoreCase(airplane.getId())) {
				iterator.remove();
				removed = true;
				break;
			}
		}

		if (removed) {
			try {
				ioService.save(airplanes, Constant.FILE_PATH_AIRPLANE);
			} catch (Exception e) {
				throw new Exception();
			}
		}
	}

	public void displayUnlocatedAirplane(Set<Airplane> airplanes) {
		System.out.println("-----------FIXED WING-------------");
		for (Airplane a : airplanes) {
			if (a instanceof FixedWing)
				System.out.println(a.getId());
		}
		System.out.println("-------------HELICOPTER---------------");
		for (Airplane a : airplanes) {
			if (a instanceof Helicopter)
				System.out.println(a.getId());
		}
	}
	
	
}
