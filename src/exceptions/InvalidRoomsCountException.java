package exceptions;

public class InvalidRoomsCountException extends IllegalArgumentException {
	String message;
	
	public InvalidRoomsCountException() { }
	public InvalidRoomsCountException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "Exception InvalidRoomsCountException: " + message;
	}
}
