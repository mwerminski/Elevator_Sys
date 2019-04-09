package Exceptions;

public class DisabledElevatorsException  extends Exception{
    public DisabledElevatorsException() {
        super("There is no working elevator");
    }
}
