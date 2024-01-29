package lv.venta.services.exceptions;

public class PersonUpdateException extends Exception {
   
	private static final long serialVersionUID = -588561371237190346L;

	public PersonUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
