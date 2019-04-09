package Elevators;

public class Call  {
    private int destinationFloor;
    private Direction direction;
    private int elevatorId;

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }
    public int getElevatorId() {
        return elevatorId;
    }

    public Call(int destinationFloor, Direction direction, int elevatorId){
        this.destinationFloor = destinationFloor;
        this.direction = direction;
        this.elevatorId = elevatorId;
    }


}
