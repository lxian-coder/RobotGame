package com.darcyxian.robotchallenge;

import com.darcyxian.robotchallenge.exceptions.FallOffTableException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Darcy Xian  24/7/22  6:16 pm      RobotChallenge
 */
@Slf4j
public class RobotChallengeApplication {

    public static void main(String[] args) {

        UserInput userInput = new UserInput();
        RobotMove robotMove = new RobotMove();
        while(true){
            List<String> commandInput = userInput.getCommandInput();
            try{
                robotMove.executeCommands(commandInput);
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
