package com.darcyxian.robotchallenge;

import com.darcyxian.robotchallenge.exceptions.FallOffTableException;
import com.darcyxian.robotchallenge.exceptions.IncompletedPlaceException;
import com.darcyxian.robotchallenge.exceptions.WrongCommandException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Darcy Xian  24/7/22  7:03 pm      RobotChallenge
 */
public class RobotMove {

    public void executeCommands(List<String> commands, RobotState robotState) {
        int index = commands.indexOf(Constant.PLACE);
        for (int i = index; i < commands.size(); i++) {
            switch (commands.get(i)) {
                case Constant.PLACE:
                    place(commands.get(i + 1), robotState);
                    i++;
                    break;
                case Constant.MOVE:
                    move(robotState);
                    break;
                case Constant.LEFT:
                    changeFacingLeft(robotState);
                    break;
                case Constant.RIGHT:
                    changeFacingRight(robotState);
                    break;
                case Constant.REPORT:
                    report(robotState);
                    break;
                default:
                    throw new WrongCommandException("WrongCommandException!  The commands above contains " + commands.get(i) + " can not be identified.");
            }
        }
    }

    public void move(RobotState robotState) {

        switch (robotState.getFacing()) {
            case "EAST":
                robotState.setX(robotState.getX() + 1);
                break;
            case "WEST":
                robotState.setX(robotState.getX() - 1);
                break;
            case "NORTH":
                robotState.setY(robotState.getY() + 1);
                break;
            case "SOUTH":
                robotState.setY(robotState.getY() - 1);
                break;
        }
        positionSafetyCheck(robotState.getX(), robotState.getY());
    }

    public void place(String position, RobotState robotState) {
        String[] positionArr = position.split(",");
        placeDetailCheck(positionArr);
        int x = Integer.valueOf(positionArr[0]);
        int y = Integer.valueOf(positionArr[1]);
        if (positionSafetyCheck(x, y)) {
            robotState.setX(x);
            robotState.setY(y);
            robotState.setFacing(positionArr[2]);
        }
    }

    public void changeFacingLeft(RobotState robotState) {
        int directionIndex = Constant.directions.indexOf(robotState.getFacing());
        if (directionIndex == 0) {
            directionIndex = 3;
        } else {
            directionIndex--;
        }
        changeFacing(directionIndex, robotState);
    }

    public void changeFacingRight(RobotState robotState) {
        int directListIndex = Constant.directions.indexOf(robotState.getFacing());
        if (directListIndex == 3) {
            directListIndex = 0;
        } else {
            directListIndex++;
        }
        changeFacing(directListIndex, robotState);
    }

    public void changeFacing(int index, RobotState robotState) {
        String newDirection = Constant.directions.get(index);
        robotState.setFacing(newDirection);
    }

    public void report(RobotState robotState) {
        System.out.println("Output: " + robotState.toStrings());
    }

    public void placeDetailCheck(String[] placeDetail) {
        boolean legal = true;
        if (placeDetail.length != 3) {
            legal = false;
        }
        if (StringUtils.isBlank(placeDetail[0]) || !StringUtils.isNumeric(placeDetail[0])) {
            legal = false;
        }
        if (StringUtils.isBlank(placeDetail[1]) || !StringUtils.isNumeric(placeDetail[1])) {
            System.out.println("Sorry, PLACE Y is not correct , Please input again.");
            legal = false;
        }
        if (StringUtils.isBlank(placeDetail[2]) || !Constant.directions.contains(placeDetail[2])) {
            legal = false;
        }
        if (!legal) {
            throw new IncompletedPlaceException("IncompletedPlaceException! The commands above can not be executed, there is one PLACE command is not complete.");
        }
    }

    public boolean positionSafetyCheck(int x, int y) {
        if (x <= 4 && x >= 0 && y <= 4 && y >= 0) {
            return true;
        }
        throw new FallOffTableException("FallOffTableException! The commands above can not be executed because the Robot could fall off the table.");
    }


}