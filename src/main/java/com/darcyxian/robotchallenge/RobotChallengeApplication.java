package com.darcyxian.robotchallenge;

import java.util.List;

/**
 * Darcy Xian  24/7/22  6:16 pm      RobotChallenge
 */
public class RobotChallengeApplication {

    public static void main(String[] args) {

        UserInput userInput = new UserInput();
        RobotMove robotMove = new RobotMove();
        do {
            List<String> commandInput = userInput.getCommandInput();
            try {
                robotMove.executeCommands(commandInput);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
