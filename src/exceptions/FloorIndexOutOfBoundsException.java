package exceptions;

public class FloorIndexOutOfBoundsException extends IndexOutOfBoundsException {
	String message;
	
	public FloorIndexOutOfBoundsException() {}
	public FloorIndexOutOfBoundsException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "Exception FloorIndexOutOfBoundsException: " + message;
	}
}
