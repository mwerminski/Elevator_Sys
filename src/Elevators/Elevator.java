package Elevators;

import java.util.LinkedList;

public class Elevator implements Comparable<Elevator>{
    static private int baseId = 0;
    private int id;
    private int destinationFloor;
    private int currentFloor;

    public void setCurrentLoad(double currentLoad) {
        this.currentLoad = currentLoad;
    }

    private double currentLoad;
    private boolean maintenanceStatus;
    private Direction direction;
    private LinkedList<Call> calls;
    private static final double MAX_LOAD = 600;
    private static final int DEFAULT_FLOOR = 0;



    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Elevator(){
        id = baseId;
        baseId++;
        destinationFloor = DEFAULT_FLOOR;
        currentFloor = DEFAULT_FLOOR;
        currentLoad = 0;
        maintenanceStatus = false;
        direction = Direction.NONE;
        calls = new LinkedList<>();
    }

    public Elevator(int iD){
        id = iD;
        destinationFloor = DEFAULT_FLOOR;
        currentFloor = DEFAULT_FLOOR;
        currentLoad = 0;
        maintenanceStatus = false;
        direction = Direction.NONE;
        calls = new LinkedList<>();
    }


    public void moveOn() {
        if ((!maintenanceStatus) && (currentLoad <= MAX_LOAD)) { //checking availability
            if (destinationFloor != currentFloor) {
                currentFloor = (direction == Direction.UP) ? currentFloor + 1 : currentFloor - 1;
            } else {
                if (!calls.isEmpty()) { //taking next call
                    Call call = calls.poll();

                    destinationFloor = call.getDestinationFloor();
                    direction = (currentFloor < destinationFloor) ? Direction.UP : Direction.DOWN;
                } else direction = Direction.NONE;
            }

        } else if (maintenanceStatus) {
            System.err.println(String.format("Elevator %d is disabled and cannot move", id));
        } else if (currentLoad > MAX_LOAD) {
            System.err.println(String.format("Elevator %d is overloaded and cannot move", id));

        }
    }
    public boolean isMaintenance(){
        return maintenanceStatus;
    }

    public Direction getDirection(){
        return direction;
    }
    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getId() {
        return id;
    }
    public void setDisabled(boolean option){
        maintenanceStatus = option;
    }
    public void updateDirection(){
        if(currentFloor != destinationFloor) {
            direction = (currentFloor < destinationFloor) ? Direction.UP: Direction.DOWN;
        }
        else direction = Direction.NONE;
    }
    public void addCall(Call call){
        calls.add(call);
    }

    @Override
    public int compareTo(Elevator other) {
        if((this.isMaintenance() == other.isMaintenance())) {
            if (((this.direction == Direction.NONE) || (other.direction == Direction.NONE)) &&
                    (this.direction != other.direction))
                return other.direction.compareTo(this.direction);
            else return Integer.compare(this.calls.size(), other.calls.size());
        }
        else return (this.isMaintenance()) ? 1 : -1;


    }
}

