package fa.training.services;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import fa.training.models.FixedWing;
import fa.training.utils.InvalidFixedWingTypeException;
import fa.training.utils.InvalidIdException;
import fa.training.utils.Validator;

public class FixedWingService {
	
	private AirplaneService airplaneService;
	private Set<FixedWing> fixedWings;

	public FixedWingService() {
		this.airplaneService = new AirplaneService();
		this.fixedWings = new HashSet<>();
	}
	
	/**Create fixed wing airplane
	 * @param scanner
	 * @return
	 */
	public Set<FixedWing> createFixedWing(Scanner scanner) {
		String loop = "";
		String id, planeType, minNeededRunwaySize, maxTakeoffWeight;
		double doMinNeededRunwaySize;
		FixedWing fixedWing;
		do {
			fixedWing = new FixedWing();
			
			do {
				System.out.println("Enter airplane id: ");
				id = scanner.nextLine();
				try {
					fixedWing.setId(id);
				} catch (InvalidIdException e) {
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("Enter plane type: ");
				planeType = scanner.nextLine();
				try {
					fixedWing.setPlaneType(planeType);
				} catch (InvalidFixedWingTypeException e) {
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("Enter max takeoff weight: ");
				maxTakeoffWeight = scanner.nextLine();
				try {
					fixedWing.setMaxTakeoffWeight(Validator.isDouble(maxTakeoffWeight));
				} catch (NumberFormatException e) {
					continue;
				}
				break;
			} while (true);
			airplaneService.createAirplane(scanner, fixedWing);
			fixedWing.setFlyMethod("fixed wing");
			System.out.println("Enter min runway size: ");
			minNeededRunwaySize = scanner.nextLine();
			try {
				doMinNeededRunwaySize = Validator.isDouble(minNeededRunwaySize);
				fixedWing.setMinNeededRunwaySize(doMinNeededRunwaySize);
			} catch (NumberFormatException e) {
				continue;
			}
			fixedWings.add(fixedWing);
			System.out.println("Do you want continue to input fixedwing airplane (Y/N)? :");
			loop = scanner.nextLine();
		} while (loop.charAt(0) == 'Y' || loop.charAt(0) == 'y');
		return fixedWings;
	}

}
