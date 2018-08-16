package ua.epam.spring.hometask.exception;

public class BookingException extends ValidationException {

    private static final long serialVersionUID = 1L;

	public BookingException() {
    }

    public BookingException(String message) {
        super(message);
    }
}
