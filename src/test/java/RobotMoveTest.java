import com.darcyxian.robotchallenge.RobotMove;
import com.darcyxian.robotchallenge.RobotState;
import com.darcyxian.robotchallenge.exceptions.FallOffTableException;
import com.darcyxian.robotchallenge.exceptions.IncompletePlaceException;
import com.darcyxian.robotchallenge.exceptions.WrongCommandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Darcy Xian  30/7/22  7:03 am      RobotChallenge
 */
@ExtendWith(MockitoExtension.class)
public class RobotMoveTest {
    @Mock
    RobotState activeRobotState;

    @Mock
    Map<String, RobotState> resultMap;

    @InjectMocks
    RobotMove robotMove;

    @Test
    void shouldThrowExceptionWhenXOrYOutOfRange() {
        int x1 = 5;
        int y1 = 5;
        int x2 = -1;
        int y2 = -1;
        assertThrows(FallOffTableException.class, () -> robotMove.positionSafetyCheck(x1, y1));
        assertThrows(FallOffTableException.class, () -> robotMove.positionSafetyCheck(x2, y2));
    }

    @Test
    void shouldReturnTrueWhenXAndYInRange() {
        int x1 = 0;
        int y1 = 0;
        int x2 = 4;
        int y2 = 4;
        boolean testRes = robotMove.positionSafetyCheck(x1, y1);
        boolean testRes2 = robotMove.positionSafetyCheck(x2, y2);
        assertEquals(true, testRes);
        assertEquals(true, testRes2);
    }

    @Test
    void shouldThrowIncompletePlaceExceptionWhenPlaceDetailIsNotLegal() {
        String[] testDetail = {"test", "test", "north"};
        String[] testDetail2 = {"1", "2"};
        String[] testDetail3 = {"1", "2", "test"};
        assertThrows(IncompletePlaceException.class, () -> robotMove.placeDetailCheck(testDetail));
        assertThrows(IncompletePlaceException.class, () -> robotMove.placeDetailCheck(testDetail2));
        assertThrows(IncompletePlaceException.class, () -> robotMove.placeDetailCheck(testDetail3));
    }

    @Test
    void shouldChangeFacingRight() {
        //Given
        RobotMove robotMove1 = spy(robotMove);
        doReturn("NORTH").when(activeRobotState).getFacing();
        doNothing().when(robotMove1).changeFacing(0, activeRobotState);
        //When
        robotMove1.changeFacingRight();
        //Then
        verify(activeRobotState, times(1)).getFacing();
        verify(robotMove1, times(1)).changeFacing(0, activeRobotState);
    }

    @Test
    void shouldChangeFacingLeft() {
        //Given
        RobotMove robotMove1 = spy(robotMove);
        doReturn("WEST").when(activeRobotState).getFacing();
        doNothing().when(robotMove1).changeFacing(1, activeRobotState);
        //When
        robotMove1.changeFacingLeft();
        //Then
        verify(activeRobotState, times(1)).getFacing();
        verify(robotMove1, times(1)).changeFacing(1, activeRobotState);
    }

    @Test
    void shouldChangeRobotStateFacingGivenIndex() {
        robotMove.changeFacing(1, activeRobotState);
        verify(activeRobotState, times(1)).setFacing("SOUTH");
    }

    @Test
    void shouldInitializePositionGivenPositionString() {
        //Given
        String position = "1,0,NORTH";
        String[] positionArr = {"1","0","NORTH"};
        RobotMove robotMove1 = spy(robotMove);
       doReturn(true).when(robotMove1).positionSafetyCheck(1,0);
       doNothing().when(robotMove1).saveCurrentRobotState();
       doNothing().when(robotMove1).placeDetailCheck(positionArr);
       //When
       robotMove1.place(position);
       //Then
        verify(robotMove1,times(1)).saveCurrentRobotState();
        verify(robotMove1,times(1)).placeDetailCheck(positionArr);
    }

    @Test
    void shouldActiveRobotGivenRobotNumber(){
        RobotState robotStateTest = new RobotState();
        robotStateTest.setX(1);
        robotStateTest.setY(0);
        robotStateTest.setFacing("EAST");
        //When
        RobotMove robotMove1 = spy(robotMove);
        doNothing().when(robotMove1).saveCurrentRobotState();
        doReturn(robotStateTest).when(resultMap).get("1");
        //Given
        robotMove1.activeExistingRobot("1");
        //Then
        verify(robotMove1,times(1)).saveCurrentRobotState();
        verify(resultMap,times(1)).get("1");
    }

    @Test
    void shouldThrowExceptionGivenNonExistedRobotNum(){
        RobotMove robotMove1 = spy(robotMove);
        doNothing().when(robotMove1).saveCurrentRobotState();
        doReturn(null).when(resultMap).get("1");

        assertThrows(WrongCommandException.class,()->robotMove1.activeExistingRobot("1"));
    }
   @Test
    void shouldChangeRobotStateGivenMoveOrder(){
        //Given
        RobotMove robotMove1 = spy(robotMove);
        doReturn("NORTH").when(activeRobotState).getFacing();
        doReturn(1).when(activeRobotState).getX();
        doReturn(2).when(activeRobotState).getY();
        doReturn(true).when(robotMove1).positionSafetyCheck(1,2);
        //When
        robotMove1.move();
        //Then
        verify(robotMove1,times(1)).positionSafetyCheck(1,2);
        verify(activeRobotState,times(1)).setY(3);
   }
   @Test
    void shouldExecutePlaceGivenPlaceCommand(){
        List<String> commands = new ArrayList<>();
        commands.add("PLACE");
        commands.add("1,1,NORTH");
        RobotMove robotMove1  = spy(robotMove);
        doNothing().when(robotMove1).place("1,1,NORTH");

        robotMove1.executeCommands(commands);

        verify(robotMove1,times(1)).place("1,1,NORTH");
   }
   @Test
    void shouldThrowExceptionGivenWrongCommand(){
        List<String> commands = new ArrayList<>();
        commands.add("PLACE");
        commands.add("1,1,NORTH");
        commands.add("TEST");
        RobotMove robotMove1 = spy(robotMove);
        doNothing().when(robotMove1).place("1,1,NORTH");

        assertThrows(WrongCommandException.class,()->robotMove1.executeCommands(commands));
    }


}
