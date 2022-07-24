package com.darcyxian.robotchallenge;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Darcy Xian  24/7/22  9:30 pm      RobotChallenge
 */
public class UserInput {

    public List<String> getCommandInput() {
       Boolean checkInputError = false;
       List<String> rawList = new ArrayList<>();
       while (!checkInputError){
            rawList =  scanCommandsFromConsole();
            checkInputError = checkInputError(rawList);

       }
        System.out.println("!!!!!!!!!!!");
       return rawList;
    }

    public List<String> scanCommandsFromConsole(){
        System.out.println("Please Enter commands:");
        Scanner inputScan = new Scanner(System.in);
        StringBuilder inputString = new StringBuilder();
        int i = 0;
        while (true){
            i++;
            String line = inputScan.nextLine().toUpperCase();
            inputString.append(" ").append(line);
            if(line.length() == 0 && i != 1) break;
        }
        List<String> inputList = new ArrayList<>(Arrays.asList(inputString.toString().split(" ")));

        inputList.removeAll(Collections.singletonList(""));
        for(String a : inputList){
            System.out.println(a);;
        }
        return inputList;
    }

    public boolean checkInputError(List<String> list){
        if(!list.contains(String.valueOf(Commands.PLACE))){
            System.out.println("Sorry, commands input must contains an 'PLACE'. Please input again.");
            return false;
        }
           int index = list.indexOf("PLACE");

         if(list.size() == index+1){
             System.out.println("Sorry, there must be position details. Please input again.");
         return false;
         }
        String position = list.get(index+1);
        String[] positionArr = position.split(",");
        if(positionArr.length != 3){
            System.out.println("Sorry, PLACE command is not completed, Please input again.");
            return false;
        }
        if(StringUtils.isBlank(positionArr[0] ) || !StringUtils.isNumeric(positionArr[0])){
            System.out.println("Sorry, PLACE X is not correct , Please input again.");
         return false;
        }
        if(StringUtils.isBlank(positionArr[1]) || !StringUtils.isNumeric(positionArr[1])){
            System.out.println("Sorry, PLACE Y is not correct , Please input again.");
            return false;
        }
        if (StringUtils.isBlank(positionArr[2]) || !EnumUtils.isValidEnumIgnoreCase(Directions.class, positionArr[2])) {
            System.out.println("Sorry, PLACE facing is not correct , Please input again.");;
            return false;

        }
        return true;
    }

}