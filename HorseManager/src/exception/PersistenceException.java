package exception;

/**
 * @author David Wietstruk 0706376
 *
 */
@SuppressWarnings("serial")
public class PersistenceException extends Exception {

	private String error;
	
	public PersistenceException(String errMessage) {
		this.error = errMessage;
	}
	
	public String getError(){
		return error;
	}
}
