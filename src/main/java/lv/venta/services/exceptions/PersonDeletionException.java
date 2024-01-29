package lv.venta.services.exceptions;

public class PersonDeletionException extends Exception {
    
	private static final long serialVersionUID = 8800622717686212952L;

	public PersonDeletionException(String message) {
        super(message);
    }
}
