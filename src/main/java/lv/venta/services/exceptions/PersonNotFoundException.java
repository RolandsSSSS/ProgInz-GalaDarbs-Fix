package lv.venta.services.exceptions;


public class PersonNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = -2555620505188084050L;

	public PersonNotFoundException(String message) {
        super(message);
    }
}
