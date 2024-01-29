package lv.venta.services.exceptions;

public class PersonsNotFoundException extends Exception {
    
	private static final long serialVersionUID = -8115284616087689450L;

	public PersonsNotFoundException(String message) {
        super(message);
    }
}
