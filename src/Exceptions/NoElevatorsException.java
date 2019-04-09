package Exceptions;

public class NoElevatorsException extends Exception{
    public NoElevatorsException(int elevatorId) {
        super(String.format("Elevator %d was not found", elevatorId));
    }
    public NoElevatorsException() {
        super("There is no any elevator");
    }
}
