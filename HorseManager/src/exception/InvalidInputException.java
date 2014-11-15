package exception;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
private String error;
	
	public InvalidInputException(String errMessage) {
		this.error = errMessage;
	}
	
	public String getError(){
		return error;
	}
}
