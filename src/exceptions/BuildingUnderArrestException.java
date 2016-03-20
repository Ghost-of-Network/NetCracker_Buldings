package exceptions;

public class BuildingUnderArrestException extends Exception {
	String message;
	
	public BuildingUnderArrestException() {}
	public BuildingUnderArrestException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "Exception BuildingUnderArrestException: " + message;
	}
}
