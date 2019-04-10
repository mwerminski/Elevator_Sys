# Elevator System based on FCFS scheduling algorithm.
## Table of content
* [General info](#general-info)
* [Interface](#interface)
* [Elevator class](#elevator-class)
* [Simulations](#simulations)
* [Ideas / Plans for the future](#ideas)

## General info
In this project I used a basic library and Collections to create a simple simulation of n-elevators.
	
## Interface
The `ElevatorSystem` interface has five methods to simulate elevator system. `ElevatorController` implements this interface and override methods as shown below:
* `status()` - returns list (`ArrayList<Integer[]>`) that contains info about elevators (ID, current floor, destination floor),
* `step()` - step of simulation, execute moveOn() method of elevators,
* `update(int iD, int currentFloor, int desinationFloor, boolean disabled)` - sets position, destination, state and updates direction of specified elevator,
* `updateCurrentLoad(int iD, double currentLoad)` - set the current load of specified elevator,
* `pickup(int destinationFloor, Direction direction, int elevatorId)` - creates a call that invoke an elevator. If elevator ID is `-1` it means that the call comes from button placed on a floor. If We call an elevator to specified floor by pushing right button in a elevator cabin, the ID should be equal or greater than zero. 
* `manageCalls()` - the core of scheduling algorithm, assign calls from linked list `calls` to elevators in `elevatorsHashMap`. Initially, it is sorting some sublists in a list with the calls. The sorted sublists contains calls with non negative ID's with the same direction. It is for a situation where a few people gets into the elevator and pushes various buttons. The next step is assign calls to specified  elevators. If an elevator ID is `-1` it will assign call to the first elevator with minimal workload and shortest distance.
* `ElevatorController()` - constructor, fills the `elevatorsHashMap` with elevators

The fields of `ElevatorController`:
* `NUM_OF_ELEVATORS` - static variable which describes number of all elevators,
* `elevatorsHashMap` - hashmap with elevators; key = ID, value = elevator object,
* `calls` - linked list of picked up calls,.

## Elevator class
This class represents an elevator in my simulation. Important methods:
* `moveOn()` - imitate a movement of elevator and accepts next call from `calls`. At the beginning it checks the state and workload of an elevator. If the elevator is not in the target position, it will move in right direction. Otherwise it will acquire a new call and update the direction. 
* `Elevator()` - constructor.
* `compareTo()` - it is overridden method, we can compare elevator class objects by workload and disability.


The fields of Elevator:
* `id` - ID,
* `destinationFloor` - destination floor, current target of an elevator,
* `currentFloor` - current floor, position of an elevator,
* `currentLoad` - current load,
* `maintenanceStatus` - flag of elevator's disability,
* `direction` - object of enum class, describes the directions (`DOWN`, `NONE`, `UP`),
* `calls` - local linked list of calls,
* `MAX_LOAD` - static variable, which is used as the limit of load,
* `DEFAULT_FLOOR` - static variable, starting point of each elevator,
* `baseId` - static variable, it could be used to create unique IDs



	
## Simulations
To run this project, you should uncomment one of four simulation methods (in `Main.java`) or create the new one in `SystemSimulation.java`, biuld and compile.

### Example
`control.pickup(4, Direction.UP, -1)` - someone from the fourth floor is calling for the elevator and he wants to get higher. Direction depends on the button from the floor.

`control.pickup(5, Direction,UP, 1)` - somone in the elevator with ID = 1 pushed button with number "5". Direction depends on the difference in current and destination position.

`control.manageCalls()` - assign calls to elevators, first call will be assigned to elevator with minimal workload

`control.step()` - elevators acquire new calls

`control.step()` - elevators change the position



## Ideas / Plans for the future
We can optimize this scheduling algorithm by using priority queue and considering:
* load of the elevator(if load is about 95%, it should not stay to pick up new people)
* time(in the morning the elevators should set the highest priority to take people from top to bottom)
