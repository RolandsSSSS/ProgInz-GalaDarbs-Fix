package lv.venta.services.exceptions;

public class WrongIdException extends Exception {

	private static final long serialVersionUID = 4185692460281157116L;

	public WrongIdException(String message) {
		super(message);
	}
}
