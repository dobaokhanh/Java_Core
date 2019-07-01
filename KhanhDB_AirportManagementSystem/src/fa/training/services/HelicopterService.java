package fa.training.services;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import fa.training.models.Helicopter;
import fa.training.utils.InvalidIdException;
import fa.training.utils.TakeoffWeightException;
import fa.training.utils.Validator;

public class HelicopterService {

	/**Create helicopter
	 * @param scanner
	 * @return
	 */
	public Set<Helicopter> createHelicopter(Scanner scanner) {
		String loop = "";
		String id, range, maxTakeoffWeight;
		double doRange, doMaxTakeoffWeight;
		AirplaneService airplaneService = new AirplaneService();
		Set<Helicopter> helicopters = new HashSet<>();
		Helicopter helicopter;

		do {
			helicopter = new Helicopter();
			airplaneService.createAirplane(scanner, helicopter);
			do {
				System.out.println("Enter max takeoff weight: ");
				maxTakeoffWeight = scanner.nextLine();
				try {
					doMaxTakeoffWeight = Validator.isDouble(maxTakeoffWeight);
					if (Validator.isTakeoffWeightOfHelicopter(doMaxTakeoffWeight, helicopter.getEmptyWeight()))
						helicopter.setMaxTakeoffWeight(doMaxTakeoffWeight);
				} catch (NumberFormatException | TakeoffWeightException e) {
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("Enter helicopter id: ");
				id = scanner.nextLine();
				try {
					helicopter.setId(id);
				} catch (InvalidIdException e) {
					continue;
				}
				break;
			} while (true);

			do {
				System.out.println("Enter range of helicopter: ");
				range = scanner.nextLine();
				try {
					doRange = Validator.isDouble(range);
					helicopter.setRange(doRange);
				} catch (NumberFormatException e) {
					continue;
				}
				break;
			} while (true);
			helicopter.setFlyMethod("rotated wing");
			
			helicopters.add(helicopter);
			System.out.println("Do you want continue input helicopter (Y/N): ");
			loop = scanner.nextLine();

		} while (loop.charAt(0) == 'Y' || loop.charAt(0) == 'y');
		return helicopters;
	}
}
