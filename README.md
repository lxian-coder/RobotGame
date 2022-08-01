
#  Robot Game Sample application
## Tech Stack:
* Lombok
* Mockito
* Junit
* Java
* Gradle

## Topics
1. [How to run this application](#How-to-run-this-application)
2. [Test Data](#Test-Data)

## How to run this application

* Navigate the root folder /RobotChallenge under the command line
* Run the command to build the whole project: **gradle clean build**
* Either run the command to start the application: **java -jar ./build/libs/RobotChallenge-1.0-SNAPSHOT.jar**

## Test Data
### 1. test 1 robot situation 
```
test
useless
command
place 1,1,north
move
left
move
right
move
report
```
should output:
```
There are 1 robot(s).   ROBOT1 is in active.
Output: ROBOT1  0, 3, NORTH
```

### 2. test 2 robots situation
```
place 1,1,north
move
left
move
right
move
place 1,1,east
move
left
move
report
```
should output:
```
There are 2 robot(s).   ROBOT2 is in active.
Output: ROBOT1  0, 3, NORTH
Output: ROBOT2  2, 2, NORTH
```
### 3. test multiple robots situation
```
place 1,1,north
left
place 1,2,west
move
right
robot 1
move
place 1,1,east
move
left
robot 1
left
robot 2
report
```
should output:
```
There are 3 robot(s).   ROBOT2 is in active.
Output: ROBOT1  0, 1, SOUTH
Output: ROBOT2  0, 2, NORTH
Output: ROBOT3  2, 1, NORTH
```
### 4. test multiple robots fall off table situation
```
place 1,1,north
move
left
place 1,2,west
move
right
robot 1
move
place 1,1,east
move
left
robot 1
move
left
move
robot 2
report
```
should output:
```
FallOffTableException! The commands above can not be executed because the Robot could fall off the table.
```
