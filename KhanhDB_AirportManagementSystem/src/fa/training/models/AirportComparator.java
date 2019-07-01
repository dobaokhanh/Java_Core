package fa.training.models;

import java.io.Serializable;
import java.util.Comparator;

public class AirportComparator implements Comparator<Airports>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Airports a0, Airports a1) {
		return a0.getId().compareToIgnoreCase(a1.getId());
	}

}
