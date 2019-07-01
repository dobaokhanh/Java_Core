package fa.training.main;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import fa.training.models.Airplane;
import fa.training.models.Airports;
import fa.training.services.AirplaneService;
import fa.training.services.AirportService;
import fa.training.services.FixedWingService;
import fa.training.services.HelicopterService;
import fa.training.services.IOService;
import fa.training.utils.AirplaneFormatException;
import fa.training.utils.Constant;
import fa.training.utils.InvalidFixedWingTypeException;
import fa.training.utils.Validator;

public class AirportManagementSystem {
	private static Set<Airports> listAirports = new TreeSet<>();
	private static Set<Airplane> listAirplanes = new HashSet<>();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String choice, status, airportId, fixedWingId, helicopterId, planeType, neededRunway;
		double doNeededRunway;
		String loopAirplane = "";
		String airplaneChoice;
		Scanner scanner = null;
		FixedWingService fixedWingService = new FixedWingService();
		HelicopterService helicopterService = new HelicopterService();
		AirplaneService airplaneService = new AirplaneService();
		AirportService airportService = new AirportService();
		IOService ioService = new IOService();
		try {
			scanner = new Scanner(System.in);
			if(listAirports != null) {
				listAirports.clear();
			}
			if(listAirplanes != null)
				listAirplanes.clear();
			try {
				listAirports = (Set<Airports>) ioService.getAll(Constant.FILE_PATH_AIRPORT);
				airportService.setAirports(listAirports);
				listAirplanes = (Set<Airplane>) ioService.getAll(Constant.FILE_PATH_AIRPLANE);
				airplaneService.setAirplanes(listAirplanes);
				Validator.setAirplaneIds(listAirplanes, listAirports);
				Validator.setAirportIds(listAirports);
			} catch (Exception e) {
				System.out.println("No data!");
			}
			do {
				System.out.println("-----------------MENU-------------------");
				System.out.println("1.Create new airport\n2.Create airplane\n3.Add airplane to airport\n"
						+ "4.Display airports informations\n5.Display status of airport\n"
						+ "6.Display fixed wings\n7.Display helicopters\n8.Change fixedwing information\n9.Remove fixed wing\n10.Remove helicopter"
						+ "\n11.Remove airport\n12.Exit");
				System.out.println("Select: ");
				choice = scanner.nextLine();
				choice.trim();
				switch (choice) {
				//Input airport
				case Constant.INPUT_AIRPORT:
					listAirports = airportService.createAirport(scanner);
					System.out.println("Input airport done!");
					try {
						ioService.save(listAirports, Constant.FILE_PATH_AIRPORT);
					} catch (Exception e2) {
						System.out.println("No data");
					}
					break;
				//Input airplanes
				case Constant.INPUT_AIRPLANE:
					System.out.println("----------INPUT AIRPLANE----------");
					do {
						do {
							try {
								System.out.println("Do you want to input fixedwing (F) or helicopter (H): ");
								airplaneChoice = Validator.isAirplane(scanner.nextLine());
							} catch (AirplaneFormatException e) {
								continue;
							}
							break;
						} while (true);
						//Add fixed wing airplanes
						if (airplaneChoice.equalsIgnoreCase("F")) {
							listAirplanes.addAll(fixedWingService.createFixedWing(scanner));
						//Add helicopters
						} else if (airplaneChoice.equalsIgnoreCase("H")) {
							listAirplanes.addAll(helicopterService.createHelicopter(scanner));
						}
						System.out.println("Do you want to input airplane (Y/N)? ");
						loopAirplane = scanner.nextLine();
					} while (loopAirplane.charAt(0) == 'Y' || loopAirplane.charAt(0) == 'y');
					try {
						ioService.save(listAirplanes, Constant.FILE_PATH_AIRPLANE);
					} catch (Exception e2) {
						System.out.println("No data!");
					}
					System.out.println("Input airplane done!");
					break;
				//Add airplanes to airport
				case Constant.ADD_AIRPLANE:
					System.out.println("---------------List of unlocated airplanes---------");
					airplaneService.displayUnlocatedAirplane(listAirplanes);
					do {
						System.out.println("Enter airport id: ");
						airportId = scanner.nextLine();
					} while (Validator.isAirportExisted(airportId));
					
					try {
						status = airportService.addAirplane(scanner, airportId, listAirplanes);
						System.out.println(status);
					} catch (Exception e) {
						System.out.println("No data!");
					}
					listAirplanes.clear();
					try {
						listAirplanes = (Set<Airplane>) ioService.getAll(Constant.FILE_PATH_AIRPLANE);
					} catch (Exception e2) {
						System.out.println("There is no unlocated airplane !");
					}
					break;
				case Constant.DISPLAY_AIRPORT:
					airportService.displayAirport(listAirports);
					break;
				case Constant.STATUS:
					do {
						System.out.println("Enter airport id: ");
						airportId = scanner.nextLine();
					} while (Validator.isAirportExisted(airportId));
						airportService.displayStatus(airportId, listAirports);		
					break;
				case Constant.DISPLAY_FIXEDWINGS:
						airportService.displayFixedWing(listAirports);
					break;
				case Constant.DISPLAY_HELICOPTERS:
						airportService.displayHelicopter(listAirports);
					break;
				case Constant.CHANGE_FIXEDWING_INFO:
					do {
						System.out.println("Enter fixedwing id: ");
						fixedWingId = scanner.nextLine();
					}while(!Validator.isFixedWingId(fixedWingId) && Validator.isValidAirplaneId(listAirplanes, listAirports, fixedWingId));
					do {
						System.out.println("Enter plane type: ");
						planeType = scanner.nextLine();
					}while(!Validator.isFixedWingType(planeType));
					System.out.println("Enter min needed runway size: ");
					neededRunway = scanner.nextLine();
					doNeededRunway = Validator.isDouble(neededRunway);
					try {
						airplaneService.changeInfoOfFixedWing(listAirplanes, listAirports, fixedWingId, planeType, doNeededRunway);
					} catch (InvalidFixedWingTypeException e1) {
						System.out.println("Invalid plane type!");
					}
					
					break;
				case Constant.REMOVE_FIXEDWING:
					do {
						System.out.println("Enter airport id: ");
						airportId = scanner.nextLine();
					}while(Validator.isAirportExisted(airportId));
					do {
						System.out.println("Enter fixed wing id: ");
						fixedWingId = scanner.nextLine();
					}while(!Validator.isFixedWingId(fixedWingId) && Validator.isAirplaneExisted(fixedWingId));
					try {
						status = airportService.removeFixedWing(fixedWingId, airportId, listAirplanes);
						listAirplanes = (Set<Airplane>) ioService.getAll(Constant.FILE_PATH_AIRPLANE);
						listAirplanes.clear();
						listAirports.clear();
						listAirports = (Set<Airports>) ioService.getAll(Constant.FILE_PATH_AIRPORT);
						System.out.println("Remove " + status);
					} catch (Exception e1) {
						System.out.println("Remove fail!");
					}
					break;
				case Constant.REMOVE_HELICOPTER:
					do {
						System.out.println("Enter airport id: ");
						airportId = scanner.nextLine();
					}while(Validator.isAirportExisted(airportId));
					do {
						System.out.println("Enter helicopter id: ");
						helicopterId = scanner.nextLine();
					}while(!Validator.isHelicopterId(helicopterId) && Validator.isAirplaneExisted(helicopterId));
					try {
						status = airportService.removeHelicopter(helicopterId, airportId, listAirplanes);
						listAirplanes = (Set<Airplane>) ioService.getAll(Constant.FILE_PATH_AIRPLANE);
						listAirplanes.clear();
						listAirports.clear();
						listAirports = (Set<Airports>) ioService.getAll(Constant.FILE_PATH_AIRPORT);
						System.out.println("Remove " + status);
					} catch (Exception e) {
						System.out.println("Remove fail!");
					}
					break;
				case Constant.REMOVE_AIRPORT:
					do {
						System.out.println("Enter airport id: ");
						airportId = scanner.nextLine();
					}while(Validator.isAirportExisted(airportId));
					try {
						status = airportService.removeAirport(airportId);
						System.out.println("Remove " + status);
					} catch (Exception e) {
						System.out.println("Remove fail!");
					}
					break;
				default:
					choice = Constant.EXIT;
					break;
				}
			} while (!choice.equalsIgnoreCase(Constant.EXIT));
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

}
