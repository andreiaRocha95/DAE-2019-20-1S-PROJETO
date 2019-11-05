package exceptions;

public class MyEntityExistsException extends Exception {

    public MyEntityExistsException() {
    }

    public MyEntityExistsException(String msg) {
        super(msg);
    }
}
