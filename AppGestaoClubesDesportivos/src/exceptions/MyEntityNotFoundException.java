package exceptions;

public class MyEntityNotFoundException extends Exception {

    public MyEntityNotFoundException() {
    }

    public MyEntityNotFoundException(String msg) {
        super(msg);
    }
}
