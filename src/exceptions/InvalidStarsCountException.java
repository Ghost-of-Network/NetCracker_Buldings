package exceptions;

public class InvalidStarsCountException extends IllegalArgumentException {
	String message;
	
	public InvalidStarsCountException() { }
	public InvalidStarsCountException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "Exception InvalidStarsCountException: " + message;
	}
}
