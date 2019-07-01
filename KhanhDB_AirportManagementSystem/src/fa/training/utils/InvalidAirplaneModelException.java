package fa.training.utils;

public class InvalidAirplaneModelException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidAirplaneModelException() {
		super();
	}
	
	public InvalidAirplaneModelException(String message) {
		super(message);
	}
}
