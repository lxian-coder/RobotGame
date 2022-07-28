package com.darcyxian.robotchallenge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Darcy Xian  24/7/22  6:39 pm      RobotChallenge
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RobotState {
    private int x;
    private int y;
    private String facing;

    public String toStrings(){
       return " "+ x +", " + y + ", " + facing;
    }
}
