package com.darcyxian.robotchallenge.exceptions;

/**
 * Darcy Xian  29/7/22  8:54 am      RobotChallenge
 */
public class WrongCommandException extends RuntimeException{
    public WrongCommandException(String message){
        super(message);
    }
}
