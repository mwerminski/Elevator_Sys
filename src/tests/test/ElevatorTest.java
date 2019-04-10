package test;

import Elevators.Direction;
import Elevators.Elevator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {


    @Test
    void moveOn_DestinationFloorHigher_Move() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(5);
        elevator.setDestinationFloor(7);
        elevator.updateDirection();
        elevator.moveOn();
        assertEquals(6, elevator.getCurrentFloor());
    }

    @Test
    void moveOn_DestinationFloorLower_Move() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(7);
        elevator.setDestinationFloor(5);
        elevator.updateDirection();
        elevator.moveOn();
        assertEquals(6, elevator.getCurrentFloor());
    }

    @Test
    void moveOn_DestinationFloorEqualsCurrent_DontMove() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(5);
        elevator.setDestinationFloor(5);
        elevator.updateDirection();
        elevator.moveOn();
        assertEquals(5, elevator.getCurrentFloor());
    }

    @Test
    void moveOn_OverLoad_DoNothing() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(5);
        elevator.setDestinationFloor(7);
        elevator.setCurrentLoad(700);
        elevator.moveOn();

        assertEquals(5, elevator.getCurrentFloor());
    }

    @Test
    void moveOn_IsMaintenance_DoNothing() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(5);
        elevator.setDestinationFloor(7);
        elevator.setCurrentLoad(300);
        elevator.setDisabled(true);
        elevator.moveOn();

        assertEquals(5, elevator.getCurrentFloor());
    }


    @Test
    void getId_SetID_ReturnCorrectID() {
        assertEquals(5,new Elevator(5).getId());
    }

    @Test
    void setDisabled_SetTrue_Check() {
        Elevator elevator = new Elevator(3);
        elevator.setDisabled(true);
        assertTrue(elevator.isMaintenance());
    }

    @Test
    void isMaintenance_DefaultValue_False() {
        assertFalse(new Elevator(3).isMaintenance());
    }

    @Test
    void updateDirection_DestinationFloorHigher_SetUP() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(1);
        elevator.setDestinationFloor(3);
        elevator.updateDirection();
        assertEquals(Direction.UP,elevator.getDirection());
    }

    @Test
    void updateDirection_DestinationFloorLower_SetDOWN() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(8);
        elevator.setDestinationFloor(3);
        elevator.updateDirection();
        assertEquals(Direction.DOWN,elevator.getDirection());
    }

    @Test
    void updateDirection_DestinationFloorEqualsCurrent_SetNONE() {
        Elevator elevator = new Elevator();
        elevator.setCurrentFloor(3);
        elevator.setDestinationFloor(3);
        assertEquals(Direction.NONE,elevator.getDirection());
    }

}