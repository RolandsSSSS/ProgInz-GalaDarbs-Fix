package lv.venta.services.exceptions;

public class DeletePersonException extends Exception {
    
	private static final long serialVersionUID = 7088403358610903126L;

	public DeletePersonException(String message, Throwable cause) {
        super(message, cause);
    }
}
