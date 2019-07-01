package fa.training.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fa.training.models.Airplane;
import fa.training.models.Airports;
import fa.training.models.FixedWing;

public class Validator {
	private static final String VALID_ID_FIXEDWING_REGEX = "FW\\d{5}";
	private static final String VALID_ID_HELICOPTER_REGEX = "RW\\d{5}";
	private static final String VALID_ID_AIRPORTS_REGEX = "^AP\\d{5}$";
	private static final String VALID_FIXEDWING_AIRPLANE_TYPE_REGEX = "CAG|LGR|PRV";
	private static Set<String> idAirplanes = new HashSet<>();
	private static Set<String> idAirports = new HashSet<>();
	/**
	 * Check fixed wing id is valid
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isFixedWingId(String id) {
		Pattern pattern = Pattern.compile(VALID_ID_FIXEDWING_REGEX);
		Matcher matcher = pattern.matcher(id);
		return matcher.matches();
	}

	/**
	 * Check helicopter id is valid
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isHelicopterId(String id) {
		Pattern pattern = Pattern.compile(VALID_ID_HELICOPTER_REGEX);
		Matcher matcher = pattern.matcher(id);
		return matcher.matches();
	}

	/**
	 * Check airports id is valid
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isAiportsId(String id) {
		Pattern pattern = Pattern.compile(VALID_ID_AIRPORTS_REGEX);
		Matcher matcher = pattern.matcher(id);
		return matcher.matches();
	}
	
	/**Check type of fixed wing is valid
	 * @param type
	 * @return
	 */
	public static boolean isFixedWingType(String type) {
		Pattern pattern = Pattern.compile(VALID_FIXEDWING_AIRPLANE_TYPE_REGEX);
		Matcher matcher = pattern.matcher(type);
		return matcher.matches();
	}

	/**
	 * Check model is valid
	 * 
	 * @param model
	 * @return
	 */
	public static boolean isModelSize(String model) {
		if(model.length() <= 40)
			return true;
		return false;
	}

	/**
	 * Check runway size is valid
	 * 
	 * @param airplaneRunwaySize
	 * @param airportRunwaySize
	 * @return
	 * @throws RunwayException
	 */
	public static boolean isRunwaySize(double airplaneRunwaySize, double airportRunwaySize) throws RunwayException {
		if (airplaneRunwaySize <= airportRunwaySize)
			return true;
		else {
			throw new RunwayException("The runway size of airport is shorter than min runway size of airplane !");
		}
	}

	/**Check takeoff weight is valid
	 * @param takeoffWeight
	 * @param emptyWeight
	 * @return
	 * @throws TakeoffWeightException
	 */
	public static boolean isTakeoffWeightOfHelicopter(double takeoffWeight, double emptyWeight)
			throws TakeoffWeightException {
		if (takeoffWeight <= emptyWeight * 1.5)
			return true;
		else
			throw new TakeoffWeightException();
	}
	
	/**Check number is double
	 * @param noDouble
	 * @return
	 */
	public static double isDouble(String noDouble) {
		double doDouble = 0;
		try {
			doDouble = Double.parseDouble(noDouble);
		}catch(NumberFormatException e) {
			throw new NumberFormatException();
		}
		return doDouble;
	}
	
	public static int isInt(String noInt) {
		int doInt = 0;
		try {
			doInt = Integer.parseInt(noInt);
		}catch(NumberFormatException e) {
			throw new NumberFormatException();
		}
		return doInt;
	}
	
	public static String isAirplane(String airplane) throws AirplaneFormatException {
		String type = "";
		if(airplane.charAt(0) == 'F' || airplane.charAt(0) == 'f')
			type = "F";
		else if(airplane.charAt(0) == 'H' || airplane.charAt(0) == 'h')
			type = "H";
		else
			throw new AirplaneFormatException();
		return type;
	}
	
	/**Check  if an airplane id is existed or not
	 * @param id
	 * @return
	 */
	public static boolean isAirplaneExisted(String id) {
		if(!idAirplanes.contains(id)) {
			idAirplanes.add(id);
			return true;
		}else
			return false;
	}
	
	/** Check if an airport id is existed or not
	 * @param id
	 * @return
	 */
	public static boolean isAirportExisted(String id) {
		if(!idAirports.contains(id)) {
			idAirports.add(id);
			return true;
		}else
			return false;
	}
	
	/**Get set of airplane ids
	 * @return
	 */
	public static Set<String> getAirplaneIds(){
		return idAirplanes;
	}
	
	
	/**Get set of airport ids
	 * @return
	 */
	public static Set<String> getAirportIds(){
		return idAirports;
	}
	
	public static void setAirplaneIds(Set<Airplane> airplanes, Set<Airports> airports) {
		idAirplanes.clear();
		for(Airplane a: airplanes)
			idAirplanes.add(a.getId());
		for(Airports a: airports) {
			for(Airplane ap: a.getAirplanes())
				idAirplanes.add(ap.getId());
		}
	}
	
	public static void setAirportIds(Set<Airports> airports) {
		idAirports.clear();
		for(Airports a: airports)
			idAirports.add(a.getId());
	}
	
	/**
	 * Check whether airport id is existed or not
	 * 
	 * @param airportId
	 * @return
	 * @throws Exception
	 */
	public static boolean isValidAirportId(String airportId, Set<Airports> airports) {
		for (Airports a : airports)
			if (a.getId().equalsIgnoreCase(airportId))
				return true;
		return false;

	}
	
	public static boolean isValidAirplaneId(Set<Airplane> airplanes,Set<Airports> airports, String id) {
		for(Airplane a: airplanes) {
			if(a.getId().equalsIgnoreCase(id)) {
				return true;
			}
		}
		for(Airports a : airports) {
			for(Airplane ap: a.getAirplanes()) {
				if(ap.getId().equalsIgnoreCase(id))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Check the min needed runway size of fixed wing airplanes
	 * 
	 * @param airport
	 * @param airplane
	 * @return
	 */
	public static boolean isFixedWingCondition(Airports airport, Airplane airplane) {
		if (airplane instanceof FixedWing) {
			if (((FixedWing) airplane).getMinNeededRunwaySize() < airport.getRunwaySize())
				return true;
		}
		return false;
	}
	
	
}
