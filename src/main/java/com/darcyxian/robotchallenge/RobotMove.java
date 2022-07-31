package com.darcyxian.robotchallenge;

import com.darcyxian.robotchallenge.exceptions.FallOffTableException;
import com.darcyxian.robotchallenge.exceptions.IncompletePlaceException;
import com.darcyxian.robotchallenge.exceptions.WrongCommandException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Darcy Xian  24/7/22  7:03 pm      RobotChallenge
 */
public class RobotMove {
    Map<String, RobotState> result;
    RobotState activeRobotState;
    Long robotTotalNum =0L;
    Long activeRobotNum =0L;

    public void executeCommands(List<String> commands) {
        result = new HashMap<>();
        robotTotalNum = 0L;
        activeRobotNum = 0L;
        int index = commands.indexOf(Constant.PLACE);

        for (int i = index; i < commands.size(); i++) {
            switch (commands.get(i)) {
                case Constant.PLACE:
                    place(commands.get(i + 1));
                    i++;
                    break;
                case Constant.MOVE:
                    move();
                    break;
                case Constant.LEFT:
                    changeFacingLeft();
                    break;
                case Constant.RIGHT:
                    changeFacingRight();
                    break;
                case Constant.REPORT:
                    report();
                    break;
                case Constant.ROBOT:
                    activeExistingRobot(commands.get(i + 1));
                    i++;
                    break;
                default:
                    throw new WrongCommandException("WrongCommandException!  The commands above contains " + commands.get(i) + " can not be identified.");
            }
        }
    }

    public void activeExistingRobot(String robotNum) {
        saveCurrentRobotState();
        RobotState existedState = result.get(robotNum);
        if (existedState == null) {
            throw new WrongCommandException("WrongCommandException! robot " + robotNum + " does not exist");
        }
        activeRobotNum = Long.valueOf(robotNum);
        activeRobotState = existedState;
    }

    public void move() {

        switch (activeRobotState.getFacing()) {
            case "EAST":
                activeRobotState.setX(activeRobotState.getX() + 1);
                break;
            case "WEST":
                activeRobotState.setX(activeRobotState.getX() - 1);
                break;
            case "NORTH":
                activeRobotState.setY(activeRobotState.getY() + 1);
                break;
            case "SOUTH":
                activeRobotState.setY(activeRobotState.getY() - 1);
                break;
        }
        positionSafetyCheck(activeRobotState.getX(), activeRobotState.getY());
    }


    public void place(String position) {
        saveCurrentRobotState();
        activeRobotNum = ++robotTotalNum;
        activeRobotState = new RobotState();
        String[] positionArr = position.split(",");
        placeDetailCheck(positionArr);
        int x = Integer.valueOf(positionArr[0]);
        int y = Integer.valueOf(positionArr[1]);
        if (positionSafetyCheck(x, y)) {
            activeRobotState.setX(x);
            activeRobotState.setY(y);
            activeRobotState.setFacing(positionArr[2]);
        }
    }

    public void saveCurrentRobotState() {
        if (activeRobotNum != 0 && robotTotalNum != 0) {
            result.put(String.valueOf(activeRobotNum), activeRobotState);
        }
    }

    public void changeFacingLeft() {
        int directionIndex = Constant.directions.indexOf(activeRobotState.getFacing());
        if (directionIndex == 0) {
            directionIndex = 3;
        } else {
            directionIndex--;
        }
        changeFacing(directionIndex, activeRobotState);
    }

    public void changeFacingRight() {
        int directListIndex = Constant.directions.indexOf(activeRobotState.getFacing());
        if (directListIndex == 3) {
            directListIndex = 0;
        } else {
            directListIndex++;
        }
        changeFacing(directListIndex, activeRobotState);
    }

    public void changeFacing(int index, RobotState robotState) {
        String newDirection = Constant.directions.get(index);
        robotState.setFacing(newDirection);
    }

    public void report() {
        saveCurrentRobotState();
        int totalRobotNum = result.size();
        System.out.println("There are " + totalRobotNum + " robot(s).   ROBOT" + activeRobotNum + " is in active.");
        for (Map.Entry<String, RobotState> entry : result.entrySet()) {
            String key = entry.getKey();
            RobotState robotState = entry.getValue();
            System.out.println("Output: ROBOT" + key + " " + robotState.toStrings());
        }

    }

    public void placeDetailCheck(String[] placeDetail) {
        boolean legal = true;
        String message = "IncompletePlaceException! The commands above can not be executed, there is one PLACE command is not complete.";
        if (placeDetail.length != 3) {
            throw new IncompletePlaceException(message);
        }
        if (StringUtils.isBlank(placeDetail[0]) || !StringUtils.isNumeric(placeDetail[0])) {
            legal = false;
        }
        if (StringUtils.isBlank(placeDetail[1]) || !StringUtils.isNumeric(placeDetail[1])) {
            legal = false;
        }
        if (StringUtils.isBlank(placeDetail[2]) || !Constant.directions.contains(placeDetail[2])) {
            legal = false;
        }
        if (!legal) {
            throw new IncompletePlaceException(message);
        }
    }

    public boolean positionSafetyCheck(int x, int y) {
        if (x <= 4 && x >= 0 && y <= 4 && y >= 0) {
            return true;
        }
        throw new FallOffTableException("FallOffTableException! The commands above can not be executed because the Robot could fall off the table.");
    }


}