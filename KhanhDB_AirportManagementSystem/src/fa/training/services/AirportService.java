package fa.training.services;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import fa.training.models.Airplane;
import fa.training.models.AirportComparator;
import fa.training.models.Airports;
import fa.training.models.FixedWing;
import fa.training.models.Helicopter;
import fa.training.utils.Constant;
import fa.training.utils.InvalidIdException;
import fa.training.utils.Validator;

/**
 * @author KHANH DO
 *
 */
public class AirportService {
	private Set<Airports> airports;
	private AirplaneService airplaneService;
	private IOService ioService;
	
	public AirportService() {
		this.airports = new TreeSet<>(new AirportComparator());
		this.airplaneService = new AirplaneService();
		this.ioService = new IOService();
	}
	
	
	public Set<Airports> getAirports() {
		return airports;
	}

	public void setAirports(Set<Airports> airports) {
		this.airports = airports;
	}

	/**
	 * Create new airport
	 * 
	 * @param scanner
	 * @return
	 */
	public Set<Airports> createAirport(Scanner scanner) {
		Airports airport;
		String id, name, runwaySize, maxFixedWingParkingPlace, maxRotatedWingParkingPlace;
		double doRunwaySize;
		int doMaxFixedWingParkingPlace, doMaxRotatedWingParkingPlace;
		String loopAirport = "";
		do {
			airport = new Airports();
			do {
				System.out.println("Enter airport id: ");
				id = scanner.nextLine();
				try {
					airport.setId(id);
				} catch (InvalidIdException e) {
					continue;
				}
				break;
			} while (true);
			System.out.println("Enter airport name: ");
			name = scanner.nextLine();
			airport.setName(name);
			do {
				System.out.println("Enter runway size: ");
				runwaySize = scanner.nextLine();
				try {
					doRunwaySize = Validator.isDouble(runwaySize);
					airport.setRunwaySize(doRunwaySize);
				} catch (NumberFormatException e) {
					continue;
				}
				break;
			} while (true);

			do {
				System.out.println("Enter max fixed wing parking place: ");
				maxFixedWingParkingPlace = scanner.nextLine();
				try {
					doMaxFixedWingParkingPlace = Validator.isInt(maxFixedWingParkingPlace);
					airport.setMaxFixedWingParkingPlace(doMaxFixedWingParkingPlace);
				} catch (NumberFormatException e) {
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("Enter max rotated wing parking place: ");
				maxRotatedWingParkingPlace = scanner.nextLine();
				try {
					doMaxRotatedWingParkingPlace = Validator.isInt(maxRotatedWingParkingPlace);
					airport.setMaxRotatedWingParkingPlace(doMaxRotatedWingParkingPlace);
				} catch (NumberFormatException e) {
					continue;
				}
				break;
			} while (true);
			airports.add(airport);
			System.out.println("Do you want continue input airport (Y/N)? ");
			loopAirport = scanner.nextLine();
		} while (loopAirport.charAt(0) == 'Y' || loopAirport.charAt(0) == 'y');
		return airports;
	}

	/**
	 * Add airplane to airport
	 * 
	 * @param idAirport
	 * @param airplanes
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String addAirplane(Scanner scanner, String idAirport, Set<Airplane> airplanes) throws Exception {
		boolean removed = false;
		Airplane airplane;
		String airplaneId, loopAdd = "";
		Airports airport = new Airports();
		for (Airports a : airports) {
			if (a.getId().equalsIgnoreCase(idAirport)) {
				airport = a;
			}
		}
		do {
			removed = false;
			airplane = new Airplane();
			do {
				System.out.println("Enter airplane id to add to airport: ");
				airplaneId = scanner.nextLine();
			} while (!Validator.isValidAirplaneId(airplanes, airports, airplaneId));
			airplane = airplaneService.getAirplane(airplanes, airplaneId);
			if (!inAirport(airplaneId)) {
				if (airplane instanceof FixedWing) {
					if (airport.getMaxFixedWingParkingPlace() > airport.getListOfFixedWingAirplaneID().size()) {
						if (Validator.isFixedWingCondition(airport, airplane)) {
							airport.getAirplanes().add(airplane);
							airport.getListOfFixedWingAirplaneID().add(airplane.getId());
							System.out.println("Add success!");
							removed = true;
						} else
							System.out.println("The needed runway is not fit with the airport!");
					} else
						System.out.println("There is no parking place for fixed wing airplane!");
				} else if (airplane instanceof Helicopter) {
					if (airport.getMaxRotatedWingParkingPlace() > airport.getListOfHelicopterID().size()) {
						airport.getAirplanes().add(airplane);
						airport.getListOfHelicopterID().add(airplane.getId());
						System.out.println("Add success!");
						removed = true;
					} else
						System.out.println("There is no parking place for helicopter!");
				}
			} else
				System.out.println("Airplane parked in an airport!");
			if (removed) {
				try {
					airplaneService.removeAirplane(airplanes, airplane);
					airplanes.clear();
					airplanes = (Set<Airplane>) ioService.getAll(Constant.FILE_PATH_AIRPLANE);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			System.out.println("Do you want continue add airplane (Y/N) : ");
			loopAdd = scanner.nextLine();
		} while ((loopAdd.charAt(0) == 'Y' || loopAdd.charAt(0) == 'y'));

		try {
			ioService.save(airports, Constant.FILE_PATH_AIRPORT);
		} catch (IOException e) {
			throw new IOException();
		}
		return "Add done !";
	}


	/**
	 * display all airport
	 * 
	 * @param airports
	 */
	public void displayAirport(Set<Airports> airports) {
		System.out.println("-----------------AIRPORTS-----------------");
		for (Airports a : airports)
			a.display();
	}

	/**
	 * Display airport status
	 * 
	 * @param id
	 * @param airports
	 */
	public void displayStatus(String id, Set<Airports> airports) {
		System.out.println("---------------STATUS---------------------");
		for (Airports a : airports) {
			if (a.getId().equalsIgnoreCase(id)) {
				System.out.println("Max fixed wing parking place " + a.getMaxFixedWingParkingPlace());
				System.out.println("Fixed wing parked: " + a.getListOfFixedWingAirplaneID().size());
				System.out.println("Max rotated wing parking place: " + a.getMaxRotatedWingParkingPlace());
				System.out.println("Helicopter parked: " + a.getListOfHelicopterID().size());
			}
		}
	}

	/**
	 * Display fixed wing airplanes with its parking airport ID and name
	 * 
	 * @param airports
	 */
	public void displayFixedWing(Set<Airports> airports) {
		System.out.println("---------------FIXED WING----------------");
		for (Airports a : airports) {
			for (Airplane airplane : a.getAirplanes()) {
				if (airplane instanceof FixedWing)
					System.out.println(((FixedWing) airplane).getId() + "\t" + a.getId() + "\t" + a.getName());
			}
		}
	}

	/**
	 * Display helicopters with its parking airport ID and name
	 * 
	 * @param airports
	 */
	public void displayHelicopter(Set<Airports> airports) {

		System.out.println("-----------------HELICOPTER-----------------");
		for (Airports a : airports) {
			for (Airplane h : a.getAirplanes()) {
				if (h instanceof Helicopter) {
					System.out.println(((Helicopter) h).getId() + "\t" + a.getId() + "\t" + a.getName());
				}
			}
		}
	}

	/**
	 * Remove airport
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String removeAirport(String id) throws Exception {
		boolean removed = false;
		Iterator<Airports> iterator = airports.iterator();
		while (iterator.hasNext()) {
			Airports airport = iterator.next();
			if (id.equalsIgnoreCase(airport.getId())) {
				iterator.remove();
				removed = true;
				break;
			}
		}

		if (removed) {
			try {
				ioService.save(airports, Constant.FILE_PATH_AIRPORT);
			} catch (Exception e) {
				throw new Exception();
			}
			return Constant.SUCCESS;
		}
		return Constant.FAIL;
	}

	/**
	 * Remove helicopter
	 * 
	 * @param idHelicopter
	 * @param idAirport
	 * @return
	 * @throws Exception
	 */
	public String removeHelicopter(String idHelicopter, String idAirport, Set<Airplane> airplanes) throws Exception {
		boolean removed = false;
		Airports airport = new Airports();
		
		for (Airports a : airports) {
			if (a.getId().equalsIgnoreCase(idAirport)) {
				Iterator<Airplane> iterator = airport.getAirplanes().iterator();
				while (iterator.hasNext()) {
					Airplane airplane = iterator.next();
					if (airplane instanceof Helicopter)
						if (((Helicopter) airplane).getId().equalsIgnoreCase(idHelicopter)) {
							iterator.remove();
							airplanes.add(airplane);
							removed = true;
							break;
						}

				}
			}
		}
		if (removed) {
			try {
				ioService.save(airports,Constant.FILE_PATH_AIRPORT);
				ioService.save(airplanes, Constant.FILE_PATH_AIRPLANE);
			} catch (Exception e) {
				throw new Exception();
			}
			return Constant.SUCCESS;
		}
		return Constant.FAIL;
	}

	/**
	 * Remove fixed wing airplanes
	 * 
	 * @param idFixedWing
	 * @param idAirport
	 * @return
	 * @throws Exception
	 */
	public String removeFixedWing(String idFixedWing, String idAirport, Set<Airplane> airplanes) throws Exception {
		boolean removed = false;

		for (Airports a : airports) {
			if (a.getId().equalsIgnoreCase(idAirport)) {
				Iterator<Airplane> iterator = a.getAirplanes().iterator();
				while (iterator.hasNext()) {
					Airplane airplane = iterator.next();
					if (airplane instanceof FixedWing)
						if (((FixedWing) airplane).getId().equalsIgnoreCase(idFixedWing)) {
							iterator.remove();
							airplanes.add(airplane);
							removed = true;
							break;
						}
				}
			}
		}
		if (removed) {
			try {
				ioService.save(airports, Constant.FILE_PATH_AIRPORT);
				ioService.save(airplanes, Constant.FILE_PATH_AIRPLANE);
			} catch (Exception e) {
				throw new Exception();
			}
			return Constant.SUCCESS;
		}
		return Constant.FAIL;
	}

	/**
	 * Check whether airplane parked in any airport ?
	 * 
	 * @param airplane
	 * @return
	 * @throws Exception
	 */
	public boolean inAirport(String airplaneId) {
		for (Airports a : airports) {
			for (Airplane ap : a.getAirplanes())
				if (ap.getId().equalsIgnoreCase(airplaneId))
					return true;
		}
		return false;
	}

}
