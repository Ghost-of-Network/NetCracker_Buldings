package exceptions;

public class SpaceIndexOutOfBoundsException extends IndexOutOfBoundsException {
	String message;
	
	public SpaceIndexOutOfBoundsException() {}
	public SpaceIndexOutOfBoundsException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "Exception SpaceIndexOutOfBoundsException: " + message;
	}
}
