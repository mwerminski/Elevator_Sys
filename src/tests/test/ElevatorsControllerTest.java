package test;


import Elevators.Direction;
import Exceptions.DisabledElevatorsException;
import Exceptions.NegativeValueException;
import Exceptions.NoElevatorsException;
import SystemControl.ElevatorsController;
import org.junit.jupiter.api.Test;


import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorsControllerTest {

    @Test
    void status_EmptyHashMap_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(0).status());
    }

    @Test
    void status_FiveElevators_Pass() throws NoElevatorsException {
        assertEquals(5, new ElevatorsController(5).status().size());
    }

    @Test
    void status_NegativeNum_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(-5).status());
    }


    @Test
    void step_EmptyHashMap_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(0).step());
    }

    @Test
    void step_FiveElevators_Pass() {
        new ElevatorsController(5);
    }

    @Test
    void step_NegativeNum_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(-5).status());
    }

    @Test
    void update_EmptyHashMap_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(0).update(0, 0, 0, false));
    }

    @Test
    void update_FiveElevators_Pass() throws NoElevatorsException {
        new ElevatorsController(5).update(4, 0, 0, false);
    }

    @Test
    void update_NegativeNum_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(-5).update(0, 0, 0, false));
    }

    @Test
    void update_WrongId_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(5).update(800, 0, 0, false));
    }


    @Test
    void updateCurrentLoad_NegativeValue_ThrowException() {
        assertThrows(NegativeValueException.class, () -> new ElevatorsController(5).updateCurrentLoad(0, -5));
    }

    @Test
    void updateCurrentLoad_WrongId_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(5).updateCurrentLoad(-3, 5));
    }

    @Test
    void updateCurrentLoad_FiveElevators_Pass() throws NegativeValueException, NoElevatorsException {
        new ElevatorsController(5).updateCurrentLoad(3, 5);
    }

    @Test
    void updateCurrentLoad_EmptyHashMap_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(0).updateCurrentLoad(1, 5));
    }

    @Test
    void pickup_FiveElevators_Pass() {
        new ElevatorsController(5).pickup(5, Direction.UP, 3);
    }

    @Test
    void manageCalls_FiveElevators_Pass() throws NoElevatorsException, DisabledElevatorsException {
        ElevatorsController control = new ElevatorsController(5);
        control.pickup(5, Direction.UP, 3);
        control.pickup(2, Direction.UP, 3);
        control.pickup(1, Direction.UP, 3);
        control.manageCalls();
    }

    @Test
    void manageCalls_NoPickups_Pass() throws NoElevatorsException, DisabledElevatorsException {
        ElevatorsController control = new ElevatorsController(5);
        control.manageCalls();
    }

    @Test
    void manageCalls_EmptyHashMap_ThrowException() {
        assertThrows(NoElevatorsException.class, () -> new ElevatorsController(0).manageCalls());
    }

    @Test
    void manageCalls_DisabledElevators_ThrowException() throws NoElevatorsException {
        ElevatorsController control = new ElevatorsController(2);
        control.update(0, 3, 3, true);
        control.update(1, 0, 0, true);
        control.pickup(5, Direction.UP, -1);
        control.pickup(6, Direction.UP, -1);

        assertThrows(DisabledElevatorsException.class, () -> control.manageCalls());
    }

}