package Elevators;

import Exceptions.DisabledElevatorsException;
import Exceptions.NegativeValueException;
import Exceptions.NoElevatorsException;

import java.util.ArrayList;


public interface ElevatorSystem {
    ArrayList<Integer[]> status() throws NoElevatorsException;
    void step() throws NoElevatorsException;;
    void update(int iD, int currentFloor, int destinationFloor, boolean disabled) throws NoElevatorsException;
    void updateCurrentLoad(int iD, double currentLoad) throws NoElevatorsException, NegativeValueException;
    void pickup(int destinationFloor, Direction direction, int elevatorId);
    void manageCalls() throws NoElevatorsException, DisabledElevatorsException;
}

