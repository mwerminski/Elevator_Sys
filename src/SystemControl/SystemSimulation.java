package SystemControl;

import Elevators.Direction;
import Elevators.ElevatorSystem;
import Exceptions.DisabledElevatorsException;
import Exceptions.NegativeValueException;
import Exceptions.NoElevatorsException;

public class SystemSimulation {
    private ElevatorSystem control;

    public SystemSimulation(){
        control = new ElevatorsController();
    }
    public SystemSimulation(ElevatorSystem control){
        this.control = control;
    }
    public SystemSimulation(int numOfElevators){
        control = new ElevatorsController(numOfElevators);
    }


    public void Simulation1(){

        try {
            control.pickup(17, Direction.UP, -1);
            control.manageCalls();
            step(2);
            control.pickup(  7,Direction.UP, -1);
            control.manageCalls();
            step(7);
            control.pickup(  8,Direction.UP, -1);
            control.manageCalls();
            control.step();
            control.pickup(20, Direction.UP, 0);
            step(20);
            print();
        }
        catch (NoElevatorsException | DisabledElevatorsException e){
            System.err.println(e.getMessage());
        }

    }
    public void Simulation2(){
        try {
            control.update(7,20,20, false);
            control.pickup(5, Direction.UP, -1);
            control.pickup(  7,Direction.DOWN, 7);
            control.pickup(  6,Direction.DOWN, 7);
            control.pickup(  4,Direction.DOWN, 7);
            control.pickup(  5,Direction.DOWN, 7);
            control.manageCalls();

            control.step();
            control.updateCurrentLoad(7, 500);
            step(13);
            print();
            control.updateCurrentLoad( 7, 450);
            step(3);
            print();
            control.updateCurrentLoad( 7, 350);
            step(3);
            print();
            control.updateCurrentLoad(7, 70);
            step(2);
            print();
            control.updateCurrentLoad(7, 0);
            control.step();
            print();

        }
        catch (NoElevatorsException | DisabledElevatorsException | NegativeValueException e){
            System.err.println(e.getMessage());
        }


    }
    public void Simulation3(){
        try {
            control.update(0, 0, 0, true);
            control.pickup(5,Direction.UP, -1);
            control.manageCalls();
            step(2);
            print();
        }
        catch (NoElevatorsException | DisabledElevatorsException e){
            System.err.println(e.getMessage());
        }

    }
    public void Simulation4(){
        try {
            control.update(0,0,0,true);
            control.update(1,2,2, false);
            control.pickup(5, Direction.UP, 1);
            control.pickup(  7,Direction.UP, 1);
            control.pickup(  6,Direction.UP, 1);
            control.pickup( 20,Direction.UP, -1);
            control.pickup(  4,Direction.UP, 1);
            control.pickup(  5,Direction.UP, 1);
            control.pickup(  4,Direction.UP, 1);
            control.pickup(  16,Direction.DOWN, 1);
            control.pickup(  15,Direction.DOWN, 1);
            control.pickup(  13,Direction.DOWN, 1);
            control.manageCalls();
            print();
        }
        catch (NoElevatorsException | DisabledElevatorsException e ){
            System.err.println(e.getMessage());
        }



    }
    private void print() throws NoElevatorsException{
        System.out.println("***************************************");
        for( Integer[] list : control.status()){
            System.out.println( list[0] + " " + list[1]+ " " +list[2]);
        };
    }

    private void step(int numOfSteps) throws NoElevatorsException{
        for (int i = 0; i < numOfSteps; i++) {
            control.step();
        }
    }
}
