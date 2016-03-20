package exceptions;

public class InvalidSpaceAreaException extends IllegalArgumentException {
	String message;
	
	public InvalidSpaceAreaException() { }
	public InvalidSpaceAreaException(String message) {
		this.message = message;
	}
	
        @Override
	public String toString() {
		return "Exception InvalidSpaceAreaException: " + message;
	}
}
