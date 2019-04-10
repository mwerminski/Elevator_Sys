package SystemControl;


import Elevators.Call;
import Elevators.Direction;
import Elevators.Elevator;
import Elevators.ElevatorSystem;
import Exceptions.DisabledElevatorsException;
import Exceptions.NegativeValueException;
import Exceptions.NoElevatorsException;

import java.util.*;

public class ElevatorsController implements ElevatorSystem {
    private static int NUM_OF_ELEVATORS = 0;
    private HashMap<Integer, Elevator> elevatorsHashMap;
    private LinkedList<Call> calls = new LinkedList<>();

    public ElevatorsController() {
        NUM_OF_ELEVATORS = 5;
        elevatorsHashMap = new HashMap<>();
        for (int i = 0; i < NUM_OF_ELEVATORS; i++) {
            Elevator elevator = new Elevator(i);
            elevatorsHashMap.put(elevator.getId(), elevator);
        }
    }

    public ElevatorsController(int numOfElevators) {
        NUM_OF_ELEVATORS = numOfElevators;
        elevatorsHashMap = new HashMap<>();
        for (int i = 0; i < NUM_OF_ELEVATORS; i++) {
            Elevator elevator = new Elevator(i);
            elevatorsHashMap.put(elevator.getId(), elevator);
        }
    }

    @Override
    public ArrayList<Integer[]> status() throws NoElevatorsException {
        if (elevatorsHashMap.isEmpty()) {
            throw new NoElevatorsException();
        }
        ArrayList<Integer[]> elevatorsList = new ArrayList<>();
        for (Map.Entry<Integer, Elevator> elevator : elevatorsHashMap.entrySet()) {
            Integer[] elevatorStatus = {elevator.getValue().getId(), elevator.getValue().getCurrentFloor(),
                    elevator.getValue().getDestinationFloor()};
            elevatorsList.add(elevatorStatus);
        }
        return elevatorsList;
    }

    @Override
    public void step() throws NoElevatorsException {
        if (elevatorsHashMap.isEmpty()) {
            throw new NoElevatorsException();
        }
        for (Map.Entry<Integer, Elevator> elevator : elevatorsHashMap.entrySet()) {
            elevator.getValue().moveOn();
        }

    }

    @Override
    public void manageCalls() throws NoElevatorsException, DisabledElevatorsException {
        if (elevatorsHashMap.isEmpty()) {
            throw new NoElevatorsException();
        } else {
            Collections.sort(calls, (t, t1) -> {// sorting calls for specified elevator with the same direction
                if (((t.getElevatorId() == t1.getElevatorId()) && (t.getElevatorId() != -1))
                        && (t.getDirection() == t1.getDirection())) {
                    return (t.getDirection() == Direction.UP) ?
                            Integer.compare(t.getDestinationFloor(), t1.getDestinationFloor()) :
                            Integer.compare(t1.getDestinationFloor(), t.getDestinationFloor());

                } else return 0;
            });

            LinkedList<Call> copyCalls = new LinkedList<>(calls);

            for (Call call : copyCalls) {

                if ((!elevatorsHashMap.containsKey(call.getElevatorId())) && (call.getElevatorId() != -1)) {
                    System.err.println(String.format("Elevator %d was not found", call.getElevatorId()));
                    calls.removeFirstOccurrence(call); //checking if elevator exist, removing call if not exist

                } else if (call.getElevatorId() != -1) { //adding call to specified elevator

                    if (elevatorsHashMap.get(call.getElevatorId()).isMaintenance()) {
                        System.err.println(String.format("Elevator %d is disabled", call.getElevatorId()));
                        calls.removeFirstOccurrence(call);
                    } else elevatorsHashMap.get(call.getElevatorId()).addCall(calls.poll());

                } else { //adding call to non specified elevator
                    //elevator with lowest workload
                    int id = Collections.min(elevatorsHashMap.values(), (t, t1) -> {
                        int result = t.compareTo(t1); //comparing by  minimal workload
                        if (result == 0) { //if workloads are equal, compare by distance between elevator position and destination floor
                            return Integer.compare(Math.abs(t.getCurrentFloor() - call.getDestinationFloor()),
                                    Math.abs(t1.getCurrentFloor() - call.getDestinationFloor()));
                        }
                        return result;
                    }).getId();
                    //if elevator with minimal workload is disabled it means that there is no working elevators
                    if (elevatorsHashMap.get(id).isMaintenance()) throw new DisabledElevatorsException();
                    else elevatorsHashMap.get(id).addCall(calls.poll());

                }
            }
        }
    }


    @Override
    public void update(int iD, int currentFloor, int destinationFloor, boolean disabled) throws NoElevatorsException {
        if (elevatorsHashMap.containsKey(iD)) {
            elevatorsHashMap.get(iD).setCurrentFloor(currentFloor);
            elevatorsHashMap.get(iD).setDestinationFloor(destinationFloor);
            elevatorsHashMap.get(iD).updateDirection();
            elevatorsHashMap.get(iD).setDisabled(disabled);

        } else throw new NoElevatorsException(iD);
    }

    @Override
    public void updateCurrentLoad(int iD, double currentLoad) throws NoElevatorsException, NegativeValueException {
        if (currentLoad < 0) throw new NegativeValueException();
        else if (elevatorsHashMap.containsKey(iD)) elevatorsHashMap.get(iD).setCurrentLoad(currentLoad);
        else throw new NoElevatorsException(iD);
    }

    @Override
    public void pickup(int destinationFloor, Direction direction, int elevatorId) {
        calls.add(new Call(destinationFloor, direction, elevatorId));

    }


}
